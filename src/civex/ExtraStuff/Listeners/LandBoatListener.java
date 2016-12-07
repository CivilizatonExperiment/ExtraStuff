
package civex.ExtraStuff.Listeners;

import civex.ExtraStuff.ExtraStuffPlugin;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;


public class LandBoatListener implements Listener {

    private final Set<Material> allowMoveOnSet_;
    private final Set<UUID> inBoatSet_;
    private final ItemStack boatItemStack_;
    
    private final int SECONDS_PER_CHECK = 3;

    public LandBoatListener(ExtraStuffPlugin plugin) {

        allowMoveOnSet_ = new HashSet();
        allowMoveOnSet_.add(Material.WATER);
        allowMoveOnSet_.add(Material.STATIONARY_WATER);

        inBoatSet_ = new HashSet();

        boatItemStack_ = new ItemStack(Material.BOAT);

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                boatOnLandCheck();
            }
        }, SECONDS_PER_CHECK * 20L, SECONDS_PER_CHECK * 20L);
    }

    public void boatOnLandCheck() {
        for (UUID uuid : inBoatSet_) {
            Player player = Bukkit.getPlayer(uuid);
            if (!allowMoveOnSet_.contains(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType())
                    && !allowMoveOnSet_.contains(player.getLocation().getBlock().getType())) {
                player.getVehicle().remove();
                player.getWorld().dropItem(player.getLocation(), boatItemStack_);
            }
        }
    }

    @EventHandler
    void onVehicleEnterEvent(VehicleEnterEvent event) {
        if (event.getVehicle() instanceof Boat && event.getEntered() instanceof Player) {
            if (allowMoveOnSet_.contains(event.getVehicle().getLocation().getBlock().getRelative(BlockFace.DOWN).getType())
                    || allowMoveOnSet_.contains(event.getVehicle().getLocation().getBlock().getType())) {
                inBoatSet_.add(event.getEntered().getUniqueId());
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    void onVehicleExitEvent(VehicleExitEvent event) {
        if (event.getVehicle().getPassenger() != null) {
            inBoatSet_.remove(event.getVehicle().getPassenger().getUniqueId());
        }
    }
}
