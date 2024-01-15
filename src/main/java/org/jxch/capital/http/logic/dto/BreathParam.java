package org.jxch.capital.http.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BreathParam implements Serializable {
    private Integer length = Integer.MAX_VALUE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BreathParam that = (BreathParam) o;

        return Objects.equals(length, that.length);
    }

    @Override
    public int hashCode() {
        return length != null ? length.hashCode() : 0;
    }
}
