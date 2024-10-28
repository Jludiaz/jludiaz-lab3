public class WordCounter {
        
    public static int processText(StringBuffer text, String stopword){
        //Count the number of words in text through the stopword
        //If stopword is not in text, raise InvalidStopwordException
        // return word count, unless the count is less than five
        // if less then five, raise TooSmallText exception

        //Method should use regular expressions to define what a word is
        // We'll limit words to only alphanumeric charatic with single quotes (a-z, A-Z, 0-9, and ')
        // for a word to be categorized as word it must be at least one character long
        // the code below allows you to specify a regular expression, and then use it to keep searching for the next word 
    }

    public StringBuffer processFile(String path){
        //convert contents of file to StringBuffer
        //if file cannot be opened, prompt the user to re-enter filename until tjey enter a file that can be opened
        //if the file is empty raise an EmptyFileException that contains the file's path in its message
        //
    }

    public static void main (String[] args){
        //asks the user to choose to process a file with option 1
        // or text with option 2
        //If the user enters an invalid option, allow them to choose again until they have a correct option
        //Both of these items will be abailable as first command line argument
        //it process the text, and outputs the number of words it counted
        // if file is empty, method will display the message of the exception raised (which includes path of file)
        //NOTE: The path of the empty file may not be the same path that was specified in the command line 
        // if the stopword is not in text, allow the user ONE chance to re-specify the stopword and process text again
        // If they enter another stopword that cant be found, report to user
    }
}
