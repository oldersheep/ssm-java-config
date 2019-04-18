package com.xxx.trail.config;

import com.jolbox.bonecp.BoneCPDataSource;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.io.IOException;
import java.util.Properties;

/**
 * @ClassName DataSourceConfig
 * @Description
 * @Author Lilg
 * @Date 2019/4/17 21:47
 * @Version 1.0
 */

@Configuration
// <context:property-placeholder location="classpath:jdbc.properties"/>
@PropertySources(value = {@PropertySource("classpath:jdbc.properties")})
@MapperScan("com.xxx.trail.mapper")
public class DataSourceConfig {

    private final static Logger LOG = Logger.getLogger(DataSourceConfig.class);
    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    /*
   <!-- 配置连接池 -->
	<bean id="dataSource" class="com.jolbox.bonecp.BoneCPDataSource"
		destroy-method="close">
		<!-- 数据库驱动 -->
		<property name="driverClass" value="${jdbc.driver}" />
		<!-- 相应驱动的jdbcUrl -->
		<property name="jdbcUrl" value="${jdbc.url}" />
		<!-- 数据库的用户名 -->
		<property name="username" value="${jdbc.username}" />
		<!-- 数据库的密码 -->
		<property name="password" value="${jdbc.password}" />
		<!-- 检查数据库连接池中空闲连接的间隔时间，单位是分，默认值：240，如果要取消则设置为0 -->
		<property name="idleConnectionTestPeriod" value="60" />
		<!-- 连接池中未使用的链接最大存活时间，单位是分，默认值：60，如果要永远存活设置为0 -->
		<property name="idleMaxAge" value="30" />
		<!-- 每个分区最大的连接数 -->
		<property name="maxConnectionsPerPartition" value="150" />
		<!-- 每个分区最小的连接数 -->
		<property name="minConnectionsPerPartition" value="5" />
	</bean>
    */
    @Bean
    public BoneCPDataSource dataSource() {
        LOG.info("Initialize the BasicDataSource...");
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    // mybatis的配置
    public org.apache.ibatis.session.Configuration getConfiguration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(true);
        configuration.setDefaultStatementTimeout(3000);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setUseColumnLabel(true);
        return configuration;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setConfiguration(getConfiguration());
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath*:mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.xxx.trail.mapper");// 别名，让*Mpper.xml实体类映射可以不加上具体包名
        return sqlSessionFactoryBean;
    }

    // 事务管理器 对mybatis操作数据库事务控制，spring使用jdbc的事务控制类
    /*<!-- 定义事务管理器 -->
	<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource" />
	</bean>
	*/
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    /*
     <!-- 定义事务策略 -->
     <tx:advice id="txAdvice" transaction-manager="transactionManager">
     <tx:attributes>
     <!--定义查询方法都是只读的 -->
     <tx:method name="query*" read-only="true" />
     <tx:method name="find*" read-only="true" />
     <tx:method name="get*" read-only="true" />
     <tx:method name="list*" read-only="true" />

     <!-- 主库执行操作，事务传播行为定义为默认行为 -->
     <tx:method name="save*" propagation="REQUIRED" />
     <tx:method name="update*" propagation="REQUIRED" />
     <tx:method name="delete*" propagation="REQUIRED" />

     <!--其他方法使用默认事务策略 -->
     <tx:method name="*" />
     </tx:attributes>
     </tx:advice>
     */
    @Bean(name = "transactionInterceptor")
    public TransactionInterceptor interceptor() {
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(dataSourceTransactionManager());
        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("delete*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("get*", "PROPAGATION_REQUIRED,readOnly");
        transactionAttributes.setProperty("find*", "PROPAGATION_REQUIRED,readOnly");
        transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED");
        interceptor.setTransactionAttributes(transactionAttributes);
        return interceptor;
    }

    //要想使用@Value 用${}占位符注入属性，这个bean是必须的，这个就是占位bean,另一种方式是不用value直接用Envirment变量直接getProperty('key')
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
