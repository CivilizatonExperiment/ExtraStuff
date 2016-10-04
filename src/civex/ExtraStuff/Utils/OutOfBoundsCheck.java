package civex.ExtraStuff.Utils;

import civex.ExtraStuff.ExtraStuffPlugin;
import org.bukkit.entity.Player;

/**
 * Created by Ryan on 10/4/2016.
 */
public class OutOfBoundsCheck
{
    public boolean isOutOfBounds(Player player)
    {
        boolean outOfBounds = false;

        Loc playerLoc = new Loc(player);

        if (playerLoc.getWorld().equalsIgnoreCase("world_nether"))
        {
            if (playerLoc.getY() > 127)
            {
                outOfBounds = true;
            }
        }

        return outOfBounds;
    }
}
