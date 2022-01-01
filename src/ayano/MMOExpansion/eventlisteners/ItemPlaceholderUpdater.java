package ayano.MMOExpansion.eventlisteners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import ayano.MMOExpansion.MMOExpansion;
import ayano.MMOExpansion.utils.utils;
import me.clip.placeholderapi.PlaceholderAPI;

public class ItemPlaceholderUpdater implements Listener {

	MMOExpansion plugin = MMOExpansion.getPlugin();

	  @EventHandler
	  public void itemPickup(EntityPickupItemEvent e) {
		if(plugin.getConfig().getBoolean("config.enabled-papi-replace") != true) {return;}
		if(plugin.getConfig().getBoolean("config.papi-options.OnPickup") != true) {return;}
		if(e.getItem().getItemStack().hasItemMeta() == false) {return;}
		if(e.getItem().getItemStack().getItemMeta().hasLore() == false) {return;}
		if(!(e.getEntity() instanceof Player)) {return;}
	    boolean havepapi = false;
	    ItemStack eitem = e.getItem().getItemStack().clone();
	    ItemMeta saved = eitem.getItemMeta();
	    Player p = (Player) e.getEntity();
	    List<String> lore = saved.getLore();
	    for(int numlore = 0; numlore < eitem.getItemMeta().getLore().size(); numlore++)
	    {
	    	String loreline = saved.getLore().get(numlore);
	    	if(utils.havepapi(p, loreline)) {
			    lore.set(numlore, PlaceholderAPI.setPlaceholders(p, eitem.getItemMeta().getLore().get(numlore)));
			    saved.setLore(lore);
	    		havepapi = true;
	    	} else if(utils.havepapi(p, saved.getDisplayName())) {havepapi = true;}
	    }
	    if(havepapi) {
		    saved.setDisplayName(PlaceholderAPI.setPlaceholders(p, saved.getDisplayName()));
		    eitem.setItemMeta(saved);
		    e.setCancelled(true);
		    e.getEntity().getWorld().dropItem(e.getItem().getLocation(), eitem).setVelocity(new Vector(0, 0, 0));
		    e.getItem().remove();
	    }
	  }
	 
	  @EventHandler
	  public void inventoryMove(InventoryClickEvent e) {
			if(plugin.getConfig().getBoolean("config.enabled-papi-replace") != true) {return;}
			if(plugin.getConfig().getBoolean("config.papi-options.OnInventoryMove") != true) {return;}
		    if(e.getCurrentItem().hasItemMeta() != false) {return;}
	    	boolean havepapi = false;
	    	ItemStack eitem = e.getCurrentItem();
		    ItemMeta saved = eitem.getItemMeta();
		    Player p = (Player) e.getWhoClicked();
		    saved.setDisplayName(PlaceholderAPI.setPlaceholders(p, saved.getDisplayName()));
		    List<String> lore = saved.getLore();
		    for(int numlore = 0; numlore <= eitem.getItemMeta().getLore().size(); numlore++)
		    {
		    	String loreline = saved.getLore().get(numlore);
		    	if(utils.havepapi(p, loreline)) {
				    lore.set(numlore, PlaceholderAPI.setPlaceholders(p, eitem.getItemMeta().getLore().get(numlore)));
				    saved.setLore(lore);
		    		havepapi = true;
		    	} else if(utils.havepapi(p, saved.getDisplayName())) {havepapi = true;}
		    }
		    if(havepapi != false) {
			    saved.setDisplayName(PlaceholderAPI.setPlaceholders(p, saved.getDisplayName()));
			    eitem.setItemMeta(saved);
			      e.setCurrentItem(eitem);
		    }
	  }
	  
	  @EventHandler
	  public void onInventoryClick(InventoryClickEvent event) {
	  if (event.getView().getTitle().contains("recipe")) {
		  event.setCancelled(true);
	  }
	  }
	
}
