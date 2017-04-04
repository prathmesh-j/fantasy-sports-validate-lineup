/**
 * Created by prathmeshjakkanwar on 3/30/17.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FantasyMain {

    // CONSTANTS
    private final int MAX_PLAYERS_IN_A_GAME = 3;
    private final int GAMES_A_LINEUP_MUST_ENCOMPASS = 2;

    //1. All roster positions listed in the contest must be filled by the lineup
    //2. The lineup must encompass at least two games
    //3. There can not be more than 3 players on a single team per game
    //4. The sum of player salary can not exceed the contests max salary cap
    //5. The lineup can not contain more than the required amount of players
    //6. Any single player can only be used once

    public boolean ValidateLine(Contest contest, ArrayList<Player> players, ArrayList<Lineup> lineup) {
        HashMap<Integer, Player> playerMap = new HashMap<>();
        for (Player p : players) {
            playerMap.put(p.playerId, p);
        }

        boolean validation1 = isContestFilledByLineup(contest, lineup);
        if (!validation1) {
            return false;
        }
        boolean validation2 = doesLineupCoverTwoGames(contest,playerMap,lineup);
        if (!validation2) {
            return false;
        }
        boolean validation3 = checkPlayersOnATeamPerGame(contest, players);
        if (!validation3) {
            return false;
        }
        boolean validation4 = isPlayersSalaryGreaterThanMaxCap(contest, lineup, playerMap);
        if (!validation4) {
            return false;
        }
        boolean validation5 = maxPlayerCountValidation(contest, lineup);
        if (!validation5) {
            return false;
        }
        boolean validation6 = isPLayerUsedOnlyOnce(lineup, playerMap);
        if (!validation6){
            return false;
        }

        return true;
    }


    //1. All roster positions listed in the contest must be filled by the lineup
    private boolean isContestFilledByLineup(Contest contest, ArrayList<Lineup> lineup){
        HashMap<String, Integer> rosterPositionMap  = new HashMap<>();
        for (RosterPosition rp : contest.RosterPositions) {
            rosterPositionMap.put(rp.Name, rp.Count);
        }

        for (Lineup l : lineup) {
            if(!rosterPositionMap.containsKey(l.RosterName)){
                return false;
            } else {
                int rosterCount = rosterPositionMap.get(l.RosterName);
                if (rosterCount == 1) {
                    rosterPositionMap.remove(l.RosterName);
                } else {
                    rosterPositionMap.put(l.RosterName, rosterPositionMap.get(l.RosterName) - rosterCount);
                }
            }
        }
        return rosterPositionMap.isEmpty();
    }

    //2. The lineup must encompass at least two games
    private boolean doesLineupCoverTwoGames(Contest contest, HashMap<Integer, Player> playerMap, ArrayList<Lineup> lineup) {
        HashMap<Integer, Player> playersInLineup = new HashMap<>();
        int validGames = 0;
        boolean isHome = false;
        boolean isAway = false;

        for (Lineup l: lineup) {
            if (!playersInLineup.containsKey(l.PlayerId)) {
                playersInLineup.put(l.PlayerId, playerMap.get(l.PlayerId));
            }
        }

        for (Game g: contest.Games) {
            if(validGames > GAMES_A_LINEUP_MUST_ENCOMPASS){
                return false;
            }
            for (Player p :playersInLineup.values()) {
                if(g.AwayTeam == p.teamId){
                    isHome = true;
                }
                if(g.HomeTeam == p.teamId){
                    isAway = true;
                }
            }
            if(isAway && isHome){
                validGames++;
            }
        }
        return true;
    }

    //3. There can not be more than 3 players on a single team per game
    private boolean checkPlayersOnATeamPerGame(Contest contest, ArrayList<Player> players) {
        HashMap<Integer, Integer> teamMap = new HashMap<>();
        for (Player p : players) {
            if(teamMap.containsKey(p.teamId)) {
                if (teamMap.get(p.teamId) > MAX_PLAYERS_IN_A_GAME) {
                    return false;
                }
                teamMap.put(p.teamId, teamMap.get(p.teamId) + 1);
            } else {
                teamMap.put(p.teamId, 1);
            }
        }
        return true;
    }

    //4. The sum of player salary can not exceed the contests max salary cap
    private boolean isPlayersSalaryGreaterThanMaxCap(Contest contest, ArrayList<Lineup> lineup, HashMap<Integer, Player> playerMap) {
        int totalLineupSalary = 0;
        for (Lineup l: lineup) {
            Player p = playerMap.get(l.PlayerId);
            totalLineupSalary = totalLineupSalary + Integer.parseInt(p.salary);
        }
        return totalLineupSalary > contest.SalaryCap;
    }

    //5. The lineup can not contain more than the required amount of players
    private boolean maxPlayerCountValidation(Contest contest, ArrayList<Lineup> lineup){
        int maxCount = 0;
        int lineupSize = lineup.size();
        for (RosterPosition rp: contest.RosterPositions) {
            maxCount = maxCount + rp.Count;
        }
        return lineupSize <= maxCount;
    }

    //6. Any single player can only be used once
    private boolean isPLayerUsedOnlyOnce(ArrayList<Lineup> lineup, HashMap<Integer, Player> playerMap) {
        HashMap<Integer, Player> playersInLineup = new HashMap<>();
        for (Lineup l: lineup) {
            if(playersInLineup.containsKey(l.PlayerId)) {
                return false;
            } else {
                playersInLineup.put(l.PlayerId, playerMap.get(l.PlayerId));
            }
        }
        return true;
    }
}
