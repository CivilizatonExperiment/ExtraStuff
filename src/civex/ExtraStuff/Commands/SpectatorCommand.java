package civex.ExtraStuff.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import civex.ExtraStuff.ExtraStuffPlugin;

public class SpectatorCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (sender.hasPermission("civex.spec") || sender.isOp()) {

				if (player.getGameMode() == GameMode.SPECTATOR) {
					// If the player is in spectator mode, try and switch them
					// back to their previous mode.
					if (player.hasMetadata("prevMode")) {
						player.setGameMode((GameMode) player.getMetadata("prevMode").get(0).value());

					} else {
						player.setGameMode(GameMode.SURVIVAL);
					}
					
					//Move the player up one block so they don't suffocate.
					player.teleport(player.getLocation().add(0,1,0));
					
					MessagePlayer(player, "You are now in " + player.getGameMode().toString() + " mode.");

				} else {
					// If the player is in any gamemode besides spectator,
					// switch
					// them to it, and make sure to save their
					// previous gamemode.
					player.setMetadata("prevMode",
							new FixedMetadataValue(ExtraStuffPlugin.plugin, player.getGameMode()));
					player.setGameMode(GameMode.SPECTATOR);
					MessagePlayer(player,"You are now in spectator mode.");

				}

				return true;

			}} else {

				// Make sure the sender is a player, afterall, consoles can't
				// play.
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "" + ChatColor.GREEN + "Only players can toggle spectator mode.");
			}
		

		return false;
	}

	
	private void MessagePlayer(Player player, String message) {
		player.sendMessage(
				ChatColor.ITALIC + "" + ChatColor.GREEN + message);
	}
	
}
