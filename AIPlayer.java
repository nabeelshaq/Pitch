package sample;

import java.util.ArrayList;

public class AIPlayer extends Player {
    char maxSuit;
    Card maxTrump = new Card();
    Card minTrump = new Card();
    Card maxTrumpCard = new Card();
    Card minTrumpCard = new Card();
    Card maxTrumpAll = new Card();
    Card minTrumpAll = new Card();
    

    public AIPlayer() {
        super();
        this.isAIPlayer = true;
        maxSuit = '0';
    }

    public AIPlayer(Card[] card) {
        super(card);
        this.isAIPlayer = true;
        maxSuit = '0';
    }

    public AIPlayer clone() {
        AIPlayer player = new AIPlayer(this.card);
        player.setScore(this.score);
        player.setSymbols(this.Symbols);
        player.setTrick(this.trick);
        player.setPostion(this.seat);
        player.setAIPlayer(this.isAIPlayer);
        player.setCard(this.card);
        player.maxSuit = this.maxSuit;
        return player;
    }


    public int bid(int currentBid, int playersNumber) {
        double[] bid = new double[4];
        double maxBid = 0;
        char[] suit = { 'C', 'D', 'H', 'S' };
        for (int i = 0; i < 4; i++) {
            bid[i] += this.countMax(playersNumber, suit[i]);
            bid[i] += this.countMin(playersNumber, suit[i]);
            bid[i] += this.countJack(suit[i]);
            bid[i] += this.countSymbol(this.card);
        }
        for (int i = 0; i < 4; i++) {
            if (bid[i] > maxBid) {
                maxBid = bid[i];
                this.maxSuit = suit[i];
            }
        }
        if ((maxBid > currentBid) && (maxBid <= 4) && (maxBid >= 2)) {
            if (currentBid < 2) {
                currentBid = 1;
            }
            return (currentBid + 1);
        }
        return 0;
    }


