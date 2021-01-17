package top.tianqi.plankton.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ResourceUtils;
import top.tianqi.plankton.common.status.BaseLogicDeleteEnum;

import javax.sql.DataSource;

/**
 * MyBatis Plus 与数据源配置类
 * @author Wukh
 * @create 2021-01-04
 */
@Configuration
@MapperScan(basePackages = {"top.tianqi.plankton.*.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();

        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(ResourceUtils.CLASSPATH_URL_PREFIX
                        + "top/tianqi/plankton/*/mapper/*Dao.xml"));
        factoryBean.setTypeAliasesPackage("top.tianqi.plankton.*.entity");
        factoryBean.setPlugins(new Interceptor[] { paginationInterceptor(),
                optimisticLockerInterceptor(), sqlExplainInterceptor(), performanceInterceptor()});
        factoryBean.setGlobalConfig(this.globalConfiguration());

        return factoryBean.getObject();
    }

    /**
     * 乐观锁插件
     *
     * @return
     */
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * SQL 分析拦截器，防止恶意删除或更新全表数据
     *
     * @return
     */
    public SqlExplainInterceptor sqlExplainInterceptor() {
        return new SqlExplainInterceptor();
    }

    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求
        // 默认false 为false时 会返回空数据
        paginationInterceptor.setOverflowCurrent(true);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        return paginationInterceptor;
    }

    /**
     * SQL 执行效率插件
     *
     * @return
     */
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor intrceptor = new PerformanceInterceptor();

        intrceptor.setMaxTime(20000);
        intrceptor.setFormat(true);

        return intrceptor;
    }

    /**
     * 全局配置，主键策略、驼峰命名、设置逻辑删除
     *
     * @return
     */
    private GlobalConfiguration globalConfiguration() {
        GlobalConfiguration conf = new GlobalConfiguration(new LogicSqlInjector());

        conf.setLogicDeleteValue(BaseLogicDeleteEnum.LogicDelete.getValue());
        conf.setLogicNotDeleteValue(BaseLogicDeleteEnum.LogicNotDelete.getValue());
        conf.setIdType(IdType.AUTO.getKey());
        conf.setDbColumnUnderline(true);

        return conf;
    }
}
