package civex.ExtraStuff.Listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Ryan on 6/17/2016.
 */
public class AntiHolePunchingDumbListener implements Listener
{

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerPlaceBlock(BlockPlaceEvent event)
    {
        Block b = event.getBlock();
        if (b.getWorld().getName().equalsIgnoreCase("world_nether"))
        {
            if (b.getY() >= 127)
            {
                if (!event.getPlayer().isOp())
                {
                    event.setCancelled(true);
                }
            }
        }

        if (b.getWorld().getName().equalsIgnoreCase("world"))
        {
            if (b.getY() == 0)
            {
                if (!event.getPlayer().isOp())
                {
                    event.setCancelled(true);
                }
            }
        }
    }

}
