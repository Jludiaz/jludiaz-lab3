import java.io.IOError;
import java.io.IOException;

public class EmptyFileException extends IOException {
    //Exception of this kind should be raised when the contents of the file to be parsed are empty
    public EmptyFileException(){}

    public EmptyFileException(String message){
        super (message);
    }
}
