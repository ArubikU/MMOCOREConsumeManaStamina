package ayano.MMOExpansion.utils.mmoitems;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import ayano.MMOExpansion.MMOExpansion;
import io.lumine.mythic.lib.api.item.ItemTag;
import io.lumine.mythic.lib.api.util.AltChar;
import io.lumine.mythic.utils.config.file.FileConfiguration;
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder;
import net.Indyuce.mmoitems.stat.data.DoubleData;
import net.Indyuce.mmoitems.stat.data.type.StatData;

public class snsstat extends net.Indyuce.mmoitems.stat.type.DoubleStat {

	public snsstat() {
		super("SNS_COST", Material.GOLD_NUGGET, "Valor del Item", new String[] { "El valor ingresado sera el ", "que usara al vender con ", "/sns sell" }, new String[] { "all" }, Material.GOLD_NUGGET);
	}
	public void whenApplied(ItemStackBuilder paramItemStackBuilder, StatData paramStatData) {
		double value = Double.valueOf(((DoubleData) paramStatData).toString());
		de.tr7zw.nbtapi.NBTItem eitem = new de.tr7zw.nbtapi.NBTItem(paramItemStackBuilder.getItemStack());
	    paramItemStackBuilder.addItemTag(new ItemTag(MMOExpansion.getPlugin().getConfig().getString("config.sns.nbttag"), value));
	    eitem.setDouble(MMOExpansion.getPlugin().getConfig().getString("config.sns.nbttag"), value);
	    paramItemStackBuilder.getItemStack().setItemMeta(de.tr7zw.nbtapi.NBTItem.convertNBTtoItem(eitem).getItemMeta());
	  }
	
	public void whenDisplayed(List<String> lore, FileConfiguration config, String id) {
		lore.add("");

		String[] split = config.contains(id + "." + getPath()) ? config.getString(id + "." + getPath()).split("\\=") : new String[] { "0" };
		String format = split.length > 1 ? split[0] + " -> " + split[1] : "" + config.getDouble(id + "." + getPath());

		lore.add(ChatColor.GRAY + "Current Value: " + ChatColor.GREEN + format);
		lore.add("");
		lore.add(ChatColor.YELLOW + AltChar.listDash + " Left click to change this value.");
		lore.add(ChatColor.YELLOW + AltChar.listDash + " Right click to remove this value.");
	}

}
