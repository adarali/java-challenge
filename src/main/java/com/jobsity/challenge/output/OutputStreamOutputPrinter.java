package com.jobsity.challenge.output;

import com.jobsity.challenge.misc.Utils;
import com.jobsity.challenge.models.frames.Frame;
import com.jobsity.challenge.models.players.Player;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.jobsity.challenge.misc.Constants.FRAMES;

public class OutputStreamOutputPrinter implements OutputPrinter<Player>{

    private OutputStream outputStream;

    public OutputStreamOutputPrinter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void print(Collection<Player> players) {
        printRow("Frame", IntStream.range(1, FRAMES + 1).boxed());

        players.forEach(player -> {
            printRow(player.getName(), Stream.empty());
            Stream<String> pinfalls = player.getFrames().stream()
                    .map(frame -> String.join("  ", frame.getPinfalls()));
            printRow("Pinfalls", pinfalls);
            Stream<Integer> scores = player.getFrames().stream()
                    .map(Frame::getScore);
            printRow("Score", scores);
        });
    }

    @SneakyThrows
    private void printRow(String prefix, Stream<?> stream) {
        Object[] arr = Stream.concat(Stream.of(prefix), stream).toArray();
        String s = String.format(Utils.repeatString("%-10s", arr.length - 1) + "%s" + "%n", arr);
        IOUtils.write(s, this.outputStream, StandardCharsets.UTF_8);
    }
}
