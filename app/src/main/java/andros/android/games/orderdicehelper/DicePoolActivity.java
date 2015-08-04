package andros.android.games.orderdicehelper;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import andros.android.games.orderdicehelper.objects.Player;
import andros.android.games.orderdicehelper.objects.TurnObject;
import andros.android.games.orderdicehelper.objects.TurnPool;

public class DicePoolActivity extends FragmentActivity implements View.OnClickListener,
PlayerConfigureFragment.OnFragmentInteractionListener,
GamePlayerFragment.OnFragmentInteractionListener,
PlayerCardListFragment.OnFragmentInteractionListener{

    protected TurnPool MainCup;
    ArrayList<TurnObject> dice;
    HashMap<String,Player> players;
    GamePlayerFragment gpf;
    private PlayerCardListFragment mPlayerListFragment;

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if(fm.getBackStackEntryCount()>1)
            fm.popBackStack();
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_pool);

        MainCup = new TurnPool();

        dice = new ArrayList<>();

        players = new HashMap<>();

        FragmentTransaction execute = getFragmentManager().beginTransaction();

        mPlayerListFragment =  new PlayerCardListFragment();

        execute.add(R.id.FragmentContainer,mPlayerListFragment);

        execute.addToBackStack(null);

        execute.commit();
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

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_add_player:
                configNewPlayer();
                break;
            case R.id.start_game:
                runGame();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configNewPlayer() {

        FragmentManager fm = getFragmentManager();

        if (fm.getBackStackEntryCount()>1)
        {
            Toast.makeText(DicePoolActivity.this, "Please submit current Player or Exit current Game", Toast.LENGTH_SHORT).show();
        }
        else
        {
            FragmentTransaction execute = fm.beginTransaction();

            execute.replace(R.id.FragmentContainer, new PlayerConfigureFragment());

            execute.addToBackStack(null);

            execute.commit();
        }

    }

    @Override
    public void onClick(View v) {

    }



    private void runGame() {

        FragmentManager fm = getFragmentManager();

        FragmentTransaction execute = fm.beginTransaction();

        gpf = GamePlayerFragment.newInstance(new ArrayList<>(players.keySet()));

        execute.replace(R.id.FragmentContainer, gpf);

        execute.addToBackStack(null);

        execute.commit();

    }

    @Override
    public void onPlayerSubmitted(Player player) {
        players.put(player.name(), player);

        getFragmentManager().popBackStack();
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

    @Override
    public ArrayList<Player> onPlayersFetchRequest(ArrayList<String> names) {
        //optionally we could scan names to selectively play with a subset of Players
        return new ArrayList<>(players.values());
    }

    @Override
    public HashMap<String, Player> onFragmentRequestsPlayers() {
        return players;
    }
}
