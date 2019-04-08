package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.layout.*;
import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.stage.Stage;

import java.util.*;

import javafx.geometry.Insets;
import javafx.scene.text.TextAlignment;
interface Dealer{}


public class PitchDealer implements Dealer  {
    Card[] deck = new Card[52];
    AIPlayer[] players;
    private final Object PAUSE_KEY = new Object();
    char trump = '0';
    Random random = new Random(0);
    public int players_number = 0;
    int bid = 0;
    int highBid = 0;
    int highPlayer = 0;
    int currentPlayer = 0;
    int currentCard = 0;
    int winPlayer = 0;
    int pitcher = 0;

    ArrayList<Card> trick = new ArrayList<Card>();
    ArrayList<Card> trickAll = new ArrayList<Card>();
    Card maxCard = new Card();
    Card minCard = new Card();


    public PitchDealer(int players_number ) {
        this.random = new java.util.Random(0);
        this.players_number = players_number;
        for (int i = 0; i < 52; i++) {
            this.deck[i] = new Card(i);
        }
        players = new AIPlayer[this.players_number];
        for (int i = 0; i < players_number; i++) {
            if (i == (1 - 1)) {
                players[i] = new AIPlayer();
                players[i].setAIPlayer(false);
                this.players[i].setPostion(i);
            } else {
                players[i] = new AIPlayer();
                players[i].setAIPlayer(true);
                this.players[i].setPostion(i);
            }
        }

    }

     void run(Stage myStage) {
        this.deck = this.shuffle(deck, random);
        this.players = this.deal(players, deck);
        goBid(myStage);
    }
    public ArrayList<Card> getTrick() { return trick;}

    public void setTrick(ArrayList<Card> trick) { this.trick = trick;}

    public Card getMaxC(){ return maxCard;}
    public void setMaxC(Card m) { this.maxCard =m ; }

    public Card[] shuffle(Card[] deck, Random random) {
        Card[] cardBox = new Card[deck.length];
        for (int i = 0; i < deck.length; i++) {
            cardBox[i] = deck[i].clone();
        }
        Card tempCard = new Card();
        for (int i = 52; i > 0; i--) {
            int temp = random.nextInt(i);
            tempCard.setRank(cardBox[temp].getRank());
            tempCard.setSuit(cardBox[temp].getSuit());
            cardBox[temp].setRank(cardBox[i - 1].getRank());
            cardBox[temp].setSuit(cardBox[i - 1].getSuit());
            cardBox[i - 1].setRank(tempCard.getRank());
            cardBox[i - 1].setSuit(tempCard.getSuit());
        }
        return cardBox;
    }


    public AIPlayer[] deal(AIPlayer[] player, Card[] deck) {
        AIPlayer[] players = new AIPlayer[player.length];
        for (int i = 0; i < player.length; i++) {
            players[i] = player[i].clone();
        }
        for (int y = 0; y < players_number; y++) {
            for (int x = 0; x < 3; x++) {
                players[y].addCard(deck[x + (3 * y)]);
            }
        }
        for (int y = 0; y < players_number; y++) {
            for (int x = 0; x < 3; x++) {
                players[y].addCard(deck[x + (3 * y) + players_number * 3]);
            }
        }
        return players;
    }

    public int goBid() {
        return bid;
    }

    private void goBid(Stage myStage) {
         StackPane layout = new StackPane();

         Label labe1 = new Label("          Your Hand(Player 1):                                                                       \n\n\n\n\n     ");
         String add = "";
         for (int j = 0; j < this.players[1].getCard().length; j++) {
             String handcards = "          " + (j + 1) + ": " + this.players[1].getCard()[j].toString();
             add = add + handcards;
         }
         Label lab = new Label(add);
         Label n = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nClick the buttons to bid, remember: Your bid must be higher than the previous bid");
         layout.getChildren().addAll(labe1,lab, n);

         doBid(layout,myStage);
     }

