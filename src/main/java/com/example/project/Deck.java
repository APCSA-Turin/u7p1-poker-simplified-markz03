package com.example.project;
import java.util.ArrayList;
import java.util.Collections;

public class Deck{
    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public void initializeDeck(){ //hint.. use the utility class
        for (int i = 0; i < Utility.getRanks().length; i++) {
            String cardRank = Utility.getRanks()[i];
            for (int j = 0; j < Utility.getSuits().length; j++) {
                String cardSuit = Utility.getSuits()[j];
                cards.add(new Card(cardRank, cardSuit));
            }
        }
    }

    public void shuffleDeck(){ //You can use the Collections library or another method. You do not have to create your own shuffle algorithm
        Collections.shuffle(cards);
    }

    public Card drawCard(){
       return new Card("","");
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

   


}