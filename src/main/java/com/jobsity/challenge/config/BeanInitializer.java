package com.jobsity.challenge.config;

import com.jobsity.challenge.input.FileInputReader;
import com.jobsity.challenge.input.InputReader;
import com.jobsity.challenge.models.players.Player;
import com.jobsity.challenge.output.OutputPrinter;
import com.jobsity.challenge.output.PlayerOutputStreamOutputPrinter;
import com.jobsity.challenge.processors.LineProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Collection;
import java.util.Map;

@Configuration
public class BeanInitializer {

    @Bean
    public OutputPrinter<Collection<Player>> outputPrinter() {
        return new PlayerOutputStreamOutputPrinter(System.out);
    }

    @Bean
    public InputReader<Map<String, Player>> inputReader(@Value("${file}") String filename, LineProcessor<Map<String,Player>> lineProcessor) {
        File file = new File(filename);
        return new FileInputReader<>(file, lineProcessor);
    }

}
