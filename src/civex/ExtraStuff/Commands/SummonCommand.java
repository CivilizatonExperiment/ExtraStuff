package civex.ExtraStuff.Commands;

import civex.ExtraStuff.ExtraStuffPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

/**
 * Created by Ryan on 10/5/2016.
 */
public class SummonCommand implements CommandExecutor
{
    private static ExtraStuffPlugin plugin;

    public SummonCommand(ExtraStuffPlugin plugin)
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "You must be a player!");
            return true;
        }

        Player player = Bukkit.getPlayer(sender.getName());

        if (!player.hasPermission("civex.tp"))
        {
            return false;
        }
        else
        {
            if (!(args.length == 1))
            {
                sendHelp(player);
                return true;
            }

            if (player.hasPermission("civex.tp.summon"))
            {
                Player target1;
                Player target2;

                target1 = player;

                if (Bukkit.getPlayer(args[0]) != null)
                {
                    target2 = Bukkit.getPlayer(args[1]);
                }
                else
                {
                    player.sendMessage(ChatColor.RED + "Target player was invalid.");
                    return true;
                }

                plugin.enableTP(target1);
                target1.teleport(target2);
                player.sendMessage(ChatColor.GREEN + target2.getName() + ChatColor.WHITE + " has been " + ChatColor.GREEN + "summoned.");
                Bukkit.getLogger().log(Level.INFO, player.getName() + " has summoned " + target2.getName() + ".");
                return true;

            }

            return false;

        }

    }


    void sendHelp(Player player)
    {
        player.sendMessage(ChatColor.RED + "/s <player> will someone to you.");
    }
}
