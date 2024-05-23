package veloctiy.inventory.management.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import veloctiy.inventory.management.entity.Supplier;
import veloctiy.inventory.management.exception.InternalServerException;


@Component
public class SupplierDao {

    private Logger logger = LoggerFactory.getLogger(Supplier.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer insert(Supplier supplier) throws InternalServerException {
        try {
            String query = "insert into supplier (supplier_id, name, contact_number, email_id, address, is_deleted, created_at, updated_at) values (?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] values = new Object[] {supplier.getSupplierId(), supplier.getName(), supplier.getContactNumber(), supplier.getEmailId(), supplier.getAddress(),
                                            supplier.getDeleted(), supplier.getCreatedAt(), supplier.getUpdatedAt()};
            return jdbcTemplate.update(query, values);
        }catch (DataAccessException exception){
            logger.error("error while inserting supplier - {}", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }

    public Supplier getSupplier(String supplierId) throws InternalServerException {
        try {
            String query = "select * from supplier where supplier_id = ? and is_deleted = 0;";
            return jdbcTemplate.queryForObject(query, new SupplierMapper(), new Object[]{supplierId});
        }catch (DataAccessException exception){
            logger.error("error while executing query - {}", exception);
            throw new InternalServerException(exception.getMessage());
        }
    }

}
