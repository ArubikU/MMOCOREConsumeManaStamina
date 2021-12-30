package ayano.MMOCOREConsumeManaStamina.utils.chests;

import net.Indyuce.mmoitems.api.player.RPGPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class mireplacer extends Event implements Cancellable {
	  private static final HandlerList HANDLERS = new HandlerList();
	  
	  private boolean isCancelled;
	  
	  private ItemStack original;
	  
	  private ItemStack converted;
	  
	  private final RPGPlayer player;
	  
	  public ItemStack getOriginal() {
	    return this.original;
	  }
	  
	  public ItemStack getConverted() {
	    return this.converted;
	  }
	  
	  public void setOriginal(ItemStack original) {
	    this.original = original;
	  }
	  
	  public void setConverted(ItemStack converted) {
	    this.converted = converted;
	  }
	  
	  public RPGPlayer getPlayer() {
	    return this.player;
	  }
	  
	  public mireplacer(RPGPlayer player, ItemStack original, ItemStack converted) {
	    this.isCancelled = false;
	    this.player = player;
	    this.original = original;
	    this.converted = converted;
	  }
	  
	  public boolean isCancelled() {
	    return this.isCancelled;
	  }
	  
	  public void setCancelled(boolean cancelled) {
	    this.isCancelled = cancelled;
	  }
	  
	  public HandlerList getHandlers() {
	    return HANDLERS;
	  }
	}