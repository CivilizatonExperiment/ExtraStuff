package civex.ExtraStuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import civex.ExtraStuff.Commands.*;
import civex.ExtraStuff.Listeners.BedPlaceListener;
import civex.ExtraStuff.Listeners.ModStickListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import civex.ExtraStuff.Listeners.OverridesListener;
import civex.ExtraStuff.Listeners.TeleportListener;
import civex.ExtraStuff.Utils.AfkKick;

public class ExtraStuffPlugin extends JavaPlugin
{
    public HashMap<UUID, Boolean> modStickStatus = new HashMap<UUID, Boolean>();
    public static ExtraStuffPlugin plugin;
    private ArrayList<UUID> tpList = new ArrayList<UUID>();
    public TeleportListener tpListener;
    public OverridesListener ovListener;
    public ModStickListener msListener;
    public AfkKick afkKicker;
    public static Long modStickTimeout = (1L); //3 minutes

    public ModStickCommand modStickCommand;

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
                //getLogger().log(Level.INFO, "Running Afk Check");
                //afkKicker.tick();
            }
        }, 0L, (20L * 60L * 10L /* 10 minutes */));

        scheduler.scheduleSyncRepeatingTask(this, new Runnable()
        {
            @Override
            public void run()
            {
                getLogger().log(Level.INFO, "AUTO FEED PEARLS");
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ppfeed");
            }
        }, 0L, (20L * 60L * 60L * 24L /* 24 hour*/));
    }

    void regListeners()
    {
        tpListener = new TeleportListener(this);
        ovListener = new OverridesListener();
        msListener = new ModStickListener(this);
        getServer().getPluginManager().registerEvents(tpListener, this);
        getServer().getPluginManager().registerEvents(ovListener, this);
        getServer().getPluginManager().registerEvents(msListener, this);
        getServer().getPluginManager().registerEvents(new BedPlaceListener(), this);
    }

    void regCommands()
    {
        modStickCommand = new ModStickCommand(this);
        getServer().getPluginCommand("modstick").setExecutor(modStickCommand);
        getServer().getPluginCommand("ban").setExecutor(new BanCommand(this));
        getServer().getPluginCommand("tp").setExecutor(new TpCommand(this));
        getServer().getPluginCommand("spec").setExecutor(new SpectatorCommand());
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