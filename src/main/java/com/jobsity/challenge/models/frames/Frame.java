package com.jobsity.challenge.models.frames;

import java.util.List;

public interface Frame {
    void setPoints(String points);
    Integer getScore();
    void setScore(int score);
    boolean isDone();
    int getTotalPoints();
    List<String> getPinfalls();
    boolean isCurrent();
}