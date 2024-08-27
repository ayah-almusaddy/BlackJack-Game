import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.ArrayList;



public class JavaFXGame extends Application {

    private Stage primaryStage;
    BlackjackGame game = new BlackjackGame();


    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        welcomePage();
    }

    private void welcomePage() {
        Label welcome = new Label("Welcome to Ayah Almusaddy's Blackjack Game!");
        welcome.setFont(new Font("Times New Roman", 24));

        Button play = new Button("Play!");
        VBox layout = new VBox(40, welcome, play);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color:pink");
        Scene welcomeScene = new Scene(layout, 600, 400);

        play.setOnAction(e -> {
            game.playerHand = new ArrayList<>();
            game.bankerHand = new ArrayList<>();
            game.gameLogic = new BlackjackGameLogic();
            game.currentBet = 0.0;
            game.totalWinnings = 1000;
            game.theDealer = new BlackjackDealer();
            betPage();
        });

        primaryStage.setTitle("JavaFX blackjack game");
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }


    private void betPage() {
        Label bet = new Label("How much would you like to bet?");
        String currentNum = "Current Money in Bank: " + game.totalWinnings;
        Label currMoney = new Label(currentNum);
        currMoney.setFont(new Font("Times New Roman",20));
        bet.setFont(new Font("Comic Sans MS", 24));
        TextField betAmount = new TextField();
        betAmount.setMaxWidth(200);
        betAmount.setPromptText("Enter amount to bet");
        Button submitBet = new Button("submit bet");

        submitBet.setOnAction(e-> {
            try {
                double amount = Double.parseDouble(betAmount.getText());
                if (amount <=0) {
                    System.out.println("Please enter a positive number");
                }
                else {
                    game.currentBet = amount;
                    gamePage();
                }
            }
            catch (NumberFormatException ex){
                System.out.println("Please enter a valid number.");
            }
        });

        VBox layout = new VBox(20, currMoney, bet, betAmount, submitBet);
        layout.setAlignment(Pos.CENTER);
        Scene betScene = new Scene(layout, 600, 400);
        layout.setStyle("-fx-background-color:#57bdf3");
        primaryStage.setScene(betScene);
    }

    private void gamePage() {
        game.theDealer.shuffleDeck();
        game.playerHand = game.theDealer.dealHand();
        game.bankerHand = game.theDealer.dealHand();
        if (game.gameLogic.handTotal(game.playerHand) > 21
                || game.gameLogic.handTotal(game.bankerHand) > 21) {
            evaluateWinners();
        }
        ArrayList<String> pCards = new ArrayList<>();
        ArrayList<String> bCards = new ArrayList<>();
        for (Card e: game.playerHand) {
            String temp = e.value + " of " + e.suit;
            pCards.add(temp);
        }
        for (Card e: game.bankerHand) {
            String temp = e.value + " of " + e.suit;
            bCards.add(temp);
        }

        HBox pHBox = new HBox(30);
        for (String cardText : pCards) {
            Label cardLabel = new Label(cardText);
            cardLabel.getStyleClass().add("card-style");
            pHBox.getChildren().add(cardLabel);
        }
        pHBox.setAlignment(Pos.CENTER);

        Label cardLabel = new Label(bCards.get(0));
        cardLabel.getStyleClass().add("card-style");
        HBox bHBox = new HBox(30, cardLabel);
        bHBox.setAlignment(Pos.CENTER);

        Button stand = new Button("STAND");
        Button hit = new Button("HIT");
        HBox middle = new HBox(30,stand, hit);
        middle.setAlignment(Pos.CENTER);

        hit.setOnAction(e->{
            Card place = game.theDealer.drawOne();
            game.playerHand.add(place);
            Card temp = game.playerHand.get(game.playerHand.size()-1);
            String newCard = temp.value + " of " + temp.suit;
            pCards.add(newCard);

            //hit resulted in player > 21
            if (game.gameLogic.handTotal(game.playerHand) > 21) {
                //game.bankerDraws();
                while (game.gameLogic.evaluateBankerDraw(game.bankerHand)) {
                    game.bankerHand.add(game.theDealer.drawOne());
                }
                evaluateWinners();
            }

            //hit did not result in player > 21
            Label newCardLabel = new Label(newCard);
            newCardLabel.getStyleClass().add("card-style");
            pHBox.getChildren().add(newCardLabel);
        });

        stand.setOnAction(e-> {
            while (game.gameLogic.evaluateBankerDraw(game.bankerHand)) {
                game.bankerHand.add(game.theDealer.drawOne());
            }
            evaluateWinners();
        });

        VBox root = new VBox(50,bHBox, middle, pHBox);
        root.setAlignment(Pos.CENTER);
        Scene gameScene = new Scene(root,600,400);
        gameScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        root.setStyle("-fx-background-color: #235623");
        primaryStage.setScene(gameScene);

    }

    private void evaluateWinners() {
        ArrayList<String> pCards = new ArrayList<>();
        ArrayList<String> bCards = new ArrayList<>();
        for (Card e: game.playerHand) {
            String temp = e.value + " of " + e.suit;
            pCards.add(temp);
        }
        for (Card e: game.bankerHand) {
            String temp = e.value + " of " + e.suit;
            bCards.add(temp);
        }

        HBox pHBox = new HBox(20);
        for (String cardText : pCards) {
            Label cardLabel = new Label(cardText);
            cardLabel.getStyleClass().add("card-style");
            pHBox.getChildren().add(cardLabel);
        }
        HBox bHBox = new HBox(20);
        for (String cardText : bCards) {
            Label cardLabel = new Label(cardText);
            cardLabel.getStyleClass().add("card-style");
            bHBox.getChildren().add(cardLabel);
        }
        bHBox.setAlignment(Pos.CENTER);
        pHBox.setAlignment(Pos.CENTER);

        double win = game.evaluateWinnings();
        String result;
        if (win == game.currentBet) {
            result = "You WON $" + win + "!";
        }
        else if (win > game.currentBet) {
            result = "BLACKJACK! You WON $" + win;
        }
        else if (win == 0.0) {
            result = "Draw. You didn't win anything.";
        }
        else {
            result = "You lost $" + win;
        }
        Label end = new Label(result);
        end.setFont(new Font("Times New Roman", 30));
        HBox middle = new HBox(end);
        middle.setAlignment(Pos.CENTER);

        Button newGame = new Button("Play Again!");
        newGame.setOnAction(e->{
            if (game.totalWinnings <= 0) {
                game = new BlackjackGame();
                welcomePage();
            }
            else {
                betPage();
            }
        });

        VBox root = new VBox(30,bHBox, middle, pHBox, newGame);
        root.setAlignment(Pos.CENTER);
        Scene gameScene = new Scene(root,600,400);
        gameScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        root.setStyle("-fx-background-color: #235623");
        primaryStage.setScene(gameScene);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
