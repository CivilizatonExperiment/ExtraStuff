package civex.ExtraStuff.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Ryan on 2/22/2017.
 */
public class BoarderBuffer implements Listener
{
    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event)
    {
        double X = event.getBlock().getX();
        double Z = event.getBlock().getZ();

        if (Math.abs(X) > 4000 || Math.abs(Z) > 4000)
        {
            event.getPlayer().sendMessage(ChatColor.RED + "You can not place blocks this close to the border.");
            event.setCancelled(true);
        }
    }
}
