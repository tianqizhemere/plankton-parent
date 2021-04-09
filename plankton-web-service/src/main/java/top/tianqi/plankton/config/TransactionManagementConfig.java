package top.tianqi.plankton.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * @EnableTransactionManagement 注解用于开启事务
 *
 * @author Wukh
 * @create 2021-01-04
 */
@SpringBootConfiguration
@EnableTransactionManagement
public class TransactionManagementConfig implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;

    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
