package ayano.MMOCOREConsumeManaStamina.utils.chests;

import org.bukkit.block.Block;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.InventoryHolder;

public class DefaultChestManager extends ChestManager {
  public DefaultChestManager(ChestCollection cc) {
    super(cc);
  }
  
  public boolean isChest(InventoryHolder holder) {
    return holder instanceof BlockInventoryHolder;
  }
  
  public Block get(InventoryHolder holder) {
    return ((BlockInventoryHolder)holder).getBlock();
  }
}
