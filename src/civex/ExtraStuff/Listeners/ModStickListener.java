package civex.ExtraStuff.Listeners;

import civex.ExtraStuff.ExtraStuffPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by Ryan on 12/22/2015.
 */
public class ModStickListener implements Listener
{

    private ExtraStuffPlugin plugin;
    private HashMap<UUID, Long> timeoutMap = new HashMap<UUID, Long>();

    public ModStickListener(ExtraStuffPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();

        checkAllTimeout();

        if (player.hasPermission("civex.modstick.normal") && action == Action.RIGHT_CLICK_BLOCK && event.getMaterial() == Material.STICK)
        {
            if (plugin.modStickStatus.containsKey(player.getUniqueId()))
            {
                Block block = event.getClickedBlock();

                if (block.getType() == Material.BEDROCK && block.getY() <= 6)
                {
                    if (player.hasPermission("civex.modstick.admin"))
                    {
                        breakBlock(player, block);
                        player.sendMessage(ChatColor.RED + "You just " + ChatColor.GREEN + "bypassed" + ChatColor.RED +
                                " bedrock Y level safety please be careful to not make a hole");
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "You're not allowed to remove bedrock below Y level 6.");
                    }
                }
                else
                {
                    breakBlock(player, block);
                }
            }
            else
            {
                player.sendMessage(ChatColor.RED + "You must enable modstick to remove blocks.");
            }
        }
    }

    private void breakBlock(Player player, Block block)
    {
        logBreak(player, block, false);
        block.setType(Material.AIR);
        addToTimeoutMap(player.getUniqueId());
    }

    private void logBreak(Player player, Block block, Boolean cancled)
    {
        //TODO For now this is just going to the console but needs a better method.
        plugin.getLogger().log(Level.INFO, player.getDisplayName() + " Has used a mod stick at [x" + block.getX() + " y"
                + block.getY() + " z" + block.getZ() + "] on block type [" + block.getType().toString() +
                " with a durability  of " + block.getData() + "]");
    }

    private void addToTimeoutMap(UUID player)
    {
        if (!timeoutMap.containsKey(player))
        {
            timeoutMap.put(player, (System.currentTimeMillis() + plugin.modStickTimeout));
        }
        else
        {
            timeoutMap.remove(player);
            timeoutMap.put(player, (System.currentTimeMillis() + plugin.modStickTimeout));
        }
    }

    private void timeOutPlayer(UUID player)
    {
        if(plugin.getServer().getPlayer(player) != null){
            Player thing = plugin.getServer().getPlayer(player);
            thing.sendMessage(ChatColor.RED + "You've exited modstick mode due to " + ChatColor.WHITE + "timeout" +
                    ChatColor.RED + ". If you would like to use the modstick you must use the command.");
        }

        plugin.modStickCommand.setModStickStatus(player, false);
        timeoutMap.remove(player);
    }

    private void checkAllTimeout()
    {
        for (UUID p : timeoutMap.keySet())
        {
            if (timeoutMap.get(p) < System.currentTimeMillis())
            {
                timeOutPlayer(p);
            }
        }
    }

}
