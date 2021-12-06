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
@MapperScan(basePackages="com.mycompany.order.memberdao", sqlSessionFactoryRef="memberSqlSessionFactory") 
public class MemberDataBaseConfig {
	
	@Value("${spring.member.datasource.xa-data-source-class-name}")
	private String xaDataSourceClassName;
	
	@Value("${spring.member.datasource.xa-properties.url}")
	private String url;
	
	@Value("${spring.member.datasource.xa-properties.user}")
	private String user;
	
	@Value("${spring.member.datasource.xa-properties.password}")
	private String password;
	
	public static final String MEMBER_DATASOURCE = "memberDataSource";
	
	@Bean(name=MEMBER_DATASOURCE) 
	public DataSource memberDataSource() { 
		AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
		ds.setUniqueResourceName(MEMBER_DATASOURCE);
		ds.setXaDataSourceClassName(xaDataSourceClassName);

		Properties p = new Properties();
		p.setProperty("URL", url);
		p.setProperty("user", user);
		p.setProperty("password", password);
		ds.setXaProperties(p);

		return ds;
	} 
	
	@Bean(name="memberSqlSessionFactory") 
	public SqlSessionFactory memberSqlSessionFactory(@Qualifier(MEMBER_DATASOURCE) DataSource memberDataSource) throws Exception{ 
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(memberDataSource);
		
		PathMatchingResourcePatternResolver resover = new PathMatchingResourcePatternResolver();
		sessionFactory.setMapperLocations(resover.getResources("classpath:mybatis/mapper/member/*.xml"));
		sessionFactory.setConfigLocation(resover.getResource("classpath:mybatis/Mapper-config.xml"));
		return sessionFactory.getObject();
	} 
}
