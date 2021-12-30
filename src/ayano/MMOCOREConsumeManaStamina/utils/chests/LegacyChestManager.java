package ayano.MMOCOREConsumeManaStamina.utils.chests;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.InventoryHolder;

public class LegacyChestManager extends ChestManager {
  public LegacyChestManager(ChestCollection cc) {
    super(cc);
  }
  
  public boolean isChest(InventoryHolder holder) {
    return holder instanceof org.bukkit.block.Container;
  }
  
  public Block get(InventoryHolder holder) {
    return ((BlockState)holder).getBlock();
  }
}
