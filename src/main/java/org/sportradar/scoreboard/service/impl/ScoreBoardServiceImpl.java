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
        return new ScoreBoard((o1, o2) -> {
            int sortByTotalScore = Integer.compare(gameService.getTotalScore(o2),
                    gameService.getTotalScore(o1));
            if (sortByTotalScore == 0) {
                return Integer.compare(o2.getOrderInBoard(), o1.getOrderInBoard());
            } else {
                return sortByTotalScore;
            }
        });
    }

    @Override
    public Game startGame(ScoreBoard board, WorldCupTeam homeTeam, WorldCupTeam awayTeam) {
        Game game = new Game(homeTeam, awayTeam);
        int idInBoard = board.getOrderCounter().incrementAndGet();
        game.setOrderInBoard(idInBoard);
        board.getGames().add(game);
        board.getGames().sort(board.getComparator());
        return game;
    }

    @Override
    public void finishGame(ScoreBoard board, Game game) {
        board.getGames().remove(game);
    }

    @Override
    public void updateScore(final ScoreBoard board, final Game game, final Score score) {
        game.setScore(score);
        board.getGames().sort(board.getComparator());
    }

    @Override
    public List<Game> getGamesSummary(ScoreBoard board) {
        return board.getGames();
    }

    @Override
    public List<String> getGamesSummaryAsText(ScoreBoard board) {
        List<Game> summary = getGamesSummary(board);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < summary.size(); i++) {
            String summaryLine = gameService.getGameHumanReadableSummaryLine(summary.get(i));
            result.add(String.format("%d. %s", i+1, summaryLine));
        }
        return result;
    }
}
