package org.sportradar.scoreboard.service;

import org.sportradar.scoreboard.domain.Game;
import org.sportradar.scoreboard.domain.ScoreBoard;
import org.sportradar.scoreboard.domain.WorldCupTeam;

import java.util.List;

public interface ScoreBoardService {
    ScoreBoard createBoard();

    Game startGame(ScoreBoard board, WorldCupTeam homeTeam, WorldCupTeam awayTeam);

    void finishGame(ScoreBoard board, Game game);

    List<Game> getGamesSummary(ScoreBoard board);
}
