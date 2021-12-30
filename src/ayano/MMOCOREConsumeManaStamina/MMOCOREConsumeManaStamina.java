package ayano.MMOCOREConsumeManaStamina;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.command.ColouredConsoleSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import ayano.MMOCOREConsumeManaStamina.utils.JsonUtils;
import ayano.MMOCOREConsumeManaStamina.utils.eventlistener;
import ayano.MMOCOREConsumeManaStamina.utils.utils;
import ayano.MMOCOREConsumeManaStamina.utils.chests.ChestManager;
import ayano.MMOCOREConsumeManaStamina.utils.chests.mireplacer;
import io.lumine.mythic.lib.api.crafting.recipes.MythicCraftingManager;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeStation;
import io.lumine.mythic.lib.api.crafting.uimanager.ProvidedUIFilter;
import io.lumine.mythic.lib.api.util.Ref;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.api.util.message.FFPMMOItems;
import net.Indyuce.mmoitems.gui.edition.recipe.RecipeBrowserGUI;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RecipeRegistry;
import net.Indyuce.mmoitems.manager.ItemManager;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.recipe.workbench.CustomRecipe;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.AirIngredient;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.MMOItemIngredient;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.VanillaIngredient;
import net.Indyuce.mmoitems.api.recipe.workbench.ingredients.WorkbenchIngredient;
import io.lumine.mythic.lib.MythicLib;
@SuppressWarnings("unused")
public class MMOCOREConsumeManaStamina extends JavaPlugin implements TabExecutor {
	PluginDescriptionFile pdffile = getDescription();
	public String rutaconf;
	public String version = pdffile.getVersion();
	public String name = ChatColor.AQUA + "["+pdffile.getName()+"]";
	public FileConfiguration config = this.getConfig();
	public List<String> listes = new ArrayList<>();
	  private final JsonUtils json = new JsonUtils();
	  public ChestManager chestManager;
	  public JsonUtils getJson() {
	    return this.json;
	  }
	private static MMOCOREConsumeManaStamina plugin;
	public static MMOCOREConsumeManaStamina getPlugin() {
		  return plugin;
		}
	private final globalconfig globals = new globalconfig();
	public globalconfig getGlobals() {
	  return this.globals;
	}
	public static boolean isValid(ItemStack stack) {return true;}
	  public static ItemStack replace(RPGPlayer player, ItemStack stack) {
		ItemStack generated = new ItemStack(stack);
	      generated.setAmount(stack.getAmount());
	      mireplacer event = new mireplacer(player, stack, generated);
	      Bukkit.getPluginManager().callEvent((Event)event);
	      if (event.isCancelled())
	        return stack; 
	      return generated;
	  }
	public void onEnable()
	{
		Bukkit.getConsoleSender().sendMessage(name + ChatColor.BLUE + " Plugin encendido (version: " + version + " )");
		this.registrarConfig();
		this.loadUnloadedConfig();
		this.getCommand("mex").setExecutor(this);
		this.getCommand("mex").setTabCompleter(this);
		

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new eventlistener(), this);
		
