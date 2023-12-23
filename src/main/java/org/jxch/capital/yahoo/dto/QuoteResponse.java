package org.jxch.capital.yahoo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QuoteResponse {
    private List<QuoteResponseResultItem> result;
    private String error;
}
