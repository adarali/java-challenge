package com.jobsity.challenge;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.err.println("Please specify the filename");
            return;
        }
        InputProcessor.processFile(new File(args[0]));
    }
}
