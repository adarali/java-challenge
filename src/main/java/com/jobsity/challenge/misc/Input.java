package com.jobsity.challenge.misc;

import com.jobsity.challenge.exceptions.AppException;

import java.util.Scanner;
import java.util.function.Consumer;

public class Input {

    public static void processInput(Scanner scanner, Consumer<String> doForEachLine) {
        int i = 0;
        while (scanner.hasNext()) {
            i++;
            String line = scanner.nextLine();
            if (!line.matches("^\\w.*\\t(F|[0-9]+)$")) {
                throw new AppException(String.format("Line %d is invalid", i));
            }
            doForEachLine.accept(line);
        }
        if (i == 0) {
            throw new AppException("The file is empty");
        }
    }


}
