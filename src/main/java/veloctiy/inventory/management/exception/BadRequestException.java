package veloctiy.inventory.management.exception;

public class BadRequestException extends Exception {

    private String message;

    public BadRequestException(String message){
        super(message);
    }
}
