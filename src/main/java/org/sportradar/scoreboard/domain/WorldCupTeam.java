package org.sportradar.scoreboard.domain;

public enum WorldCupTeam {
    MEXICO("Mexico"),
    CANADA("Canada"),
    SPAIN("Spain"),
    BRAZIL("Brazil"),
    GERMANY("Germany"),
    FRANCE("France"),
    URUGUAY("Uruguay"),
    ITALY("Italy"),
    ARGENTINA("Argentina"),
    AUSTRALIA("Australia");

    private final String name;

    WorldCupTeam(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
