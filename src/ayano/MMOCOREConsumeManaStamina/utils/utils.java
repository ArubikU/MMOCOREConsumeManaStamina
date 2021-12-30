package ayano.MMOCOREConsumeManaStamina.utils;

import java.util.List;

import org.bukkit.entity.Player;

import io.lumine.mythic.lib.api.crafting.recipes.MythicCraftingManager;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeBlueprint;
import io.lumine.mythic.lib.api.crafting.recipes.MythicRecipeStation;
import me.clip.placeholderapi.PlaceholderAPI;

public class utils {
	
	public static long getTicks(double seconds) {
		return (long) seconds*20;
	}
	
	public static String getRecipe() {
		String temp;
		
		List<MythicRecipeBlueprint> recipes = MythicCraftingManager.getStationRecipes(MythicRecipeStation.WORKBENCH, "");
		
		temp = recipes.get(1).getNk().toString();
		
		return temp;
	}
	
	public static Boolean havepapi(Player player,String text) {
		
		String transmuted = PlaceholderAPI.setPlaceholders(player, text);
		if(transmuted != text) {
			return true;
		}else {return false;}
	}
}
