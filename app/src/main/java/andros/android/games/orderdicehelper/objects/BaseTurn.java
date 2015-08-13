package andros.android.games.orderdicehelper.objects;

import java.util.List;

/**
 * Created by Andrew on 8/9/2015.
 */
public abstract class BaseTurn {

    protected Player Owner;

    public abstract String getOwnerName();

    public abstract List<String> descibeActions();

    public abstract String[] describeActions();
}
