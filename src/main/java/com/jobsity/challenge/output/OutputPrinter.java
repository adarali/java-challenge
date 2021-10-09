package com.jobsity.challenge.output;

import com.jobsity.challenge.models.players.Player;

import java.util.Collection;

public interface OutputPrinter<T> {
    void print(T players);
}
