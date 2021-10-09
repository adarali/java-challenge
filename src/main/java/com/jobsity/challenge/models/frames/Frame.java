package com.jobsity.challenge.models.frames;

import java.util.List;

public interface Frame {

    /**
     * Adds points to the current frame.
     * @param points
     */
    void setPoints(String points);

    /**
     * gets the frame score.
     * @return
     */
    Integer getScore();

    /**
     * Sets the frame score and returns the new score.
     * @param playerScore the player's current score.
     * @return the newly set score.
     * @throws com.jobsity.challenge.exceptions.AppException if the frame is not done.
     */
    int setScore(int playerScore);

    /**
     * Checks if this method returns true, it means that no more points can be added to the frame
     * and the score must be set.
     * @return true if no more points can be added
     */
    boolean isDone();

    /**
     * This method indicates that the frame is currently in play and a new frame must not be added.
     * @return true if the frame is currently in play, false otherwise.
     */
    boolean isCurrent();

    /**
     * Gets the pinfalls of the frame as a list of strings.
     * @return
     */
    List<String> getPinfalls();
}