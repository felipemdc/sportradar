package org.sportradar.scoreboard.domain;

import java.util.Objects;

public class Game {
    private final int orderInBoard;
    private final WorldCupTeam homeTeam;
    private final WorldCupTeam awayTeam;
    private final Score score;

    public Game(int orderInBoard, WorldCupTeam homeTeam, WorldCupTeam awayTeam) {
        this.orderInBoard = orderInBoard;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        score = new Score(0, 0);
    }

    public Game(int orderInBoard, WorldCupTeam homeTeam, WorldCupTeam awayTeam, Score score) {
        this.orderInBoard = orderInBoard;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = score;
    }

    public Game(Game game, Score score) {
        this.orderInBoard = game.orderInBoard;
        this.homeTeam = game.homeTeam;
        this.awayTeam = game.awayTeam;
        this.score = score;
    }

    public int getOrderInBoard() {
        return orderInBoard;
    }

    public WorldCupTeam getHomeTeam() {
        return homeTeam;
    }

    public WorldCupTeam getAwayTeam() {
        return awayTeam;
    }

    public Score getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(homeTeam, game.homeTeam) && Objects.equals(awayTeam, game.awayTeam) && Objects.equals(score, game.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam, score);
    }
}
