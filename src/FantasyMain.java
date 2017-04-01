/**
 * Created by prathmeshjakkanwar on 3/30/17.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FantasyMain {

    public static void main(String[] args) {

    }

    //1. All roster positions listed in the contest must be filled by the lineup
    //2. The lineup must encompass at least two games
    //3. There can not be more than 3 players on a single team per game
    //4. The sum of player salary can not exceed the contests max salary cap
    //5. The lineup can not contain more than the required amount of players
    //6. Any single player can only be used once
    public boolean ValidateLine(Contest contest, ArrayList<Player> players, ArrayList<Lineup> lineup) {

        boolean validation1 = isContestFilledByLineup(contest, lineup);
        boolean validation3 = checkPlayersOnATeamPerGame(contest, players);
        boolean validation5 = maxPlayerCountValidation(contest, lineup);
        boolean validationOther =  remainingValidations(contest, players, lineup);

        return validation1 && validation3 && validation5 && validationOther;
    }

    //2. The lineup must encompass at least two games
    //4. The sum of player salary can not exceed the contests max salary cap
    //6. Any single player can only be used once
    public boolean remainingValidations(Contest contest, ArrayList<Player> players, ArrayList<Lineup> lineup){
        HashMap<String, Player> playerMap = new HashMap<>();
        HashMap<Integer, Player> playersInLineup = new HashMap<>();

        int maxSalary = 0;
        int validGames = 0;
        boolean isHome = false;
        boolean isAway = false;

        for (Player p : players) {
            playerMap.put(p.playerId, p);
        }

        for (Lineup l: lineup) {
            if(playersInLineup.containsKey(l.PlayerId)){
                return false; // -->  6. Any single player can only be used once
            } else {
                playersInLineup.put(l.PlayerId, playerMap.get(l.PlayerId));
            }

            Player player = playerMap.get(l.PlayerId);
            maxSalary = maxSalary +  Integer.parseInt(player.salary);

            if (maxSalary > contest.SalaryCap) {
                return  false; // ---> 4. The sum of player salary can not exceed the contests max salary cap
            }
        }

        for (Game g: contest.Games) {
            if(validGames > 2){
                return false;   // --->  2. The lineup must encompass at least two games
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

    //1. All roster positions listed in the contest must be filled by the lineup
    public boolean isContestFilledByLineup(Contest contest, ArrayList<Lineup> lineup){
        //Lineup contains PlayerId and RosterName
        //RosterPosition contains Name and count
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

    //3. There can not be more than 3 players on a single team per game
    public boolean checkPlayersOnATeamPerGame(Contest contest, ArrayList<Player> players) {
        int count = 0;
        int homeCount = 0;
        int awayCount = 0;
        for (Game g : contest.Games) {
            for (Player p: players) {
                if(homeCount > 3 || awayCount > 3) {
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

    //5. The lineup can not contain more than the required amount of players
    public boolean maxPlayerCountValidation(Contest contest, ArrayList<Lineup> lineup){
        int maxCount = 0;
        int lineupSize = lineup.size();
        for (RosterPosition rp: contest.RosterPositions) {
            maxCount = maxCount + rp.Count;
        }
        return lineupSize <= maxCount;
    }
}
