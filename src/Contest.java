/**
 * Created by prathmeshjakkanwar on 3/30/17.
 */

import java.util.ArrayList;
import java.util.Date;

public class Contest
{
    public String Name;
    public int SalaryCap ;
    public ArrayList<Game> Games;
    public ArrayList<RosterPosition> RosterPositions;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSalaryCap() {
        return SalaryCap;
    }

    public void setSalaryCap(int salaryCap) {
        SalaryCap = salaryCap;
    }

    public ArrayList<Game> getGames() {
        return Games;
    }

    public void setGames(ArrayList<Game> games) {
        Games = games;
    }

    public ArrayList<RosterPosition> getRosterPositions() {
        return RosterPositions;
    }

    public void setRosterPositions(ArrayList<RosterPosition> rosterPositions) {
        RosterPositions = rosterPositions;
    }
}

class RosterPosition
{

    public String Name ;
    public int Count;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}

class Game
{
    public int GameId ;
    public String AwayTeam;
    public int AwayTeamId;
    public String HomeTeam;
    public int HomeTeamId;
    public Date GameTime;

    public int getGameId() {
        return GameId;
    }

    public void setGameId(int gameId) {
        GameId = gameId;
    }

    public String getAwayTeam() {
        return AwayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        AwayTeam = awayTeam;
    }

    public int getAwayTeamId() {
        return AwayTeamId;
    }

    public void setAwayTeamId(int awayTeamId) {
        AwayTeamId = awayTeamId;
    }

    public String getHomeTeam() {
        return HomeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        HomeTeam = homeTeam;
    }

    public int getHomeTeamId() {
        return HomeTeamId;
    }

    public void setHomeTeamId(int homeTeamId) {
        HomeTeamId = homeTeamId;
    }

    public Date getGameTime() {
        return GameTime;
    }

    public void setGameTime(Date gameTime) {
        GameTime = gameTime;
    }
}
