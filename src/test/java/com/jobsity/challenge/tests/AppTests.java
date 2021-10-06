package com.jobsity.challenge.tests;


import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.input.FileInputReader;
import com.jobsity.challenge.input.InputReader;
import com.jobsity.challenge.input.InputStreamInputReader;
import com.jobsity.challenge.input.ScannerInputReader;
import com.jobsity.challenge.models.frames.Frame;
import com.jobsity.challenge.models.frames.FrameFactory;
import com.jobsity.challenge.models.players.Player;
import com.jobsity.challenge.processors.PlayerLineProcessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.jobsity.challenge.misc.Constants.FRAMES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTests {


    @Test
    void testRegularFrame() {
        AppException ex = assertThrows(AppException.class, () -> createFrame(false, "6", "5"));
        assertEquals("You can't knock down more than 10 pins in a single frame", ex.getMessage());

        assertEquals(Arrays.asList("","X"), createFrame(false, "10").getPinfalls());
        assertEquals(Arrays.asList("","X"), createFrame(false, "10", "7", "3").getPinfalls());
        assertEquals(Arrays.asList("8","/"), createFrame(false, "8", "2").getPinfalls());
        assertEquals(Arrays.asList("7","2"), createFrame(false, "7", "2").getPinfalls());
        assertEquals(Arrays.asList("7","2"), createFrame(false, "7", "2").getPinfalls());
        assertEquals(Arrays.asList("7","F"), createFrame(false, "7", "F").getPinfalls());
        assertEquals(Arrays.asList("F","7"), createFrame(false, "F", "7").getPinfalls());
        assertEquals(Arrays.asList("7","0"), createFrame(false, "7", "0").getPinfalls());
        assertEquals(Arrays.asList("0","7"), createFrame(false, "0", "7").getPinfalls());
        assertEquals(Arrays.asList("0","/"), createFrame(false, "0", "10").getPinfalls());
        assertEquals(Arrays.asList("F","5"), createFrame(false, "F", "5").getPinfalls());
        assertEquals(Arrays.asList("F","/"), createFrame(false, "F", "10").getPinfalls());

        assertFalse(createFrame(false, "4", "6").isDone());
        assertFalse(createFrame(false, "10", "6").isDone());
        assertTrue(createFrame(false, "1", "8").isDone());
        assertTrue(createFrame(false, "1", "8", "1").isDone());

        assertTrue(createFrame(false, "8").isCurrent());
        assertFalse(createFrame(false, "8", "1").isCurrent());
        assertFalse(createFrame(false, "10").isCurrent());

        Frame frame = createFrame(false);
        assertThrows(AppException.class, () -> frame.setScore(30));

    }

    @Test
    void testFinalFrame() {
        assertThrows(AppException.class, () -> createFrame(true, "11"));

        Frame frame = createFrame(true, "10", "7");
        assertThrows(AppException.class, () -> frame.setPoints("4"));
        frame.setPoints("3");
        assertEquals(Arrays.asList("X","7","/"), frame.getPinfalls());

        assertThrows(AppException.class, () -> createFrame(true, "10", "8", "3"));
        assertThrows(AppException.class, () -> createFrame(true, "10", "10", "20"));

        assertEquals(Arrays.asList("3", "/", "4"), createFrame(true, "3", "7", "4").getPinfalls());
        assertEquals(Arrays.asList("3", "/", "X"), createFrame(true, "3", "7", "10").getPinfalls());
        assertEquals(Arrays.asList("X", "X", "4"), createFrame(true, "10", "10", "4").getPinfalls());
        assertEquals(Arrays.asList("X", "X", "X"), createFrame(true, "10", "10", "10").getPinfalls());
        assertEquals(Arrays.asList("X", "F", "/"), createFrame(true, "10", "F", "10").getPinfalls());
        assertEquals(Arrays.asList("F", "/", "X"), createFrame(true, "F", "10", "10").getPinfalls());

        assertTrue(createFrame(true, "5", "4").isDone());
        assertFalse(createFrame(true, "6", "4").isDone());
        assertFalse(createFrame(true, "10", "4").isDone());
        assertTrue(createFrame(true, "10", "4", "3").isDone());
        assertTrue(createFrame(true, "10", "4", "5").isDone());
    }


    @Test
    void testPerfectScore() {
        Map<String, Player> map = new HashMap<>();
            new InputStreamInputReader<>(getResource("positive/perfect.txt"), new PlayerLineProcessor(map)).read();
            String key = map.keySet().stream().findAny().orElse(null);
            List<Frame> frames = map.get(key).getFrames();
            List<Integer> scores = frames.stream().map(Frame::getScore).collect(Collectors.toList());
            int i;
            for (i = 0; i < frames.size() - 1; i++) {
                assertEquals(Arrays.asList("", "X"), frames.get(i).getPinfalls());
            }
            assertEquals(Arrays.asList("X", "X", "X"), frames.get(i).getPinfalls());
            assertEquals(Arrays.asList(30, 60, 90, 120, 150, 180, 210, 240, 270, 300), scores);

    }

    @Test
    void testZeros() {
        Map<String, Player> map = new HashMap<>();

        getInputReader(getResource("positive/zeros.txt"), map).read();
        List<Frame> frames = Objects.requireNonNull(map.values().stream().findFirst().orElse(null)).getFrames();
        assertEquals(FRAMES, frames.size());
        assertTrue(frames.stream().map(Frame::getScore).allMatch(n -> n == 0));


    }

    @Test
    void testScores() {
        Map<String, Player> map = new HashMap<>();

        new InputStreamInputReader<>(getResource("positive/scores.txt"), new PlayerLineProcessor(map)).read();
        List<Frame> jeffFrames = map.get("Jeff").getFrames();
        List<Frame> johnFrames = map.get("John").getFrames();

        List<Integer> expectedJeffScores = Arrays.asList(20, 39, 48, 66, 74, 84, 90, 120, 148, 167);
        assertEquals(expectedJeffScores, jeffFrames.stream().map(Frame::getScore).collect(Collectors.toList()));

        List<Integer> expectedJohnScores = Arrays.asList(16, 25, 44, 53, 82, 101, 110, 124, 132, 151);
        assertEquals(expectedJohnScores, johnFrames.stream().map(Frame::getScore).collect(Collectors.toList()));

        assertEquals(Arrays.asList("3", "/"), johnFrames.get(0).getPinfalls());
        assertEquals(Arrays.asList("7", "/"), johnFrames.get(7).getPinfalls());
        assertEquals(Arrays.asList("8", "/"), jeffFrames.get(5).getPinfalls());
        assertEquals(Arrays.asList("", "X"), jeffFrames.get(0).getPinfalls());



    }

    @Test
    void testNegativeInput() {
        File dir = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("negative")).getFile());
        for (File file : Objects.requireNonNull(dir.listFiles())) {

            Map<String, Player> map = new HashMap<>();
            String message = assertThrows(AppException.class, () -> new FileInputReader<>(file, new PlayerLineProcessor(map)).read()
                    , String.format("File \"%s\" does not throw exception", file.getName()))
                    .getMessage();

            switch (file.getName()) {
                case "free-text.txt":
                    assertEquals("Line 1 is invalid", message);
                    break;
                case "negative.txt":
                case "invalid-score.txt":
                    assertEquals("Line 2 is invalid", message);
                    break;
                case "empty.txt":
                    assertEquals("The file is empty", message);
                    break;
                case "extra-score.txt":
                case "zeros-extra-score.txt":
                    assertEquals("The game is over. You cannot add more frames.", message);
                    break;
            }

        }

    }

    private Frame createFrame(boolean isFinal, String... points) {
        Frame frame = FrameFactory.createFrame(isFinal ? FRAMES - 1 : 0);
        for (String point : points) {
            frame.setPoints(point);
        }
        return frame;
    }

    private InputStream getResource(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }

    private InputReader<Collection<Player>> getInputReader(InputStream inputStream, Map<String, Player> map) {
        return new InputStreamInputReader<>(inputStream, new PlayerLineProcessor(map));
    }


}
