package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.misc.Constants;
import lombok.Getter;

import java.util.List;

@Getter
class FinalFrame extends AbstractFrame {
    public FinalFrame(Integer frameNumber) {
        super(frameNumber);
    }

    @Override
    public boolean isCurrent() {
        return !isDone();
    }
}
