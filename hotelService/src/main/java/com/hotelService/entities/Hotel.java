package com.hotelService.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Hotel {
    @Id
    private String hotelId;
    private String name;
    private String location;
    private String about;
}
