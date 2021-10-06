package com.jobsity.challenge.processors;

import com.jobsity.challenge.models.players.Player;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class PlayerLineProcessor implements LineProcessor<Player> {

    private final Map<String, Player> map;

    public PlayerLineProcessor() {
        this.map = new HashMap<>();
    }

    @Override
    public void process(String line) {
        String[] arr = line.split("\\t");
        String name = arr[0];
        String points = arr[1];
        Player player = map.getOrDefault(name, Player.createPlayer(name));
        player.setPoints(points);
        map.putIfAbsent(name, player);
    }

    @Override
    public Collection<Player> getProcessedData() {
        return map.values();
    }
}
