package org.sportradar.scoreboard.service.impl;

import org.sportradar.scoreboard.domain.Game;
import org.sportradar.scoreboard.domain.Score;
import org.sportradar.scoreboard.service.GameService;

public class GameServiceImpl implements GameService {
    @Override
    public void updateScore(final Game game, final Score score) {
        game.setScore(score);
    }

    @Override
    public int getTotalScore(Game game) {
        return game.getScore().getAwayTeamScore() + game.getScore().getHomeTeamScore();
    }
}