     private void doBid(StackPane layout, Stage myStage){
        int pass = 0;
        int lowest_bid = 2;
        Scene sc = new Scene(layout, 1000, 1000);
        Label hbid = new Label();
        HBox hb = new HBox();
        HashMap<String, Scene> sceneMap;
        Button bid0 = new Button("Bid 0 (Pass)");
        Button bid2 = new Button("Bid 2");
        Button bid3 = new Button("Bid 3");
        Button bid4 = new Button("Bid 4");
        int highest_bid = 4;
        Label highbidlabel = new Label("\n\n\nHigh bid so far:  ");

        for (int i = 0; i < players_number; i++) {
            currentPlayer = i;
            do {
                if (highBid == 0) { }
                else if (highBid ==2){
                    Label space = new Label ("\n\n\n\n\n    2");
                    layout.getChildren().add(space);

                }
                else if (highBid == 3){
                    Label space2 = new Label ("\n\n\n\n\n          3");
                    layout.getChildren().add(space2);
                }
                else if (highBid == 4){
                    Label space3 = new Label ("\n\n\n\n\n                4");
                    layout.getChildren().add(space3);
                }

                if (this.players[i].isAIPlayer()) {
                    AIPlayer temp = this.players[i];
                    bid = temp.bid(highBid, this.players_number);
                }
                else{
                    bid0.setOnAction(event -> {
                        bid = 0;
                        resume();
                    });
                    bid2.setOnAction(event -> {
                        bid = 2;
                        resume();
                    });
                    bid3.setOnAction(event -> {
                        bid = 3;
                        resume();
                    });
                    bid4.setOnAction(event -> {
                        bid = 4;
                        resume();
                    });
                    hb = new HBox(10,bid0,bid2,bid3,bid4);
                    layout.getChildren().add(hb);
                    myStage.setScene(sc);
                    myStage.show();
                    pause();
                }

            } while ((bid != pass && (bid < lowest_bid || bid > highest_bid)) || ((bid <= highBid) && (bid != pass)));

            if (bid > highBid) {
                highBid = bid;
                highPlayer = currentPlayer + 1;
            }

            if ((bid >= lowest_bid) && (bid <= highest_bid)) {
                if (this.players[i].isAIPlayer()) {

                    Label aibid = new Label("");
                    if      (bid==2) {
                        aibid = new Label("\n\n\n\n\n\n\n\n\n\nPlayer "  + (currentPlayer + 1) + " bids "+ bid);
                    }
                    else if (bid==3) {
                        aibid = new Label("\n\n\n\n\n\n\n\n\n\n                             " +bid);
                    }
                    else if (bid==4) {
                        aibid = new Label("\n\n\n\n\n\n\n\n\n\n                                       " +bid);
                    }
                    layout.getChildren().add(aibid);


                }
                else {

                    Label pbid = new Label("");
                    if      (bid==2) {
                        pbid = new Label("\n\n\n\n\n\n\n\n\n\nPlayer "  + (currentPlayer + 1) + " bids "+ bid);
                    }
                    else if (bid==3) {
                        pbid = new Label("\n\n\n\n\n\n\n\n\n\n                             " +bid);
                    }
                    else if (bid==4) {
                        pbid = new Label("\n\n\n\n\n\n\n\n\n\n                                       " +bid);
                    }
                    layout.getChildren().add(pbid);
                }
            }

            if (bid == pass) {
                Label passbid = new Label("");
                if (this.players[i].isAIPlayer()) {
                    if(currentPlayer+1==2){
                        passbid = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer " + (currentPlayer + 1) + " passes");
                    }
                    else if(currentPlayer+1==3) {
                        passbid = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer " + (currentPlayer + 1) + " passes");
                    }
                    else if(currentPlayer+1==4) {
                        passbid = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer " + (currentPlayer + 1) + " passes");
                    }
                    layout.getChildren().add(passbid);
                }
                else {
                    if(currentPlayer+1==2){
                        passbid = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer " + (currentPlayer + 1) + " passes");
                    }
                    else if(currentPlayer+1==3) {
                        passbid = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer " + (currentPlayer + 1) + " passes");
                    }
                    else if(currentPlayer+1==4) {
                        passbid = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer " + (currentPlayer + 1) + " passes");
                    }
                    layout.getChildren().add(passbid);
                }
            }

            if (currentPlayer == players_number - 1) {
                Label prnt = new Label("");
                if (highBid == pass) {
                    prnt = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nNo one bid, quitting...");
                    layout.getChildren().add(prnt);
                    System.exit(1);
                }
                else if (this.players[highPlayer - 1].isAIPlayer()) {
                    if (highBid==2){
                        prnt = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer " + highPlayer + " wins with bid of " + highBid + ".");
                    }
                    else if (highBid==3){
                        prnt = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n                                                     " + highBid + ".");
                    }
                    else if (highBid==4){
                        prnt = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n                                                            " + highBid + ".");
                    }

                    layout.getChildren().add(prnt);
                }
                else {
                    if (highBid==2){
                        prnt = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer " + highPlayer + " wins with bid of " + highBid + ".");
                    }
                    else if (highBid==3){
                        prnt = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n                                                     " + highBid + ".");
                    }
                    else if (highBid==4){
                        prnt = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n                                                            " + highBid + ".");
                    }

                    layout.getChildren().add(prnt);
                }
            }
        }
         
        play(layout);
    }



