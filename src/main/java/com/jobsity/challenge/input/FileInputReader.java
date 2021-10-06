package com.jobsity.challenge.input;

import com.jobsity.challenge.processors.LineProcessor;
import lombok.SneakyThrows;

import java.io.File;
import java.util.Collection;
import java.util.Scanner;

public class FileInputReader<T> extends ScannerInputReader<T>{

    private File file;

    @SneakyThrows
    public FileInputReader(File file, LineProcessor<T> lineProcessor) {
        super(lineProcessor);
        this.file = file;
    }

    @SneakyThrows
    @Override
    public Collection<T> read() {
        try (Scanner scanner = new Scanner(file)) {
            return read(scanner);
        }
    }
}
