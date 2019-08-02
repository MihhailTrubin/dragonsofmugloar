import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class Main {
    public static String GAME_ID;
    public static String MESSAGE_ID;
    public static int GOLD;
    public static int LEVEL = 0;
    public static int LIVES = 3;
    public static int SCORE;
    public static int ITEM_TO_BUY=0;
    public static int TURN=0;
    public static int HIGHSCORE = 0;

    public static void main(String[] args) {
        Connections.createGame();
        while (LIVES>0){
            if (LIVES<3 && GOLD>=50){
                Connections.purchaseItem("hpot");
            }
            if (GOLD>=150){
                buyItem();
            }

            chooseMessage(Connections.getMessages());
            Connections.solveMessage();
            System.out.println("Your score: " + SCORE +"\n");

        }

        System.out.println("Game Over!");
        System.out.println("You lasted: "+ TURN + " turns");
        System.out.println("Your level: "+ LEVEL);
        System.out.println("Your score: " + SCORE );
        System.out.println("Highscore: " + HIGHSCORE );


    }

    public static void buyItem(){
        String itemToPurchase = "cs";
        switch (ITEM_TO_BUY) {
            case 0:
                itemToPurchase = "cs";
            case 1:
                itemToPurchase = "gas";
            case 2:
                itemToPurchase = "wax";
            case 3:
                itemToPurchase = "tricks";
            case 4:
                itemToPurchase = "wingpot";
        }
        ITEM_TO_BUY++;
        if (ITEM_TO_BUY>4){
            ITEM_TO_BUY=0;
        }

        Connections.purchaseItem(itemToPurchase);
        System.out.println("Your level is: "+ LEVEL);
    }

    public static void chooseMessage(List<Message> messageList){

        int maxReward = 0;
        int maxRewardSteal = 0;
        String messageIDSteal =null;
        int probabilityCount = 1;
        String probability = "Sure thing";

        while (maxReward == 0) {
            switch (probabilityCount) {
                case 1:
                    probability = "Sure thing";
                    break;
                case 2:
                    probability = "Piece of cake";
                    break;
                case 3:
                    probability = "Walk in the park";
                    break;
                case 4:
                    probability = "Quite likely";
                    break;
                case 5:
                    probability = "Hmmm....";
                    break;
                case 6:
                    probability = "Gamble";
                    break;
                case 7:
                    probability = "Rather detrimental";
                    break;
                case 8:
                    probability = "Risky";
                    break;
                case 9:
                    probability = "Playing with fire";
                    break;
                case 10:
                    probability = "Impossible";
                    break;
                case 11:
                    probability = "Suicide mission";
                    break;

            }
            for (int i = 0; i < messageList.size(); i++) {
                if (messageList.get(i).getProbability().equals(probability)) {
                    if (messageList.get(i).getReward() > maxReward && messageList.get(i).getMessage().indexOf("Steal") == -1) {
                        maxReward = messageList.get(i).getReward();
                        MESSAGE_ID = messageList.get(i).getAdId();
                    } else if (maxRewardSteal==0){
                        maxRewardSteal = messageList.get(i).getReward();
                        messageIDSteal = messageList.get(i).getAdId();
                    }
                }

            }
            probabilityCount++;
            if (probabilityCount>11){
                maxReward = maxRewardSteal;
                MESSAGE_ID = messageIDSteal;
            }
        }
    }
}
