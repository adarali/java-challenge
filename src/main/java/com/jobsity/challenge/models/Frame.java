package com.jobsity.challenge.models;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.misc.Constants;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.stream.IntStream;

@Getter
@EqualsAndHashCode(of="frameNumber")
public class Frame {
    private Integer first;
    private Integer second;
    private Integer score;
    private int extraPoints;
    private final Integer frameNumber;

    public Frame(Integer first, Integer frameNumber) {
        setFirst(first);
        this.frameNumber = frameNumber;
    }

    protected void setFirst(Integer first) {
        if(IntStream.range(0, Constants.PINS + 1).noneMatch(n -> n == first)) {
            throw new AppException("The first attempt score must be between 0 and 10");
        }
        this.first = first;
    }

    protected void setSecond(Integer second) {
        if(IntStream.range(0, Constants.PINS + 1).noneMatch(n -> n == second)) {
            throw new AppException("The second attempt score must be between 0 and 10");
        }
        if (first == null) {
            throw new AppException("You must set the first value first");
        }

        this.second = second;
    }

    public boolean setPoints(int points) {
        if (getFirst() == null) {
            setFirst(points);
            return true;
        }

        if(!isStrike() && second == null) {
            if (points + first > Constants.PINS) {
                throw new AppException("The total number of points in a frame cannot be more than 10");
            }
            setSecond(points);
            return true;
        }

        return false;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTotalPoints() {
        Integer sec = getSecond();
        return getFirst() + (sec == null ? 0 : sec) + getExtraPoints();
    }

    public boolean isStrike() {
        return getFirst() == 10;
    }

    public boolean isSpare() {
        return !isStrike() && getTotalPoints() == 10;
    }

    public boolean isNormal() {
        return !(isStrike() || isSpare()) && second != null;
    }

    public void addExtraPoints(int points) {
        extraPoints += points;
    }

    @Override
    public String toString() {
        return String.valueOf(score);
    }
}
