package com.example.persistence.reactive.user.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;

import com.example.persistence.reactive.user.entity.converter.UserReadingConverter;

@Configuration
@EnableR2dbcRepositories(basePackages = {"com.example.persistence.reactive.user.*"})
public class R2dbcConfiguration  {

    // private final ConnectionFactory factory;

    // @Override
    // public ConnectionFactory connectionFactory() {
    //     return factory;
    // }

    // @Override
    // protected List<Object> getCustomConverters() {
    //     return List.of(new UserReadingConverter());
    // }


    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(DatabaseClient databaseClient) {
        var dialect = DialectResolver.getDialect(databaseClient.getConnectionFactory());
        var converters = new ArrayList<>(dialect.getConverters());
        converters.addAll(R2dbcCustomConversions.STORE_CONVERTERS);

        return new R2dbcCustomConversions(
                CustomConversions.StoreConversions.of(
                    dialect.getSimpleTypeHolder(), converters),
                    getCustomConverters()
                );
    }

    private List<Object> getCustomConverters() {
        return List.of(new UserReadingConverter());
    }


}
