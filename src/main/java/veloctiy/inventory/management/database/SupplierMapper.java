package veloctiy.inventory.management.database;

import org.springframework.jdbc.core.RowMapper;
import veloctiy.inventory.management.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierMapper implements RowMapper<Supplier> {

    public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setId(rs.getLong("id"));
        supplier.setSupplierId(rs.getString("supplier_id"));
        supplier.setName(rs.getString("name"));
        supplier.setContactNumber(rs.getString("contact_number"));
        supplier.setEmailId(rs.getString("email_id"));
        supplier.setAddress(rs.getString("address"));
        supplier.setDeleted(rs.getBoolean("is_deleted"));
        supplier.setCreatedAt(rs.getTimestamp("created_at"));
        supplier.setUpdatedAt(rs.getTimestamp("updated_at"));
        return supplier;
    }
}
