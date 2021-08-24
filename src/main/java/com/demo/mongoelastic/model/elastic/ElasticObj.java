package com.demo.mongoelastic.model.elastic;

import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Getter
public abstract class ElasticObj implements Serializable {

    @Id
    protected String id;

}
