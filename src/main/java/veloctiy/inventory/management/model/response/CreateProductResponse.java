package veloctiy.inventory.management.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateProductResponse {
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

    public static CreateProductResponse createSuccessResponse(String message){
        CreateProductResponse createProductResponse = new CreateProductResponse();
        createProductResponse.setStatus("SUCCESS");
        createProductResponse.setMessage(message);
        return createProductResponse;
    }

    public static CreateProductResponse createFailureResponse(String message){
        CreateProductResponse createProductResponse = new CreateProductResponse();
        createProductResponse.setStatus("FAILED");
        createProductResponse.setMessage(message);
        return createProductResponse;
    }
}
