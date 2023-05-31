package org.sportradar.scoreboard.service.impl;

import org.sportradar.scoreboard.domain.Game;
import org.sportradar.scoreboard.domain.ScoreBoard;
import org.sportradar.scoreboard.domain.WorldCupTeam;
import org.sportradar.scoreboard.service.GameService;
import org.sportradar.scoreboard.service.ScoreBoardService;

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
        Game game = new Game(homeTeam, awayTeam);
        int idInBoard = board.getOrderCounter().incrementAndGet();
        game.setOrderInBoard(idInBoard);
        board.getGames().put(idInBoard, game);
        return game;
    }

    @Override
    public void finishGame(ScoreBoard board, Game game) {
        board.getGames().remove(game.getOrderInBoard());
    }

    @Override
    public List<Game> getGamesSummary(ScoreBoard board) {
        return board.getGames().values().stream()
                .sorted((o1, o2) -> {
                    int sortByTotalScore = Integer.compare(gameService.getTotalScore(o2),
                        gameService.getTotalScore(o1));
                    if (sortByTotalScore == 0) {
                        return Integer.compare(o2.getOrderInBoard(), o1.getOrderInBoard());
                    } else {
                        return sortByTotalScore;
                    }
                })
                .toList();
    }
}
