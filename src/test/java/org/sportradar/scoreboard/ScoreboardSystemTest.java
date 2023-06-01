package org.sportradar.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sportradar.scoreboard.domain.Game;
import org.sportradar.scoreboard.domain.Score;
import org.sportradar.scoreboard.domain.ScoreBoard;
import org.sportradar.scoreboard.domain.WorldCupTeam;
import org.sportradar.scoreboard.service.GameService;
import org.sportradar.scoreboard.service.ScoreBoardService;
import org.sportradar.scoreboard.service.impl.GameServiceImpl;
import org.sportradar.scoreboard.service.impl.ScoreBoardServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreboardSystemTest {

    private ScoreBoard scoreBoard;
    private ScoreBoardService scoreBoardService;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameServiceImpl();
        scoreBoardService = new ScoreBoardServiceImpl(gameService);
        scoreBoard = scoreBoardService.createBoard();
    }

    private record BoardGameData(WorldCupTeam homeTeam, WorldCupTeam awayTeam,
                                 Score score) {
        BoardGameData(WorldCupTeam homeTeam, WorldCupTeam awayTeam, int homeTeamScore, int awayTeamScore) {
            this(homeTeam, awayTeam, new Score(homeTeamScore, awayTeamScore));
        }
    }

    @Test
    void shouldReturnGamesSummarySortedByScore() {
        // given
        List<BoardGameData> teamPairToScoreMap = List.of(
                new BoardGameData(WorldCupTeam.MEXICO, WorldCupTeam.CANADA, 0, 5),
                new BoardGameData(WorldCupTeam.SPAIN, WorldCupTeam.BRAZIL, 10, 2),
                new BoardGameData(WorldCupTeam.GERMANY, WorldCupTeam.FRANCE, 2, 2),
                new BoardGameData(WorldCupTeam.URUGUAY, WorldCupTeam.ITALY, 6, 6),
                new BoardGameData(WorldCupTeam.ARGENTINA, WorldCupTeam.AUSTRALIA, 3, 1)
        );

        // when
        teamPairToScoreMap.forEach(it -> {
            Game game = scoreBoardService.startGame(scoreBoard, it.homeTeam, it.awayTeam);
            scoreBoardService.updateScore(scoreBoard, game, it.score);
        }
        );
        List<Game> scores = new ArrayList<>(scoreBoardService.getGamesSummary(scoreBoard));

        // then
        assertEquals(5, scores.size());
        assertEquals(WorldCupTeam.URUGUAY, scores.get(0).getHomeTeam());
        assertEquals(WorldCupTeam.ITALY, scores.get(0).getAwayTeam());
        assertEquals(WorldCupTeam.SPAIN, scores.get(1).getHomeTeam());
        assertEquals(WorldCupTeam.BRAZIL, scores.get(1).getAwayTeam());
        assertEquals(10, scores.get(1).getScore().getHomeTeamScore());
        assertEquals(2, scores.get(1).getScore().getAwayTeamScore());
        assertEquals(WorldCupTeam.MEXICO, scores.get(2).getHomeTeam());
        assertEquals(WorldCupTeam.CANADA, scores.get(2).getAwayTeam());
        assertEquals(WorldCupTeam.ARGENTINA, scores.get(3).getHomeTeam());
        assertEquals(WorldCupTeam.AUSTRALIA, scores.get(3).getAwayTeam());
        assertEquals(WorldCupTeam.GERMANY, scores.get(4).getHomeTeam());
        assertEquals(WorldCupTeam.FRANCE, scores.get(4).getAwayTeam());
    }
}
