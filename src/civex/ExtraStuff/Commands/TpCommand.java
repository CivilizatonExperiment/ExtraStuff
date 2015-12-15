package civex.ExtraStuff.Commands;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import civex.ExtraStuff.ExtraStuffPlugin;

public class TpCommand implements CommandExecutor
{
    private static ExtraStuffPlugin plugin;

    public TpCommand(ExtraStuffPlugin plugin)
    {
        TpCommand.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
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
            if (!(args.length >= 1 && args.length <= 4))
            {
                sendHelp(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("help"))
            {
                sendHelp(player);
            }

            if (args.length == 3 || args.length == 4)
            {
                if (player.hasPermission("civex.tp.cords"))
                {
                    double x, y, z;
                    try
                    {
                        x = Double.parseDouble(args[0]);
                        y = Double.parseDouble(args[1]);
                        z = Double.parseDouble(args[2]);
                    }
                    catch (Exception ex)
                    {
                        try
                        {
                            x = Double.parseDouble(args[1]);
                            y = Double.parseDouble(args[2]);
                            z = Double.parseDouble(args[3]);
                        }
                        catch (Exception exc)
                        {
                            player.sendMessage(ChatColor.RED + "Couldn't parse cords.");
                            return true;
                        }
                    }

                    Location temp = new Location(player.getWorld(), x, y, z);
                    plugin.enableTP(player);
                    player.teleport(temp);
                    player.sendMessage(ChatColor.GREEN + "You've been teleported.");
                    Bukkit.getLogger().log(Level.INFO, player.getName() + " has teleported to X:" + x + " Y:" + y + " Z:" + z + ".");
                    return true;
                }
                else
                {
                    sendPermisionError(player, "to cords");
                    return true;
                }
            }
            else if (args.length == 2)
            {
                if (player.hasPermission("civex.tp.summon"))
                {
                    Player target1;
                    Player target2;

                    if (Bukkit.getPlayer(args[0]) != null)
                    {
                        target1 = Bukkit.getPlayer(args[0]);
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "First player was invalid.");
                        return true;
                    }

                    if (Bukkit.getPlayer(args[1]) != null)
                    {
                        target2 = Bukkit.getPlayer(args[1]);
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "Second player was invalid.");
                        return true;
                    }

                    plugin.enableTP(target1);
                    target1.teleport(target2);
                    player.sendMessage(ChatColor.GREEN + target1.getName() + " was teleported to " + target2.getName());
                    Bukkit.getLogger().log(Level.INFO, player.getName() + " has teleported " + target1.getName() + " to "
                            + target2.getName() + " at X:" + target1.getLocation().getX() + " Y:" + target1.getLocation().getY()
                            + " Z: " + target1.getLocation().getZ() + ".");
                    return true;
                }
                else
                {
                    sendPermisionError(player, "players to other players");
                    return true;
                }
            }
            else if (args.length == 1)
            {
                if (player.hasPermission("civex.tp"))
                {
                    Player target1;

                    if (Bukkit.getPlayer(args[0]) != null)
                    {
                        target1 = Bukkit.getPlayer(args[0]);
                    }
                    else
                    {
                        player.sendMessage(ChatColor.RED + "The player was invalid");
                        return true;
                    }

                    plugin.enableTP(player);
                    player.teleport(target1.getLocation());
                    player.sendMessage(ChatColor.GREEN + "You've teleported to " + target1.getName() + ".");
                    Bukkit.getLogger().log(Level.INFO, player.getName() + " has teleported to " + target1.getName() + " at X:"
                            + target1.getLocation().getX() + " Y:" + target1.getLocation().getY() + " Z: "
                            + target1.getLocation().getZ() + ".");
                    return true;
                }
                else
                {
                    sendPermisionError(player, "to other players");
                    return false;
                }
            }
            else
            {
                sendHelp(player);
                return false;
            }
        }
    }

    public void sendHelp(Player player)
    {
        player.sendMessage(ChatColor.GREEN + "You can use the commands below to teleport around.");
        if (player.hasPermission("civex.tp"))
        {
            player.sendMessage(ChatColor.GRAY + "/tp <player>");
        }
        if (player.hasPermission("civex.tp.cords"))
        {
            player.sendMessage(ChatColor.GRAY + "/tp <x> <y> <z>");
        }
        if (player.hasPermission("civex.tp.summon"))
        {
            player.sendMessage(ChatColor.GRAY + "/tp <player1> <player2>");
        }
    }

    public void sendPermisionError(Player player, String errorType)
    {
        player.sendMessage(ChatColor.RED + "You do not have permision to teleport " + errorType + ".");
        if (player.hasPermission("civex.tp"))
        {
            player.sendMessage(ChatColor.RED + "You can teleport to players via /tp <player>");
        }
        if (player.hasPermission("civex.tp.cords"))
        {
            player.sendMessage(ChatColor.RED + "You can teleport to cords via /tp <x> <y> <z>");
        }
        if (player.hasPermission("civex.tp.summon"))
        {
            player.sendMessage(ChatColor.RED + "You can also teleport players to other players via /tp <player1> <player2>");
        }
    }
}