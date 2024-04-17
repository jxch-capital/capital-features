package org.jxch.capital.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResErrorEntity {
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    @Builder.Default
    private int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    @Builder.Default
    private String error = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    private String message;
    private String path;
}
