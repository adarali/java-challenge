package com.jobsity.challenge;

import com.jobsity.challenge.misc.Constants;
import com.jobsity.challenge.models.Player;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Output {

    public static void print(Map<String, Player> map) {
        String tab = "\t\t";

        String frames = IntStream.range(1, Constants.FRAMES + 1).boxed()
                .map(Object::toString).collect(Collectors.joining(tab, "Frame\t\t", ""));

        System.out.println(frames);

        map.forEach((name, player) -> {
            System.out.println(name);
            String pinfalls = player.getFrames().stream().map(frame -> String.join("\t", frame.getPinfalls()))
                    .collect(Collectors.joining("    ", "Pinfalls\t\t", ""));
            System.out.println(pinfalls);
            String scores = player.getFrames().stream().map(frame -> frame.getScore())
                    .map(Objects::toString).collect(Collectors.joining("        ", "Score\t\t", ""));
            System.out.println(scores);
        });
    }

}
