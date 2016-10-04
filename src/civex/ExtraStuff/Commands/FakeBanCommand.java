package civex.ExtraStuff.Commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import civex.ExtraStuff.ExtraStuffPlugin;

public class FakeBanCommand implements CommandExecutor
{
    public final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ExtraStuffPlugin plugin;

    public FakeBanCommand(ExtraStuffPlugin plugin)
    {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender.hasPermission("civex.ban.fake"))
        {// ./ban player timeinhours reason
            if (!(args.length >= 1))
            {
                sender.sendMessage(ChatColor.RED + "You must enter the following");
                if (sender.hasPermission("civex.ban.fake"))
                {
                    sender.sendMessage(ChatColor.GRAY + "/fakeban <Player> [hours (Optional)] <Reason> ");
                    return true;
                }
                else
                {
                    sender.sendMessage(ChatColor.GRAY + "/fakeban <Player> [hours] <Reason> ");
                    return true;
                }
            }

            int hours;
            boolean perm = false;

            try
            {
                hours = Integer.parseInt(args[1]);
            }
            catch (Exception ex)
            {
                hours = Integer.MAX_VALUE;
                perm = true;
            }

            String target = "";
            if (Bukkit.matchPlayer(args[0]).size() == 1)
            {
                target = Bukkit.matchPlayer(args[0]).get(0).getName();
            }
            else
            {
                if (Bukkit.getOfflinePlayer(args[0]) != null)
                {
                    target = Bukkit.getOfflinePlayer(args[0]).getName();
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "You need to enter the correct name");
                    return true;
                }
            }
            String reason = "";
            int i = 0;
            for (String s : args)
            {
                i++;
                if (i >= 2)
                {
                    try
                    {
                        Integer.parseInt(s);
                        continue;
                    }
                    catch (Exception ex)
                    {

                    }

                    reason += " " + s;
                }
            }

            if (reason == "")
            {
                sender.sendMessage(ChatColor.RED + "You must have a reason to ban someone.");
                return true;
            }

            Date expire;

            if (hours == Integer.MAX_VALUE)
            {
                expire = DateUtils.addYears(new Date(), 9999);
            }
            else
            {
                expire = DateUtils.addHours(new Date(), hours);
            }

            if (hours != 0)
            {
                Bukkit.getLogger().log(Level.INFO, sender.getName() + " has fake banned " + target + " for " + hours + " hours.");
            }
            else
            {
                Bukkit.getLogger().log(Level.INFO, sender.getName() + " has fake perma banned " + target + ".");
            }

            if (perm)
            {
                Bukkit.getServer().broadcastMessage(ChatColor.WHITE + target + ChatColor.LIGHT_PURPLE + " was permanently banned by " + ChatColor.WHITE + sender.getName());
                Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "Reason:" + ChatColor.WHITE + reason);
            }
            else
            {
                Bukkit.getServer().broadcastMessage(ChatColor.WHITE + target + ChatColor.LIGHT_PURPLE + " was temporarily banned by " + ChatColor.WHITE + sender.getName());
                Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "Expire: " + ChatColor.WHITE + dateFormat.format(expire) + " (" + plugin.stringUtils.getFormatedTime(hours) + ")");
                Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "Reason:" + ChatColor.WHITE + reason);
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}