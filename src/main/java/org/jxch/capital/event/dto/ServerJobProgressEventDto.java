package org.jxch.capital.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ServerJobProgressEventDto {
    @Builder.Default
    private String title = null;
    @Builder.Default
    private String source = null;
    private Double progress;
    @Builder.Default
    private String detail = null;
}
