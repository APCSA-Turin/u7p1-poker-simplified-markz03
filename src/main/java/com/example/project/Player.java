package com.example.project;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; //the current community cards + hand
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c){
        hand.add(c);
    }

    public String playHand(ArrayList<Card> communityCards){
        allCards = new ArrayList<>();
        for (int i = 0; i < communityCards.size(); i++) {
            allCards.add(communityCards.get(i));
        }
        for (int j = 0; j < hand.size(); j++) {
            allCards.add(hand.get(j));
        }

        sortAllCards();

        ArrayList<Integer> rankFrequency = findRankingFrequency();
        ArrayList<Integer> suitFrequency = findSuitFrequency();
        System.out.println(rankFrequency);
        System.out.println(suitFrequency);

        for (int i = 0; i < suitFrequency.size(); i++) {
            if (suitFrequency.get(i) == 5) {
                if (checkRoyalFlush(rankFrequency)) {
                    return "Royal Flush";
                }
            }
        }

        return "Nothing";
    }

    public void sortAllCards(){
        for (int i = 1; i < allCards.size(); i++) {
            int j = i - 1;
            while (j > -1 && Utility.getRankValue(allCards.get(j).getRank()) > Utility.getRankValue(allCards.get(j + 1).getRank())) {
                Card temp = allCards.set(j + 1, allCards.get(j));
                allCards.set(j, temp);
                j--;
            }
        }
    } 

    public ArrayList<Integer> findRankingFrequency(){
        ArrayList<Integer> rankFrequency = new ArrayList<>();

        for (int i = 0; i < ranks.length; i++) {
            int frequency = 0;
            for (int j = 0; j < allCards.size(); j++) {
                if (Utility.getRankValue(ranks[i]) == Utility.getRankValue(allCards.get(j).getRank())) {
                    frequency++;
                }
            }
            rankFrequency.add(frequency);
        }

        return rankFrequency; 
    }

    public ArrayList<Integer> findSuitFrequency(){
        // {♠, ♥, ♣, ♦} order
        ArrayList<Integer> suitFrequency = new ArrayList<>();
        for (int i = 0; i < suits.length; i++) {
            int frequency = 0;
            for (int j = 0; j < allCards.size(); j++) {
                if (suits[i].equals(allCards.get(j).getSuit())) {
                    frequency++;
                }
            }
            suitFrequency.add(frequency);
        }
        return suitFrequency; 
    }

   
    @Override
    public String toString(){
        return hand.toString();
    }

    public Boolean checkRoyalFlush(ArrayList<Integer> rankFrequency) {
        int streak = 0;
        for (int j = 8; j < rankFrequency.size(); j++) {
            if (rankFrequency.get(j) == 1) {
                streak++;
            }
        }

        if (streak == 5) {
            return true;
        }
        return false;
    }

    Public Boolean checkStraightFlush(ArrayList<Integer> rankFrequency) {
        int streak = 0;
        int maxStreak = 0;
        for (int j = 0; j < rankFrequency.size(); j++) {
            if (rankFrequency.get(j) == 5) {
                streak++;
            }
            else {
                maxStreak = streak;
                streak = 0;
            }
        }
    }



}
