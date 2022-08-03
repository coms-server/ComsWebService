package coms.kw.ac.kr.server.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 
 */
@Configuration
@EnableTransactionManagement
@MapperScan("coms.kw.ac.kr.server.dao")
public class MyBatisConfig {

    @Bean
    public PlatformTransactionManager transactionManger(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean
                .setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        TypeAliasRegistry registry = configuration.getTypeAliasRegistry();
        registry.registerAliases("coms.kw.ac.kr.server.vo");
        registry.registerAliases("coms.kw.ac.kr.server.vo.event");
        registry.registerAliases("coms.kw.ac.kr.server.vo.article");
        registry.registerAliases("coms.kw.ac.kr.server.vo.statics");
        registry.registerAliases("coms.kw.ac.kr.server.vo.user");

        factoryBean.setConfiguration(configuration);

        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        final SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
        return template;
    }

}