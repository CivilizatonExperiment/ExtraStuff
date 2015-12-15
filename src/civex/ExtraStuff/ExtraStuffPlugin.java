package civex.ExtraStuff;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import civex.ExtraStuff.Listeners.OverridesListener;
import civex.ExtraStuff.Listeners.TeleportListener;
import civex.ExtraStuff.Utils.AfkKick;
import civex.ExtraStuff.Commands.BanCommand;
//import civex.ExtraStuff.Commands.SpectatorCommand;
import civex.ExtraStuff.Commands.TpCommand;
import civex.ExtraStuff.Commands.FakeBanCommand;

public class ExtraStuffPlugin extends JavaPlugin
{
    public static ExtraStuffPlugin plugin;
    private ArrayList<UUID> tpList = new ArrayList<UUID>();
    public TeleportListener tpListener;
    public OverridesListener ovListener;
    public AfkKick afkKicker;

    @Override
    public void onEnable()
    {
        regTools();
        regListeners();
        regCommands();

        plugin = this;
    }

    @Override
    public void onDisable()
    {

    }

    void regTools()
    {

        afkKicker = new AfkKick(this);

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable()
        {
            @Override
            public void run()
            {
                afkKicker.tick();
            }
        }, 0L, 12000L);

    }

    void regListeners()
    {
        tpListener = new TeleportListener(this);
        ovListener = new OverridesListener();
        getServer().getPluginManager().registerEvents(tpListener, this);
        getServer().getPluginManager().registerEvents(ovListener, this);
    }

    void regCommands()
    {
        getServer().getPluginCommand("ban").setExecutor(new BanCommand(this));
        getServer().getPluginCommand("tp").setExecutor(new TpCommand(this));
        //getServer().getPluginCommand("spec").setExecutor(new SpectatorCommand());
        getServer().getPluginCommand("fakeban").setExecutor(new FakeBanCommand(this));
    }

    public boolean canTeleport(Player player)
    {
        UUID uuid = player.getUniqueId();
        if (tpList.contains(uuid))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void enableTP(Player player)
    {
        UUID uuid = player.getUniqueId();
        if (!tpList.contains(uuid))
        {
            tpList.add(uuid);
        }
    }

    public void disableTP(Player player)
    {
        UUID uuid = player.getUniqueId();
        if (tpList.contains(uuid))
        {
            tpList.remove(uuid);
        }
    }

    public String getFormatedTime(int hours)
    {
        String output = "";
        int month = 0;
        int week = 0;
        int day = 0;
        boolean notFirst = false;

        int remain = hours;

        if (remain >= 720)
        {
            month = remain / 720;
            remain = remain % 720;
        }

        if (remain >= 168)
        {
            week = remain / 168;
            remain = remain % 168;
        }

        if (remain >= 24)
        {
            day = remain / 24;
            remain = remain % 24;
        }

        if (month > 0)
        {
            output += month + "m";
            notFirst = true;
        }

        if (week > 0)
        {
            if (notFirst)
            {
                output += " ";
            }
            output += week + "w";
            notFirst = true;
        }

        if (day > 0)
        {
            if (notFirst)
            {
                output += " ";
            }
            output += day + "d";
            notFirst = true;
        }

        if (remain > 0)
        {
            if (notFirst)
            {
                output += " ";
            }
            output += remain + "h";
        }

        return output;
    }

}