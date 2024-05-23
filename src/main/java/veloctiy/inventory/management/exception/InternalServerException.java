package veloctiy.inventory.management.exception;

public class InternalServerException extends Exception {

    private String message;

    public InternalServerException(String message){
        super(message);
    }
}
