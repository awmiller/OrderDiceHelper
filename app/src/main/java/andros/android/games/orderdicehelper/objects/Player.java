package andros.android.games.orderdicehelper.objects;

/**
 * Created by Andrew on 8/2/2015.
 */
public class Player {
    public TurnPool poolContent;
    public String Name;

    public Player(String name) {
        Name = name;
        poolContent = new TurnPool();
    }

    public int addToPool(TurnObject po) {
        poolContent.addPoolObject(po);
        return poolContent.size();
    }

    public TurnObject removeOneFromPool() {
        return poolContent.removeRandom();
    }
}
