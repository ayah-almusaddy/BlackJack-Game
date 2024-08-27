import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
public class Tests {

    //BlackjackDealer Class
    @Test
    void testGenerateDeck() {
        BlackjackDealer dealer = new BlackjackDealer();
        dealer.generateDeck();
        assertEquals(52, dealer.deckSize(), "generate deck didnt generate 52 cards");
    }

    @Test
    void testDealHand() {
        ArrayList<Card> cards;
        BlackjackDealer dealer = new BlackjackDealer();
        dealer.generateDeck();
        cards = dealer.dealHand();
        assertEquals(50,dealer.deckSize(),"deck of cards did not reduce after being dealt");
        assertEquals(2,cards.size(),"arraylist of cards does not equal 2 when dealt first hands");
    }

    @Test
    void testDrawOne() {
        BlackjackDealer dealer = new BlackjackDealer();
        dealer.generateDeck();
        Card myCard = dealer.drawOne();
        assertEquals(11, myCard.value, "wrong card assigned");
        assertEquals("Clubs", myCard.suit, "wrong card assigned");
    }

    @Test
    void testDrawOneEmptyDeck() {
        BlackjackDealer dealer = new BlackjackDealer();
        dealer.generateDeck();

        for (int i = 0; i < 52; i++) {
            dealer.drawOne();
        }
        assertNull(dealer.drawOne(), "incorrect null behavior for draw one");
    }

    @Test
    void shuffleDeckSize() {
        BlackjackDealer dealer = new BlackjackDealer();
        dealer.generateDeck();
        dealer.dealHand();
        dealer.drawOne();
        dealer.drawOne();
        dealer.drawOne();
        dealer.shuffleDeck();
        assertEquals(52, dealer.deckSize(), "shuffle deck doesnt properly generate deck");
    }

    @Test
    void testShuffleDeck() {
        BlackjackDealer dealer1 = new BlackjackDealer();
        BlackjackDealer dealer2 = new BlackjackDealer();
        dealer1.generateDeck();
        dealer2.shuffleDeck();
        assertNotEquals(dealer1.drawOne(), dealer2.drawOne(), "incorrect shuffle behavior");
    }

    @Test
    void testDeckSize() {
        BlackjackDealer d2 = new BlackjackDealer();
        d2.generateDeck();
        d2.dealHand();
        d2.drawOne();
        d2.drawOne();
        assertEquals(48, d2.deckSize());
    }

    //BlackjackGameLogic tests
    @Test
    void testHandTotal() {
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();
        ArrayList<Card> cards = new ArrayList<>();
        Card aceOfHearts = new Card("Hearts",11);
        Card kingOfHearts = new Card("Hearts",10);
        Card tenOfSpades = new Card("Spades", 10);
        Card fourOfClubs = new Card("Clubs", 4);
        Card sixOfDiamonds = new Card("Diamonds", 6);
        cards.add(aceOfHearts);
        cards.add(fourOfClubs);
        assertEquals(15,gameLogic.handTotal(cards),"ace should count as 11");
        cards.add(kingOfHearts);
        assertEquals(15,gameLogic.handTotal(cards), "ace should count as 1");
    }

    @Test
    void testWhoWonPlayerWins() {
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();
        ArrayList<Card> pcards = new ArrayList<>();
        ArrayList<Card> bcards = new ArrayList<>();
        Card aceOfHearts = new Card("Hearts",11);
        Card kingOfHearts = new Card("Hearts",10);
        Card tenOfSpades = new Card("Spades", 10);
        Card fourOfClubs = new Card("Clubs", 4);
        Card sixOfDiamonds = new Card("Diamonds", 6);
        pcards.add(aceOfHearts);
        pcards.add(kingOfHearts);
        bcards.add(tenOfSpades);
        bcards.add(sixOfDiamonds);
        assertEquals("player",gameLogic.whoWon(pcards,bcards));
    }

    @Test
    void testWhoWonDealerWins() {
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();
        ArrayList<Card> pcards = new ArrayList<>();
        ArrayList<Card> bcards = new ArrayList<>();
        Card aceOfHearts = new Card("Hearts",11);
        Card kingOfHearts = new Card("Hearts",10);
        Card tenOfSpades = new Card("Spades", 10);
        Card fourOfClubs = new Card("Clubs", 4);
        Card sixOfDiamonds = new Card("Diamonds", 6);
        bcards.add(aceOfHearts);
        bcards.add(kingOfHearts);
        pcards.add(tenOfSpades);
        pcards.add(sixOfDiamonds);
        assertEquals("dealer",gameLogic.whoWon(pcards,bcards));
    }

