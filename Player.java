package sample;

import java.util.ArrayList;

public class Player {
    public static int maxPlayer = 4;
    public static int maxCard = 6;
    boolean isAIPlayer = false;
    Card[] card;
    Card[] trick;
    int score = 0;
    int Symbols;
    int seat;

    public Player clone() {
        Player player = new Player(this.card);
        player.setScore(this.score);
        player.setSymbols(this.Symbols);
        player.setTrick(this.trick);
        player.setPostion(this.seat);
        player.setAIPlayer(this.isAIPlayer);
        return player;
    }
    public void addCard(Card card) {
        Card[] tempCard = new Card[this.getCard().length + 1];
        for (int i = 0; i < this.getCard().length; i++) {
            tempCard[i] = this.getCard()[i].clone();
        }
        tempCard[this.getCard().length] = card.clone();
        this.card = tempCard;
    }
    public void addTrick(Card card) {
        Card[] tempCard = new Card[this.getTrick().length + 1];
        for (int i = 0; i < this.getTrick().length; i++) {
            tempCard[i] = this.getTrick()[i].clone();
        }
        tempCard[this.getTrick().length] = card.clone();
        this.trick = tempCard;
    }


    public void addTricks(ArrayList<Card> card) {
        for (int i = 0; i < card.size(); i++) {
            this.addTrick(card.get(i));
        }
    }


    public int getSeat() {
        return seat;
    }

    public void setPostion(int postion) {
        this.seat = postion;
    }

    public boolean isAIPlayer() {
        return isAIPlayer;
    }

    public void setAIPlayer(boolean isAIPlayer) {
        this.isAIPlayer = isAIPlayer;
    }

    public Player() {
        this.card = new Card[0];
        this.score = 0;
        this.trick = new Card[0];
    }

    public Player(Card[] card) {
        if (card.length <= maxCard) {
            this.card = new Card[card.length];
            for (int i = 0; i < card.length; i++) {
                this.card[i] = new Card();
                this.card[i].setRank(card[i].getRank());
                this.card[i].setSuit(card[i].getSuit());
            }
            this.score = 0;
        }
    }



    public Card[] getTrick() {
        return trick;
    }

    public void setTrick(Card[] trick) {
        Card[] trickTemp = new Card[trick.length];
        for (int i = 0; i < trick.length; i++) {
            trickTemp[i] = new Card();
            trickTemp[i].setRank(trick[i].getRank());
            trickTemp[i].setSuit(trick[i].getSuit());
        }
        this.trick=trickTemp;
    }

    public void removeCard(int position) {
        Card[] tempCard = new Card[this.getCard().length - 1];
        for (int i = 0, j = 0; i < this.getCard().length; i++, j++) {
            if (i == position) {
                i++;
            }
            if (i < this.getCard().length) {
                tempCard[j] = this.getCard()[i].clone();
            }
        }
        this.card = tempCard;
    }

    public int countSymbols(ArrayList<Card> trick) {
        Card[] card=new Card[trick.size()];
        for(int i=0;i<trick.size();i++){
            card[i]=trick.get(i).clone();
        }
        return countSymbols(card);
    }

    public int countSymbols(Card[] card) {
        int Symbols = 0;
        for (int i = 0; i < card.length; i++) {
            switch (card[i].getRank()) {
                case 'A':
                    Symbols += 4;
                    break;
                case 'K':
                    Symbols += 3;
                    break;
                case 'Q':
                    Symbols += 2;
                    break;
                case 'J':
                    Symbols += 1;
                    break;
                case 'T':
                    Symbols += 10;
            }
        }
        return Symbols;
    }

    public void setScoreOfSymbols(char trump) {
        if (this.trick.length == 0) {
            this.score = 0;
        }
        for (int i = 0; i < this.trick.length; i++) {
            if (this.trick[i].equals(new Card(trump, 'J'))) {
                this.score++;
            }
        }
        this.Symbols = this.countSymbols(trick);
    }

    public boolean isVaildCard(int cardPostion, Card leadCard, char trump) {
        if (cardPostion < 0 || cardPostion>=this.card.length){return false;}
        if (leadCard.getRank() == '0' && leadCard.getSuit() == '0') {
            return true;
        }
        else if (this.getCard()[cardPostion].getSuit() == trump) {
            return true;
        }
        else if (this.hasSameSuit(leadCard)) {
            return (this.getCard()[cardPostion].getSuit() == leadCard.getSuit());
        }
        else { return true; }
    }
    
    public boolean hasSameSuit(Card leadCard) {
        if (leadCard.getRank() == '0' && leadCard.getSuit() == '0') {
            return true;
        }
        for (int i = 0; i < this.getCard().length; i++) {

            if (this.getCard()[i].getSuit() == leadCard.getSuit()) {
                return true;
            }
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Card[] getCard() {
        return card;
    }

    public void setCard(Card[] card) {
        Card[] cardTemp = new Card[card.length];
        for (int i = 0; i < card.length; i++) {
            cardTemp[i] = new Card();
            cardTemp[i].setRank(card[i].getRank());
            cardTemp[i].setSuit(card[i].getSuit());
        }
        this.card = cardTemp;
    }

    public int getSymbols() {
        return Symbols;
    }

    public void setSymbols(int Symbols) {
        this.Symbols = Symbols;
    }
}
