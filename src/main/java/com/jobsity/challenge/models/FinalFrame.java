package com.jobsity.challenge.models;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.misc.Constants;
import lombok.Getter;

import java.util.stream.IntStream;

@Getter
public class FinalFrame extends Frame{
    private Integer third;
    public FinalFrame(Integer first, Integer frameNumber) {
        super(first, frameNumber);
    }

    private void setThird(int third) {
        if(IntStream.range(0, Constants.PINS + 1).noneMatch(n -> n == third)) {
            throw new AppException("The second attempt score must be between 0 and 10");
        }
        this.third = third;
    }

    @Override
    public boolean setPoints(int points) {
        if (getFirst() == null) {
            setFirst(points);
            return true;
        }
        if (getSecond() == null) {
            setSecond(points);
            return true;
        }
        if (getTotalPoints() >= Constants.PINS && getThird() == null) {
            setThird(points);
            return true;
        }
        return false;
    }

    @Override
    public Integer getTotalPoints() {
        int third = getThird() != null ? getThird() : 0;
        return super.getTotalPoints() + third;
    }

    @Override
    public boolean isStrike() {
        return false;
    }

    @Override
    public boolean isSpare() {
        return false;
    }

    @Override
    public boolean isNormal() {
        return getThird() != null || getTotalPoints() < Constants.PINS && getSecond() != null;
    }
}
