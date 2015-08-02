package andros.android.games.orderdicehelper;

/**
 * Created by Andrew on 8/2/2015.
 */
public class Player {
    protected TurnPool poolContent;
    private String Name;

    public Player(String name) {
        Name = name;
        poolContent = new TurnPool();
    }

    public int addToPool(TurnObject po) {
        poolContent.addPoolObject(po);
        return poolContent.size();
    }

    public String name() {
        return Name;
    }

    public TurnObject removeOneFromPool() {
        return poolContent.removeRandom();
    }
}
