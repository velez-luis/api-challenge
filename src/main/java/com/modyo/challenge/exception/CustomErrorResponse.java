package com.modyo.challenge.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {

    private LocalDateTime datetime;
    private String message;
    private String details;
}
