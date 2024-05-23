package veloctiy.inventory.management.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteProductRequest {
    @JsonProperty("product_id")
    private String productId;

    public String getProductId(){
        return this.productId;
    }
}
