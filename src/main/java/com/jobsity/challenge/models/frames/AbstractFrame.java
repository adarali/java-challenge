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

@Getter(AccessLevel.PROTECTED)
public abstract class AbstractFrame implements Frame {

    private int frameNumber;
    private Integer score;
    private List<Integer> attempts = new ArrayList<>();
    @Getter(AccessLevel.PUBLIC)
    private List<String> pinfalls = new ArrayList<>();
    private int extraPoints = 0;

    protected AbstractFrame(String points, int frameNumber) {
        setPoints(points);
        this.frameNumber = frameNumber;
    }

    @Override
    public boolean setPoints(String pointsString) {
        List<Integer> attempts = getAttempts();
        int points = Utils.parsePoints(pointsString);
        if(IntStream.range(0, Constants.PINS + 1).noneMatch(n -> n == points)) {
            throw new AppException("Points must be between 0 and " + Constants.PINS + " (inclusive)");
        }
        List<String> pinfalls = getPinfalls();
        if (setPoints(points)) {
            String p = points == Constants.PINS ? "X" : pointsString;
            pinfalls.add(p);
            insertStrike();
            insertSlash();
            return true;
        }
        return false;
    }

    protected void insertStrike() {
        if(isStrike()) pinfalls.add(0, "");
    }

    protected void insertSlash() {
        List<Integer> attempts = getAttempts();
        int size = attempts.size();
        if(isStrike() || size < 2) return;
        int last1 = attempts.get(size - 1);
        int last2 = attempts.get(size - 2);
        boolean b = last2 < Constants.PINS && last1 + last2 == Constants.PINS;
        if(b) getPinfalls().set(size - 1, "/");

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

    protected void setExtraPoints(int extraPoints) {
        this.extraPoints = extraPoints;
    }

    @Override
    public int getTotalPoints() {
        return getAttempts().stream().mapToInt(n -> n).sum() + getExtraPoints();
    }

    @Override
    public String toString() {
        return getScore() + " " + getPinfalls();
    }
}
