package com.mycompany.order.dbconfig;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.atomikos.jdbc.AtomikosDataSourceBean;

@Configuration 
@MapperScan(basePackages="com.mycompany.order.orderdao", sqlSessionFactoryRef="orderSqlSessionFactory") 
public class OrderDataBaseConfig {
	
	@Value("${spring.order.datasource.xa-data-source-class-name}")
	private String xaDataSourceClassName;
	
	@Value("${spring.order.datasource.xa-properties.url}")
	private String url;
	
	@Value("${spring.order.datasource.xa-properties.user}")
	private String user;
	
	@Value("${spring.order.datasource.xa-properties.password}")
	private String password;
	
	public static final String ORDER_DATASOURCE = "orderDataSource";
	
	@Bean(name=ORDER_DATASOURCE) 
	public DataSource orderDataSource() { 
		AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
		ds.setUniqueResourceName(ORDER_DATASOURCE);
		ds.setXaDataSourceClassName(xaDataSourceClassName);

		Properties p = new Properties();
		p.setProperty("URL", url);
		p.setProperty("user", user);
		p.setProperty("password", password);
		ds.setXaProperties(p);

		return ds;
	} 
	
	@Bean(name="orderSqlSessionFactory") 
	public SqlSessionFactory orderSqlSessionFactory(@Qualifier(ORDER_DATASOURCE) DataSource orderDataSource) throws Exception{ 
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(orderDataSource);
		
		PathMatchingResourcePatternResolver resover = new PathMatchingResourcePatternResolver();
		sessionFactory.setMapperLocations(resover.getResources("classpath:mybatis/mapper/order/*.xml"));
		sessionFactory.setConfigLocation(resover.getResource("classpath:mybatis/Mapper-config.xml"));
		return sessionFactory.getObject();
	} 
}
