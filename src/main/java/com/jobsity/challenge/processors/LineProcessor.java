package com.jobsity.challenge.processors;

import java.util.Collection;

public interface LineProcessor<T> {

    void process(String line);
    T getProcessedData();
}
