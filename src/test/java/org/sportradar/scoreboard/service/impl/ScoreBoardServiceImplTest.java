package org.sportradar.scoreboard.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sportradar.scoreboard.domain.Game;
import org.sportradar.scoreboard.domain.ScoreBoard;
import org.sportradar.scoreboard.domain.WorldCupTeam;
import org.sportradar.scoreboard.service.GameService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScoreBoardServiceImplTest {
    private ScoreBoard scoreBoard;
    private ScoreBoardServiceImpl underTest;
    @Mock
    private GameService gameService;


    @BeforeEach
    void setUp() {
        underTest = new ScoreBoardServiceImpl(gameService);
        scoreBoard = new ScoreBoard();
    }

    @Test
    void shouldCreateGame() {
        // given

        // when
        Game game = underTest.startGame(scoreBoard, WorldCupTeam.BRAZIL, WorldCupTeam.GERMANY);

        // then
        assertEquals(game.getHomeTeam(), WorldCupTeam.BRAZIL);
        assertEquals(game.getAwayTeam(), WorldCupTeam.GERMANY);
        assertEquals(game.getScore().getHomeTeamScore(), 0);
        assertEquals(game.getScore().getAwayTeamScore(), 0);
    }

    @Test
    void shouldAddStartedGameToBoard() {
        // given

        // when
        Game game = underTest.startGame(scoreBoard, WorldCupTeam.BRAZIL, WorldCupTeam.GERMANY);
        List<Game> board = underTest.getGamesSummary(scoreBoard);

        // then
        assertEquals(board.size(), 1);
        assertEquals(board.get(0), game);
    }

    @Test
    void shouldRemoveGameFromBoard() {
        // given
        WorldCupTeam teamBrazil = WorldCupTeam.BRAZIL;
        WorldCupTeam teamGermany = WorldCupTeam.GERMANY;
        WorldCupTeam teamMexico = WorldCupTeam.MEXICO;
        WorldCupTeam teamCanada = WorldCupTeam.CANADA;

        // when
        Game gameBrazilVsGermany = underTest.startGame(scoreBoard, teamBrazil, teamGermany);
        Game gameMexicoVsCanada = underTest.startGame(scoreBoard, teamMexico, teamCanada);
        underTest.finishGame(scoreBoard, gameBrazilVsGermany);
        List<Game> board = underTest.getGamesSummary(scoreBoard);

        // then
        assertEquals(board.size(), 1);
        assertEquals(board.get(0), gameMexicoVsCanada);
    }

    private record TeamPair(WorldCupTeam homeTeam, WorldCupTeam awayTeam) {
    }

    @Test
    void shouldReturnGamesSummarySortedByScore() {
        // given
        List<TeamPair> boardData = List.of(
                new TeamPair(WorldCupTeam.MEXICO, WorldCupTeam.CANADA),
                new TeamPair(WorldCupTeam.SPAIN, WorldCupTeam.BRAZIL),
                new TeamPair(WorldCupTeam.GERMANY, WorldCupTeam.FRANCE),
                new TeamPair(WorldCupTeam.URUGUAY, WorldCupTeam.ITALY),
                new TeamPair(WorldCupTeam.ARGENTINA, WorldCupTeam.AUSTRALIA)
        );

        // when
        boardData.forEach(it ->
                underTest.startGame(scoreBoard, it.homeTeam, it.awayTeam)
        );
        List<Game> scores = underTest.getGamesSummary(scoreBoard);

        // then
        assertEquals(5, scores.size());
        assertEquals(WorldCupTeam.URUGUAY, scores.get(0).getHomeTeam());
        assertEquals(WorldCupTeam.ITALY, scores.get(0).getAwayTeam());
        assertEquals(WorldCupTeam.SPAIN, scores.get(1).getHomeTeam());
        assertEquals(WorldCupTeam.BRAZIL, scores.get(1).getAwayTeam());
        assertEquals(WorldCupTeam.MEXICO, scores.get(2).getHomeTeam());
        assertEquals(WorldCupTeam.CANADA, scores.get(2).getAwayTeam());
        assertEquals(WorldCupTeam.ARGENTINA, scores.get(3).getHomeTeam());
        assertEquals(WorldCupTeam.AUSTRALIA, scores.get(3).getAwayTeam());
        assertEquals(WorldCupTeam.GERMANY, scores.get(4).getHomeTeam());
        assertEquals(WorldCupTeam.FRANCE, scores.get(4).getAwayTeam());
    }

    @Test
    void shouldReturnGamesSummaryInHumanReadableFormat() {
        // given
        List<TeamPair> boardData = List.of(
                new TeamPair(WorldCupTeam.MEXICO, WorldCupTeam.CANADA),
                new TeamPair(WorldCupTeam.SPAIN, WorldCupTeam.BRAZIL)
        );

        // when
        boardData.forEach(it ->
                underTest.startGame(scoreBoard, it.homeTeam, it.awayTeam)
        );
        List<String> scores = underTest.getGamesSummaryAsText(scoreBoard);

        // then
        assertEquals(2, scores.size());
        assertEquals("1. Spain 0 x 0 Brazil", scores.get(0));
        assertEquals("2. Mexico 0 x 0 Canada", scores.get(1));
    }
}