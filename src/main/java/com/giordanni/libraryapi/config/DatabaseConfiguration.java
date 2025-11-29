package com.giordanni.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource..driver-class-name}")
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
        HikariConfig config = new HikariConfig(); // mais recomendado para produção
        config.setJdbcUrl(this.dbUrl);
        config.setUsername(this.username);
        config.setPassword(this.password);
        config.setDriverClassName(this.driver);

        config.setMaximumPoolSize(10); // maximos de conexoes liberadas
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); // quanto tempo uma conexao pode ficar aberta em milisegundos
        config.setConnectionTimeout(100000); // tempo maximo para conseguir uma conexao do pool em milisegundos
        config.setConnectionTestQuery("select 1"); // query para testar se a conexao esta valida

        return new HikariDataSource(config);
    }
}
