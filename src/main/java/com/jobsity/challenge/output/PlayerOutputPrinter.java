package com.jobsity.challenge.output;

import com.jobsity.challenge.models.frames.Frame;
import com.jobsity.challenge.models.players.Player;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.jobsity.challenge.misc.Constants.FRAMES;

@Component
public class PlayerOutputPrinter implements OutputPrinter<Collection< Player>>, AutoCloseable{

    private final PrintWriter printWriter;

    public PlayerOutputPrinter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    @SneakyThrows
    private void printRow(String prefix, Stream<?> stream) {
        Object[] arr = Stream.concat(Stream.of(prefix), stream).toArray();
        String format = "%-12s" + StringUtils.repeat("%-10s", arr.length - 2) + "%s";
        printRow(String.format(format, arr));
    }

    @SneakyThrows
    @Override
    public void print(Collection<Player> players) {
        printRow("Frame", IntStream.range(1, FRAMES + 1).boxed());
        players.forEach(player -> {
            printRow(player.getName());
            Stream<String> pinfalls = player.getFrames().stream()
                    .map(frame -> String.join("  ", frame.getPinfalls()));
            printRow("Pinfalls", pinfalls);
            Stream<Integer> scores = player.getFrames().stream()
                    .map(Frame::getScore);
            printRow("Score", scores);
        });
    }

    private void printRow(String row) {
        printWriter.println(row);
    }

    @PreDestroy
    public void close() {
        printWriter.close();
    }
}
