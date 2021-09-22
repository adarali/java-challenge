package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.misc.Constants;
import com.jobsity.challenge.misc.Utils;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter(AccessLevel.PROTECTED)
@EqualsAndHashCode(of="frameNumber")
public class Frame {
    @Getter(AccessLevel.PUBLIC)
    private Integer score;
    private List<Integer> attempts = new ArrayList<>();
    private List<String> attemptsString = new ArrayList<>();
    @Setter(AccessLevel.PROTECTED)
    private int extraPoints;
    private final int frameNumber;

    public Frame(String points, int frameNumber) {
        setPoints(points);
        this.frameNumber = frameNumber;
    }

    public boolean setPoints(String pointsString) {
        List<Integer> attempts = getAttempts();
        int points = Utils.parsePoints(pointsString);
        if(IntStream.range(0, Constants.PINS + 1).noneMatch(n -> n == points)) {
            throw new AppException("Points must be between 0 and " + Constants.PINS + " (inclusive)");
        }
        List<String> attemptsString = getAttemptsString();
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

    boolean setPoints(int points) {
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

    public void setScore(Integer score) {
        this.score = score;
    }

    public int getTotalPoints() {
        return getAttempts().stream().mapToInt(n -> n).sum() + getExtraPoints();
    }

    public boolean isStrike() {
        return !getAttempts().isEmpty() && getAttempts().get(0) == Constants.PINS;
    }

    public boolean isSpare() {
        int size = getAttempts().size();
        int sum = size > 1 ? getAttempts().get(size - 2) + getAttempts().get(size - 1) : 0;
        return sum == Constants.PINS;
    }

    public boolean isNormal() {
        return !(isStrike() || isSpare()) && attempts.size() == 2;
    }

    public void addExtraPoints(String points) {
        setExtraPoints(getExtraPoints() + Utils.parsePoints(points));
    }

    @Override
    public String toString() {
        return score + " " + attemptsString;
    }
}
