package com.jobsity.challenge.input;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.processors.LineProcessor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Scanner;

@Component
@Profile("!test")
public class FileInputReader<T> extends ScannerInputReader<T>{

    private File file;

    @SneakyThrows
    public FileInputReader(@Value("${file}") File file, LineProcessor<T> lineProcessor) {
        super(lineProcessor);
        this.file = file;
    }

    @SneakyThrows
    @Override
    public T read() {
        try (Scanner scanner = new Scanner(file)) {
            return read(scanner);
        }
    }

    @Override
    protected void doIfEmpty() {
        throw new AppException("The file is empty");
    }
}