    private void pause() {
        Platform.enterNestedEventLoop(PAUSE_KEY);
    }

    private void resume() {
        Platform.exitNestedEventLoop(PAUSE_KEY, null);
    }


    private void play(StackPane layout) {
        winPlayer = highPlayer;
        pitcher = highPlayer;
        HBox h = new HBox();
        boolean valid = false;
        String spacer = "\n";
        Card largestCard = new Card();
        Card leadCard = new Card();
        String ad = "";

        for (int round = 0; round < Player.maxCard; round++) {
            currentPlayer = winPlayer;
            largestCard = new Card();
            leadCard = new Card();

            for (int position = currentPlayer - 1, time = 0; time < this.players_number; position++, time++) {
                if (position > this.players_number - 1) {
                    position = position - this.players_number;
                }

                Label selcard = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nSelect a card to lead, determining trump: ");
                layout.getChildren().add(selcard);

                boolean vaild = false;

                go: do {
                    try {
                        if (this.players[position].isAIPlayer()) {
                            currentCard = this.players[position].play(round, trump, leadCard, trick, time, trickAll) + 1;
                            Label aiselcard = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nAI selected card");
                            layout.getChildren().add(aiselcard);
                        }
                        else {

                            Button card1 = new Button("Play card 1");
                            Button card2 = new Button("Play card 2");
                            Button card3 = new Button("Play card 3");
                            Button card4 = new Button("Play card 4");
                            Button card5 = new Button("Play card 5");
                            Button card6 = new Button("Play card 6");

                            card1.setOnAction(event -> {
                                currentCard = 1;
                                resume();
                            });
                            card2.setOnAction(event -> {
                                currentCard = 2;
                                resume();
                            });
                            card3.setOnAction(event -> {
                                currentCard = 3;
                                resume();
                            });
                            card4.setOnAction(event -> {
                                currentCard = 4;
                                resume();
                            });
                            card5.setOnAction(event -> {
                                currentCard = 5;
                                resume();
                            });
                            card6.setOnAction(event -> {
                                currentCard = 6;
                                resume();
                            });
                            h = new HBox(card1, card2, card3, card4, card5, card6);
                            h.setAlignment(Pos.BASELINE_RIGHT);
                            layout.getChildren().add(h);
                            pause();
                        }

                        vaild = this.players[position].isVaildCard( (currentCard - 1), leadCard, trump);

                    }
                    catch (Exception e) {
                        vaild = false;
                    }

                    if (!vaild) {
                        Label followsuit = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nYou must follow suit if you can, unless you trump!");
                        layout.getChildren().add(followsuit);
                    }

                }
                while (!vaild);
                if (time == 0) { leadCard = this.players[position].getCard()[currentCard - 1];  }

                if (round == 0 && time == 0) { trump = this.players[position].getCard()[currentCard - 1].getSuit(); }

                this.getTrick().add(this.players[position].getCard()[currentCard - 1]);

                if (this.players[position].getCard()[currentCard - 1].greaterThan(largestCard, trump)) {
                    largestCard = this.players[position].getCard()[currentCard - 1].clone();
                    winPlayer = position + 1;
                }

                this.players[position].removeCard(currentCard - 1);

                if (time == this.players_number - 1) {
                    for (int i = 0; i < this.trick.size(); i++) {
                        this.trickAll.add(this.trick.get(i));

                    }
                    this.players[winPlayer - 1].addTricks(this.trick);
                    trick.clear();
                }
            }
        }
        score(layout);
    }


