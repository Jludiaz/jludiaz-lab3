import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WordCounter {
        
    public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException,  TooSmallText{
        //Count the number of words in text through the stopword
        int wordCount = 0;

        //Method should use regular expressions to define what a word is
        // We'll limit words to only alphanumeric charatic with single quotes (a-z, A-Z, 0-9, and ')
        Pattern wordPattern = Pattern.compile("[a-zA-Z0-9']+");
        Matcher matcher = wordPattern.matcher(text);

        //indicated if the current stopword is in the text
        boolean stopwordInText = false;

        //IF STOPWORD IS NULL
        if (stopword == null){
            while(matcher.find()){
                wordCount++;
                System.out.println(matcher.group() + " " + wordCount);
            }

            if (wordCount < 5){
                throw new TooSmallText("TooSmallText: Only found " + wordCount + " words.");
            }
            return wordCount;
        }

        //while loop to find patterns
        while(matcher.find()){
            //define the word 
            String word = matcher.group();
            wordCount++;//increment wordCount each time word is found

            //IF STOPWORD ENCOUNTERED 
            if (stopword.equals(word)){
                stopwordInText = true;
            }

            //IF STOPWORD IN TEXT and WORD COUNT < 5 and 
            if((stopwordInText) && (wordCount >= 5)){
                break;
            }
        }

        //IF TOO SMALL TEXT
        if (wordCount < 5){
            throw new TooSmallText("Only found " + wordCount + " words.");
        }

        //IF STOPWORD IS NOT IN TEXT
        if(stopwordInText == false){
            //System.out.println("Hello World");
            throw new InvalidStopwordException("Couldn't find stopword: " + stopword);
        }

        // return word count, unless the count is less than five
        return wordCount;
    }

    public static StringBuffer processFile(String path) throws EmptyFileException {
        StringBuffer fileContents = new StringBuffer();
        boolean isEmpty = true;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.append(line);
                isEmpty = false; // Set to false once any content is read
            }
            
            // If no content was read, throw EmptyFileException
            if (isEmpty) {
                throw new EmptyFileException(path + " was empty");
            }
            
        } catch (EmptyFileException e) {
            throw e; // Rethrow the exception to signal the file is empty
        } catch (Exception e) {
            System.out.println("Please enter a new filename: ");
            Scanner scanner = new Scanner(System.in);
            path = scanner.nextLine(); // Update path with new filename entered by user
            return processFile(path); // Retry with the new path
        }

        return fileContents;
    }

    public static void main (String[] args){

        /*
         * Check if no arguments are provided, if so then
                a. Use STDIN
            If one argument is provided, and it starts (and end) with double quotes then
                a. Use the sentence provided (without the quotes)
            If args not in quotes:
                a. try opening the file
                b. check if stopword given
                c. process the file
         */
        //asks the user to choose to process a file with option 1
        // or text with option 2
        //input: 
        //1. java WordCounter valid.txt green
        //2. java WordCounter valid.txt 

        //IF NO ARGUMENTS ARE PROVIDED
        if (args.length == 0){
            noArgumentsProvided();
        }

        //IF DOUBLE QUOTES IS SPECIFIED
        if (args.length == 1 && args[0].startsWith("\"") && args[0].endsWith("\"")){
            doubleQuotes(args);
        }

        //IF ARGUMENTS ARE PROVIDED
        if (args.length > 0 && args[0] != null){
            ArgumentsProvided(args);
        }

    }
    
    public static void noArgumentsProvided(){

        int option = 0;
        Scanner scanner = new Scanner(System.in);

        //Choose option from 1 or 2
        while (option != 1 && option != 2) {

            System.out.println("Please enter 1 if you'd like to read from a file, or 2 if you'd like to read a text:");

            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine(); // consume newline
            } else {
                System.out.println("Invalid input. Please enter a valid option (1 or 2).");
                scanner.nextLine(); // consume invalid input
            }
        }
        
        //OPTION 1
        if (option == 1){
            String path = null;

            //ASK USER TO ENTER FILENAME
            while (true) {
                System.out.println("Enter the filename path:");
                try {
                    path = scanner.nextLine();
                    break;
                } catch (Exception e) {
                    System.out.println("Error, invalid input");
                    scanner.next();
                }
            }
    
            //NOW TO CONVERT TO STRINGBUFFER AND ASK FOR STOPWORD
            StringBuffer text = null;

            //EMPTY FILE EXCEPTION
            try {
                text = processFile(path);
            } catch (EmptyFileException e) {
                e.printStackTrace();
                text = new StringBuffer("");
            }

            //ASK FOR STOPWORD IN FILE
            System.out.println("Enter stopword: "); //ASK USER TO ENTER STOPWORD
            String stopword = scanner.nextLine(); //READ THE OBJ

            int UserStopWordCount = 0;
            while(UserStopWordCount <= 1){
                try {
                    System.out.println(processText(text, stopword));
                    break;
                } catch (InvalidStopwordException e) {
                    e.printStackTrace();
                    System.out.println("Stopword not found. Please enter a new stopword:");
                    stopword = scanner.nextLine();
                    UserStopWordCount++;
                } catch (TooSmallText e) {
                    e.printStackTrace();
                    break;
                }
            }

            System.out.println(stopword);


        }else if (option == 2){
            //Enter Text
            System.out.println("Enter text: ");

            String text = scanner.nextLine();
            StringBuffer textStringBuffer = new StringBuffer(text);//CONVERT to text

            System.out.println("Enter stopword: "); //ASK USER TO ENTER STOPWORD
            String stopword = scanner.nextLine(); //READ THE OBJ

            int stopwordCount = 0;
            int processCount = 0;
            while(stopwordCount <= 1){
                try {
                    processCount = processText(textStringBuffer, stopword);
                    System.out.println(processCount);
                    break;
                } catch (InvalidStopwordException e) {
                    System.out.println("Stopword not found. Please enter a new stopword:");
                    stopword = scanner.nextLine();
                    stopwordCount++;
                } catch (TooSmallText e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        scanner.close();
    }

    public static void ArgumentsProvided(String[] Args){

        Scanner scanner = new Scanner(System.in);
        //NOW TO CONVERT TO STRINGBUFFER AND ASK FOR STOPWORD
        StringBuffer path = new StringBuffer(Args[0]);
        String stringPath = path.toString();

        //OPEN FILE WITH EMPTY FILE EXCEPTION CATCH
        try {
            path = processFile(stringPath);
        } catch (EmptyFileException e) {
            e.printStackTrace();
            path = new StringBuffer("");
        }

        //CHECK IF STOPWORD ARGUMENT IS NOT NULL
        String stopword = null;
        if (Args.length == 2) {
            stopword = Args[1];  // Second argument, optional
        }

        int UserStopWordCount = 0;
        int processTextWordCount= 0;
        while(UserStopWordCount <= 1){
            try {
                processTextWordCount = processText(path, stopword);
                System.out.println("Found " + processTextWordCount + " words.");
                break;
            } catch (InvalidStopwordException e) {
                e.printStackTrace();
                System.out.println("Stopword not found. Please enter a new stopword:");
                stopword = scanner.nextLine();
                UserStopWordCount++;
            } catch (TooSmallText e) {
                System.out.println(e.getMessage());
                break;
            }
        }

        scanner.close();
    }

    public static void doubleQuotes(String[] Args){
        String text = Args[0].substring(1, Args[0].length() - 1);
        StringBuffer textBuffer = new StringBuffer(text);
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter stopword: ");
        String stopword = scanner.nextLine();
        
        try {
            int wordCount = processText(textBuffer, stopword);
            System.out.println(wordCount);
        } catch (InvalidStopwordException e) {
            System.out.println("Stopword not found. " + e.getMessage());
        } catch (TooSmallText e) {
            System.out.println("TooSmallText. " + e.getMessage());
        }
        
        scanner.close();
        return;
    }

}
