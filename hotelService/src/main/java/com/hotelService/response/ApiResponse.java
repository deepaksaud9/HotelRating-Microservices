package com.hotelService.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
        private String statusName;
        private boolean success;
        private Integer statusCode;
        private String message;
        private T response;
}
