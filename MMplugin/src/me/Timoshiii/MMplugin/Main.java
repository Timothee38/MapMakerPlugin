package me.Timoshiii.MMplugin;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable(){
        getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable(){
	}



    public static ArrayList<String> players = new ArrayList<>();

    public static void addToHeadQueue(String playerName) {
        if (!(players.contains(playerName))) {
            players.add(playerName);
        }
    }

    public static void removeFromHeadQueue(String playerName) {
        players.remove(playerName);
    }

    public static Inventory MM(Player player) {
        Inventory mm = Bukkit.createInventory(null, 9, ChatColor.RED + "Map Maker Plugin!");

        ItemStack wand = new ItemStack(Material.WOOD_AXE, 1);
        ItemMeta meta = wand.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Kit");
        ArrayList<String> description = new ArrayList<>();
        description.add(ChatColor.DARK_RED + "Basic map maker kit.");
        meta.setLore(description);
        wand.setItemMeta(meta);
        mm.addItem(wand);

        ItemStack skull = new ItemStack(397, 1, (short) 3);
        ItemMeta skullmeta = skull.getItemMeta();
        skullmeta.setDisplayName(ChatColor.YELLOW + "Player Head");
        ArrayList<String> skulldescription = new ArrayList<>();
        skulldescription.add(ChatColor.DARK_RED + "Get a player head by typing their name in chat.");
        skullmeta.setLore(skulldescription);
        skull.setItemMeta(skullmeta);
        mm.addItem(skull);

        ItemStack clear = new ItemStack(Material.WOOL, 1, (short) 14);
        ItemMeta woolmeta = clear.getItemMeta();
        woolmeta.setDisplayName(ChatColor.YELLOW + "Clear Inventory");
        ArrayList<String> wooldescription = new ArrayList<>();
        wooldescription.add(ChatColor.DARK_RED + "Clear your inventoru.");
        woolmeta.setLore(wooldescription);
        clear.setItemMeta(woolmeta);
        mm.addItem(clear);

        return mm;
    }

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args){

        if (!(sender.isOp())) {
            sender.sendMessage(ChatColor.RED + "You are not OP!");
            return false;
        }

        if (commandlabel.equalsIgnoreCase("mm")) {
            ((Player)sender).openInventory(MM((Player)sender));
        }

		if(commandlabel.equalsIgnoreCase("mmh")){
			Player player = (Player) sender;
			if(args.length !=1){
				player.sendMessage("Incorrect syntax. Usage: /mmh [player username]");
			} else if(args.length == 1){
				ItemStack skull = new ItemStack(397, 1, (short) 3);
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwner(args[0]);
				skull.setItemMeta(meta);
				player.getInventory().addItem(skull);
				player.sendMessage(ChatColor.GREEN + "Given " + args[0] + "'s head.");
			}
		}

		 if(commandlabel.equalsIgnoreCase("mmk")){
			Player player = (Player) sender;
			//wand
			ItemStack wand = new ItemStack(Material.WOOD_AXE, 1);
			ItemMeta meta = wand.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Wand");
			ArrayList<String> description = new ArrayList<String>();
			description.add(ChatColor.DARK_RED + "Use me with world edit.");
			meta.setLore(description);
			wand.setItemMeta(meta);
			
			//Compass
			ItemStack item = new ItemStack(Material.COMPASS, 1);
			ItemMeta meta1 = item.getItemMeta();
			meta1.setDisplayName(ChatColor.YELLOW + "Compass");
			ArrayList<String> description1 = new ArrayList<String>();
			description1.add(ChatColor.DARK_RED + "Use me to teleport.");
			meta1.setLore(description1);
			item.setItemMeta(meta1);
			
			player.getInventory().addItem(wand, item);
			player.sendMessage(ChatColor.GREEN + "Given map maker's kit.");
		}

		if(commandlabel.equalsIgnoreCase("mmc")){
			Player player = (Player) sender;
			PlayerInventory pli= player.getInventory();
			pli.clear();
			player.sendMessage(ChatColor.GREEN + "Cleared inventory.");
		}

		return true;
	}

    @EventHandler
    public void invClick(InventoryClickEvent event) {
        if (event.getInventory().getName().contains(ChatColor.RED + "Map Maker Plugin!")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.SKULL_ITEM) {
                event.getWhoClicked().closeInventory();
                ((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "Type the name of the player's head you want!");
                addToHeadQueue(event.getWhoClicked().getName());
            } else if (event.getCurrentItem().getType() == Material.WOOD_AXE) {
                Bukkit.dispatchCommand((CommandSender)event.getWhoClicked(), "mmk");
                event.getWhoClicked().closeInventory();
            } else if (event.getCurrentItem().getType() == Material.WOOL) {
                event.getWhoClicked().getInventory().clear();
            } else {
                event.getWhoClicked().openInventory(event.getInventory());
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (players.contains(event.getPlayer().getName())) {
            event.setCancelled(true);
            removeFromHeadQueue(event.getPlayer().getName());
            ItemStack skull = new ItemStack(Material.SKULL_ITEM);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner(event.getMessage());
            meta.setDisplayName(ChatColor.WHITE + event.getMessage() + "'s Head");
            skull.setItemMeta(meta);
            skull.setDurability((short)3);
            event.getPlayer().getInventory().addItem(skull);
            event.getPlayer().sendMessage(ChatColor.GREEN + "Given " + meta.getOwner() + "'s head.");
        }
    }

}
