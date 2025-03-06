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

        // Only if 5 consecutive numbers in rankFrequency
        if (maxStreak(rankFrequency) == 5) {
            if (checkRoyalFlush(rankFrequency)) {
                return "Royal Flush";
            }
            else if (checkStraightFlush(suitFrequency)) {
                return "Straight Flush";
            }
            else if (checkStraight(suitFrequency)){
                return "Straight";
            }
        }
        // Only if all cards are of the same suit
        else if (highestFrequency(suitFrequency) == 5) {
            return "Flush";
        }
        // Only if a rank appears 4 times in allCards
        else if (highestFrequency(rankFrequency) == 4) {
            if (checkFourOfAKind(rankFrequency)) {
                return "Four of a Kind";
            }
        }
        // Only if a rank appears 3 times in AllCards
        else if (highestFrequency(rankFrequency) == 3) {
            // Full House if a pair is found, else it's just a Three of a Kind
            if (checkPair(rankFrequency) == 1) {
                return "Full House";
            }
            else {
                return "Three of a Kind";
            }
        }
        // Only if one or more pair is detected in AllCards
        else if (checkPair(rankFrequency) > 0) {
            // If there are Two or One Pair in allCards
            if (checkPair(rankFrequency) == 2) {
                return "Two Pair";
            }
            else {
                return "A Pair";
            }
        }
        // Only if player's hand has a card greater than the highest Card in the communityCards
        else if (highestCard(hand) > highestCard(communityCards)) {
            return "High Card";
        }

        // Only if everything else is false, the player's handRanking is nothing
        return "Nothing";
    }

    public void sortAllCards(){
        // Insertion Sort
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
        
        // Iterates through AllCards to find the frequency of each Card's rank and adds it into rankFrequency
        for (int i = 0; i < ranks.length; i++) {
            int frequency = 0;
            for (int j = 0; j < allCards.size(); j++) {
                if (Utility.getRankValue(ranks[i]) == Utility.getRankValue(allCards.get(j).getRank())) {
                    frequency++;
                }
            }
            rankFrequency.add(frequency);
        }

        // returns an ArrayList<Integer> rankFrequency that contains the frequency of each card's rank in AllCards
        return rankFrequency; 
    }

    public ArrayList<Integer> findSuitFrequency(){
        // {♠, ♥, ♣, ♦} order
        ArrayList<Integer> suitFrequency = new ArrayList<>();

        // Iterates through AllCards to find the frequency of each Card's suit and adds it into suitFrequency
        for (int i = 0; i < suits.length; i++) {
            int frequency = 0;
            for (int j = 0; j < allCards.size(); j++) {
                if (suits[i].equals(allCards.get(j).getSuit())) {
                    frequency++;
                }
            }
            suitFrequency.add(frequency);
        }
        
        // returns an ArrayList<Integer> suitFrequency that contains the frequency of each card's suit in AllCards
        return suitFrequency; 
    }

    public Boolean checkRoyalFlush(ArrayList<Integer> rankFrequency) {
        // checks if player has a Royal Flush via rankFrequency
        // True when the player has a {10, J, Q, K, A}
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

    public Boolean checkStraightFlush(ArrayList<Integer> suitFrequency) {
        // checks if player has a Straight Flush via suitFrequency
        // True when allCards has 5 cards of the same suit (5 consecutive ranks are checked in playHand)
        if (highestFrequency(suitFrequency) == 5) {
            return true;
        }
        return false;
    }

    public Boolean checkFourOfAKind(ArrayList<Integer> rankFrequency) {
        // checks if the player has a Four of a Kind via rankFrequency
        // True if allCards contains 4 cards of the same rank
        if (highestFrequency(rankFrequency) == 4) {
            return true;
        }
        return false;
    }

    public int checkPair(ArrayList<Integer> rankFrequency) {
        // checks the amount of pairs in AllCards and returns it
        int pairCount = 0;
        for (int i = 0; i < rankFrequency.size(); i++) {
            if (rankFrequency.get(i) == 2) {
                pairCount++;
            }
        }
        return pairCount;
    }

    public boolean checkStraight(ArrayList<Integer> suitFrequency) {
        // Checks if the player has a Straight (5 consective ranks has already been checked in playHand)
        // true if AllCards contains 5 consecutive ranks of any suit
        if (highestFrequency(suitFrequency) != 5) {
            return true;
        }

        return false;
    }

    public int highestCard(ArrayList<Card> hand) {
        // determines the player's highest rank value in their hand
        int highestRank = 0;
        int rank = 0;

        for (int i = 0; i < hand.size(); i++) {
            rank = Utility.getRankValue(hand.get(i).getRank());
            if (rank > highestRank) {
                highestRank = rank;
            }
        }

        return highestRank;
    }

    public int maxStreak(ArrayList<Integer> arrayFrequency) {
        // Determines and returns the highest consecutive streak
        // Used exclusively on rankFrequency as the parameter
        int streak = 0;
        int maxStreak = 0; 

        for (int i = 0; i < arrayFrequency.size(); i++) {
            if (arrayFrequency.get(i) != 0) {
                streak++;
                if (streak > maxStreak) {
                    maxStreak = streak;
                }
            }
            else {
                streak = 0;
            }
        }

        return maxStreak;
    }

    public int highestFrequency(ArrayList<Integer> arrayFrequency) {
        // Determines and returns g that is the greatest in arrayFrequency
        // Used by both rankFrequency and suitFrequency
        int highestFreq = 0;

        for (int i = 0; i < arrayFrequency.size(); i++) {
            if (arrayFrequency.get(i) > highestFreq) {
                highestFreq = arrayFrequency.get(i);
            }
        }

        return highestFreq;
    }

    @Override
    public String toString(){
        return hand.toString();
    }
}
