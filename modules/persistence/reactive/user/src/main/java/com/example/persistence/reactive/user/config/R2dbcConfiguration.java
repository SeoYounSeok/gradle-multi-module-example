package com.example.persistence.reactive.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(basePackages = {"com.example.persistence.reactive.user.*"})
public class R2dbcConfiguration {

    // @Bean
    // public ConnectionFactoryInitializer testProfileInitializer(ConnectionFactory connectionFactory) {
    //     ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    //     initializer.setConnectionFactory(connectionFactory);

    //     CompositeDatabasePopulator populator = new CompositeDatabasePopulator();

    //     populator.addPopulators(new ResourceDatabasePopulator(
    //         new ClassPathResource("sql/testSchema.sql")));

    //     initializer.setDatabasePopulator(populator);

    //     return initializer;
    // }

}
