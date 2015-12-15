package civex.ExtraStuff.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Loc
{
    private double X;
    private double Y;
    private double Z;
    private String world;

    public Loc(double X, double Y, double Z, String world)
    {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.world = world;
    }

    public Loc(Player player)
    {
        this.X = player.getLocation().getX();
        this.Y = player.getLocation().getY();
        this.Z = player.getLocation().getZ();
        this.world = player.getLocation().getWorld().getName();
    }

    public Loc(Location location)
    {
        this.X = location.getX();
        this.Y = location.getY();
        this.Z = location.getZ();
        this.world = location.getWorld().getName();
    }

    public double getX()
    {
        return X;
    }

    public double getY()
    {
        return Y;
    }

    public double getZ()
    {
        return Z;
    }

    public String getWorld()
    {
        return world;
    }

    public String toString()
    {
        return X + " " + Y + " " + Z + " " + world;
    }
}