package com.ratingService.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse<T> {

    private String statusName;
    private boolean success;
    private Integer statusCode;
    private String message;
    private T response;
}
