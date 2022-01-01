package ayano.MMOExpansion.cmd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ayano.MMOExpansion.MMOExpansion;
import ayano.MMOExpansion.utils.utils;
import de.tr7zw.nbtapi.NBTItem;

public class sns implements TabExecutor {
	
	private MMOExpansion plugin = MMOExpansion.getPlugin();

	public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {

		Player jugador = (Player) player;
		
		if(Bukkit.getPluginManager().getPlugin("NBTAPI") == null)
		{
			jugador.sendMessage(utils.messageext("&c A ocurrido un error no se encuentra la dependencia &aNBTAPI"));
			return false;
		}
		if(Bukkit.getPluginManager().getPlugin("Vault") == null)
		{
			jugador.sendMessage(utils.messageext("&c A ocurrido un error no se encuentra la dependencia &aVault"));
			return false;
		}
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
		{
			jugador.sendMessage(utils.messageext("&c A ocurrido un error no se encuentra la dependencia &aPlaceholderAPI"));
			return false;
		}
		
		if(!(player instanceof Player))
		{
			Bukkit.getConsoleSender().sendMessage(plugin.name + ChatColor.BLUE + "No puedes enviar un comando por consola");
			return false;
		}
		else
		{
			String nbttag = plugin.getConfig().getString("config.sns.nbttag");
			if(args.length <= 0)
			{
			jugador.sendMessage(utils.messageext("&a&m----------------->"));
			jugador.sendMessage(utils.messageext("&7Comandos:"));
			jugador.sendMessage(utils.messageext("&7sns: muestra este mensaje"));
			jugador.sendMessage(utils.messageext("&7sns sellhand: vende el item de tu mano"));
			jugador.sendMessage(utils.messageext("&7sns sellallinv: vende todos los items de tu inventario"));
			jugador.sendMessage(utils.messageext("&6!!&cAtencion vende todos los items con la opcion de vender&6!!"));
			jugador.sendMessage(utils.messageext("&a&m----------------->"));
			return true;
			}
			else if(args.length > 0)
			{
				if(args[0].equalsIgnoreCase("sellallinv"))
				{
					if(jugador.getItemInHand().equals(null)) {jugador.sendMessage(utils.messageext(" &cEl item no existe o no se puede usar")); return false;}
					if(jugador.getItemInHand().getAmount() <= 0) {jugador.sendMessage(utils.messageext(" &cEl item no existe o no se puede usar")); return false;}
					ItemStack item = jugador.getItemInHand();
					NBTItem nbt = new NBTItem(item);
					if(nbt.hasKey(nbttag))
					{
						int money = 0;
				        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				        for(int i = 0; i < 35; i++)
				        {
							if(jugador.getInventory().getItem(i) != null) {
				        		if(jugador.getInventory().getItem(i).getAmount() > 0);{
						        	ItemStack islot = jugador.getInventory().getItem(i);
									NBTItem nbti = new NBTItem(islot);
						        	if(nbti.hasKey(nbttag))
						        	{
							        		int temp = nbti.getInteger(nbttag);
							        		int stacksize = jugador.getInventory().getItem(i).getAmount();
							        		jugador.getInventory().getItem(i).setAmount(0);
							        		money += temp * stacksize;
						        	}
				        		}
							}
				        }
			        	
						
						jugador.sendMessage(utils.messageext("&a&m----------------->"));
						jugador.sendMessage(utils.messageext("&7Has vendido todos tus items vendibles" ));
						jugador.sendMessage(utils.messageext("&7Total ganado : " + String.valueOf(money).toString() ));
						jugador.sendMessage(utils.messageext("&a&m----------------->"));
						Bukkit.dispatchCommand(console, plugin.getConfig().getString("config.sns.ECOCMD").replace("%player%", jugador.getName().toString()).replace("%money%", String.valueOf(money)));
						return true;
					} else {return false;}
						

				}
				else if(args[0].equalsIgnoreCase("sellhand"))
				{
					if(jugador.getItemInHand().equals(null)) {jugador.sendMessage(utils.messageext(" &cEl item no existe o no se puede usar")); return false;}
					if(jugador.getItemInHand().getAmount() <= 0) {jugador.sendMessage(utils.messageext(" &cEl item no existe o no se puede usar")); return false;}
					org.bukkit.inventory.ItemStack item = jugador.getItemInHand();
					NBTItem nbt = new NBTItem(item);
					if(nbt.hasKey(nbttag))
					{
						int i = nbt.getInteger(nbttag);
						int money = i * jugador.getItemInHand().getAmount(); 
				        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
						
						jugador.sendMessage(utils.messageext("&a&m----------------->"));
						jugador.sendMessage(utils.messageext("&7Has vendido (" + item.getItemMeta().getDisplayName() + "&7) por un valor unitario de (&6" + String.valueOf(i).toString() + "&7)" ));
						jugador.sendMessage(utils.messageext("&7Total ganado : " + String.valueOf(money).toString() ));
						jugador.sendMessage(utils.messageext("&a&m----------------->"));
						jugador.getItemInHand().setAmount(0);
						Bukkit.dispatchCommand(console, plugin.getConfig().getString("config.sns.ECOCMD").replace("%player%", jugador.getName().toString()).replace("%money%", String.valueOf(money)));
						return true;
					}
					else
						{
							jugador.sendMessage(utils.messageext("&cEl item no tiene un NBT para vender"));
							return false;
						}
					}
						
				
					
				
				else 
				{
					jugador.sendMessage(utils.messageext("&cEl comando no existe"));
					return false;
				}
				} 
				else 
				{
					jugador.sendMessage(utils.messageext("&cEl comando no existe"));
					return false;
				}
			}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		 if(args.length == 1) {
			 List<String> list = new ArrayList<>();
			 list.add("sellhand");
			 list.add("sellallinv");

			 Collections.sort(list);
			 
			 return list;
		 }
		return null;
	}
}
