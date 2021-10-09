package com.jobsity.challenge.input;


import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.processors.LineProcessor;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Scanner;

@RequiredArgsConstructor
public abstract class ScannerInputReader<T> implements InputReader<T> {

    private final LineProcessor<T> lineProcessor;

    protected T read(Scanner scanner) {

        int i = 0;

        while (scanner.hasNext()) {
            i++;
            String line = scanner.nextLine();
            if (!line.matches("^\\w.*\\t(F|[0-9]+)$")) {
                throw new AppException(String.format("Line %d is invalid", i));
            }
            lineProcessor.process(line);
        }
        if (i == 0) {
            throw new AppException("The file is empty");
        }
        return lineProcessor.getProcessedData();
    }
}
