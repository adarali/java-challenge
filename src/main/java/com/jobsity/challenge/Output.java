package com.jobsity.challenge;

import com.jobsity.challenge.models.Player;
import com.jobsity.challenge.models.frames.Frame;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.jobsity.challenge.misc.Constants.FRAMES;

public class Output {

    public static void print(Map<String, Player> map) {

        printRow("Frame", IntStream.range(1, FRAMES + 1).boxed());

        map.forEach((name, player) -> {
            System.out.println(name);
            Stream<String> pinfalls = player.getFrames().stream()
                    .map(frame -> String.join("   ", frame.getPinfalls()));
            printRow("Pinfalls", pinfalls);
            Stream<Integer> scores = player.getFrames().stream()
                    .map(Frame::getScore);
            printRow("Score", scores);
        });
    }

    private static void printRow(String prefix, Stream<?> stream) {
        Object[] arr = Stream.concat(Stream.of(prefix), stream).toArray();
        System.out.println(String.format(StringUtils.repeat("%-12s", arr.length), arr).trim());
    }

}
