package veloctiy.inventory.management.exception;

public enum Errors {

    INVALID_REQUEST("ERROR-000", "request is not valid");

    private String code;
    private String message;

    Errors(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }
}
