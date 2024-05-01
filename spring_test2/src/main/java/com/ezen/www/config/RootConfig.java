package com.ezen.www.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableScheduling //file 삭제할 때 handler에서 사용
@EnableTransactionManagement
@MapperScan(basePackages = {"com.ezen.www.repository"})
@Configuration //spring에게 설정파일임을 인지할 수 있도록 안내
public class RootConfig {

	@Autowired
	ApplicationContext applicationContext;
	
	@Bean
	public DataSource dataSoruce() {
		HikariConfig hikariConfig = new HikariConfig();
		// driver >> log4jdbc > DB의 로그를 찍을 수 있는 드라이버 설정 변경
		// 기본설정 부분
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/springtest");
		hikariConfig.setUsername("springUser");
		hikariConfig.setPassword("mysql");
		
		/*-------여기부터 hikari 추가 설정 -------*/
		hikariConfig.setMaximumPoolSize(5); // 최대 커넥션 개수 **필수
		hikariConfig.setMinimumIdle(5); // 최소 유휴 커넥션 개수 (비어있는 커넥션 / Max와 같은 개수로 설정)  **필수
		
		hikariConfig.setConnectionTestQuery("SELECT now()"); // connecting 시 test 쿼리문
		hikariConfig.setPoolName("springHikariCP");
		
		// 기본값이 있는 추가 설정 
		// cachePrepStmts : chche 사용 여부 설정
		hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true"); //데이터소스에 다른 값을 추가로 넣는 설정
		// mysql 드라이버가 연결당 cache 사이즈 : 250 ~ 500 사이 권장 (기본)
		hikariConfig.addDataSourceProperty("dataSource.prepStmtsCacheSize", "250");
		//connection 당 캐싱할 preparedStatement의 개수 지정 옵션 : default 256
		hikariConfig.addDataSourceProperty("dataSource.prepStmtsCacheSqlLimit", "true"); //기본갑 설정(256)
		// mysql 서버에서 최신 이슈가 있을 경우 지원을 받을 것인지 설정
		hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true"); //사용자에 대한 서버 이슈
		
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		
		return hikariDataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessoinFactory() throws Exception {
		SqlSessionFactoryBean sqlFactoryBean = new SqlSessionFactoryBean();
		sqlFactoryBean.setDataSource(dataSoruce());
		sqlFactoryBean.setMapperLocations(
				applicationContext.getResources("classpath:/mappers/*.xml"));
		// DB : _(스네이크 표기법 사용) / java : 가멜표기법
		// DB : file_name => java : fileName 인지 할 수 있도록 변환
		// 별칭 설정
		sqlFactoryBean.setConfigLocation(
				applicationContext.getResource("classpath:/MybatisConfig.xml"));
		
		return sqlFactoryBean.getObject();
	}
	
	//트랜젝션 매니저 설정
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSoruce());
	}
	
	
	
}
