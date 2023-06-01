package org.sportradar.scoreboarddemo;

import org.sportradar.scoreboard.domain.Game;
import org.sportradar.scoreboard.domain.Score;
import org.sportradar.scoreboard.domain.ScoreBoard;
import org.sportradar.scoreboard.domain.WorldCupTeam;
import org.sportradar.scoreboard.service.GameService;
import org.sportradar.scoreboard.service.ScoreBoardService;
import org.sportradar.scoreboard.service.impl.GameServiceImpl;
import org.sportradar.scoreboard.service.impl.ScoreBoardServiceImpl;

import java.util.Scanner;

// console demo app
public class Main {
    Scanner ins = new Scanner(System.in);
    ScoreBoardService scoreBoardService;
    GameService gameService;
    ScoreBoard scoreBoard;

    public Main() {
        gameService = new GameServiceImpl();
        scoreBoardService = new ScoreBoardServiceImpl(gameService);
        scoreBoard = scoreBoardService.createBoard();
    }

    public static void main(String[] args) {
        System.out.println("Hello to the sport radar demo app!");
        Main main = new Main();
        while (true) {
            main.processUserInput();
        }
    }

    private void processUserInput() {
        System.out.println("Available commands: add = add new game, summary = summarize games, exit = exit program");
        String input = ins.nextLine();
        switch (input) {
            case "add":
                processUserAddInput();
                break;
            case "summary":
                for (String line : scoreBoardService.getGamesSummaryAsText(scoreBoard)) {
                    System.out.println(line);
                }
                break;
            case "exit":
                System.exit(0);
        }
    }

    public static WorldCupTeam getWorldcupTeamFromString(String enumText) {
        for (WorldCupTeam value : WorldCupTeam.values()) {
            if (value.name().equalsIgnoreCase(enumText)) {
                return value;
            }
        }
        return null; // Enum value not found for the given text
    }

    private void processUserAddInput() {
        System.out.println("Input the game data in the format: hometeam,awayteam,homegoals,awaygoals");
        String input = ins.nextLine();
        String[] teams = input.split(",");
        WorldCupTeam homeTeam = getWorldcupTeamFromString(teams[0]);
        WorldCupTeam awayTeam = getWorldcupTeamFromString(teams[1]);
        Game game = scoreBoardService.startGame(scoreBoard, homeTeam, awayTeam);
        int homeTeamGoals = Integer.parseInt(teams[2]);
        int awayTeamGoals = Integer.parseInt(teams[2]);
        scoreBoardService.updateScore(scoreBoard, game, new Score(homeTeamGoals, awayTeamGoals));
    }
}