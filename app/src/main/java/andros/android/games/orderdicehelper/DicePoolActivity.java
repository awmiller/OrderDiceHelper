package andros.android.games.orderdicehelper;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class DicePoolActivity extends FragmentActivity implements View.OnClickListener,
PlayerConfigureFragment.OnFragmentInteractionListener{

    protected TurnPool MainCup;
    ArrayList<TurnObject> dice;
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

        players = new HashMap<>();
        players.put(">#//Wheres!_*&Waldo.?    ", new Player(">#//Wheres!_*&Waldo.?    "));
    }

    @Override
    protected void onResume() {
        super.onResume();

        mGameLog = (TextView)findViewById(R.id.GameLogView);

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

    }



    private void runGame() {

        for(Player p : players.values())
        {
            MainCup.addPoolObjects(p.poolContent);
        }
        Game go = new Game(MainCup);

        TurnObject po = go.takeOneTurn();

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
                mGameLog.append(String.format("Owner %s has taken dice %d/%d\r\n",po.getOwnerName(),go.getTurn(),go.getAvailableTurns()));
                po = go.takeOneTurn();
            }
        }

    }

    @Override
    public void onPlayerSubmitted(Player player) {
        players.put(player.name(),player);
    }

    @Override
    public Player getPlayerOrNew(String match) {
        if (players.containsKey(match)) {
            return players.get(match);
        } else {
            Player p = new Player(match);
            players.put(match,p);
            return p;
        }
    }

    @Override
    public void passClick(View v)
    {
        runGame();
    }
}
