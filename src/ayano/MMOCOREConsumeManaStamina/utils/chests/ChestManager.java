package ayano.MMOCOREConsumeManaStamina.utils.chests;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.InventoryHolder;

public abstract class ChestManager {
  protected final ChestCollection chestCollection;
  
  protected ChestManager(ChestCollection cc) {
    this.chestCollection = cc;
  }
  
  public abstract boolean isChest(InventoryHolder paramInventoryHolder);
  
  public abstract Block get(InventoryHolder paramInventoryHolder);
  
  public void load() {
    this.chestCollection.load();
  }
  
  public void reload() {
    this.chestCollection.reload();
  }
  
  public void save() {
    this.chestCollection.save();
  }
  
  public void add(Location location) {
    this.chestCollection.add(location);
  }
  
  public boolean has(Location location) {
    return this.chestCollection.has(location);
  }
}