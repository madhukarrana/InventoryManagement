package veloctiy.inventory.management.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static GenericResponse createSuccessResponse(String message){
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setStatus("SUCCESS");
        genericResponse.setMessage(message);
        return genericResponse;
    }

    public static GenericResponse createFailureResponse(String message){
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setStatus("FAILED");
        genericResponse.setMessage(message);
        return genericResponse;
    }

}