    public int play(int round, char trump1, Card leadCard, ArrayList<Card> trick, int time, ArrayList<Card> trickAll) {
        char trump;
        if (trump1 == '0') {
            trump = this.maxSuit;
            return this.getMaxTrump(trump);
        }
        else {
            trump = trump1;
        }
        if (time == 0) {
            if (this.getNumCard(trump) >= 2) {
                return this.getMaxTrump(trump);
            }
            else if ((this.getMaxNone(trump) != 0)) {
                return this.getMaxNone(trump);
            }
            else {
                return this.getMaxCard();
            }
        }
        maxTrump = new Card();
        minTrump = new Card();
        maxTrumpCard = new Card();
        minTrumpCard = new Card();
        maxTrumpAll = new Card();
        minTrumpAll = new Card();
        Card maxLeadCard = new Card();
        Card minLeadCard = new Card();
        Card maxLeadInTrick = new Card();
        Card minLeadInTrick = new Card();
        
        maxTrump = this.getMax(trick, trump);
        minTrump = this.getMin(trick, trump);
        maxTrumpCard = this.getMax(this.card, trump);
        minTrumpCard = this.getMin(this.card, trump);
        maxTrumpAll = this.getMax(trickAll, trump);
        minTrumpAll = this.getMin(trickAll, trump);
        
        
        maxLeadInTrick = this.getMax(trick, leadCard.getSuit());
        minLeadInTrick = this.getMin(trick, leadCard.getSuit());
        maxLeadCard = this.getMax(this.card, leadCard.getSuit());
        minLeadCard = this.getMin(this.card, leadCard.getSuit());
        Card leastTrumpCard;
        Card leastLeadCard;
        Card trumpJ = new Card(trump, 'J');
        if (time == 6) {
            if ((this.hasTrump(trick, trump))
                    && (this.hasTrump(this.card, trump))
                    && minTrump.lessThan(minTrumpAll,
                    trump)
                    && minTrump.lessThan(minTrumpCard, trump)
                    && maxTrumpCard.greaterThan(maxTrump)) {
                leastTrumpCard = maxTrumpCard;
                for (int i = 0; i < this.card.length; i++) {
                    if (this.card[i].greaterThan(maxTrump, trump)
                            && this.card[i].lessThan(leastTrumpCard)) {
                        leastTrumpCard = this.card[i].clone();
                    }
                }
                return this.getPosition(leastTrumpCard);
            }
            else if ((!this.hasTrump(trick, trump))
                    && (this.hasTrump(trickAll, trump))
                    && (minTrumpCard.lessThan(minTrumpAll))) {
                return this.getPosition(minTrumpCard);

            }
            else if ((this.hasCard(trumpJ)) && (trumpJ.greaterThan(maxTrump))) {
                return this.getPosition(trumpJ);
            }
            else if (this.hasSameSuit(leadCard) && (maxLeadCard.greaterThan(maxLeadInTrick))) {
                leastLeadCard = maxLeadCard;
                for (int i = 0; i < this.card.length; i++) {
                    if (this.card[i].greaterThan(maxLeadInTrick, trump) && this.card[i].lessThan(leastLeadCard)) {
                        leastLeadCard = this.card[i].clone();
                    }
                }
                return this.getPosition(leastLeadCard);

            }
            else if ((this.countSymbols(trick) >= 10) && (this.hasTrump(this.card, trump)) && (((this.hasTrump(trick, trump)) && (this.maxTrumpCard.greaterThan(maxTrump)))
                                                                                                 || (!this.hasTrump(trick, trump)))) {
                if (!this.hasTrump(trick, trump)) {
                    return this.getPosition(minTrumpCard);
                }
                else {
                    leastTrumpCard = maxTrumpCard;
                    for (int i = 0; i < this.card.length; i++) {
                        if (this.card[i].greaterThan(maxTrump, trump) && this.card[i].lessThan(leastTrumpCard)) {
                            leastTrumpCard = this.card[i].clone();
                        }
                    }
                    return this.getPosition(leastTrumpCard);
                }
            }
            else if ((leadCard.suit == trump) && (this.getNumCard(trump) >= 2) && (minTrumpCard.lessThan(minTrumpAll)) && minTrumpCard.lessThan(minTrump)) {
                leastTrumpCard = maxTrumpCard;
                for (int i = 0; i < this.card.length; i++) {
                    if (this.card[i].greaterThan(minTrumpCard) && this.card[i].lessThan(leastTrumpCard)) {
                        leastTrumpCard = this.card[i].clone();
                    }
                }
                return this.getPosition(leastTrumpCard);
            }
            else if (this.hasSameSuit(leadCard)) {
                return this.getPosition(minLeadCard);
            }
            else if (this.hasNone(trump)) {
                return this.getPosition(this.getMinRankNone(trump));
            }
            else {
                return this.getPosition(this.getMinRankCard());
            }
        }
        else {
            int SymbolNumber = this.countSymbols(trick);
            if (((SymbolNumber >= 10) || (this.hasTrump(trick, trump) && this.minTrump.lessThan(minTrumpCard)) || this.hasTrumpJ(trick,trump)) && this.minTrump.lessThan(minTrumpAll)
                && (this.hasTrump(card, trump) && (this.maxTrumpCard.greaterThan(this.maxTrump)))) {
                return this.getPosition(maxTrumpCard);
            }
            else if (this.hasSameSuit(leadCard) && maxLeadCard.greaterThan(maxLeadInTrick)) {
                return this.getPosition(maxLeadCard);
            }
            else if ((leadCard.getSuit() == trump) && (this.getNumCard(trump) >= 2) && (((this.hasTrump(trickAll, trump)) && (this.minTrumpCard.lessThan(this.minTrump) && this.minTrumpCard.lessThan(this.minTrumpAll))) || (!this.hasTrump(trickAll, trump)))) {
                leastTrumpCard = maxTrumpCard;
                for (int i = 0; i < this.card.length; i++) {
                    if (this.card[i].getSuit() == trump) {
                        if (this.card[i].greaterThan(minTrumpCard)&& this.card[i].lessThan(leastTrumpCard)) {
                            leastTrumpCard = this.card[i].clone();
                        }
                    }
                }
                return this.getPosition(leastTrumpCard);
            }
            else if (this.hasSameSuit(leadCard)) {
                return this.getPosition(minLeadCard);
            }
            else if (this.hasNone(trump)) {

                return this.getPosition(this.getMinRankNone(trump));

            }
            else {
                return this.getPosition(this.getMinRankCard());
            }
        }
    }

