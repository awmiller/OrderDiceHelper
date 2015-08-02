package andros.android.games.orderdicehelper;

import android.content.ReceiverCallNotAllowedException;
import android.renderscript.Allocation;
import android.support.v4.util.Pools;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Random;

public class DicePoolActivity extends AppCompatActivity implements View.OnClickListener {

    protected TurnPool MainCup;
    ArrayList<PoolObject> dice;
    HashMap<String,Player> players;
    Button mAddDiceButton;
    private TextView mOwnerTextView;
    private Button mStartButton;
    private TextView mGameLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_pool);

        MainCup = new TurnPool();

        dice = new ArrayList<>();

        mAddDiceButton = (Button) findViewById(R.id.addDieButton);
        mAddDiceButton.setOnClickListener(this);

        mStartButton = (Button) findViewById(R.id.startGameButtonText);
        mStartButton.setOnClickListener(this);

        mOwnerTextView = (TextView)findViewById(R.id.editDieOwnerText);
        mGameLog = (TextView)findViewById(R.id.GameLogView);

        players = new HashMap<>();
        players.put(">#//Wheres!_*&Waldo.?    ",new Player(">#//Wheres!_*&Waldo.?    "));

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dice_pool, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.startGameButtonText:
                runGame();
                break;
            case R.id.addDieButton:
                getNewDice();
                break;
        }
    }

    private void getNewDice() {
        if(mOwnerTextView!=null)
        {
            String name = mOwnerTextView.getText().toString();

            if(players.containsKey(name))
            {
                players.get(name).addToPool(new PoolObject(players.get(name)));
            }
            else
            {
                players.put(name, new Player(name));
                players.get(name).addToPool(new PoolObject(players.get(name)));
            }
        }
    }

    private void runGame() {

        //MainCup.addPoolObjects(dice);
        for(Player p : players.values())
        {
            MainCup.addPoolObjects(p.poolContent.AvailableTurns);
        }
        Game go = new Game(MainCup);

        PoolObject po = go.takeOneTurn();

        if(po == null)
        {
            if(mGameLog!=null)
            {
                mGameLog.setText("Pool is empty!\r\n");
            }
        }
        else
        {
            while(po!=null)
            {
                mGameLog.append(String.format("Owner %s has taken dice %d/%d\r\n",po.Owner.Name,MainCup.AvailableTurns.indexOf(po),MainCup.size()));
                po = go.takeOneTurn();
            }
        }

    }

    public class TurnPool{
        protected ArrayList<PoolObject> AvailableTurns;

        private Random randoNumGen;

        public TurnPool(){
            AvailableTurns = new ArrayList<>();
            randoNumGen = new Random(System.nanoTime());
        }

        public int addPoolObjects(Collection<PoolObject> poolObjects)
        {
            AvailableTurns.addAll(poolObjects);
            return AvailableTurns.size();
        }

        public int addPoolObject(PoolObject poolObject)
        {
            AvailableTurns.add(poolObject);
            return AvailableTurns.size();
        }

        public PoolObject removeRandom()
        {
            if (AvailableTurns.size()>0) {

                int gen =randoNumGen.nextInt(AvailableTurns.size());

                return AvailableTurns.remove(gen);
            }
            else
            {
                return null;
            }

        }

        public int size()
        {
            return AvailableTurns.size();
        }
    }

    public class PoolObject {
        private ArrayList<String> Faces;

        public Player Owner;

        public PoolObject(Player own)
        {
            Faces = new ArrayList<String>();
            Collections.addAll(Faces, "1","2","3","4","5","6");
            Owner = own;
        }
    }

    public class Game {

        TurnPool mainTurnPool;

        public Game(TurnPool turnPool)
        {
            mainTurnPool = turnPool;
        }

        public PoolObject takeOneTurn()
        {
            return mainTurnPool.removeRandom();
        }

    }

    public class Player
    {
        protected TurnPool poolContent;
        private String Name;

        public Player(String name)
        {
            Name = name;
            poolContent = new TurnPool();
        }

        public int addToPool(PoolObject po)
        {
            poolContent.addPoolObject(po);
            return poolContent.size();
        }

        public PoolObject removeOneFromPool()
        {
            return poolContent.removeRandom();
        }
    }
}
