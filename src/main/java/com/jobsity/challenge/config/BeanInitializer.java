package com.jobsity.challenge.config;

import com.jobsity.challenge.input.FileInputReader;
import com.jobsity.challenge.models.players.Player;
import com.jobsity.challenge.output.OutputPrinter;
import com.jobsity.challenge.output.PlayerOutputStreamOutputPrinter;
import com.jobsity.challenge.processors.LineProcessor;
import com.jobsity.challenge.processors.PlayerLineProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class BeanInitializer {

    @Value("${file}")
    private String filename;

    @Bean
    public OutputPrinter<Player> outputPrinter() {
        return new PlayerOutputStreamOutputPrinter(System.out);
    }

    @Bean
    public LineProcessor<Player> lineProcessor() {
        return new PlayerLineProcessor();
    }

    @Bean
    public FileInputReader inputReader(LineProcessor<Player> lineProcessor) {
//        String filename = arguments.getNonOptionArgs().get(0);
        File file = new File(filename);
        return new FileInputReader(file, lineProcessor);
    }
}
