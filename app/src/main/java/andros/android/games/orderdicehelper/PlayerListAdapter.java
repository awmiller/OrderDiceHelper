package andros.android.games.orderdicehelper;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by amiller on 8/3/2015.
 */
public class PlayerListAdapter extends ArrayAdapter<Player> {
    private Context mContext;

    public PlayerListAdapter(ArrayList<Player> players,Context context) {
        super(context,0,players);
        mContext = context;
    }

    /**
     * {@inheritDoc}
     *
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Player p = getItem(position);

        if(convertView==null)
        {
            convertView = ((Activity)(mContext)).getLayoutInflater().inflate(R.layout.player_list_item,null);
        }

        ((TextView)convertView.findViewById(R.id.textViewPlayerName)).setText(p.name());
        ((TextView)convertView.findViewById(R.id.textViewDiceCount)).setText(String.valueOf(p.poolContent.size()));

        return convertView;
    }
}
