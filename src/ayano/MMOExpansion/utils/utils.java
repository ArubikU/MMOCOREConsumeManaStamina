package ayano.MMOExpansion.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import ayano.MMOExpansion.MMOExpansion;
import ayano.MMOExpansion.cmd.mex;
import ayano.MMOExpansion.cmd.sns;
import ayano.MMOExpansion.utils.invs.inventoryes;
import ayano.MMOExpansion.utils.mmoitems.snsstat;
import io.lumine.mythic.lib.api.crafting.ingredients.MythicBlueprintInventory;
import io.lumine.mythic.lib.api.crafting.ingredients.MythicRecipeIngredient;
import io.lumine.mythic.lib.api.crafting.outputs.MythicRecipeOutput;
import io.lumine.mythic.lib.api.crafting.recipes.MythicCraftingManager;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipe;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeBlueprint;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeStation;
import io.lumine.mythic.lib.api.util.Ref;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackPalette;
import io.lumine.mythic.lib.api.util.ui.FriendlyFeedbackProvider;
import me.clip.placeholderapi.PlaceholderAPI;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.MMOItemsAPI;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import net.Indyuce.mmoitems.api.item.template.MMOItemTemplate;
import net.Indyuce.mmoitems.comp.mmocore.load.GetMMOItemObjective;
import net.Indyuce.mmoitems.gui.edition.recipe.RecipeBrowserGUI;
import net.Indyuce.mmoitems.gui.edition.recipe.recipes.RecipeMakerGUI;
import net.Indyuce.mmoitems.gui.edition.recipe.registry.RecipeRegistry;
import net.Indyuce.mmoitems.stat.type.ItemStat;

public class utils {
	public static MMOItems mi;
	public static ItemStat snsstat;
	public static FileConfiguration config = MMOExpansion.getPlugin().getConfig();
	public static MMOExpansion plugin = MMOExpansion.getPlugin();
	
