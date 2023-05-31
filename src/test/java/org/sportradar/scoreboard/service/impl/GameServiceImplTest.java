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
    void shouldUpdateScore() {
        // given
        Game game = new Game(WorldCupTeam.MEXICO, WorldCupTeam.ARGENTINA);
        Score score = new Score(4, 5);

        // when
        underTest.updateScore(game, score);

        // then
        assertEquals(4, game.getScore().getHomeTeamScore());
        assertEquals(5, game.getScore().getAwayTeamScore());
    }
}