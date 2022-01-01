package ayano.MMOExpansion.cmd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import ayano.MMOExpansion.MMOExpansion;
import ayano.MMOExpansion.utils.utils;
import io.lumine.mythic.lib.api.crafting.recipes.MythicCraftingManager;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipe;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeBlueprint;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeStation;

public class mex implements TabExecutor {
	
	MMOExpansion plugin;
	
	public mex(MMOExpansion pl) {
		this.plugin = pl;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			if(args[0].equalsIgnoreCase("mana"))
				{
		        	if(sender instanceof Player)
		        	{
		        		Player p = (Player) sender;
		        		if(p.hasPermission("mmoextension.consume") || p.isOp() || p.hasPermission("mmoextension.*") || p.hasPermission("*"))
		        		{} else {
		        			p.sendMessage(utils.errorperm("mmoextension.consume"));
		        			return true;}} else {}
			        try {
			    		Player jugador = Bukkit.getServer().getPlayer(args[1]);
			    		if(args[2].contains("+")) {
			    			Double consume = net.Indyuce.mmocore.api.player.PlayerData.get(jugador).getMana() + Double.parseDouble(args[2].replace("+", ""));
			        		net.Indyuce.mmocore.api.player.PlayerData.get(jugador).setMana(consume);
			    		} else if(args[2].contains("-")) {
				    			Double consume = net.Indyuce.mmocore.api.player.PlayerData.get(jugador).getMana() - Double.parseDouble(args[2].replace("-", ""));
				        		net.Indyuce.mmocore.api.player.PlayerData.get(jugador).setMana(consume);
				    		} else {
				        		net.Indyuce.mmocore.api.player.PlayerData.get(jugador).setMana(Double.parseDouble(args[2]));
				    		}
			        	if(sender instanceof Player)
			        	{
			        		Player p = (Player) sender;
			        		if(args[2].contains("+")) {
			        			String temp = args[2].replace("+", "");
				        		p.sendMessage(utils.messageext(" &7se agrego " + temp + " de mana al jugador " + args[1]));} 
			        		else if(args[2].contains("-")) {
			        			String temp = args[2].replace("-", "");
			        			p.sendMessage(utils.messageext(" &7se consumio " + temp + " de mana del jugador " + args[1]));
			        		} else {
			        			p.sendMessage(utils.messageext(" &7se establecio " + args[2] + " el mana del jugador " + args[1]));
			        		}
			        	} else {
			        		if(args[2].contains("+")) {
			        			String temp = args[2].replace("+", "");
			        			Bukkit.getConsoleSender().sendMessage(utils.messageext(" &7se agrego " + temp + " de mana al jugador " + args[1]));} 
			        		else if(args[2].contains("-")) {
			        			String temp = args[2].replace("-", "");
			        			Bukkit.getConsoleSender().sendMessage(utils.messageext(" &7se consumio " + temp + " de mana del jugador " + args[1]));
			        		} else {
			        			Bukkit.getConsoleSender().sendMessage(utils.messageext(" &7se establecio " + args[2] + " el mana del jugador " + args[1]));
			        		}
			        	}
			        	return true;
			        } catch (Exception e) {
			        	if(sender instanceof Player)
			        	{
			        		Player p = (Player) sender;
			        		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[&6MMO&eExpansion&c] &7no se pudo consumir o agregar " + args[2] + " de mana del jugador " + args[1]));
			        	} else {
			        		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[&6MMO&eExpansion&c] &7no se pudo consumir o agregar" + args[2] + " de mana del jugador " + args[1]));
			        	}
			        	return false;
			        }
				}
				else if(args[0].equalsIgnoreCase("stamina")) {

		        	if(sender instanceof Player)
		        	{
		        		Player p = (Player) sender;
		        		if(p.hasPermission("mmoextension.consume") || p.isOp() || p.hasPermission("mmoextension.*") || p.hasPermission("*"))
		        		{} else {
		        			p.sendMessage(utils.errorperm("mmoextension.consume"));
		        			return true;}} else {}
			        try {
			    		Player jugador = Bukkit.getServer().getPlayer(args[1]);
			    		if(args[2].contains("+")) {
			    			Double consume = net.Indyuce.mmocore.api.player.PlayerData.get(jugador).getStamina() + Double.parseDouble(args[2].replace("+", ""));
			        		net.Indyuce.mmocore.api.player.PlayerData.get(jugador).setStamina(consume);
			    		} else if(args[2].contains("-")) {
				    			Double consume = net.Indyuce.mmocore.api.player.PlayerData.get(jugador).getStamina() - Double.parseDouble(args[2].replace("-", ""));
				        		net.Indyuce.mmocore.api.player.PlayerData.get(jugador).setStamina(consume);
				    		} else {
				        		net.Indyuce.mmocore.api.player.PlayerData.get(jugador).setStamina(Double.parseDouble(args[2]));
				    		}
			        	if(sender instanceof Player)
			        	{
			        		Player p = (Player) sender;
			        		if(args[2].contains("+")) {
			        			String temp = args[2].replace("+", "");
				        		p.sendMessage(utils.messageext(" &7se agrego " + temp + " de stamina al jugador " + args[1]));} 
			        		else if(args[2].contains("-")) {
			        			String temp = args[2].replace("-", "");
			        			p.sendMessage(utils.messageext(" &7se consumio " + temp + " de stamina del jugador " + args[1]));
			        		} else {
			        			p.sendMessage(utils.messageext(" &7se establecio " + args[2] + " la stamina del jugador " + args[1]));
			        		}
			        	} else {
			        		if(args[2].contains("+")) {
			        			String temp = args[2].replace("+", "");
			        			Bukkit.getConsoleSender().sendMessage(utils.messageext(" &7se agrego " + temp + " de stamina al jugador " + args[1]));} 
			        		else if(args[2].contains("-")) {
			        			String temp = args[2].replace("-", "");
			        			Bukkit.getConsoleSender().sendMessage(utils.messageext(" &7se consumio " + temp + " de stamina del jugador " + args[1]));
			        		} else {
			        			Bukkit.getConsoleSender().sendMessage(utils.messageext(" &7se establecio " + args[2] + " al stamina del jugador " + args[1]));
			        		}}
			        	return true;
			        } catch (Exception e) {
			        	if(sender instanceof Player)
			        	{
			        		Player p = (Player) sender;
			        		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[&6MMO&eExpansion&c] &7no se pudo consumir o agregar" + args[2] + " de stamina del jugador " + args[1]));
			        	} else {
			        		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[&6MMO&eExpansion&c] &7no se pudo consumir o agregar " + args[2] + " de stamina del jugador " + args[1]));
			        	}
			        	return false;
			        }
				} else if (args[0].equalsIgnoreCase("reload")) {

	        		Player p = (Player) sender;
	        		if(p.hasPermission("mmoextension.reload") || p.isOp() || p.hasPermission("mmoextension.*") || p.hasPermission("*"))
	        		{
	        			utils.savecfg();
	        			Boolean run = utils.reload();
	        			if(run = true) {
	        				utils.loadexpansion();
	        				MMOExpansion.getPlugin().onLoad();
	        				
	        				p.sendMessage(utils.messageext("&cConfiguracion Recargada"));
	        			} else {
	        				p.sendMessage(utils.messageext("&cOcurrio un error"));
	        			}
	        			return run;
	        			
	        		} else {
	        			p.sendMessage(utils.errorperm("mmoextension.reload"));
	        			return true;}
				}
					else if (args[0].equalsIgnoreCase("mrecipe")) {

		        	if(sender instanceof Player)
		        	{
		        		Player p = (Player) sender;
		        		if(p.hasPermission("mmoextension.mrecipe") || p.isOp() || p.hasPermission("mmoextension.*") || p.hasPermission("*"))
		        		{
		        			MythicRecipe MRecipe = utils.getRecipeMythic(args[1]);
			        		HashMap<Integer, ItemStack> Recipe = utils.getRecipe(args[1]);
			        		utils.generatePreview(Recipe, p, MRecipe);
		        			
		        		} else {
		        			p.sendMessage(utils.errorperm("mmoextension.mrecipe"));
		        			return true;}
		        		
		        		//write there
			        	
		        	} else {
		        		ConsoleCommandSender cs = Bukkit.getConsoleSender();
		        		try {
		        		cs.sendMessage(utils.getRecipe(args[1]).toString());
		        		HashMap<Integer, ItemStack> Recipe = utils.getRecipe(args[1]);
		        		} catch (Exception e) {cs.sendMessage(e.toString());}
		        	}
					return true;
					}  else if (args[0].equalsIgnoreCase("model")) {

			        	if(sender instanceof Player)
			        	{
			        		Player p = (Player) sender;
			        		if(p.hasPermission("mmoextension.model") || p.isOp() || p.hasPermission("mmoextension.*") || p.hasPermission("*"))
			        		{
			        			p.sendMessage(utils.messageext(" &7El modelo del item en mano es :&f" + p.getItemInHand().getItemMeta().getCustomModelData()));
			        			
			        		} else {
			        			p.sendMessage(utils.errorperm("mmoextension.model"));
			        			return true;}
			        		
			        		//write there
				        	
			        	} else {}
						return true;
						}
				else {
		        	if(sender instanceof Player)
		        	{
		        		Player p = (Player) sender;
		        		messagehelpp(p);
		        	} else {
		        		utils.messagehelpc(Bukkit.getConsoleSender());
		        	}
		        	return false;
				}
		} catch (Exception e) {
        	if(sender instanceof Player)
        	{
        		Player p = (Player) sender;
        		messagehelpp(p);
        	} else {
        		utils.messagehelpc(Bukkit.getConsoleSender());
        	}
        	return true;
		}
    }
	
	 public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		 if(args.length == 1) {
			 List<String> list = new ArrayList<>();
			 list.add("mana");
			 list.add("mrecipe");
			 list.add("model");
			 list.add("stamina");
			 list.add("reload");

			 Collections.sort(list);
			 
			 return list;
		 }
		if(args[0].equalsIgnoreCase("mrecipe") == false)
		{
			 if(args.length == 2) {
				 List<String> list = new ArrayList<>();
				 for(Player all : Bukkit.getServer().getOnlinePlayers())
				 {
					 list.add(all.getName());
				 }
				 
				 if(list != null)
					 Collections.sort(list);
				 return list;
			 }
			 if(args.length == 3){
				 List<String> list = new ArrayList<>();
				 for(int val = 0; val < 100; val++)
				 {
					 list.add(val + "");
				 }
				 
				 return list;
			 }
		}
		if (args[0].equalsIgnoreCase("mrecipe"))
				{
					List<String> list = new ArrayList<>();
					List<MythicRecipeBlueprint> recipes = MythicCraftingManager.getStationRecipes(MythicRecipeStation.WORKBENCH, "");
					@NotNull List<MythicRecipe> finalrecipe = new ArrayList<MythicRecipe>();
					for(int i = 0; i < recipes.size(); i++)
					{
						list.add(recipes.get(i).getNk().getKey());
					}
					return list;
				}
		
		 
		 return MMOExpansion.listes;
	 }
	

		public boolean messagehelpp(Player cs) {
			try {
				List<String> helplist = plugin.getConfig().getStringList("config.message-help");
				
				for(int i = 0; i < helplist.size(); i++){
					cs.sendMessage(helplist.get(i));
				}
			
			return true;}
			catch (Exception e) {return false;}
		}
	 
}
