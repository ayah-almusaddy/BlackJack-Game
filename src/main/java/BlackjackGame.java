import java.util.ArrayList;
public class BlackjackGame {
    public ArrayList<Card> playerHand;
    public ArrayList<Card> bankerHand;
    public BlackjackDealer theDealer;
    public BlackjackGameLogic gameLogic;
    public double currentBet;
    public double totalWinnings;


    public double evaluateWinnings() {
        String result = gameLogic.whoWon(playerHand, bankerHand);
        if ("player".equals(result) && gameLogic.handTotal(playerHand) == 21) {
            totalWinnings += (currentBet * 1.5);
            return currentBet * 1.5;
        }
        else if ("player".equals(result)) {
            totalWinnings += currentBet;
            return currentBet;
        }
        else if ("dealer".equals(result)) {
            totalWinnings -= currentBet;
            return -currentBet;
        }
        else {
            return 0.0;
        }
    }

}
