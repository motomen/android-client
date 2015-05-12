package com.application.yaroslav.searchprogm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Yaroslav on 08.05.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link implements Serializable {

    private String address;

    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
