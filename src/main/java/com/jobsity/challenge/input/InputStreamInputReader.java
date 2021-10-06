package com.jobsity.challenge.input;

import com.jobsity.challenge.processors.LineProcessor;

import java.io.InputStream;
import java.util.Collection;
import java.util.Scanner;

public class InputStreamInputReader<T> extends ScannerInputReader<T> {

    private InputStream inputStream;

    public InputStreamInputReader(InputStream inputStream, LineProcessor<T> lineProcessor) {
        super(lineProcessor);
        this.inputStream = inputStream;
    }

    @Override
    public Collection<T> read() {
        try (Scanner scanner = new Scanner(inputStream)) {
            return super.read(scanner);
        }
    }
}