		 for(int val = -100; val < 100; val++)
		 {
			 listes.add(val + "");
		 }
		 
	}
	
	public void onDisable()
	{
		Bukkit.getConsoleSender().sendMessage(name + ChatColor.BLUE + " Plugin desactivado (version: " + version + " )");
	}

	
	public void registrarConfig()
	{
		File config = new File(this.getDataFolder(),"config.yml");
		rutaconf = config.getPath();
		if(!config.exists())
		{
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
	
	public Boolean loadUnloadedConfig() {
		try {
			if(config.getConfigurationSection("config") == null) {config.createSection("config");}
			if(config.getString("config.enabled") == null) {config.set("config.enabled", false);}
			return true;
		}
		catch (Exception e)
		{
			Bukkit.getConsoleSender().sendMessage(messageext("&7Ah ocurrido un error"));
			Bukkit.getConsoleSender().sendMessage(messageext(e.toString()));
			return false;
		}
	}

	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
				if(args[0].equalsIgnoreCase("mana"))
				{
		        	if(sender instanceof Player)
		        	{
		        		Player p = (Player) sender;
		        		if(p.hasPermission("mmoextension.consume") || p.isOp() || p.hasPermission("mmoextension.*") || p.hasPermission("*"))
		        		{} else {
		        			p.sendMessage(errorperm("mmoextension.consume"));
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
				        		p.sendMessage(messageext(" &7se agrego " + temp + " de mana al jugador " + args[1]));} 
			        		else if(args[2].contains("-")) {
			        			String temp = args[2].replace("-", "");
			        			p.sendMessage(messageext(" &7se consumio " + temp + " de mana del jugador " + args[1]));
			        		} else {
			        			p.sendMessage(messageext(" &7se establecio " + args[2] + " el mana del jugador " + args[1]));
			        		}
			        	} else {
			        		if(args[2].contains("+")) {
			        			String temp = args[2].replace("+", "");
			        			Bukkit.getConsoleSender().sendMessage(messageext(" &7se agrego " + temp + " de mana al jugador " + args[1]));} 
			        		else if(args[2].contains("-")) {
			        			String temp = args[2].replace("-", "");
			        			Bukkit.getConsoleSender().sendMessage(messageext(" &7se consumio " + temp + " de mana del jugador " + args[1]));
			        		} else {
			        			Bukkit.getConsoleSender().sendMessage(messageext(" &7se establecio " + args[2] + " el mana del jugador " + args[1]));
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
		        			p.sendMessage(errorperm("mmoextension.consume"));
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
				        		p.sendMessage(messageext(" &7se agrego " + temp + " de stamina al jugador " + args[1]));} 
			        		else if(args[2].contains("-")) {
			        			String temp = args[2].replace("-", "");
			        			p.sendMessage(messageext(" &7se consumio " + temp + " de stamina del jugador " + args[1]));
			        		} else {
			        			p.sendMessage(messageext(" &7se establecio " + args[2] + " la stamina del jugador " + args[1]));
			        		}
			        	} else {
			        		if(args[2].contains("+")) {
			        			String temp = args[2].replace("+", "");
			        			Bukkit.getConsoleSender().sendMessage(messageext(" &7se agrego " + temp + " de stamina al jugador " + args[1]));} 
			        		else if(args[2].contains("-")) {
			        			String temp = args[2].replace("-", "");
			        			Bukkit.getConsoleSender().sendMessage(messageext(" &7se consumio " + temp + " de stamina del jugador " + args[1]));
			        		} else {
			        			Bukkit.getConsoleSender().sendMessage(messageext(" &7se establecio " + args[2] + " al stamina del jugador " + args[1]));
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
				} else if (args[0].equalsIgnoreCase("itemmeta")) {

	        		Player p = (Player) sender;
	        		if(p.hasPermission("mmoextension.itemmeta") || p.isOp() || p.hasPermission("mmoextension.*") || p.hasPermission("*"))
	        		{
	        			p.sendMessage(p.getItemInHand().getItemMeta().toString());
	        			return true;
	        			
	        		} else {
	        			p.sendMessage(errorperm("mmoextension.itemmeta"));
	        			return true;}
				}
					else if (args[0].equalsIgnoreCase("mrecipe")) {

		        	if(sender instanceof Player)
		        	{
		        		Player p = (Player) sender;
		        		if(p.hasPermission("mmoextension.mrecipe") || p.isOp() || p.hasPermission("mmoextension.*") || p.hasPermission("*"))
		        		{
		        			p.sendMessage(utils.getRecipe());
		        			
		        		} else {
		        			p.sendMessage(errorperm("mmoextension.mrecipe"));
		        			return true;}
		        		
		        		//write there
			        	
		        	} else {}
					return true;
					}  else if (args[0].equalsIgnoreCase("model")) {

			        	if(sender instanceof Player)
			        	{
			        		Player p = (Player) sender;
			        		if(p.hasPermission("mmoextension.model") || p.isOp() || p.hasPermission("mmoextension.*") || p.hasPermission("*"))
			        		{
			        			p.sendMessage(messageext(" &7El modelo del item en mano es :&f" + p.getItemInHand().getItemMeta().getCustomModelData()));
			        			
			        		} else {
			        			p.sendMessage(errorperm("mmoextension.model"));
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
		        		messagehelpc(Bukkit.getConsoleSender());
		        	}
		        	return false;
				}
		} catch (Exception e) {
        	if(sender instanceof Player)
        	{
        		Player p = (Player) sender;
        		messagehelpp(p);
        	} else {
        		messagehelpc(Bukkit.getConsoleSender());
        	}
        	return true;
		}
    }
	
	 @Override
	 public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		 if(args.length == 1) {
			 List<String> list = new ArrayList<>();
			 list.add("mana");
			 list.add("mrecipe");
			 list.add("model");
			 list.add("stamina");
			 list.add("itemmeta");

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
		if (args[0].equalsIgnoreCase("mrecipe") == true)
				{
				List<String> list = getkeys();
					return list;
				}
		
		 
		 return listes;
	 }

		@NotNull public NamespacedKey getRecipeKey(@NotNull Type type, @NotNull String id, @NotNull String recipeType, @NotNull String number) {
			return new NamespacedKey(MMOItems.plugin, recipeType + "_" + type.getId() + "_" + id + "_" + number);
		}
		
		public List<String> getkeys(){
			List<String> list = new ArrayList<>();
			for (Type type : MMOItems.plugin.getTypes().getAll()) {
				FileConfiguration config = type.getConfigFile().getConfig();
				for (MMOItemTemplate template : MMOItems.plugin.getTemplates().getTemplates(type)) {
					if (config.contains(template.getId() + ".base.crafting")) {
						ConfigurationSection section = RecipeMakerGUI.getSection(config, template.getId() + ".base.crafting");
						for (String recipeType : RecipeBrowserGUI.getRegisteredRecipes()) {
							RecipeRegistry rr = RecipeBrowserGUI.getRegisteredRecipe(recipeType);
							ConfigurationSection typeSection = RecipeMakerGUI.getSection(section, recipeType);
							for (String recipeName : typeSection.getKeys(false)) {
								// Generate its key
								NamespacedKey nk = getRecipeKey(template.getType(), template.getId(), recipeType, recipeName);
								// Wrap
								Ref<NamespacedKey> nkRef = new Ref<>(nk);
								// Error yes
								//FriendlyFeedbackProvider ffpMinor = new FriendlyFeedbackProvider(FFPMMOItems.get());
								//ffpMinor.activatePrefix(true, "Recipe of $u" + template.getType() + " " + template.getId());
								list = MythicCraftingManager.getLoadedRecipes();
								//list.add(nk.toString());
								//list.add(typeSection.);
							}	
						}
					}
				}
			}
			return list;
		}
		
		public String errorperm(String permission) {
			String message = ChatColor.translateAlternateColorCodes('&', "&c[&6MMO&eExpansion&c] &7te hace falta el permiso " + permission);
			return message;
		}
		
		public String messageext(String msg) {
			String message = ChatColor.translateAlternateColorCodes('&', "&c[&6MMO&eExpansion&c] " + msg);
			return message;
		}

		public boolean messagehelpc(ConsoleCommandSender cs) {
			try {
    		cs.sendMessage(messageext("&7&m--------------->"));
    		cs.sendMessage(messageext("&cAyuda  / Help"));
    		cs.sendMessage(messageext("&7mex <mana/stamina> <player> <value>"));
    		cs.sendMessage(messageext("&7Sirve para poder restar mana o stamina del mmocore"));
    		cs.sendMessage(messageext("&7mex mrecipe <itemID>"));
    		cs.sendMessage(messageext("&7Sirve para poder ver la receta de un mmoitem &6Proximamente"));
    		cs.sendMessage(messageext("&7&m--------------->"));
    		return true;}
			catch (Exception e) {return false;}
		}

		public boolean messagehelpp(Player cs) {
			try {
    		cs.sendMessage(messageext("&7&m--------------->"));
    		cs.sendMessage(messageext("&cAyuda  / Help"));
    		cs.sendMessage(messageext("&7mex <mana/stamina> <player> <value>"));
    		cs.sendMessage(messageext("&7Sirve para poder restar mana o stamina del mmocore"));
    		cs.sendMessage(messageext("&7mex mrecipe <itemID>"));
    		cs.sendMessage(messageext("&7Sirve para poder ver la receta de un mmoitem &6Proximamente"));
    		cs.sendMessage(messageext("&7&m--------------->"));
    		return true;}
			catch (Exception e) {return false;}
		}

}

