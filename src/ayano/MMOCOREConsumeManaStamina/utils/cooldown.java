package ayano.MMOCOREConsumeManaStamina.utils;

import ayano.MMOCOREConsumeManaStamina.MMOCOREConsumeManaStamina;

public class cooldown {
	
	int IDTaks;
	int time;

	@SuppressWarnings("unused")
	private MMOCOREConsumeManaStamina plugin = MMOCOREConsumeManaStamina.getPlugin();
	
	/*public void cooldownExecute(String Taks, int ticks) {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		time = 1;
		IDTaks = scheduler.scheduleAsyncRepeatingTask(plugin, new Runnable() {
			@SuppressWarnings("unchecked")
			public void run() {
				if(time == 0) {
					
					Collection<? extends Player> Players = Bukkit.getServer().getOnlinePlayers();
					for(Player player : Players)
					{
						int is;
						Inventory inv = player.getInventory();
						for(is = 0; is <= inv.getSize(); is++);
						{
							ItemStack stack = inv.getItem(is);
							NBTItem itemnbt = NBTItem.get(stack);
							List<String> lore = stack.getItemMeta().getLore();
							List<String> nbt = (List<String>) itemnbt.getTags();
							
							int temp1;
							for(temp1 = 0; temp1 < lore.size(); temp1++) {
								if(PlaceholderAPI.containsPlaceholders(lore.get(temp1))) {
									String newlore = PlaceholderAPI.setBracketPlaceholders(player, lore.get(temp1));
									lore.set(temp1, newlore);
									stack.getItemMeta().getLore().set(temp1, newlore);
										itemnbt = NBTItem.get(stack);
										nbt = (List<String>) itemnbt.getTags();
									itemnbt.setString(temp1, "s");
								}
							}
						}
					}
					
					
					//Bukkit.getScheduler().cancelTask(IDTaks);
				}
				else {
					time --;
				}
			}
		}, 0l, 1l*ticks);
	}*/
}