    public int getPitch (){
        return pitcher;
    }
    private void score(StackPane layout) {
        int highestPlayer = 0;
        int highestScore = 0;
        boolean tie = false;
        for (int i = 0; i < players_number; i++) {
            this.players[i].setScoreOfSymbols(trump);
            if (this.players[i].getSymbols() == highestScore) {
                tie = true;
            }
            if (this.players[i].getSymbols() > highestScore) {
                highestScore = this.players[i].getSymbols();
                highestPlayer = i + 1;
                tie = false;
            }
        }

        if (tie == false) {
            this.players[highestPlayer - 1].score++;
        }

        for (int i = 0; i < this.players_number * Player.maxCard; i++) {
            if (this.deck[i].greaterThan(maxCard, trump)) {
                maxCard = this.deck[i].clone();
            }
            if (this.deck[i].lessThan(minCard, trump)) {
                minCard = this.deck[i].clone();
            }
        }
        for (int i = 0; i < this.players_number; i++) {
            for (int j = 0; j < this.players[i].trick.length; j++) {
                if (this.players[i].getTrick()[j].equals(maxCard)) {
                    this.players[i].score++;
                }
                if (this.players[i].getTrick()[j].equals(minCard)) {
                    this.players[i].score++;
                }
            }
        }
        Label failbid = new Label ("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer " + highPlayer + " failed to make the bid of " + highBid + " points");
        Label contractlbl = new Label ("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer " + highPlayer + " fulfilled the contract with " + this.players[highPlayer - 1].getScore() + " points");
        if (this.players[highPlayer - 1].getScore() < highBid) {
            this.players[highPlayer - 1].setScore(0 - highBid);
            layout.getChildren().add(failbid);
        }
        else {
            layout.getChildren().add(contractlbl);

        }

        Label prntscore= new Label ("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nAfter this game, the scores stand at:");
        Label p1scores = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n    Player 1: " + this.players[0].getScore() + " points");
        Label p2scores = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n    Player 2: " + this.players[1].getScore() + " points");
        Label p3scores = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n    Player 3: " + this.players[2].getScore() + " points");
        Label p4scores = new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n    Player 4: " + this.players[3].getScore() + " points");
        layout.getChildren().addAll(prntscore,p1scores,p2scores,p3scores,p4scores);
        Button exit = new Button("Quit Game");
        exit.setOnAction(event -> {
            System.exit(1);
            resume();
        });
        HBox h = new HBox(exit);
        h.setAlignment(Pos.BOTTOM_RIGHT);
        layout.getChildren().add(h);
        pause();

    }

    public static void main(String[] args) {

        try {
        } catch (Exception e) {
            System.exit(1);
        }
    }
    
}
