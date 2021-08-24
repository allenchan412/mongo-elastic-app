package com.demo.mongoelastic.model.elastic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "car")
public class Car extends ElasticObj {

    private String model;
    private String brand;
}
