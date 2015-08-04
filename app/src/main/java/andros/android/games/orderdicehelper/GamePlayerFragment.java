package andros.android.games.orderdicehelper;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import andros.android.games.orderdicehelper.objects.Game;
import andros.android.games.orderdicehelper.objects.Player;
import andros.android.games.orderdicehelper.objects.TurnObject;
import andros.android.games.orderdicehelper.objects.TurnPool;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GamePlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GamePlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamePlayerFragment extends Fragment {

    private static final String STRING_ARRAY_ARG_PLAYER_NAMES = "player<>names!0_:'1927";

    private ArrayList<String> fetchList;
    private ArrayList<Player> players;
    private Game game;

    private OnFragmentInteractionListener mListener;
    private TextView mGameLogView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V =  inflater.inflate(R.layout.fragment_game_player, container, false);

        mGameLogView = (TextView)V.findViewById(R.id.GameFragLogView);

        playGame();

        return V;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        players = mListener.onPlayersFetchRequest(fetchList);

        TurnPool MainCup = new TurnPool();

        for(Player p : players)
        {
            MainCup.addPoolObjects(p.poolContent);
        }
        game = new Game(MainCup);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public ArrayList<Player> onPlayersFetchRequest(ArrayList<String> names);
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
}