    public boolean hasTrumpJ(ArrayList<Card> trick, char trump) {
        for (int i = 0; i < trick.size(); i++) {
            if (trick.get(i).equals(new Card(trump, 'J')))
                return true;
        }
        return false;
    }

    public boolean hasTrumpJ(Card[] card, char trump) {
        for (int i = 0; i < card.length; i++) {
            if (card[i].equals(new Card(trump, 'J')))
                return true;
        }
        return false;
    }

    public boolean hasNone(char trump) {
        for (int i = 0; i < this.card.length; i++) {
            if (this.card[i].getSuit() != trump)
                return true;
        }
        return false;
    }

    public Card getMinRankNone(char trump) {
        Card minCard = new Card();
        for (int i = 0; i < this.card.length; i++) {
            if (!(this.card[i].getSuit() == trump)) {
                if (minCard.getRank() == '0') {
                    minCard = this.card[i].clone();
                }
                if (this.card[i].getCardNumber(this.card[i].getRank()) < minCard.getCardNumber(minCard.getRank())) {
                    minCard = this.card[i].clone();
                }
            }
        }
        return minCard;
    }

    public Card getMinRankCard() {
        Card minCard = this.card[0].clone();
        for (int i = 0; i < this.card.length; i++) {
            if (this.card[i].getCardNumber(this.card[i].getRank()) < minCard.getCardNumber(minCard.getRank())) {
                minCard = this.card[i].clone();
            }
        }
        return minCard;
    }

    public boolean hasCard(Card card) {
        for (int i = 0; i < this.card.length; i++) {
            if (this.card[i].equals(card))
                return true;
        }
        return false;
    }

    public boolean hasTrump(Card[] card, char trump) {
        for (int i = 0; i < card.length; i++) {
            if (card[i].getSuit() == trump)
                return true;
        }
        return false;
    }

    public boolean hasTrump(ArrayList<Card> card, char trump) {
        for (int i = 0; i < card.size(); i++) {
            if (card.get(i).getSuit() == trump)
                return true;
        }
        return false;
    }

    public int getPosition(Card card) {
        for (int i = 0; i < this.card.length; i++) {
            if (this.card[i].equals(card)) {
                return i;
            }
        }
        return -1;
    }

    public Card getMax(Card[] card, char suit) {
        Card max = new Card();
        for (int i = 0; i < card.length; i++) {
            if (card[i].getSuit() == suit) {
                if (card[i].greaterThan(max)) {
                    max = card[i].clone();
                }
            }
        }
        return max;
    }
    
    public Card getMax(ArrayList<Card> card, char suit) {
        Card max = new Card();
        for (int i = 0; i < card.size(); i++) {
            if (card.get(i).getSuit() == suit) {
                if (card.get(i).greaterThan(max)) {
                    max = card.get(i).clone();
                }
            }
        }
        return max;
    }

    public Card getMin(Card[] card, char suit) {
        Card min = new Card();
        for (int i = 0; i < card.length; i++) {
            if (card[i].getSuit() == suit) {
                if (card[i].lessThan(min)) {
                    min = card[i].clone();
                }
            }
        }
        return min;
    }

    public Card getMin(ArrayList<Card> card, char suit) {
        Card max = new Card();
        for (int i = 0; i < card.size(); i++) {
            if (card.get(i).getSuit() == suit) {
                if (card.get(i).lessThan(max)) {
                    max = card.get(i).clone();
                }
            }
        }
        return max;
    }


