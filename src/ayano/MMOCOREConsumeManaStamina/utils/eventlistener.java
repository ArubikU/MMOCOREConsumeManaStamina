package ayano.MMOCOREConsumeManaStamina.utils;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ayano.MMOCOREConsumeManaStamina.MMOCOREConsumeManaStamina;
import me.clip.placeholderapi.PlaceholderAPI;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.player.RPGPlayer;

public class eventlistener implements Listener {


	  @EventHandler
	  public void itemPickup(EntityPickupItemEvent e) {

		if(e.getItem().getItemStack().hasItemMeta() == false) {return;}
		if(e.getItem().getItemStack().getItemMeta().hasLore() == false) {return;}
		if(!(e.getEntity() instanceof Player)) {return;}
	    boolean havepapi = false;
	    Player p = (Player) e.getEntity();
	    int numlore;
	    for(numlore = 1; numlore <= e.getItem().getItemStack().getItemMeta().getLore().size(); numlore++)
	    {
	    	String loreline = e.getItem().getItemStack().getItemMeta().getLore().get(numlore);
	    	if(utils.havepapi(p ,loreline)) {
	    		havepapi = true;
			    e.setCancelled(true);
			    e.getItem().getItemStack().getItemMeta().getLore().set(numlore, PlaceholderAPI.setPlaceholders((OfflinePlayer)e.getEntity(), e.getItem().getItemStack().getItemMeta().getLore().get(numlore)));
	    	}
	    }
	    if(havepapi == true) {
		    RPGPlayer rpg = PlayerData.get((OfflinePlayer)e.getEntity()).getRPG();
		    e.getEntity().getWorld().dropItem(e.getItem().getLocation(), e.getItem().getItemStack()).setVelocity(new Vector(0, 0, 0));
		    e.getItem().remove();
	    }
	  }
	 
	  @EventHandler
	  public void inventoryMove(InventoryClickEvent e) {
		  if(e.getCurrentItem().hasItemMeta() != false) {return;}
	    	boolean havepapi = false;
	    	ItemStack eitem = e.getCurrentItem();
		    for(int numlore = 0; numlore <= eitem.getItemMeta().getLore().size(); numlore++)
		    {
		    	if(PlaceholderAPI.containsPlaceholders(eitem.getItemMeta().getLore().get(numlore))) {
		    		havepapi = true;
				    e.setCancelled(true);
		    		eitem.getItemMeta().getLore().set(numlore, PlaceholderAPI.setPlaceholders((OfflinePlayer)e.getWhoClicked(), eitem.getItemMeta().getLore().get(numlore)));
		    	}
		    }
		    if(havepapi != false) {
			      RPGPlayer rpg = PlayerData.get(e.getWhoClicked().getUniqueId()).getRPG();
			      e.setCurrentItem(eitem);
		    }
	  }
	  
	  
	  @SuppressWarnings("unused")
	private void convertInventory(UUID opener, Inventory inv) {
	    for (int i = 0; i < inv.getSize(); i++) {
	      ItemStack stack = inv.getItem(i);
	      if (stack != null && 
	        MMOCOREConsumeManaStamina.isValid(stack)) {
	        RPGPlayer rpg = PlayerData.get(opener).getRPG();
	        inv.setItem(i, MMOCOREConsumeManaStamina.replace(rpg, stack));
	      } 
	    } 
	  }
	  
	  private boolean worldCheck(String worldName) {
	    return !MMOCOREConsumeManaStamina.getPlugin().getGlobals().checkWorld(worldName);
	  }
	
}
