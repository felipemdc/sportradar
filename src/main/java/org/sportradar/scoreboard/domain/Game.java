package org.sportradar.scoreboard.domain;

import java.util.Objects;

public class Game {
    private int orderInBoard = 0;
    private final WorldCupTeam homeTeam;
    private final WorldCupTeam awayTeam;
    private Score score;

    public Game(WorldCupTeam homeTeam, WorldCupTeam awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        score = new Score(0, 0);
    }

    public int getOrderInBoard() {
        return orderInBoard;
    }

    public void setOrderInBoard(int orderInBoard) {
        this.orderInBoard = orderInBoard;
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


    public void setScore(Score score) {
        this.score = score;
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
