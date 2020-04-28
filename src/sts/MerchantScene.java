package sts;

import javafx.event.EventHandler;
import Models.Cards.AbstractCard;
import Models.Dungeon.Room.Merchant;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import Models.Cards.Deck;
import Models.Creatures.AbstractCharacter;
import Models.Game;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import Models.Object.AbstractRelic;

import java.util.ArrayList;

import static sts.Main.game;

public class MerchantScene extends RoomScene  {
    Pane pane;
    HBox box;
    ArrayList<AbstractCard> cards;
    ArrayList<Integer> prices;
    ArrayList<AbstractRelic> relics;
    ArrayList<Integer> cardPrices;
    ArrayList<Integer> relicPrices;

    public MerchantScene() {
        pane = new Pane();
        box = new HBox();
        root.setMinSize( width, height);
    }

    @Override
    public void draw() {

    }

    @Override
    public void initialize(){
        cards = ((Merchant) game.getDungeon().getCurrentRoom()).getCards();
        cardPrices = ((Merchant) game.getDungeon().getCurrentRoom()).getCardPrices();
        relics = ((Merchant) game.getDungeon().getCurrentRoom()).getRelics();
        relicPrices = ((Merchant) game.getDungeon().getCurrentRoom()).getRelicPrices();
        addBackground();
        shopCards();
        shopRelics();
    }

    public void warning(int x, int y, boolean destroy){
        ImageView imageView = new ImageView(new Image("warning.png"));
        imageView.setPreserveRatio(true);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(100);
        if(!destroy){
            pane.getChildren().add( imageView );
        }
        else
            pane.getChildren().removeAll(imageView);
    }

    public void shopCards(){
        int space = width/4;

        boolean saleAdded = true;
        int rand = (int) (Math.random() * 5);

        System.out.println("========INITIAL MASTER DECK=========");
        for(int k=0; k<game.getPlayer().masterDeck.getSize(); k++){
            System.out.println(game.getPlayer().masterDeck.getCard(k).getName());
        }
        System.out.println("========INITIAL MASTER DECK=========");

        for (int i = 0; i < cards.size(); i++){
            int price = cardPrices.get(i);
            String name = cards.get(i).getName();
            name = name + ".png";
            Rectangle rect = new Rectangle();
            rect.setFill(new ImagePattern(new Image(name)));
            rect.setX(space);
            rect.setY(height/5);
            rect.setWidth(100);
            rect.setHeight(150);
            rect.setVisible(true);
            if(saleAdded && rand == i ){
                saleAdded = false;
                price = price/2;
                Text text1 = new Text("SALE");
                text1.setX(space+15);
                text1.setY(height/5-5);
                text1.setFont(Font.font ("Verdana", 30));
                text1.setFill(Color.RED);
                pane.getChildren().add(text1);
            }

            pane.getChildren().add(rect);
            String cost = "cost " + price;
            Text text = new Text(cost);
            text.setX(space+10);
            text.setY(height/5+160);
            text.setFont(Font.font ("Verdana", 15));
            text.setFill(Color.WHITE);
            pane.getChildren().add(text);
            int warningLoc = space;
            int j = i;

            rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {

                    if (game.getPlayer().getGold() >= cardPrices.get(j)) {
                        game.getPlayer().changeGold(-cardPrices.get(j));
                        System.out.println(game.getPlayer().getGold());
                        game.getPlayer().masterDeck.addCard(cards.get(j));
                        pane.getChildren().remove(rect);
                        pane.getChildren().remove(text);

                        System.out.println("========AFTER CLICK MASTER DECK=========");
                        for(int k=0; k<game.getPlayer().masterDeck.getSize(); k++){
                            System.out.println(game.getPlayer().masterDeck.getCard(k).getName());
                        }

                    } else {
                        warning(warningLoc,height/5,false);
                        System.out.println("You don't have enough gold");
                    }

                }
            });

            rect.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), rect);
                    scaleTransition.setToX(1.5f);
                    scaleTransition.setToY(1.5f);
                    scaleTransition.setCycleCount(1);
                    scaleTransition.setAutoReverse(true);
                    scaleTransition.play();
                }
            });

            rect.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(400), rect);
                    scaleTransition.setToX(1);
                    scaleTransition.setToY(1);
                    scaleTransition.setAutoReverse(true);
                    scaleTransition.play();
                    warning(warningLoc,height/5,true);
                }
            });

            space = space + 120;
        }
        root.getChildren().add(pane);

    }

    public void shopRelics(){

    }

    private void addBackground() {
        ImageView back = new ImageView(new Image("back_merchant.jpg"));
        back.setFitWidth(width);
        back.setFitHeight(height);

        ImageView image = new ImageView(new Image("papirus.png"));
        image.setFitWidth(width-300);
        image.setFitHeight(height);
        root.getChildren().add(back);
        root.getChildren().add(image);

    }


}

