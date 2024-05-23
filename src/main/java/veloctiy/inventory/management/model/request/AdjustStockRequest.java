package veloctiy.inventory.management.model.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdjustStockRequest {
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("adjust_quantity")
    private Integer adjustQuantity;

    public String getProductId() {
        return productId;
    }

    public Integer getAdjustQuantity() {
        return adjustQuantity;
    }
}
