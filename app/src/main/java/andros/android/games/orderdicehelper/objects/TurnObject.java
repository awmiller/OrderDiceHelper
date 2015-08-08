package andros.android.games.orderdicehelper.objects;

import java.util.jar.Attributes;

/**
 * Created by Andrew on 8/2/2015.
 */
public class TurnObject {

    private Player Owner;

    public TurnObject(Player own) {
        Owner = own;
    }

    public TurnObject(TurnObject turnObject) {
        this.Owner = turnObject.Owner;
    }

    public String getOwnerName() {
        return Owner.Name;
    }

    @Override
    public String toString() {
        return TurnObject.class.getCanonicalName()+":"+Owner.Name;
    }
}
