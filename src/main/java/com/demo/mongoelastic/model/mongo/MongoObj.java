package com.demo.mongoelastic.model.mongo;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

@Getter
public abstract class MongoObj implements Persistable<String> {

    @Id
    protected String id;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
