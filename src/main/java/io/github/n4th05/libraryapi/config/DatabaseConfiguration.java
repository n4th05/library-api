package io.github.n4th05.libraryapi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.driver-class-name}")
    String driver;

    // @Bean Não é recomendado usar o DriverManagerDataSource em produção, pois ele não possui pool de conexões.
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    /**
     * configuracao Hikary com detalhes
     * https://github.com/brettwooldridge/HikariCP
     * @return
     */

    @Bean // HikariCP é um pool de conexões de alta performance. - Mais recomendado que o DriverManagerDataSource.
    public DataSource hikariDataSource() {

        log.info("Iniciando conexao com o Banco na URL: {}", url);

        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);

        config.setMaximumPoolSize(10); // Máximo de conexões liberadas.
        config.setMinimumIdle(1); // Tamanho inicial do pool.
        config.setPoolName("library-db-pool"); // Nome do pool de conexões(opcional).
        config.setMaxLifetime(600000); // 600 mil milliseconds (10 min) - tempo máximo de vida de uma conexão no pool. O padrão é 30 min.
        config.setConnectionTimeout(100000); // 1 min - timeout para conseguir uma conexão.
        config.setConnectionTestQuery("select 1"); // Query para testar a conexão.
        
        return new HikariDataSource(config);
    }

}