    @Test
    void testWhoWonPush() {
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();
        ArrayList<Card> pcards = new ArrayList<>();
        ArrayList<Card> bcards = new ArrayList<>();
        Card aceOfHearts = new Card("Hearts",11);
        Card kingOfHearts = new Card("Hearts",10);
        Card tenOfSpades = new Card("Spades", 10);
        Card fourOfClubs = new Card("Clubs", 4);
        Card sixOfDiamonds = new Card("Diamonds", 6);
        bcards.add(fourOfClubs);
        bcards.add(kingOfHearts);
        pcards.add(tenOfSpades);
        pcards.add(fourOfClubs);
        assertEquals("push",gameLogic.whoWon(pcards,bcards));
    }

    @Test
    void testEvaluateBankerDraw() {
        BlackjackGameLogic gameLogic = new BlackjackGameLogic();
        ArrayList<Card> under16 = new ArrayList<>();
        ArrayList<Card> over16 = new ArrayList<>();
        Card aceOfHearts = new Card("Hearts",11);
        Card kingOfHearts = new Card("Hearts",10);
        Card tenOfSpades = new Card("Spades", 10);
        Card fourOfClubs = new Card("Clubs", 4);
        Card sixOfDiamonds = new Card("Diamonds", 6);
        under16.add(tenOfSpades);
        under16.add(fourOfClubs);
        over16.add(aceOfHearts);
        over16.add(kingOfHearts);
        assertTrue(gameLogic.evaluateBankerDraw(under16));
        assertFalse(gameLogic.evaluateBankerDraw(over16));
    }

    @Test
    void testEvaluateWinningsPlayerBlackjack() {
        BlackjackGame game = new BlackjackGame();
        game.theDealer = new BlackjackDealer();
        game.gameLogic = new BlackjackGameLogic();
        game.playerHand = new ArrayList<>();
        game.bankerHand = new ArrayList<>();
        game.totalWinnings = 1000;
        game.currentBet = 100;

        game.playerHand.add(new Card("Hearts",11));
        game.playerHand.add(new Card("Spades",10));
        game.bankerHand.add(new Card("Hearts",9));
        game.bankerHand.add(new Card("Diamonds",9));

        double winnings = game.evaluateWinnings();
        assertEquals(150, winnings);
        assertEquals(1150, game.totalWinnings);
    }

    @Test
    void testEvaluateWinningsPlayerWins() {
        BlackjackGame game = new BlackjackGame();
        game.theDealer = new BlackjackDealer();
        game.gameLogic = new BlackjackGameLogic();
        game.playerHand = new ArrayList<>();
        game.bankerHand = new ArrayList<>();
        game.totalWinnings = 1000;
        game.currentBet = 100;

        game.playerHand.add(new Card("Hearts",10));
        game.playerHand.add(new Card("Spades",10));
        game.bankerHand.add(new Card("Hearts",9));
        game.bankerHand.add(new Card("Diamonds",9));

        double winnings = game.evaluateWinnings();
        assertEquals(100,winnings);
        assertEquals(1100, game.totalWinnings);
    }

    @Test
    void testEvaluateWinningsBankerWins() {
        BlackjackGame game = new BlackjackGame();
        game.theDealer = new BlackjackDealer();
        game.gameLogic = new BlackjackGameLogic();
        game.playerHand = new ArrayList<>();
        game.bankerHand = new ArrayList<>();
        game.totalWinnings = 1000;
        game.currentBet = 100;

        game.playerHand.add(new Card("Hearts",11));
        game.playerHand.add(new Card("Spades",11));
        game.bankerHand.add(new Card("Hearts",9));
        game.bankerHand.add(new Card("Diamonds",9));

        double winnings = game.evaluateWinnings();
        assertEquals(-100,winnings);
        assertEquals(900, game.totalWinnings);
    }

    @Test
    void testEvaluateWinningsDraw() {
        BlackjackGame game = new BlackjackGame();
        game.theDealer = new BlackjackDealer();
        game.gameLogic = new BlackjackGameLogic();
        game.playerHand = new ArrayList<>();
        game.bankerHand = new ArrayList<>();
        game.totalWinnings = 1000;
        game.currentBet = 100;

        game.playerHand.add(new Card("Hearts",10));
        game.playerHand.add(new Card("Spades",10));
        game.bankerHand.add(new Card("Hearts",10));
        game.bankerHand.add(new Card("Diamonds",10));

        double winnings = game.evaluateWinnings();
        assertEquals(0,winnings);
        assertEquals(1000, game.totalWinnings);
    }






}
