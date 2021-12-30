package ayano.MMOCOREConsumeManaStamina;

import io.lumine.mythic.lib.api.item.NBTItem;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;

public class globalconfig {

	  public boolean global_keepEnchants;
	  
	  public boolean global_keepDisplayName;
	  
	  public boolean convertOnPickup;
	  
	  public boolean convertOnCraft;
	  
	  public boolean convertOnSmelt;
	  
	  public boolean convertOnChest;
	  
	  public boolean convertOnInventory;
	  
	  public boolean convertOnSmithing;
	  
	  public boolean slimefun;
	  
	  public String previewDisplayName;
	  
	  public String previewPreLore;
	  
	  public String loreMatcher;
	  
	  public String nameMatcher;
	  
	  public List<String> worldList;
	  
	  public List<String> exemptNBTTags;
	  
	  private List<String> previewCraftLore;
	  
	  public void setPreviewCraftLore(List<String> previewCraftLore) {
	    this.previewCraftLore = previewCraftLore;
	  }
	  
	  public List<String> getConstructedLore() {
	    List<String> lore = new ArrayList<>();
	    for (String s : this.previewCraftLore)
	      lore.add(ChatColor.translateAlternateColorCodes('&', s)); 
	    return lore;
	  }
	  
	  public boolean checkWorld(String worldName) {
	    return (this.worldList.isEmpty() || this.worldList.contains(worldName));
	  }
	  
	  private boolean validLore(List<String> lore) {
	    if (this.loreMatcher.isEmpty() || this.loreMatcher.equals("([\\s\\S])+"))
	      return true; 
	    boolean match = false;
	    for (String line : lore)
	      match = line.matches(this.loreMatcher); 
	    return !match;
	  }
	  
	  private boolean validDisplayName(String display) {
	    if (this.loreMatcher.isEmpty() || this.loreMatcher.equals("([\\s\\S])+"))
	      return true; 
	    return !display.matches(this.loreMatcher);
	  }
	  
	  public boolean metaCheck(ItemMeta meta) {
	    return ((!meta.hasDisplayName() || validDisplayName(meta.getDisplayName())) && (!meta.hasLore() || validLore(meta.getLore())));
	  }
	  
	  public boolean nbtCheck(NBTItem nbt) {
	    boolean slimeFlag = (this.slimefun && nbt.getNBTCompound("PublicBukkitValues").hasTag("slimefun:slimefun_item"));
	    return (nbt.getTags().stream().anyMatch(tag -> this.exemptNBTTags.contains(tag)) || slimeFlag);
	  }
}
