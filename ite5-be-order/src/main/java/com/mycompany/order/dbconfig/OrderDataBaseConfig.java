package com.mycompany.order.dbconfig;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration 
@MapperScan(value="com.mycompany.order.orderdao", sqlSessionFactoryRef="orderSqlSessionFactory") 
@EnableTransactionManagement 
public class OrderDataBaseConfig {

	@Bean(name="orderDataSource") 
	@ConfigurationProperties(prefix="spring.order.datasource") 
	public DataSource orderDataSource() { 
		//application.properties에서 정의한 DB 연결 정보를 빌드 
		return DataSourceBuilder.create().build(); 
	} 
	

	@Bean(name="orderSqlSessionFactory") 
	public SqlSessionFactory orderSqlSessionFactory(@Qualifier("orderDataSource") DataSource productDataSource, ApplicationContext applicationContext) throws Exception{ 
		//세션 생성 시, 빌드된 DataSource를 세팅하고 SQL문을 관리할 mapper.xml의 경로를 알려준다. 
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean(); 
		sqlSessionFactoryBean.setDataSource(productDataSource); 
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/order/*.xml"));
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/Mapper-config.xml"));
		return sqlSessionFactoryBean.getObject(); 
	} 
	

	@Bean(name="orderSqlSessionTemplate") 
	public SqlSessionTemplate orderSqlSessionTemplate(SqlSessionFactory productSqlSessionFactory) throws Exception { 
		return new SqlSessionTemplate(productSqlSessionFactory); 
	} 
}
