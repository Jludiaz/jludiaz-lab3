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

        //while loop to find patterns
        while(matcher.find()){
            //define the word 
            String word = matcher.group();

            //IF STOPWORD ENCOUNTERED
            if (stopword.equals(word)){
                stopwordInText = true;
                break;
            }
            wordCount++;//increment wordCount each time word is found
        }

        //IF STOPWORD IS NOT IN TEXT
        if(stopwordInText == false){
            throw new InvalidStopwordException();
        }
        //if wordCount is lower than five
        if (wordCount < 5){
            throw new TooSmallText();
        }


        // return word count, unless the count is less than five
        return wordCount;
    }

    public static StringBuffer processFile(String path) throws EmptyFileException{
        //convert contents of file to StringBuffer
        boolean isEmpty = true;
        StringBuffer fileContents = new StringBuffer();

        while(isEmpty == true){
        
            try (BufferedReader fr = new BufferedReader(new FileReader (path))){
                String line = new String();
                while((line = fr.readLine()) != null){
                    fileContents.append(fr).append("\n");
                    isEmpty = false;
                }
                //if the file is empty raise an EmptyFileException that contains the file's path in its message
                if(isEmpty == true){
                    throw new EmptyFileException("The file " + path + " is empty");
                }

            //if file cannot be opened, prompt the user to re-enter filename until 
            //they enter a file that can be opened
            }catch(Exception e){
                // File could not be opened; prompt the user to re-enter filename
                System.out.println("Please enter a new filename: ");
                Scanner scanner = new Scanner(System.in);
                path = scanner.nextLine(); // Update path with new filename entered by user
            }

            break;
        }


        return fileContents;
    }

    public static void main (String[] args){
        //asks the user to choose to process a file with option 1
        // or text with option 2
        Scanner scanner = new Scanner(System.in);
        int option = 0;


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
    
            StringBuffer text = null;

            //EMPTY FILE EXCEPTION
            try {
                text = processFile(path);
            } catch (EmptyFileException e) {
                System.out.println("Empty file exception");
                text = new StringBuffer("");
            }

            //ASK FOR STOPWORD IN FILE



        }else if (option == 2){
            //Enter Text
            System.out.println("Enter text: ");

            String text = scanner.nextLine();
            StringBuffer textStringBuffer = new StringBuffer(text);//CONVERT to STOPWORD

            System.out.println("Enter stopword: "); //ASK USER TO ENTER STOPWORD
            String stopword = scanner.nextLine(); //READ THE OBJ

            while(true){
                try {
                    System.out.println(processText(textStringBuffer, stopword));
                    break;
                } catch (InvalidStopwordException e) {
                    System.out.println("Stopword not found. Please enter a new stopword:");
                    stopword = scanner.nextLine();
                } catch (TooSmallText e) {
                    e.printStackTrace();
                }
            }
        }
        //If the user enters an invalid option, allow them to choose again until they have a correct option
        //Both of these items will be abailable as first command line argument
        //it process the text, and outputs the number of words it counted
        // if file is empty, method will display the message of the exception raised (which includes path of file)
        //NOTE: The path of the empty file may not be the same path that was specified in the command line 
        // if the stopword is not in text, allow the user ONE chance to re-specify the stopword and process text again
        // If they enter another stopword that cant be found, report to user
    }
}
