package sample;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Deck{

    Card[] deck = new Card[52];


    public Card[] shuffle(Card[]deck,Random random){
        Card[]cardBox=new Card[deck.length];
        for(int i=0;i<deck.length;i++){
            cardBox[i]=deck[i].clone();
        }
        Card tempCard=new Card();
        for(int i=52;i>0;i--){
            int temp=random.nextInt(i);
            tempCard.setRank(cardBox[temp].getRank());
            tempCard.setSuit(cardBox[temp].getSuit());
            cardBox[temp].setRank(cardBox[i-1].getRank());
            cardBox[temp].setSuit(cardBox[i-1].getSuit());
            cardBox[i-1].setRank(tempCard.getRank());
            cardBox[i-1].setSuit(tempCard.getSuit());
        }
        return cardBox;
    }

}