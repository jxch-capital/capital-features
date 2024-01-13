package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class KLine {
    private Date date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KLine kLine = (KLine) o;

        if (!Objects.equals(date, kLine.date)) return false;
        if (!Objects.equals(open, kLine.open)) return false;
        if (!Objects.equals(high, kLine.high)) return false;
        if (!Objects.equals(low, kLine.low)) return false;
        if (!Objects.equals(close, kLine.close)) return false;
        return Objects.equals(volume, kLine.volume);
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (open != null ? open.hashCode() : 0);
        result = 31 * result + (high != null ? high.hashCode() : 0);
        result = 31 * result + (low != null ? low.hashCode() : 0);
        result = 31 * result + (close != null ? close.hashCode() : 0);
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        return result;
    }
}
