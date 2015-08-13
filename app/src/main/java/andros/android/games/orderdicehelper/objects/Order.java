package andros.android.games.orderdicehelper.objects;

import java.security.Policy;

/**
 * Created by Andrew on 8/9/2015.
 */
public class Order {

    public String Name;

    public String[] Options;

    public Order(String name,String[] options)
    {
        Name = name;
        Options = options;
    }
}
