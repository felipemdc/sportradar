package org.sportradar.scoreboard.service;

import org.sportradar.scoreboard.domain.Game;
import org.sportradar.scoreboard.domain.Score;

public interface GameService {
    void updateScore(Game game, Score score);

    int getTotalScore(Game game);
}
