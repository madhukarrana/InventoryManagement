package veloctiy.inventory.management.database;

import org.springframework.jdbc.core.RowMapper;
import veloctiy.inventory.management.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {

    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setProductId(rs.getString("product_id"));
        product.setName(rs.getString("name"));
        product.setSupplierId(rs.getString("supplier_id"));
        product.setPrice(rs.getDouble("price"));
        product.setQuantity(rs.getInt("quantity"));
        product.setDescription(rs.getString("description"));
        product.setDeleted(rs.getBoolean("is_deleted"));
        product.setCreatedAt(rs.getTimestamp("created_at"));
        product.setUpdatedAt(rs.getTimestamp("updated_at"));
        return product;
    }
}
