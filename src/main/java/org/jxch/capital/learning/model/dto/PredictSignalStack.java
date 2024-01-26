package org.jxch.capital.learning.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PredictSignalStack {
    private Double signal = 0.0;
    private Map<String, Double> signalStack = new ConcurrentHashMap<>();

    public Boolean isUp(Double thAbs) {
        return signal > thAbs;
    }

    public Boolean isDown(Double thAbs) {
        return signal < -thAbs;
    }

    public Boolean isFlat(Double thAbs) {
        return signal >= -thAbs && signal <= thAbs;
    }

    public PredictSignalStack add(String name, Double signal) {
        this.signal += signal;
        signalStack.put(name, signal);
        return this;
    }

    public PredictSignalStack add(@NotNull PredictSignalStack stack) {
        this.signal += stack.getSignal();
        signalStack.putAll(stack.getSignalStack());
        return this;
    }

    public static PredictSignalStack of(String name, Double signal) {
        return new PredictSignalStack().add(name, signal);
    }

}
