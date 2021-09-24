package com.jobsity.challenge.tests;


import com.jobsity.challenge.misc.Input;
import com.jobsity.challenge.exceptions.AppException;
import static com.jobsity.challenge.misc.Constants.*;

import com.jobsity.challenge.misc.Utils;
import com.jobsity.challenge.models.Player;
import com.jobsity.challenge.models.frames.Frame;
import com.jobsity.challenge.models.frames.FrameFactory;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class AppTests {

    @Test
    void testRegularFrame() {
        assertThrows(AppException.class, () -> createFrame(false, "6", "5"));
        assertThrows(AppException.class, () -> createFrame(false, "6", "16"));

        assertEquals(Arrays.asList("","X"), createFrame(false, "10").getPinfalls());
        assertEquals(Arrays.asList("8","/"), createFrame(false, "8", "2").getPinfalls());
        assertEquals(Arrays.asList("7","2"), createFrame(false, "7", "2").getPinfalls());
        assertEquals(Arrays.asList("7","2"), createFrame(false, "7", "2").getPinfalls());
        assertEquals(Arrays.asList("7","F"), createFrame(false, "7", "F").getPinfalls());
        assertEquals(Arrays.asList("0","/"), createFrame(false, "0", "10").getPinfalls());
        assertEquals(Arrays.asList("F","5"), createFrame(false, "F", "5").getPinfalls());
        assertEquals(Arrays.asList("F","/"), createFrame(false, "F", "10").getPinfalls());

        assertTrue(createFrame(false, "10").isStrike());
        assertFalse(createFrame(false, "8").isStrike());
        assertTrue(createFrame(false, "1", "9").isSpare());
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
    }


    @Test
    void testPerfectInput() {
        Map<String, Player> map = new HashMap<>();
        try(Scanner scanner = new Scanner(getResource("positive/perfect.txt"))) {
            Input.processInput(scanner, line -> Utils.setScores(map, line));
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
    }

    @Test
    void testZeros() {
        Map<String, Player> map = new HashMap<>();
        try(Scanner scanner = new Scanner(getResource("positive/zeros.txt"))) {
            Input.processInput(scanner, line -> Utils.setScores(map, line));
            List<Frame> frames = Objects.requireNonNull(map.values().stream().findFirst().orElse(null)).getFrames();
            assertEquals(FRAMES, frames.size());
            assertTrue(frames.stream().map(Frame::getScore).allMatch(n -> n == 0));
        }

    }

    @Test
    void testScores() {
        Map<String, Player> map = new HashMap<>();
        try(Scanner scanner = new Scanner(getResource("positive/scores.txt"))) {
            Input.processInput(scanner, line -> Utils.setScores(map, line));
            List<Frame> jeffFrames = map.get("Jeff").getFrames();
            List<Frame> johnFrames = map.get("John").getFrames();

            List<Integer> expectedJeffScores = Arrays.asList(20, 39, 48, 66, 74, 84, 90, 120, 148, 167);
            assertEquals(expectedJeffScores, jeffFrames.stream().map(Frame::getScore).collect(Collectors.toList()));


            List<Integer> expectedJohnScores = Arrays.asList(16, 25, 44, 53, 82, 101, 110, 124, 132, 151);
            assertEquals(expectedJohnScores, johnFrames.stream().map(Frame::getScore).collect(Collectors.toList()));
        }

    }

    @Test
    void testNegativeInput() throws FileNotFoundException {
        File dir = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("negative")).getFile());
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            try (Scanner scanner = new Scanner(file)) {
                Map<String, Player> map = new HashMap<>();
                String message = assertThrows(AppException.class, () -> Input.processInput(scanner, line -> Utils.setScores(map, line)))
                        .getMessage();

                switch (file.getName()) {
                    case "free-text.txt": assertEquals("Line: 1 is invalid", message); break;
                    case "negative.txt": assertEquals("Line: 2 is invalid", message); break;
                    case "empty.txt": assertEquals("The file is empty", message); break;
                    case "extra-score.txt": assertEquals("Too many attempts", message); break;
                    case "invalid-score.txt": assertEquals("Line: 2 is invalid", message); break;
                }

            }
        }
    }

    private Frame createFrame(boolean isFinal, String... points) {
        Frame frame = FrameFactory.createFrame(points[0], isFinal ? FRAMES - 1 : 0);
        for (int i = 1; i < points.length; i++) {
            frame.setPoints(points[i]);
        }
        return frame;
    }

    private InputStream getResource(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }
}
