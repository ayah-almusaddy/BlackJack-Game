import java.util.ArrayList;
public class BlackjackGameLogic {
    public String whoWon(ArrayList <Card> playerHand1, ArrayList<Card> dealerHand) {
        int playerScore = handTotal(playerHand1);
        int dealerScore = handTotal(dealerHand);

        if (playerScore == dealerScore)
            return "push";
        else if (playerScore > 21)
            return "dealer";
        else if (dealerScore > 21)
            return "player";
        else if (playerScore > dealerScore)
            return "player";
        else
            return "dealer";

    }

    public int handTotal(ArrayList<Card> hand) {
        int total = 0;
        boolean ace = false;
        for (Card e : hand) {
            total += e.value;
            if (e.value == 11)
                ace = true;
        }
        if (total > 21 && ace) {
            total -= 10;
        }
        return total;
    }

    public boolean evaluateBankerDraw(ArrayList<Card> hand) {
        int total = handTotal(hand);
        if (total <= 16) {
            return true;
        }
        return false;
    }

}
