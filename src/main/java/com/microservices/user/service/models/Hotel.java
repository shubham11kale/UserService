package com.microservices.user.service.models;

import lombok.Data;

@Data
public class Hotel {
    private String id;
    private String name;
    private String location;
    private String about;
}
