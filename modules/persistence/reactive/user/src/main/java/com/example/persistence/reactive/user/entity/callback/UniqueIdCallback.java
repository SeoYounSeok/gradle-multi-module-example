package com.example.persistence.reactive.user.entity.callback;

import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;

import com.example.utils.generator.UniqueIdGenerator;

import reactor.core.publisher.Mono;

@Component
public class UniqueIdCallback implements BeforeConvertCallback<UniqueIdEntity> {

    @Override
    public Publisher<UniqueIdEntity> onBeforeConvert(UniqueIdEntity entity, SqlIdentifier table) {
        entity.generateId(UniqueIdGenerator.uuid());
        return Mono.just(entity);
    }
    
}
