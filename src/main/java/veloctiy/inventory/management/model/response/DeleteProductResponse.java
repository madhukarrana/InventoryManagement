package veloctiy.inventory.management.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteProductResponse {
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

    public static DeleteProductResponse createSuccessResponse(String message){
        DeleteProductResponse deleteProductResponse = new DeleteProductResponse();
        deleteProductResponse.setStatus("SUCCESS");
        deleteProductResponse.setMessage(message);
        return deleteProductResponse;
    }

    public static DeleteProductResponse createFailureResponse(String message){
        DeleteProductResponse deleteProductResponse = new DeleteProductResponse();
        deleteProductResponse.setStatus("FAILED");
        deleteProductResponse.setMessage(message);
        return deleteProductResponse;
    }
}
