package com.gra.worstmovieapp.entities.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class IntervalTest {

    @Test
    void nullValueForPreviousShouldThrowException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> new Interval(null, 1900))
        .withMessage("Interval must have value for previous and following.");
    }

    @Test
    void nullValueForFollowingShouldThrowException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> new Interval(1900, null))
        .withMessage("Interval must have value for previous and following.");
    }

    @Test
    void previousGreaterThanFollowingValueShouldThrowException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> new Interval(1900, 1899))
        .withMessage("Following value must be greater than previous value.");
    }

    @Test
    void getDiffWithSameValueForPreviousAndFollowing() {
        assertThat(new Interval(1900, 1900).getDiff())
        .isEqualTo(0);
    }

    @Test
    void getDiffWithWithFollowingGreaterThanPrevious() {
        assertThat(new Interval(1900, 1901).getDiff())
        .isEqualTo(1);
    }
}