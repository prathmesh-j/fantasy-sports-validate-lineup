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
        HashMap<String, Player> playerMap = new HashMap<>();
        for (Player p : players) {
            playerMap.put(p.playerId, p);
        }

        boolean validation1 = isContestFilledByLineup(contest, lineup);
        boolean validation2 = doesLineupCoverTwoGames(contest,playerMap,lineup);
        boolean validation3 = checkPlayersOnATeamPerGame(contest, players);
        boolean validation4 = isPlayersSalaryGreaterThanMaxCap(contest, lineup, playerMap);
        boolean validation5 = maxPlayerCountValidation(contest, lineup);
        boolean validation6 = isPLayerUsedOnlyOnce(lineup, playerMap);

        return validation1 && validation2 && validation3 && validation4 && validation5 && validation6;
    }


    //1. All roster positions listed in the contest must be filled by the lineup
    private boolean isContestFilledByLineup(Contest contest, ArrayList<Lineup> lineup){
        HashSet<String> rosterNames  = new HashSet<>();
        for (RosterPosition rp : contest.RosterPositions) {
            rosterNames.add(rp.Name);
        }

        for (Lineup l : lineup) {
            if(!rosterNames.contains(l.RosterName)){
                return false;
            }
        }
        return true;
    }

    //2. The lineup must encompass at least two games
    private boolean doesLineupCoverTwoGames(Contest contest, HashMap<String, Player> playerMap, ArrayList<Lineup> lineup) {
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
        int homeCount = 0;
        int awayCount = 0;
        for (Game g : contest.Games) {
            for (Player p: players) {
                if(homeCount > MAX_PLAYERS_IN_A_GAME || awayCount > MAX_PLAYERS_IN_A_GAME) {
                    return false;
                }
                if(g.AwayTeam == p.teamId){
                    homeCount++;
                }
                if(g.HomeTeam == p.teamId){
                    awayCount++;
                }
            }
            homeCount = 0;
            awayCount = 0;
        }
        return true;
    }

    //4. The sum of player salary can not exceed the contests max salary cap
    private boolean isPlayersSalaryGreaterThanMaxCap(Contest contest, ArrayList<Lineup> lineup, HashMap<String, Player> playerMap) {
        int maxSalary = 0;
        for (Lineup l: lineup) {
            Player p = playerMap.get(l.PlayerId);
            maxSalary = maxSalary + Integer.parseInt(p.salary);
        }

        if(maxSalary > contest.SalaryCap) {
            return false;
        }
        return true;
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
    private boolean isPLayerUsedOnlyOnce(ArrayList<Lineup> lineup, HashMap<String, Player> playerMap) {
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
