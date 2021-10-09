package com.jobsity.challenge.processors;

import com.jobsity.challenge.models.players.Player;
import com.jobsity.challenge.models.players.PlayerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class PlayerLineProcessor implements LineProcessor<Map<String, Player>> {

    private Map<String, Player> map = new HashMap<>();
    private final PlayerFactory playerFactory;

    @Override
    public void process(String line) {
        String[] arr = line.split("\\t");
        String name = arr[0];
        String points = arr[1];
        Player player;
        if(map.containsKey(name)) {
            player = map.get(name);
        } else {
            player = getPlayer(name);
            map.put(name, player);
        }
        player.setPoints(points);
    }

    @Override
    public Map<String, Player> getProcessedData() {
        return map;
    }

    private Player getPlayer(String name) {
        return playerFactory.createPlayer(name);
    }
}
