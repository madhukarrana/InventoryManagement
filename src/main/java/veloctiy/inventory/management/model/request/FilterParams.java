package veloctiy.inventory.management.model.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterParams {
    @JsonProperty("supplier_id")
    private String supplierId;
    @JsonProperty("price_range")
    private PriceRange priceRange;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PriceRange{
        @JsonProperty("start")
        private double start;
        @JsonProperty("end")
        private double end;
    }
}
