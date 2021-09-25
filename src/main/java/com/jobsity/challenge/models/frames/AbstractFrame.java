package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.misc.Constants;
import com.jobsity.challenge.misc.Utils;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.jobsity.challenge.misc.Constants.PINS;

@Getter(AccessLevel.PROTECTED)
@EqualsAndHashCode(of="frameNumber")
public abstract class AbstractFrame implements Frame {

    private int frameNumber;
    private Integer score;
    private List<Integer> attempts = new ArrayList<>();
    @Getter(AccessLevel.PUBLIC)
    private List<String> pinfalls = new ArrayList<>();

    protected AbstractFrame(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    @Override
    public void setPoints(String pointsString) {
        int points = Utils.parsePoints(pointsString);
        validatePoints(points);
        if (isCurrent()) {
            pinfalls.add(points == PINS ? "X" : pointsString);
        }
        if (!isDone()) {
            getAttempts().add(points);
        }
        if (isSpare()) {
            pinfalls.set(pinfalls.size() - 1, "/");
        }
    }

    @Override
    public Integer getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public int getTotalPoints() {
        return getAttempts().stream().mapToInt(n -> n).sum();
    }

    @Override
    public String toString() {
        return getScore() + " " + getAttempts();
    }

    @Override
    public boolean isDone() {
        int pins = PINS;
        List<Integer> attempts = getAttempts();
        if(attempts.size() < 2) return false;
        if (attempts.size() == 2) {
            int sum = attempts.get(0) + attempts.get(1);
            return attempts.get(0) < pins && sum < pins;
        }
        return true;
    }

    protected boolean isSpare() {
        int size = getAttempts().size();
        if (size < 2) return false;
        int sum = getAttempts().get(size - 2) + getAttempts().get(size - 1);
        return sum == PINS && getAttempts().get(size - 2) < PINS;
    }

    protected void validatePoints(int points) {
        if(points < 0 || points > PINS) {
            throw new AppException("Points must be between 0 and " + PINS + " (inclusive)");
        }
        int size = getAttempts().size();
        if (!isSpare() && isCurrent() && size > 0) {
            int last = getAttempts().get(size - 1);
            if (points + last > PINS && last < PINS) {
                throw new AppException(String.format("You can't knock down more than %d in a single frame", PINS));
            }
        }
    }
}
