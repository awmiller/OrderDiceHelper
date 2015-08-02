package andros.android.games.orderdicehelper;

/**
 * Created by Andrew on 8/2/2015.
 */
public class Game {

    TurnPool mainTurnPool;
    TurnPool discardTurnPool;

    private int AvailableTurns;

    private int Turn;

    private int Round;

    public Game(TurnPool turnPool) {
        mainTurnPool = turnPool;
        discardTurnPool = new TurnPool();
        Turn = 0;
        Round = 0;
        AvailableTurns = turnPool.size();
    }

    public TurnObject takeOneTurn() {
        Turn++;
        TurnObject to = mainTurnPool.removeRandom();
        discardTurnPool.add(to);
        if (to == null) {
            Round++;
        }

        return to;
    }

    public int getRound() {
        return Round;
    }

    public int getTurn() {
        return Turn;
    }

    public int getAvailableTurns() {
        return AvailableTurns;
    }


}