	public static Boolean loadexpansion() {
        	snsstat = new snsstat();
        	MMOItems.plugin.getStats().register(snsstat);
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
              new xpansion().register();
        }
        return true;
	}
	
	public static Boolean createconfig() {
		config.set("config.enabled-papi-replace", true);
		config.set("config.papi-options.OnInventoryMove", false);
		config.set("config.papi-options.OnPickup", true);
		config.set("config.sns.nbttag", "CUSTOM_SELL");
		config.set("config.sns.ECOCMD", "eco give %player% %money%");
		plugin.saveConfig();
		return true;
	}
	
	public static long getTicks(double seconds) {
		return (long) seconds*20;
	}
	
	public static HashMap<Integer, ItemStack> getRecipe(String nk) {
		//Esto añade todas las MythicRecipes a una Lista
		@NotNull List<MythicRecipe> finalrecipe = getMythicRecipeList();
		for(int i = 0; i < finalrecipe.size(); i++) {
			MythicRecipe tempcipe = finalrecipe.get(i);
			if(tempcipe.getName().equalsIgnoreCase(nk)) {
				List<MythicRecipeIngredient> ingredients = tempcipe.getIngredients();
				HashMap<Integer, ItemStack> recipec = new HashMap<Integer, ItemStack>();
				for(int e = 0; e < ingredients.size(); e++) {
					ItemStack temp = ingredients.get(e).getIngredient().getRandomSubstituteItem(null);
					recipec.put(e, temp);
				}
				return recipec;
			}
		}
		return null;
	}
	

	public static MythicRecipe getRecipeMythic(String nk) {
		//Esto añade todas las MythicRecipes a una Lista
		@NotNull List<MythicRecipe> finalrecipe = getMythicRecipeList();
		for(int i = 0; i < finalrecipe.size(); i++) {
			MythicRecipe tempcipe = finalrecipe.get(i);
			if(tempcipe.getName().equalsIgnoreCase(nk)) {
				return tempcipe;
				}
			}
		return null;
	}
	
	public static List<MythicRecipe> getMythicRecipeList(){
		List<MythicRecipeBlueprint> recipes = MythicCraftingManager.getStationRecipes(MythicRecipeStation.WORKBENCH, "");
		@NotNull List<MythicRecipe> finalrecipe = new ArrayList<MythicRecipe>();
		for(int i = 0; i < recipes.size(); i++)
		{
			finalrecipe.add(recipes.get(i).getMainCheck());
		}	
		return finalrecipe;
	}
	
	public static MythicRecipeBlueprint getMythicRecipeBlueprint(String nk){
		List<MythicRecipeBlueprint> recipes = MythicCraftingManager.getStationRecipes(MythicRecipeStation.WORKBENCH, "");
		@NotNull List<MythicRecipe> finalrecipe = new ArrayList<MythicRecipe>();
		for(int i = 0; i < recipes.size(); i++)
		{
			if(recipes.get(i).getNk().toString().contains(nk)) {
				return recipes.get(i);
			}
		}
		return null;	
	}
	
	
	public static String generatePreview(HashMap<Integer, ItemStack> recipec, Player p, MythicRecipe recipe) {
		//se usara luegi if(p != null) {}
		if(recipec.size() <= 9) 
		{
			MythicRecipeBlueprint print = getMythicRecipeBlueprint(recipe.getName());
			String tempname = print.getNk().toString().replace("mmoitems:", "");
			String[] namearray = tempname.split("_");
			String tempnametwo = tempname.replaceFirst(namearray[0], "").replaceFirst(namearray[1], "").replace("_", " ").replaceFirst("  ", "").replaceFirst(namearray[namearray.length - 1], "");
			Inventory normal = Bukkit.createInventory(null, 27, tempnametwo + " recipe");
			ItemStack fill = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
			
			for(int i = 0; i < normal.getSize(); i++) {

				normal.setItem(i, fill); //ok
			}
			
			//set ingredients
			//ItemStack out = result.getItem(namearray[1], tempname.replaceFirst(namearray[0], "").replaceFirst(namearray[1], "").replaceFirst("_"+namearray[namearray.length], ""));

			int remp = namearray.length - 1;
			String type = namearray[1];
			String id = tempname.replaceFirst(namearray[0] + "_", "").replaceFirst(namearray[1] + "_", "").replaceFirst("_" + namearray[remp], "");
			
			
			normal.setItem(0, recipec.get(0)); //ok
			normal.setItem(1, recipec.get(3)); //ok
			normal.setItem(2, recipec.get(6)); //ok
			
			normal.setItem(9, recipec.get(1));//ok
			normal.setItem(10, recipec.get(4)); //ok
			normal.setItem(11, recipec.get(7)); //ok
			
			//normal.setItem(16, out);
			
			normal.setItem(18, recipec.get(2)); //ok
			normal.setItem(19, recipec.get(5)); //ok
			normal.setItem(20, recipec.get(8)); //ok
			
			//set output
			//normal.setItem(13, print.ge)
			
			p.openInventory(normal);
			
			return "normal";
		}
		if(recipec.size() <= 25 && recipec.size() > 9)
		{
			MythicRecipeBlueprint print = getMythicRecipeBlueprint(recipe.getName());
			String tempname = print.getNk().toString().replace("mmoitems:", "");
			String[] namearray = tempname.split("_");
			String tempnametwo = tempname.replaceFirst(namearray[0], "").replaceFirst(namearray[1], "").replace("_", " ").replaceFirst("  ", "").replaceFirst(namearray[namearray.length - 1], "");
			Inventory normal = Bukkit.createInventory(null, 45, tempnametwo + " recipe");
			ItemStack fill = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
			
			for(int i = 0; i < normal.getSize(); i++) {

				normal.setItem(i, fill); //ok
			}
			
			int remp = namearray.length - 1;
			String type = namearray[1];
			String id = tempname.replaceFirst(namearray[0] + "_", "").replaceFirst(namearray[1] + "_", "").replaceFirst("_" + namearray[remp], "");
			
			/* 
			 * 0 = 1
			 * 1 = 6
			 * 2 = 11
			 * 3 = 16
			 * 4 = 21
			 * 
			 * 5 = 2
			 * 6 = 7
			 * 7 = 12
			 * 8 = 17
			 * 9 = 22
			 * 
			 * 10 = 3
			 * 11 = 8
			 * 12 = 13
			 * 13 = 18
			 * 14 = 23
			 * 
			 * 15 = 4
			 * 16 = 9
			 * 17 = 14
			 * 18 = 19
			 * 19 = 24
			 * 
			 * 20 = 5
			 * 21 = 10
			 * 22 = 15
			 * 23 = 20
			 * 24 = 25
			 * 
			 * */
			normal.setItem(0, recipec.get(0)); //ok
			normal.setItem(1, recipec.get(1));
			normal.setItem(2, recipec.get(2));
			normal.setItem(3, recipec.get(3));
			normal.setItem(4, recipec.get(4));
			
			normal.setItem(9, recipec.get(5)); //1
			normal.setItem(10, recipec.get(6));
			normal.setItem(11, recipec.get(7));
			normal.setItem(12, recipec.get(8));
			normal.setItem(13, recipec.get(9));
			
			normal.setItem(18, recipec.get(10));//2
			normal.setItem(19, recipec.get(11));
			normal.setItem(20, recipec.get(12));
			normal.setItem(21, recipec.get(13));
			normal.setItem(22, recipec.get(14));

			normal.setItem(27, recipec.get(15));//3
			normal.setItem(28, recipec.get(16));
			normal.setItem(29, recipec.get(17));
			normal.setItem(30, recipec.get(18));
			normal.setItem(31, recipec.get(19));

			normal.setItem(36, recipec.get(20));//4
			normal.setItem(37, recipec.get(21));
			normal.setItem(38, recipec.get(22));
			normal.setItem(39, recipec.get(23));
			normal.setItem(40, recipec.get(24)); //ok
			
			p.openInventory(normal);
			
			//superworkbench
			return "super";
		}
		if(recipec.size() > 25)
		{
			//megaworkbench
			return "mega";
		}
		return null;
	}
	
	public static Boolean havepapi(Player player,String text) {
		
		String transmuted = PlaceholderAPI.setPlaceholders(player, text);
		if(transmuted.equalsIgnoreCase(text)) {
			return false;
		}else {return true;}
	}
	

	@NotNull public static NamespacedKey getRecipeKey(@NotNull Type type, @NotNull String id, @NotNull String recipeType, @NotNull String number) {
		return new NamespacedKey(MMOItems.plugin, recipeType + "_" + type.getId() + "_" + id + "_" + number);
	}
	

	public static String errorperm(String permission) {
		String message = ChatColor.translateAlternateColorCodes('&', "&c[&6MMO&eExpansion&c] &7te hace falta el permiso " + permission);
		return message;
	}
	
	public static String messageext(String msg) {
		String message = ChatColor.translateAlternateColorCodes('&', "&c[&6MMO&eExpansion&c] " + msg);
		return message;
	}

	public static boolean messagehelpc(ConsoleCommandSender cs) {
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

	public static boolean messagehelpp(Player cs) {
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
	
	public static List<String> getNums(){
		List<String> listes = new ArrayList<>();
		 for(int val = -100; val < 100; val++)
		 {
			 listes.add(val + "");
		 }
		return listes;
	}
	
	public static List<String> getkeys(){
		List<String> list = new ArrayList<>();
		List<MythicRecipeBlueprint> recipes = MythicCraftingManager.getStationRecipes(MythicRecipeStation.WORKBENCH, "");
		@NotNull List<MythicRecipe> finalrecipe = new ArrayList<MythicRecipe>();
		for(int i = 0; i < recipes.size(); i++)
		{
			list.add(finalrecipe.get(i).getName());
		}
		return list;
	}

}
