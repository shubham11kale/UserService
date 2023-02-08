package com.microservices.user.service.models;

import lombok.*;
import org.hibernate.type.StringNVarcharType;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    private String ratingId;
    private String userId;
    private String hotelId;
    private String rating;
    private String feedback;
    private Hotel hotel;
}
