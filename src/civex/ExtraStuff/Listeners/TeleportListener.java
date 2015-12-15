package civex.ExtraStuff.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import civex.ExtraStuff.ExtraStuffPlugin;

public class TeleportListener implements Listener
{
    ExtraStuffPlugin plugin;

    public TeleportListener(ExtraStuffPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerTeleportEvent(PlayerTeleportEvent event)
    {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.SPECTATOR)
        {
            if (plugin.canTeleport(player))
            {
                plugin.disableTP(player);
            }
            else
            {
                player.sendMessage(ChatColor.RED + "You're not allowed to TP via the in game method please use /tp");
                event.setCancelled(true);
            }
        }
    }
}