package civex.ExtraStuff.Listeners;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OverridesListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event){
        if(!event.isCancelled()){
            Player player = event.getPlayer();
            String command[] = event.getMessage().split(" ");

            Bukkit.getLogger().log(Level.INFO, "CommandPreprocess fired");
            if(command[0].equalsIgnoreCase("/plugin")){
                event.setCancelled(true);
                sendPluginMessage(player);
            } else if(command[0].equalsIgnoreCase("/plugins")){
                event.setCancelled(true);
                sendPluginMessage(player);
            } else if(command[0].equalsIgnoreCase("/pl")){
                event.setCancelled(true);
                sendPluginMessage(player);
            }
        }
    }

    public void sendPluginMessage(Player player){
        player.sendMessage(ChatColor.RED + "Please look at our reddit wiki page for more information about our plugins.");
    }
}