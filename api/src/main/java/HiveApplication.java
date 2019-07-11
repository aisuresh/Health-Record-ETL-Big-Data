import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class HiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(HiveApplication.class, args);
    }

    @Configuration
    @Profile("default")
    static class LocalConfiguration {
        Logger logger = LoggerFactory.getLogger(LocalConfiguration.class);

        @Value("${hiveuri}")
        private String databaseUri;

        @Value("${hivepassword}")
        private String password;

        @Value("${hiveusername}")
        private String username;

        @Bean
        public DataSource dataSource() {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUrl(databaseUri);
            dataSource.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            logger.error("Initialized Hive");
            return dataSource;
        }
    }
}