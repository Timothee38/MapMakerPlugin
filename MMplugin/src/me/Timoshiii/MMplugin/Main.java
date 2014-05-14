package me.Timoshiii.MMplugin;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	@Override
	public void onEnable(){
		getLogger().info("Map Makers Plugin now enabled.");
	}
	@Override
	public void onDisable(){
		getLogger().info("Map Makers Plugin now disabled.");
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender,Command cmd, String commandlabel, String[] args){
		if(commandlabel.equalsIgnoreCase("mmh")){
			Player player = (Player) sender;
			if(args.length == 0){
				player.sendMessage("Uncompleted syntaxe. usage: /mmh [player username]");
			}
			else if(args.length == 1){
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
				ItemStack skull = new ItemStack(397, 1, (short) 3);
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwner(target);
				skull.setItemMeta(meta);
				player.getInventory().addItem(skull);
			}
		}
		return true;
	}
}
