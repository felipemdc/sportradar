package org.sportradar.scoreboard.domain;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreBoard {
    private final AtomicInteger orderCounter = new AtomicInteger();
    private final Comparator<Game> comparator;
    private final List<Game> games = new ArrayList<>();

    public ScoreBoard(Comparator<Game> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Game> getComparator() {
        return comparator;
    }

    public List<Game> getGames() {
        return games;
    }

    public AtomicInteger getOrderCounter() {
        return orderCounter;
    }
}
