package andros.android.games.orderdicehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import andros.android.games.orderdicehelper.objects.Player;
import andros.android.games.orderdicehelper.objects.TurnObject;

public class GameActivity extends AppCompatActivity {

    public static String ORDERED_PLAYER_LIST_EXTRA = "A()W*Tgha9)YA*HWIT(Gug39[oai";
    public static String ORDERED_PLAYER_DICE_EXTRA = "A_0uja -fa]qwkdf[k    ";
    public static String TURN_OBJECT_TYPE_EXTRA = "A)Y*wa9g[hdf90aHWIawh90a";

    public ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        players = new ArrayList<>();

        Intent intent = getIntent();

        if(intent.hasExtra(ORDERED_PLAYER_LIST_EXTRA))
        {
            String[] stringArrayExtra = intent.getStringArrayExtra(ORDERED_PLAYER_LIST_EXTRA);
            for (int i = 0; i < stringArrayExtra.length; i++) {
                String name = stringArrayExtra[i];
                players.add(new Player(name));
            }
        }
        if(intent.hasExtra(ORDERED_PLAYER_DICE_EXTRA))
        {
            int[] stringArrayExtra = intent.getIntArrayExtra(ORDERED_PLAYER_DICE_EXTRA);
            for (int i = 0; i < stringArrayExtra.length; i++) {
                int dice = stringArrayExtra[i];
                players.get(i).addToPool(new TurnObject(players.get(i)),dice);
            }
        }
        if(intent.hasExtra(TURN_OBJECT_TYPE_EXTRA))
        {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
}
