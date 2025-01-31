package veloctiy.inventory.management.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import veloctiy.inventory.management.entity.Product;
import veloctiy.inventory.management.exception.InternalServerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ProductDao {

    private Logger logger = LoggerFactory.getLogger(ProductDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer insert(Product product) throws InternalServerException {
        try {
            String query = "insert into product (product_id, name, supplier_id, price, quantity, description, is_deleted, created_at, updated_at) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] values = new Object[] {product.getProductId(), product.getName(), product.getSupplierId(), product.getPrice(), product.getQuantity(),
                                            product.getDescription(), product.getDeleted(), product.getCreatedAt(), product.getUpdatedAt()};
            return jdbcTemplate.update(query, values);
        }catch (DataAccessException exception){
            logger.error("error while inserting sample - {}", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }

    public Integer update(Product product) throws InternalServerException {
        try {
            String query = "update product set name = ?, supplier_id = ?, price = ?, quantity = ?, description = ? where product_id = ? and is_deleted = 0;";
            Object[] values = new Object[] {product.getName(), product.getSupplierId(), product.getPrice(), product.getQuantity(), product.getDescription(), product.getProductId()};
            return jdbcTemplate.update(query, values);
        }catch (DataAccessException exception){
            logger.error("error while executing query - {}", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }

    public Integer delete(Product product) throws InternalServerException {
        try {
            String query = "update product set is_deleted = 1 where product_id = ?;";
            Object[] values = new Object[] {product.getProductId()};
            return jdbcTemplate.update(query, values);
        }catch (DataAccessException exception){
            logger.error("error while executing query - {}", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }

    public Product getProduct(String productId) throws InternalServerException {
        try {
            String query = "select * from product where product_id = ? and is_deleted = 0;";
            return jdbcTemplate.queryForObject(query, new ProductMapper(), new Object[]{productId});
        }catch (DataAccessException exception){
            logger.error("error while executing query - {}", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }

    public List<Product> getProduct(HashMap<String, Object> filter) throws InternalServerException {
        try {
            StringBuilder query = new StringBuilder("select * from product where is_deleted = 0");
            for (String key : filter.keySet()) {
                String column = key;
                if (key.equals(Product.startPriceValue)){
                    column = Product.startPriceColumnMapping;
                    query.append(" and ").append(column).append(" >= ?");
                }else if (key.equals(Product.endPriceValue)){
                    column = Product.endPriceColumnMapping;
                    query.append(" and ").append(column).append(" <= ?");
                }else {
                    query.append(" and ").append(column).append(" = ?");
                }
            }
            List<Object> values = new ArrayList<>();
            for (Object value : filter.values()) {
                values.add(value);
            }
            return jdbcTemplate.query(query.toString(), new ProductMapper(), values);
        }catch (DataAccessException exception){
            logger.error("error while executing query - {}", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }
}
