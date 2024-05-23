package veloctiy.inventory.management.entity;

import java.sql.Timestamp;

public class ProductImage {
    private Long id;
    private String imageId;
    private String productId;
    private String imageUrl;
    private Boolean isDeleted;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
