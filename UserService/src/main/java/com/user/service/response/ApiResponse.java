package com.user.service.response;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private String statusName;
    private boolean success;
    private Integer statusCode;
    private String message;
    private T response;
}
