package veloctiy.inventory.management.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateProductResponse {
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

    public static UpdateProductResponse createSuccessResponse(String message){
        UpdateProductResponse updateProductResponse = new UpdateProductResponse();
        updateProductResponse.setStatus("SUCCESS");
        updateProductResponse.setMessage(message);
        return updateProductResponse;
    }

    public static UpdateProductResponse createFailureResponse(String message){
        UpdateProductResponse updateProductResponse = new UpdateProductResponse();
        updateProductResponse.setStatus("FAILED");
        updateProductResponse.setMessage(message);
        return updateProductResponse;
    }
}
