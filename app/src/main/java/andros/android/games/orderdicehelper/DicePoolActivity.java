package andros.android.games.orderdicehelper;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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
    private void configPlayer(Player player) {

        FragmentManager fm = getFragmentManager();

        if (fm.getBackStackEntryCount()>1)
        {
            Toast.makeText(DicePoolActivity.this, "Please submit current Player or Exit current Game", Toast.LENGTH_SHORT).show();
        }
        else
        {
            FragmentTransaction execute = fm.beginTransaction();

            PlayerConfigureFragment pcf = PlayerConfigureFragment.newInstance(player.Name);

            execute.replace(R.id.FragmentContainer, pcf);

            execute.addToBackStack(null);

            execute.commit();

            pcf.setPlayer(player);
        }

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    private void runGame() {

        FragmentManager fm = getFragmentManager();

        FragmentTransaction execute = fm.beginTransaction();

        gpf = GamePlayerFragment.newInstance(new ArrayList<>(players.keySet()));

        execute.replace(R.id.FragmentContainer, gpf);

        execute.addToBackStack(null);

        execute.commit();

//        Intent intent = new Intent();
//        String[] playernames = players.keySet().toArray(new String[players.size()]);
//        Integer[] playerdice = new Integer[playernames.length];
//        for(int i =0; i<playernames.length;i++)
//        {
//            playerdice[i] = players.get(playernames[i]).poolContent.size();
//        }
//        intent.putExtra(GameActivity.ORDERED_PLAYER_LIST_EXTRA,playernames );
//
//        intent.putExtra(GameActivity.ORDERED_PLAYER_DICE_EXTRA,playerdice);
//
//        intent.putExtra(GameActivity.TURN_OBJECT_TYPE_EXTRA,TurnObject.class.getCanonicalName());


    }

    @Override
    public void onPlayerSubmitted(Player player) {
        players.put(player.Name, player);

        getFragmentManager().popBackStack();
    }

    @Override
    public Player onPlayerRequested(String name) {
        if(players.containsKey(name))
            return players.get(name);
        else
            return new Player(name);
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

    @Override
    public boolean onListItemClicked(Player player) {

        if(player != null)
        {
            configPlayer(player);
            return true;
        }
        return false;
    }
}
