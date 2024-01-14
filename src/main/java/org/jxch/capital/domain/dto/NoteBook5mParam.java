package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.stock.EngineEnum;
import org.jxch.capital.utils.DateTimeU;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NoteBook5mParam {
    @Builder.Default
    private String timeZone = "America/Los_Angeles";
    @Builder.Default
    private String engine = EngineEnum.defaultEngine().getEngine();
    @Builder.Default
    private String code = "ES=F";
    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date = LocalDate.now();

    @Builder.Default
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime = DateTimeU.americaToBeijingStockStartWinterTime();
    @Builder.Default
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime endTime = DateTimeU.americaToBeijingStockEndWinterTime();

}
