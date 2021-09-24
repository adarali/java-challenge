package com.jobsity.challenge.misc;

import com.jobsity.challenge.models.players.Player;
import com.jobsity.challenge.models.frames.Frame;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.jobsity.challenge.misc.Constants.FRAMES;

public class Output {

    public static void print(Collection<Player> collection) {

        printRow("Frame", IntStream.range(1, FRAMES + 1).boxed());

        collection.forEach(player -> {
            System.out.println(player.getName());
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
