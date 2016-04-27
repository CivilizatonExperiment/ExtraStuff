package civex.ExtraStuff.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by Ryan on 4/25/2016.
 */
public class BedPlaceListener implements Listener
{
    @EventHandler(priority = EventPriority.LOW)
    public void onBedPlace(BlockPlaceEvent event)
    {
        if (event.getBlock().getType() == Material.BED_BLOCK)
        {
            String world = event.getBlock().getWorld().getName();
            if (world.equalsIgnoreCase("world_the_end") || world.equalsIgnoreCase("world_nether"))
            {
                event.setCancelled(true);
                if (world.equalsIgnoreCase("world_the_end"))
                {
                    event.getPlayer().sendMessage(ChatColor.RED + "You're not allowed to place beds in the end.");
                }
                else
                {
                    event.getPlayer().sendMessage(ChatColor.RED + "You're not allowed to place beds in the nether.");
                }
            }
        }
    }
}
