package civex.ExtraStuff.Commands;

import civex.ExtraStuff.ExtraStuffPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Ryan on 12/22/2015.
 */
public class ModStickCommand  implements CommandExecutor
{
    ExtraStuffPlugin plugin;


    public ModStickCommand(ExtraStuffPlugin plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender.hasPermission("civex.modstick.normal") || sender.hasPermission("civex.modstick.admin"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage(ChatColor.RED + "You must be a player!");
                return true;
            }

            Player player = ((Player) sender).getPlayer();

            if (args.length > 0)
            {
                if (args[0].equalsIgnoreCase("on"))
                {
                    setModStickStatus(player.getUniqueId(), true);
                    sendPlayerStatus(player);
                    return true;
                }

                if (args[0].equalsIgnoreCase("off"))
                {
                    setModStickStatus(player.getUniqueId(), false);
                    sendPlayerStatus(player);
                    return true;
                }

                player.sendMessage(ChatColor.RED + "You must choose on or off with this command.");
            }
            else
            {
                toggleModStickStatus(player.getUniqueId());
                sendPlayerStatus(player);
                return true;
            }
        }

        return false;
    }

    public void setModStickStatus(UUID player, boolean status)
    {
        if (plugin.modStickStatus.containsKey(player))
        {
            if (status == false)
            {
                plugin.modStickStatus.remove(player);
            }
            else
            {
                plugin.modStickStatus.remove(player);
            }
        }

        if (status == true)
        {
            plugin.modStickStatus.put(player, true);
        }
    }

    public void toggleModStickStatus(UUID player)
    {
        if (plugin.modStickStatus.containsKey(player))
        {
            if (plugin.modStickStatus.get(player) == true)
            {
                plugin.modStickStatus.remove(player);
            }
        }
        else
        {
            plugin.modStickStatus.put(player, true);
        }
    }

    public void sendPlayerStatus(Player player)
    {
        if (plugin.modStickStatus.containsKey(player.getUniqueId()))
        {
            player.sendMessage(ChatColor.AQUA + "Mod stick status : " + ChatColor.GREEN + "ON.");
        }
        else
        {
            player.sendMessage(ChatColor.AQUA + "Mod stick status : " + ChatColor.RED + "OFF.");
        }
    }

}
