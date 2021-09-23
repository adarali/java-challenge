package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.misc.Constants;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter(AccessLevel.PROTECTED)
@EqualsAndHashCode(of="frameNumber")
class RegularFrame extends AbstractFrame {

    RegularFrame(String points, int frameNumber) {
        super(points, frameNumber);
    }

    @Override
    protected boolean setPoints(int points) {
        List<Integer> attempts = getAttempts();
        if (attempts.size() < 2) {
            if(isStrike()) return false;
            attempts.add(points);
            if(!isStrike() && attempts.size() > 1) {
                if (points + attempts.get(attempts.size() - 2) > Constants.PINS) {
                    throw new AppException("The total number of points in a frame cannot be more than " + Constants.PINS);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isStrike() {
        return !getAttempts().isEmpty() && getAttempts().get(0) == Constants.PINS;
    }

    @Override
    public boolean isSpare() {
        int size = getAttempts().size();
        int sum = size > 1 ? getAttempts().get(size - 2) + getAttempts().get(size - 1) : 0;
        return sum == Constants.PINS;
    }

    @Override
    public boolean isDone() {
        return !(isStrike() || isSpare()) && getAttempts().size() == 2;
    }


}
