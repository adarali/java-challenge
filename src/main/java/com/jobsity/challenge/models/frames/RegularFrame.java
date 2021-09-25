package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.misc.Constants;

import java.util.Arrays;
import java.util.List;

class RegularFrame extends AbstractFrame {

    RegularFrame(int frameNumber) {
        super(frameNumber);
    }

    @Override
    public List<String> getPinfalls() {
        if(isStrike()) {
            return Arrays.asList("", "X");
        }
        return super.getPinfalls();
    }

    private boolean isStrike() {
        return !getAttempts().isEmpty() && getAttempts().get(0) == Constants.PINS;
    }

    @Override
    protected boolean isSpare() {
        return !isStrike() && super.isSpare();
    }

    @Override
    public boolean isCurrent() {
        boolean b1 = isStrike() && getAttempts().size() == 1;
        return !(b1 || getAttempts().size() == 2);
    }
}
