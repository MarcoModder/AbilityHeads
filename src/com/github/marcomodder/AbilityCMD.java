package com.github.marcomodder;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AbilityCMD implements CommandExecutor {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))
		{
			return true;
		}
		Player p = (Player) sender;
		if(!p.hasPermission("abilityhead.help"))
		{
			p.sendMessage(Utils.color("&cNo permissions!"));
			return true;
		}
		if(args.length == 0)
		{
			p.sendMessage("§7§m--------§aAbility Commands§7§m--------");
			p.sendMessage("§aability reload - Reload the plugin");
			p.sendMessage("§aability give {username} {ability_name} {amount} - Give an ability head to a specified user");
			p.sendMessage("§7§m--------------------------------");
			return true;
		}
		if(args.length == 1 || args[0].equalsIgnoreCase("give"))
		{
			if(args[0].equalsIgnoreCase("help"))
			{
				p.sendMessage("§7§m--------§aAbility Commands§7§m--------");
				p.sendMessage("§aability reload - Reload the plugin");
				p.sendMessage("§aability give {username} {ability_name} {amount} - Give an ability head to a specified user");
				p.sendMessage("§7§m--------------------------------");
				return true;
			}
			else if(args[0].equalsIgnoreCase("reload"))
			{
				AbilityHeads.getInstance().reloadConfig();
				AbilityHeads.getInstance().saveConfig();
				p.sendMessage("§aYou have succesfully reload AbilityHeads!");
			}
			else if(args[0].equalsIgnoreCase("give"))
			{
				if(args.length == 4)
				{
					try
					{
						String target = args[1];
						String aName = args[2];
						String correct = aName.toLowerCase();
						int amount = Integer.parseInt(args[3]);
						Player tg = Bukkit.getPlayer(target);
						switch(correct)
						{
						case "beast":
							tg.getInventory().addItem(Utils.getBeastItem(amount));
							break;
						case "heal":
							tg.getInventory().addItem(Utils.getHealItem(amount));
							break;
						case "flash":
							tg.getInventory().addItem(Utils.getFlashItem(amount));
							break;
						case "exhaust":
							tg.getInventory().addItem(Utils.getExhaustItem(amount));
							break;
						case "ignite":
							tg.getInventory().addItem(Utils.getIgniteItem(amount));
							break;
						case "ghost":
							tg.getInventory().addItem(Utils.getGhostItem(amount));
							break;
						case "barrier":
							tg.getInventory().addItem(Utils.getBarrierItem(amount));
							break;
						case "explosive":
							tg.getInventory().addItem(Utils.getExplosiveItem(amount));
							break;
						case "deadman":
							tg.getInventory().addItem(Utils.getDeadmanItem(amount));
							break;
						default:
							p.sendMessage("§cInvalid Ability Head name!");
							break;
						}
						if(!p.getName().equalsIgnoreCase(tg.getName()))
						{
							String first = AbilityHeads.getInstance().getConfig().getString("Messages.HeadRecieved");
							String def = first.replace("{PLAYER}", p.getName());
							tg.sendMessage(Utils.color(def));
						}
					}catch(NumberFormatException e)
					{
						p.sendMessage("§cIncorrect number!");
						
					}catch(NullPointerException ex)
					{
						p.sendMessage("§cThis player is not online!");
					}
					return true;
				}
				else
				{
					p.sendMessage("§cCorrect usage: /ability give {username} {ability_name} {amount}");
					return true;
				}
			}
			else
			{
				p.sendMessage("§cWrong syntax!");
				return true;
			}
		}
		else
		{
			p.sendMessage("§cWrong syntax!");
		}
		return true;
	}

}
