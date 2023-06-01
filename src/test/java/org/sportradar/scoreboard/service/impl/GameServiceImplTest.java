package org.sportradar.scoreboard.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sportradar.scoreboard.domain.Game;
import org.sportradar.scoreboard.domain.Score;
import org.sportradar.scoreboard.domain.WorldCupTeam;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceImplTest {
    GameServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new GameServiceImpl();
    }

    @Test
    void shouldCalculateTotalScore() {
        // given
        Game game = new Game(WorldCupTeam.MEXICO, WorldCupTeam.ARGENTINA);
        Score score = new Score(4, 5);
        game.setScore(score);

        // when
        int result = underTest.getTotalScore(game);

        // then
        assertEquals(9, result);
    }

    @Test
    void shouldReturnGameSummaryInHumanReadableFashion() {
        // given
        Game game = new Game(WorldCupTeam.MEXICO, WorldCupTeam.ARGENTINA);
        game.setScore(new Score(4, 5));

        // when
        String result = underTest.getGameHumanReadableSummaryLine(game);

        // then
        assertEquals("Mexico 4 x 5 Argentina", result);
    }
}