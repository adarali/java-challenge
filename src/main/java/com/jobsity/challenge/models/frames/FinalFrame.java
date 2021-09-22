package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.misc.Constants;
import lombok.Getter;

import java.util.List;
import java.util.stream.IntStream;

@Getter
public class FinalFrame extends Frame{
    private Integer third;
    public FinalFrame(String first, Integer frameNumber) {
        super(first, frameNumber);
    }

    private void setThird(int third) {
        getAttempts().add(third);
        this.third = third;
    }

    @Override
    boolean setPoints(int points) {
        if (getAttempts().size() < 3) {
            getAttempts().add(points);
            return true;
        }
        return false;
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
        List<Integer> attempts = getAttempts();
        if (!attempts.isEmpty()) {
            if(attempts.size() < 2) return false;
            int sum = attempts.get(0) + attempts.get(1);
            if(attempts.get(0) < 10 && sum < 10) return true;
            return sum < 10 || attempts.size() == 3;
        }
        return false;
    }
}
