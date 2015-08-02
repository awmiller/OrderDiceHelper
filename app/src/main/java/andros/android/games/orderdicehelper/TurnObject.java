package andros.android.games.orderdicehelper;

/**
 * Created by Andrew on 8/2/2015.
 */
public class TurnObject {

    private Player Owner;

    public TurnObject(Player own) {
        Owner = own;
    }

    public String getOwnerName() {
        return Owner.name();
    }
}
