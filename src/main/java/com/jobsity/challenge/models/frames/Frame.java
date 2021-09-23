package com.jobsity.challenge.models.frames;

import java.util.List;

public interface Frame {
    boolean setPoints(String points);
    Integer getScore();
    void setScore(int score);
    boolean isStrike();
    boolean isSpare();
    boolean isDone();
    void addExtraPoints(String points);
    int getTotalPoints();
    List<String> getPinfalls();
}