package civex.ExtraStuff.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(sender instanceof Player){
            sender.sendMessage(ChatColor.RED + "Please look at our plugins page on the wiki.");
            return true;
        } else {
            return true;
        }
    }
}