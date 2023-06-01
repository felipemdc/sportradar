package org.sportradar.scoreboard.service.impl;

import org.sportradar.scoreboard.domain.Game;
import org.sportradar.scoreboard.service.GameService;

public class GameServiceImpl implements GameService {
    @Override
    public int getTotalScore(Game game) {
        return game.getScore().getAwayTeamScore() + game.getScore().getHomeTeamScore();
    }

    @Override
    public String getGameHumanReadableSummaryLine(Game game) {
        return String.format("%s %d x %d %s", game.getHomeTeam().getName(),
                game.getScore().getHomeTeamScore(), game.getScore().getAwayTeamScore(),
                game.getAwayTeam().getName());
    }
}
