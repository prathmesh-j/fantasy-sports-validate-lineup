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
        boolean validation3 = checkPlayersOnaTeamPerGame(contest, players);
        //------------------------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------------------------

        HashMap<String, Player> playerMap = new HashMap<>();
        for (Player p : players) {
            playerMap.put(p.playerId, p);
        }
        //HashMap<String, Integer> teamPlayerMap = new HashMap<>();
        HashSet<Integer> playersInLineup = new HashSet<>();
        int maxSalary = 0;
        for (Lineup l: lineup) {
            if(playersInLineup.contains(l.PlayerId)){
                return false; // --> Any single player can only be used once
            } else {
                playersInLineup.add(l.PlayerId);
            }

            Player player = playerMap.get(l.PlayerId);
            maxSalary = maxSalary +  Integer.parseInt(player.salary);

            if (maxSalary > contest.SalaryCap) {
                return  false; // ---> The sum of player salary can not exceed the contests max salary cap
            }

//            if (teamPlayerMap.containsKey(player.teamId)) {
//                int count = teamPlayerMap.get(player.teamId);
//                if(count > 3 )
//                    return false;  // ---> There can not be more than 3 players on a single team per game
//                teamPlayerMap.put(player.teamId, count + 1);
//            } else {
//                teamPlayerMap.put(player.teamId, 1);
//            }
        }

        for (Game g: contest.Games) {

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
    public boolean checkPlayersOnaTeamPerGame(Contest contest, ArrayList<Player> players) {
        int count = 0;
        for (Game g : contest.Games) {
            for (Player p: players) {
                if(count == 3) {
                    return false;
                }
                if(p.teamId == g.AwayTeam || p.teamId == g.AwayTeam) {
                    count++;
                }
            }
            count = 0;
        }
        return true;
    }

}
