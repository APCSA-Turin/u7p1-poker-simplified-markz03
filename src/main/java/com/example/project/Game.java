package com.example.project;
import java.util.ArrayList;


public class Game{
    public static String determineWinner(Player p1, Player p2,String p1Hand, String p2Hand,ArrayList<Card> communityCards){
        // int values of Hand Ranking
        int p1Ranking = Utility.getHandRanking(p1Hand);
        int p2Ranking = Utility.getHandRanking(p2Hand);

        // Player 1 has a higher hand, therefore Player 1 wins
        if (p1Ranking > p2Ranking) {
            return "Player 1 wins!";
        }
        // Player 2 has a higher hand, therefore Player 1 wins
        else if (p2Ranking > p1Ranking) {
            return "Player 2 wins!";
        }
        // if they have the same hand ranking
        else if (p1Ranking == p2Ranking) {
            // checks the highest card in each player's hand, the higher one wins, else if equal = tie
            if (p1.highestCard(p1.getHand()) > p2.highestCard(p2.getHand())) {
                return "Player 1 wins!";
            }
            else if (p2.highestCard(p2.getHand()) > p1.highestCard(p1.getHand())) {
                return "Player 2 wins!";
            }
            else {
                return "Tie!";
            }
        }

        return "Error";
    }

    public static void play(){ //simulate card playing
        
    }
        
        

}