    public int getMaxNone(char trump) {
        char maxCard = '0';
        int position = 0;
        for (int i = 0; i < this.card.length; i++) {
            if (this.card[i].getSuit() != trump) {
                if (this.card[i].getCardNumber(this.card[i].getRank()) > this.card[i].getCardNumber(maxCard)) {
                    maxCard = this.card[i].getRank();
                    position = i;
                }
            }
        }
        return position;

    }


    public int getMaxTrump(char suit) {
        char maxCard = '0';
        int position = 0;
        for (int i = 0; i < this.card.length; i++) {
            if (this.card[i].getSuit() == suit) {
                if (this.card[i].getCardNumber(this.card[i].getRank()) > this.card[i].getCardNumber(maxCard)) {
                    maxCard = this.card[i].getRank();
                    position = i;
                }
            }
        }
        return position;
    }


    public int getMaxCard() {
        char maxCard = '0';
        int position = 0;
        for (int i = 0; i < this.card.length; i++) {
            if (this.card[i].getCardNumber(this.card[i].getRank()) > this.card[i].getCardNumber(maxCard)) {
                maxCard = this.card[i].getRank();
                position = i;
            }
        }
        return position;
    }

    public int getNumCard(char suit) {
        int number = 0;
        for (int i = 0; i < this.card.length; i++) {
            if (this.getCard()[i].getSuit() == suit) {
                number++;
            }
        }
        return number;
    }


    public int getMinTrump(char suit) {
        char minCard = 'Z';
        int position = 0;
        for (int i = 0; i < this.card.length; i++) {
            if (this.card[i].getSuit() == suit) {
                if (this.card[i].getCardNumber(this.card[i].getRank()) < this.card[i].getCardNumber(minCard)) {
                    minCard = this.card[i].getRank();
                    position = i;
                }
            }
        }
        return position;
    }

    public double countMax(int playersNumber, char suit) {
        Double prob = 1.0;
        if (!this.hasSameSuit(new Card(suit, '0'))) {
            return 0D;
        }
        int r = 0;
        char lastCardRank = 49;
        for (int i = 0; i < this.card.length; i++) {
            if (this.getCard()[i].getSuit() == suit) {
                if (this.getCard()[i].getCardNumber(lastCardRank) < this.getCard()[i].getCardNumber(this.getCard()[i].getRank())) {
                    lastCardRank = this.getCard()[i].getRank();
                }
            }
        }
        r = 62 - lastCardRank;
        for (int i = 0; i < r; i++) {
            prob *= (double) (52 - 6 * playersNumber - i) / (46 - i);
        }
        return prob;
    }

    public double countMin(int playersNumber, char suit) {
        Double prob = 1.0;
        if (!this.hasSameSuit(new Card(suit, '0'))) {
            return 0D;
        }

        int r = 0;
        char firstCardRank = 63;
        for (int i = 0; i < this.card.length; i++) {
            if (this.getCard()[i].getSuit() == suit) {
                if (firstCardRank > this.getCard()[i].getRank()) {
                    firstCardRank = this.getCard()[i].getRank();
                }
            }
        }
        r = firstCardRank - 50;
        for (int i = 0; i < r; i++) {
            prob *= (double) (52 - 6 * playersNumber - i) / (46 - i);
        }
        return prob;
    }

    public double countJack(char suit) {
        Double prob = 0.0D;
        for (int i = 0; i < this.card.length; i++) {
            if (this.getCard()[i].getSuit() == suit) {
                if (this.getCard()[i].getRank() == 'J') {
                    prob = 1.0D;
                }
            }
        }
        return prob;
    }


    private double countSymbol(Card[] card) {
        Double prob = 0.0D;
        int Symbols = countSymbols(card);
        prob = (double) ((double) Symbols / (double) 25);
        return prob;
    }

}
