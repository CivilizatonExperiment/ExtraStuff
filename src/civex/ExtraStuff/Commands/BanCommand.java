package civex.ExtraStuff.Commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.apache.commons.lang.time.DateUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import civex.ExtraStuff.ExtraStuffPlugin;

public class BanCommand implements CommandExecutor
{
    public final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public ExtraStuffPlugin plugin;

    public BanCommand(ExtraStuffPlugin plugin)
    {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender.hasPermission("civex.ban.normal"))
        {// ./ban player timeinhours reason

            if (!(args.length >= 1))
            {
                sender.sendMessage(ChatColor.RED + "You must enter the following");
                if (sender.hasPermission("civex.ban.perm"))
                {
                    sender.sendMessage(ChatColor.GRAY + "/ban <Player> [hours (Optional)] <Reason> ");
                    return true;
                }
                else
                {
                    sender.sendMessage(ChatColor.GRAY + "/ban <Player> [hours] <Reason> ");
                    return true;
                }
            }

            int hours;
            boolean offline = false;
            boolean perm = false;

            try
            {
                hours = Integer.parseInt(args[1]);
                if (hours > 504)
                {
                    if (!(sender.hasPermission("civex.ban.perm") || sender.hasPermission("civex.ban.long")))
                    {
                        sender.sendMessage(ChatColor.RED + "The amount of time is too long your max time is 504 hours (3 weeks).");
                        return true;
                    }
                }
            }
            catch (Exception ex)
            {
                if (sender.hasPermission("civex.ban.perm"))
                {
                    hours = Integer.MAX_VALUE;
                    perm = true;
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "Couldn't understand how many hours");
                    return true;
                }
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
                    offline = true;
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

            Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(target, reason, expire, sender.getName());

            if (hours != 0)
            {
                Bukkit.getLogger().log(Level.INFO, sender.getName() + " has banned " + target + " for " + hours + " hours.");
            }
            else
            {
                Bukkit.getLogger().log(Level.INFO, sender.getName() + " has perma banned " + target + ".");
            }

            sender.sendMessage(ChatColor.GREEN + "Player was successfully banned");
            //Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + sender.getName() + " has banned " + target + " for" + reason + ".");

            if (perm)
            {
                Bukkit.getServer().broadcastMessage(ChatColor.WHITE + target + ChatColor.LIGHT_PURPLE + " was permanently banned by " + ChatColor.WHITE + sender.getName());
                Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "Reason:" + ChatColor.WHITE + reason);
            }
            else
            {
                Bukkit.getServer().broadcastMessage(ChatColor.WHITE + target + ChatColor.LIGHT_PURPLE + " was temporarily banned by " + ChatColor.WHITE + sender.getName());
                Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "Expire: " + ChatColor.WHITE + dateFormat.format(expire) + " (" + plugin.getFormatedTime(hours) + ")");
                Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "Reason:" + ChatColor.WHITE + reason);
            }


            if (!offline)
            {
                if (hours == 1)
                {
                    Bukkit.getPlayer(target).kickPlayer("You've been banned by " + sender.getName() + " for " + hours + " hour.");
                }
                else
                {
                    Bukkit.getPlayer(target).kickPlayer("You've been banned by " + sender.getName() + " for " + hours + " hours.");
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}