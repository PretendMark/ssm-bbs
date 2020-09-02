package com.fly.web.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors
public class ProvincialAndCity implements Serializable {

    /**
     * 数据库里取出的省市对应关系
     */
    private String provincial;
    private String city;

}
