package andros.android.games.orderdicehelper.objects;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew on 8/8/2015.
 */
public class GameTypeSettings {
    private String GameTypeString;

    public GameTypeSettings()
    {
        GameTypeString = GAME_TYPE_BOLT_ACTION;
    }

    public String getGameType() { return GameTypeString;}
    public boolean setGameType(String type) {
        if(GAME_TYPES.containsKey(type))
        {
            GameTypeString = type;
            return true;
        }
        return false;
    }

    public static int TYPE_ID_NONE = 0;
    public static int BOLT_ACTION_TYPE_ID = 1;
    public static int BOLT_TACTICS_TYPE_ID = 2;
    public static String GAME_TYPE_BOLT_ACTION = "GAME TYPE BOLT ACTION";
    public static String GAME_TYPE_BOLT_TACTICS = "GAME TYPE BOLT ACTION TACTICS";

    private static HashMap<String,Integer> GAME_TYPES = new HashMap<>();

    static{
        GAME_TYPES.put(GAME_TYPE_BOLT_ACTION,BOLT_ACTION_TYPE_ID);
        GAME_TYPES.put(GAME_TYPE_BOLT_TACTICS,BOLT_TACTICS_TYPE_ID);
    }

    public static String lookupType(int ID)
    {
        for(String s : GAME_TYPES.keySet())
        {
            if(GAME_TYPES.get(s) == ID)
                return s;
        }
        return null;
    }

}
