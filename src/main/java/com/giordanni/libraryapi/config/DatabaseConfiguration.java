package com.giordanni.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

//    @Bean
//    public DataSource dataSource() {
//        // Define your DataSource bean here
//        // este padrao "DriverManagerDataSource" não é muito recomendado para produção pois quebra com muitos usuários
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setUrl(this.dbUrl);
//        ds.setUsername(this.username);
//        ds.setPassword(this.password);
//        ds.setDriverClassName(this.driver);
//        return ds;
//    }

    @Bean // utilizar este datasource com HikariCP
    public DataSource hikariDataSource() {
        log.info("Iniciando conexão com o banco na URL: {}", dbUrl);

        HikariConfig config = new HikariConfig(); // mais recomendado para produção
        config.setJdbcUrl(this.dbUrl);
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setDriverClassName(this.driver);

        config.setMaximumPoolSize(10); // maximo de conexões liberadas
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); // 600 mil ms (10 minutos)
        config.setConnectionTimeout(100000); // timeout para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); // query de teste

        return new HikariDataSource(config);
    }
}
