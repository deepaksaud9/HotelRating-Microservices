package com.user.service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActualRatingResponse {
    private String ratingId;
    private String userId;
    private int rating;
    private String feedback;
    private Hotel hotel;
}
