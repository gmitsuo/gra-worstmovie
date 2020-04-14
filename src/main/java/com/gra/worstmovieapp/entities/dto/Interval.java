package com.gra.worstmovieapp.entities.dto;

import java.util.Objects;
import java.util.StringJoiner;

public class Interval {

    private final Integer previous;
    private final Integer following;

    public Interval(Integer previous, Integer following) {

        if (previous == null || following == null)
            throw new IllegalArgumentException("Interval must have value for previous and following.");

        if (following < previous)
            throw new IllegalArgumentException("Following value must be greater than previous value.");

        this.previous = previous;
        this.following = following;
    }

    public Integer getPrevious() {
        return previous;
    }

    public Integer getFollowing() {
        return following;
    }

    public Integer getDiff() {
        return following - previous;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return Objects.equals(previous, interval.previous) &&
                Objects.equals(following, interval.following);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previous, following);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Interval.class.getSimpleName() + "[", "]")
                .add("previous=" + previous)
                .add("following=" + following)
                .toString();
    }
}
