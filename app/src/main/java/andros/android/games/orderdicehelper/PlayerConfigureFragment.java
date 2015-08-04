package andros.android.games.orderdicehelper;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import andros.android.games.orderdicehelper.objects.Player;
import andros.android.games.orderdicehelper.objects.TurnObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerConfigureFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerConfigureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerConfigureFragment extends Fragment implements View.OnClickListener {

    private static final String STRING_NAME_ARGUEMENT = "Player!!(NAme_+=12345";
    private OnFragmentInteractionListener mListener;
    private TextView mOwnerTextView;
    private Button mAddDiceButton;
    private Button SubmitPlayerButton;
    Player player;
    private TextView mDiceCountView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayerConfigureFragment.
     */
    public static PlayerConfigureFragment newInstance() {
        PlayerConfigureFragment fragment = new PlayerConfigureFragment();
        return fragment;
    }

    /**
     * Overloaded newInstance() supports referencing an existing playername indirectly
     *
     * @param playerName string value used to bind this fragment to the existing player object
     * @return A new fragment
     */
    public static PlayerConfigureFragment newInstance(String playerName) {
        PlayerConfigureFragment fragment = new PlayerConfigureFragment();
        Bundle bund = new Bundle();
        bund.putString(STRING_NAME_ARGUEMENT,playerName);
        fragment.setArguments(bund);
        return fragment;
    }

    public PlayerConfigureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_configure_player,container,false);

        mOwnerTextView = (TextView)view.findViewById(R.id.editDieOwnerText);

        mAddDiceButton = (Button) view.findViewById(R.id.addDieButton);
        mAddDiceButton.setOnClickListener(this);

        SubmitPlayerButton = (Button) view.findViewById(R.id.submitPlayerButton);
        SubmitPlayerButton.setOnClickListener(this);

        mDiceCountView = (TextView) view.findViewById(R.id.diceCountTextView);

        return view;
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
        if (getArguments() != null) {
            String name = getArguments().getString(STRING_NAME_ARGUEMENT);
            player = mListener.getPlayerOrNew(name);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        mOwnerTextView.clearFocus();

        switch(v.getId())
        {
            case R.id.submitPlayerButton:
                if(mListener!=null)
                {
                    if (player==null) {
                        String name = mOwnerTextView.getText().toString();
                        player = mListener.getPlayerOrNew(name);
                    }

                    mListener.onPlayerSubmitted(player);
                }
                break;
            case R.id.addDieButton:
                addDiceCallback();
                break;
        }
    }

    private void addDiceCallback() {
        if ((mOwnerTextView != null)&&(mListener!=null)) {

            if (player==null) {
                String name = mOwnerTextView.getText().toString();
                player = mListener.getPlayerOrNew(name);
            }

            player.addToPool(new TurnObject(player));
        }
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
        public void onPlayerSubmitted(Player player);
        public Player getPlayerOrNew(String match);
        public void passClick(View v);
    }

}
