package veloctiy.inventory.management.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class Connection {

    @Bean(name = {"datasource"})
    @ConfigurationProperties(prefix = "inventory.management.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = {"jdbcTemplate"})
    public JdbcTemplate jdbcTemplate(@Qualifier("datasource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

}
