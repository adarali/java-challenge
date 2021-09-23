package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.misc.Constants;
import com.jobsity.challenge.misc.Utils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter(AccessLevel.PROTECTED) @Setter(AccessLevel.PROTECTED)
public abstract class AbstractFrame implements Frame {

    private int frameNumber;
    @Setter(AccessLevel.PUBLIC)
    private Integer score;
    private List<Integer> attempts = new ArrayList<>();
    @Getter(AccessLevel.PUBLIC)
    private List<String> pinfalls = new ArrayList<>();
    private int extraPoints = 0;

    protected AbstractFrame(String first, int frameNumber) {
        setPoints(first);
        this.frameNumber = frameNumber;
    }

    @Override
    public boolean setPoints(String pointsString) {
        List<Integer> attempts = getAttempts();
        int points = Utils.parsePoints(pointsString);
        if(IntStream.range(0, Constants.PINS + 1).noneMatch(n -> n == points)) {
            throw new AppException("Points must be between 0 and " + Constants.PINS + " (inclusive)");
        }
        List<String> attemptsString = getPinfalls();
        if (setPoints(points)) {
            String p = points == Constants.PINS ? "X" : pointsString;
            attemptsString.add(p);
            if (isStrike()) {
                attemptsString.add(0, "");
            } else if (attempts.size() > 1 && attempts.get(attempts.size() - 1) + attempts.get(attempts.size() - 2) == Constants.PINS) {
                attemptsString.set(attemptsString.size() - 1, "/");
            }
            return true;
        }
        return false;
    }

    protected abstract boolean setPoints(int points);

    @Override
    public Integer getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void addExtraPoints(String points) {
        setExtraPoints(getExtraPoints() + Utils.parsePoints(points));
    }

    @Override
    public int getTotalPoints() {
        return getAttempts().stream().mapToInt(n -> n).sum() + getExtraPoints();
    }

    protected void setFrameNumber(int number) {
        this.frameNumber = number;
    }

    @Override
    public String toString() {
        return getScore() + " " + getPinfalls();
    }
}
