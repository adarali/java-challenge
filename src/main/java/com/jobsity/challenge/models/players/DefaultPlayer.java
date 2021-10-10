package com.jobsity.challenge.models.players;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.models.frames.Frame;
import com.jobsity.challenge.models.frames.FrameFactory;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import static com.jobsity.challenge.misc.Constants.FRAMES;

@EqualsAndHashCode(of = "name")
public class DefaultPlayer implements Player {

    private final FrameFactory frameFactory;
    private final String name;
    private final List<Frame> frames = new ArrayList<>();
    private int score = 0;
    private Frame currentFrame;
    private List<Frame> nonDoneFrames = new ArrayList<>(); //Frames that are not done

    public DefaultPlayer(FrameFactory frameFactory, String name) {
        this.frameFactory = frameFactory;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setPoints(String points) {
        if (frames.isEmpty() || !currentFrame.isCurrent()) {
            addFrame();
        }

        //Adding points to non-done frames and removing done frames
        nonDoneFrames.removeIf(frame -> {
            frame.setPoints(points);
            if (frame.isDone()) {
                this.score = frame.setScore(this.score);
                return true;
            }
            return false;
        });
    }

    @Override
    public List<Frame> getFrames() {
        return frames;
    }

    private void addFrame() {
        if(frames.size() == FRAMES) {
            throw new AppException("The game is over. You cannot add more frames.");
        }
        Frame frame = getNewFrame();
        frames.add(frame);
        nonDoneFrames.add(frame);
        this.currentFrame = frame;
    }

    private Frame getNewFrame() {
        return frameFactory.createFrame(this);
    }

    @Override
    public String toString() {
        return "Player{" + "name='" + name + '\'' +
                ", frames=" + frames +
                ", score=" + score +
                '}';

    }
}
