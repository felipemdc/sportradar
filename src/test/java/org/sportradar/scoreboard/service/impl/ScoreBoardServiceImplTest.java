package org.sportradar.scoreboard.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sportradar.scoreboard.domain.Game;
import org.sportradar.scoreboard.domain.Score;
import org.sportradar.scoreboard.domain.ScoreBoard;
import org.sportradar.scoreboard.domain.WorldCupTeam;
import org.sportradar.scoreboard.service.GameService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoreBoardServiceImplTest {
    private static final String SPAIN_VS_BRAZIL = "spainVsBrazil";
    private static final String MEXICO_VS_CANADA = "mexicoVsCanada";

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
        List<Game> board = new ArrayList<>(underTest.getGamesSummary(scoreBoard));

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
        List<Game> board = new ArrayList<>(underTest.getGamesSummary(scoreBoard));

        // then
        assertEquals(board.size(), 1);
        assertEquals(board.get(0), gameMexicoVsCanada);
    }

    @Test
    void shouldUpdateScore() {
        // given
        Game game = new Game(1, WorldCupTeam.MEXICO, WorldCupTeam.ARGENTINA);
        Score score = new Score(4, 5);

        // when
        Game result = underTest.updateScore(scoreBoard, game, score);

        // then
        assertEquals(4, result.getScore().getHomeTeamScore());
        assertEquals(5, result.getScore().getAwayTeamScore());
    }

    private record TeamPair(WorldCupTeam homeTeam, WorldCupTeam awayTeam) {
    }

    @Test
    void shouldReturnGamesSummarySortedByScore() {
        // given
        List<TeamPair> boardData = List.of(
                new TeamPair(WorldCupTeam.MEXICO, WorldCupTeam.CANADA),
                new TeamPair(WorldCupTeam.SPAIN, WorldCupTeam.BRAZIL)
        );

        // when
        boardData.forEach(it ->
                underTest.startGame(scoreBoard, it.homeTeam, it.awayTeam)
        );
        List<Game> scores = new ArrayList<>(underTest.getGamesSummary(scoreBoard));

        // then
        assertEquals(2, scores.size());
        assertEquals(WorldCupTeam.SPAIN, scores.get(0).getHomeTeam());
        assertEquals(WorldCupTeam.BRAZIL, scores.get(0).getAwayTeam());
        assertEquals(WorldCupTeam.MEXICO, scores.get(1).getHomeTeam());
        assertEquals(WorldCupTeam.CANADA, scores.get(1).getAwayTeam());
    }

    @Test
    void shouldReturnGamesSummaryInHumanReadableFormat() {
        // given
        Game mexicoVsCanada = underTest.startGame(scoreBoard, WorldCupTeam.MEXICO, WorldCupTeam.CANADA);
        Game spainVsBrazil = underTest.startGame(scoreBoard, WorldCupTeam.SPAIN, WorldCupTeam.BRAZIL);
        when(gameService.getGameHumanReadableSummaryLine(mexicoVsCanada)).thenReturn(MEXICO_VS_CANADA);
        when(gameService.getGameHumanReadableSummaryLine(spainVsBrazil)).thenReturn(SPAIN_VS_BRAZIL);

        // when
        List<String> scores = underTest.getGamesSummaryAsText(scoreBoard);

        // then
        assertEquals(2, scores.size());
        assertEquals("1. " + SPAIN_VS_BRAZIL, scores.get(0));
        assertEquals("2. " + MEXICO_VS_CANADA, scores.get(1));
    }
}