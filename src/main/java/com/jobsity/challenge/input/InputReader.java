package com.jobsity.challenge.input;

import com.jobsity.challenge.processors.LineProcessor;

import java.util.function.Consumer;

public interface InputReader<T> {

    T read();

}
