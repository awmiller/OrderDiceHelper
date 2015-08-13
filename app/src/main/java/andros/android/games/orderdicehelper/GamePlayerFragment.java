package andros.android.games.orderdicehelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.ArrayList;

import andros.android.games.orderdicehelper.objects.Game;
import andros.android.games.orderdicehelper.objects.GameTypeSettings;
import andros.android.games.orderdicehelper.objects.Player;
import andros.android.games.orderdicehelper.objects.TurnObject;
import andros.android.games.orderdicehelper.objects.TurnPool;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link GamePlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamePlayerFragment extends Fragment {

    private static final String STRING_ARRAY_ARG_PLAYER_NAMES = "player<>names!0_:'1927";

    private ArrayList<String> fetchList;
    private ArrayList<Player> players;
    private Game game;
    private TurnObject currentTurn;

    private TextView mGameLogView;

    private ArrayDeque<TurnObject> turnDeque;

    GameTypeSettings mGameSettings;
    private DialogInterface.OnClickListener onActionSelected
            = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mGameLogView.append(
                    String.format("Player %s has taken dice %d/%d\r\nPlayer selects action: %s",
                            currentTurn.getOwnerName(),
                            game.getTurn(),
                            game.getAvailableTurns(),
                            currentTurn.describeActions()[which]));
        }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param PlayerNames list of string names associated with player objects that need to be filled.
     * @return A new instance of fragment GamePlayerFragment.
     */
    public static GamePlayerFragment newInstance(ArrayList<String> PlayerNames) {
        GamePlayerFragment fragment = new GamePlayerFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(STRING_ARRAY_ARG_PLAYER_NAMES, PlayerNames);
        fragment.setArguments(args);
        return fragment;
    }

    public GamePlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fetchList = getArguments().getStringArrayList(STRING_ARRAY_ARG_PLAYER_NAMES);
        }
        setHasOptionsMenu(true);
        turnDeque = new ArrayDeque<>();

        TurnPool MainCup = new TurnPool();

        for(Player p : players)
        {
            MainCup.addPoolObjects(p.poolContent);
        }
        game = new Game(MainCup);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_game, menu);
        if(menu.findItem(R.id.action_add_player)!=null)
        {
            menu.removeItem(R.id.action_add_player);
        }
        if(menu.findItem(R.id.start_game)!=null)
        {
            menu.removeItem(R.id.start_game);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_take_turn:
                takeAturn();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View V =  inflater.inflate(R.layout.fragment_game_player, container, false);

        mGameLogView = (TextView)V.findViewById(R.id.GameFragLogView);

        if(game.getAvailableTurns() < 2)
        {
            mGameLogView.setText("Error: less than two turns. game is trivial. go kill yourself...\r\n");
        }

        return V;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setGameSettings(GameTypeSettings Settings) {
        mGameSettings = Settings;
    }

    public void setPlayers(ArrayList<Player> plist)
    {
        players = plist;
    }

    public void playGame()
    {
        TurnObject po = game.takeOneTurn();

        if(po == null)
        {
            if(mGameLogView!=null)
            {
                mGameLogView.setText("Pool is empty!\r\n");
            }
        }
        else
        {
            while(po!=null)
            {
                mGameLogView.append(String.format("Player %s has taken dice %d/%d\r\n",po.getOwnerName(),game.getTurn(),game.getAvailableTurns()));
                po = game.takeOneTurn();
            }
        }
    }

    private void takeAturn() {
        TurnObject po = game.takeOneTurn();
        turnDeque.push(po);

        if (po == null) {
            if (mGameLogView != null) {
                mGameLogView.setText("Game over!\r\n");
            }
        } else {

            currentTurn = po;
            showUnitDialog();

        }
    }

    private void showUnitDialog() {

        AlertDialog.Builder actionDialog = new AlertDialog.Builder(getActivity());
        actionDialog.setItems(currentTurn.describeActions(),onActionSelected);
        actionDialog.create().show();

    }
}
