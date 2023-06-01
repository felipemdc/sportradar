package org.sportradar.scoreboard.service;

import org.sportradar.scoreboard.domain.Game;

public interface GameService {
    int getTotalScore(Game game);

    String getGameHumanReadableSummaryLine(Game game);
}
