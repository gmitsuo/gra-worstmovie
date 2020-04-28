package com.gra.worstmovieapp.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gra.worstmovieapp.entities.Producer;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class ProducerWinningInterval {

    private final String producer;
    private final Integer interval;
    private final Integer previousWin;
    private final Integer followingWin;

    public ProducerWinningInterval(
            @JsonProperty("producer") String producer,
            @JsonProperty("interval") Integer interval,
            @JsonProperty("previousWin") Integer previousWin,
            @JsonProperty("followingWin") Integer followingWin) {

        this.producer = producer;
        this.interval = interval;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }

    public String getProducer() {
        return producer;
    }

    public Integer getInterval() {
        return interval;
    }

    public Integer getPreviousWin() {
        return previousWin;
    }

    public Integer getFollowingWin() {
        return followingWin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProducerWinningInterval that = (ProducerWinningInterval) o;
        return Objects.equals(producer, that.producer) &&
                Objects.equals(interval, that.interval) &&
                Objects.equals(previousWin, that.previousWin) &&
                Objects.equals(followingWin, that.followingWin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producer, interval, previousWin, followingWin);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProducerWinningInterval.class.getSimpleName() + "[", "]")
                .add("producer='" + producer + "'")
                .add("interval=" + interval)
                .add("previousWin=" + previousWin)
                .add("followingWin=" + followingWin)
                .toString();
    }

    public static Stream<ProducerWinningInterval> fromProducer(Producer producer) {
        return producer.getWinningIntervals().map(minInterval -> {
            String name = producer.getName();
            Integer interval = minInterval.getDiff();
            Integer previousWin = minInterval.getPrevious();
            Integer followingWin = minInterval.getFollowing();
            return new ProducerWinningInterval(name, interval, previousWin, followingWin);
        });
    }
}