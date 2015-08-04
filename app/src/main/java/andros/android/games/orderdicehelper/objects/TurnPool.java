package andros.android.games.orderdicehelper.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by Andrew on 8/2/2015.
 */
public class TurnPool extends ArrayList<TurnObject> {
    private Random randoNumGen;

    public TurnPool() {
        super();
        randoNumGen = new Random(System.nanoTime());
    }

    public int addPoolObjects(Collection<TurnObject> turnObjects) {
        this.addAll(turnObjects);
        return this.size();
    }

    public int addPoolObject(TurnObject turnObject) {
        this.add(turnObject);
        return this.size();
    }

    public TurnObject removeRandom() {
        if (this.size() > 0) {

            int gen = randoNumGen.nextInt(this.size());

            return this.remove(gen);
        } else {
            return null;
        }

    }

}
