package org.sportradar.scoreboard.domain;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreBoard {
    private final AtomicInteger orderCounter = new AtomicInteger();
    // insertion order (1 first) -> game
    private final Map<Integer, Game> games = new HashMap<>();

    public Map<Integer, Game> getGames() {
        return games;
    }

    public AtomicInteger getOrderCounter() {
        return orderCounter;
    }
}
