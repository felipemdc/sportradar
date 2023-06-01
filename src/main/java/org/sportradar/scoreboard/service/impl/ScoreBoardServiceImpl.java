package org.sportradar.scoreboard.service.impl;

import org.sportradar.scoreboard.domain.Game;
import org.sportradar.scoreboard.domain.Score;
import org.sportradar.scoreboard.domain.ScoreBoard;
import org.sportradar.scoreboard.domain.WorldCupTeam;
import org.sportradar.scoreboard.service.GameService;
import org.sportradar.scoreboard.service.ScoreBoardService;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoardServiceImpl implements ScoreBoardService {
    private final GameService gameService;

    public ScoreBoardServiceImpl(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public ScoreBoard createBoard() {
        return new ScoreBoard();
    }

    @Override
    public Game startGame(ScoreBoard board, WorldCupTeam homeTeam, WorldCupTeam awayTeam) {
        int idInBoard = board.getOrderCounter().incrementAndGet();
        Game game = new Game(idInBoard, homeTeam, awayTeam);
        board.getGames().add(game);
        return game;
    }

    @Override
    public void finishGame(final ScoreBoard board, final Game game) {
        board.getGames().remove(game);
    }

    @Override
    public Game updateScore(final ScoreBoard board, final Game game, final Score score) {
        Game newGame = new Game(game, score);
        board.getGames().remove(game);
        board.getGames().add(newGame);
        return newGame;
    }

    @Override
    public List<Game> getGamesSummary(final ScoreBoard board) {
        return board.getGames().stream()
                .sorted((o1, o2) -> {
                    int sortByTotalScore = Integer.compare(gameService.getTotalScore(o2),
                            gameService.getTotalScore(o1));
                    if (sortByTotalScore == 0) {
                        return Integer.compare(o2.getOrderInBoard(), o1.getOrderInBoard());
                    } else {
                        return sortByTotalScore;
                    }
                }).toList();
    }

    @Override
    public List<String> getGamesSummaryAsText(final ScoreBoard board) {
        List<Game> summary = getGamesSummary(board);
        List<String> result = new ArrayList<>();
        int position = 0;
        for (Game game : summary) {
            String summaryLine = gameService.getGameHumanReadableSummaryLine(game);
            result.add(String.format("%d. %s", ++position, summaryLine));
        }
        return result;
    }
}
