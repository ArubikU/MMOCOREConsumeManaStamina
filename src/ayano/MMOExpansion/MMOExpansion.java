package ayano.MMOExpansion;

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

import ayano.MMOExpansion.cmd.mex;
import ayano.MMOExpansion.cmd.sns;
import ayano.MMOExpansion.utils.JsonUtils;
import ayano.MMOExpansion.utils.eventlistener;
import ayano.MMOExpansion.utils.utils;
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
public class MMOExpansion extends JavaPlugin implements TabExecutor {
	PluginDescriptionFile pdffile = getDescription();
	public String rutaconf;
	public String version = pdffile.getVersion();
	public String name = ChatColor.AQUA + "["+pdffile.getName()+"]";
	public FileConfiguration config = this.getConfig();
	public static List<String> listes = new ArrayList<>();
	private static MMOExpansion plugin;
	public static MMOExpansion getPlugin() {
		  return plugin;
		}
	public void onEnable()
	{
		Bukkit.getConsoleSender().sendMessage(name + ChatColor.BLUE + " Plugin encendido (version: " + version + " )");
		this.registrarConfig();
		this.loadUnloadedConfig();
		plugin = this;
		this.getCommand("mex").setExecutor(new mex());
		this.getCommand("mex").setTabCompleter(new mex());
		this.getCommand("sns").setExecutor(new sns());
		this.getCommand("sns").setTabCompleter(new sns());
		utils.loadexpansion();
		listes = utils.getNums();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new eventlistener(), this);
		 
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
			if(config.getString("config.enabled-papi-replace") == "") {config.set("config.enabled-papi-replace", true);}
			if(config.getString("config.papi-options.OnInventoryMove") == "") {config.set("config.papi-options.OnInventoryMove", false);}
			if(config.getString("config.papi-options.OnPickup") == "") {config.set("config.papi-options.OnPickup", true);}
			if(config.getString("config.sns.nbttag") == "") {config.set("config.sns.nbttag", "CUSTOM_SELL");}
			if(config.getString("config.sns.ECOCMD") == "") {config.set("config.sns.ECOCMD", "eco give %player% %money%");}
			saveConfig();
			return true;
		}
		catch (Exception e)
		{
			Bukkit.getConsoleSender().sendMessage(utils.messageext("&7Ah ocurrido un error"));
			Bukkit.getConsoleSender().sendMessage(utils.messageext(e.toString()));
			return false;
		}
	}
}

