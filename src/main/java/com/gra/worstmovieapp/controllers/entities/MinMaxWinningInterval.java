package com.gra.worstmovieapp.controllers.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gra.worstmovieapp.entities.dto.ProducerWinningInterval;

import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

public class MinMaxWinningInterval {

    private final Collection<ProducerWinningInterval> min;
    private final Collection<ProducerWinningInterval> max;

    public MinMaxWinningInterval(
            @JsonProperty("min") Collection<ProducerWinningInterval> min,
            @JsonProperty("max") Collection<ProducerWinningInterval> max) {
        this.min = min;
        this.max = max;
    }

    public Collection<ProducerWinningInterval> getMin() {
        return min;
    }

    public Collection<ProducerWinningInterval> getMax() {
        return max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinMaxWinningInterval that = (MinMaxWinningInterval) o;
        return Objects.equals(min, that.min) &&
                Objects.equals(max, that.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MinMaxWinningInterval.class.getSimpleName() + "[", "]")
                .add("min=" + min)
                .add("max=" + max)
                .toString();
    }
}
