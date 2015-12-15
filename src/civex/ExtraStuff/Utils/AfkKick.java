package civex.ExtraStuff.Utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import civex.ExtraStuff.ExtraStuffPlugin;

public class AfkKick
{
    private ExtraStuffPlugin plugin;
    private HashMap<UUID, Loc> lastLoc = new HashMap<UUID, Loc>();

    public AfkKick(ExtraStuffPlugin plugin)
    {
        this.plugin = plugin;
    }

    public void tick()
    {
        HashMap<UUID, Loc> newLoc = new HashMap<UUID, Loc>();

        for (Player player : plugin.getServer().getOnlinePlayers())
        {
            if (getLastLoc(player.getUniqueId()) != null)
            {
                if (shouldKick(player))
                {
                    if (!canPlayerBypassAFKkick(player))
                    {
                        player.kickPlayer("You've not moved for too long so you've been kicked.");
                    }
                    else
                    {
                        player.sendMessage(ChatColor.GREEN + "You've just bypassed AFK kick please do not be AFK.");
                    }
                }
                else
                {
                    newLoc.put(player.getUniqueId(), new Loc(player));
                }
            }
            else
            {
                newLoc.put(player.getUniqueId(), new Loc(player));
            }
        }

        lastLoc.clear();
        lastLoc = newLoc;
        newLoc = null;
    }

    public Boolean shouldKick(Player player)
    {
        if (new Loc(player).toString().equals(getLastLoc(player.getUniqueId())))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private Boolean canPlayerBypassAFKkick(Player player)
    {
        if (player.getGameMode() != GameMode.SURVIVAL)
        {
            return true;
        }

        return false;
    }

    private Loc getLastLoc(UUID player)
    {
        if (lastLoc.containsKey(player))
        {
            return lastLoc.get(player);
        }
        else
        {
            return null;
        }
    }
}