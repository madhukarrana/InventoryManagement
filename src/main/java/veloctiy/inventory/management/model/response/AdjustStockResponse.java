package veloctiy.inventory.management.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdjustStockResponse {
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

    public static AdjustStockResponse createSuccessResponse(String message){
        AdjustStockResponse adjustStockResponse = new AdjustStockResponse();
        adjustStockResponse.setStatus("SUCCESS");
        adjustStockResponse.setMessage(message);
        return adjustStockResponse;
    }

    public static AdjustStockResponse createFailureResponse(String message){
        AdjustStockResponse adjustStockResponse = new AdjustStockResponse();
        adjustStockResponse.setStatus("FAILED");
        adjustStockResponse.setMessage(message);
        return adjustStockResponse;
    }
}
