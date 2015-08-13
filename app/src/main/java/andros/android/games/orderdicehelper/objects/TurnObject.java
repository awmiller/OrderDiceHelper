package andros.android.games.orderdicehelper.objects;

import java.lang.reflect.Array;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by Andrew on 8/2/2015.
 */
public class TurnObject extends BaseTurn {


    public TurnObject(Player own) {
        Owner = own;
    }

    public TurnObject(TurnObject turnObject) {
        this.Owner = turnObject.Owner;
    }

    public static String[] Actions = {"Run", "Advance", "Ambush", "Shoot", "Rally", "Down"};


    @Override
    public String toString() {
        return TurnObject.class.getCanonicalName()+":"+Owner.Name;
    }


    @Override
    public String getOwnerName() {
        return Owner.Name;
    }

    @Override
    public List<String> descibeActions() {
        return Arrays.asList(Actions);
    }

    @Override
    public String[] describeActions() {
        return Actions;
    }
}
