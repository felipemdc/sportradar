package org.sportradar.scoreboard.domain;

import java.util.Objects;

public class Score {
    private final int homeTeamScore;
    private final int awayTeamScore;

    public Score(int homeTeamScore, int awayTeamScore) {
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return homeTeamScore == score.homeTeamScore && awayTeamScore == score.awayTeamScore;
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeamScore, awayTeamScore);
    }
}
