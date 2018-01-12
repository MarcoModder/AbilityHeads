package com.github.marcomodder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.github.marcomodder.storageutils.AbilitySign;
import com.github.marcomodder.storageutils.SignStorage;

import net.milkbowl.vault.economy.Economy;

public class Listeners implements Listener {
	
	public static Economy economy = null;
	
	//Deny user from placing heads
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		if(!(e.getItemInHand() != null))
		{
			return;
		}
		ItemStack it = e.getItemInHand();
		if(it.getType() == Material.SKULL_ITEM)
		{
			e.setCancelled(true);
		}
	}
	
	
	//Sign
	@EventHandler(priority=EventPriority.HIGHEST)
	public void Signit(SignChangeEvent e) 
	{
		Player p = e.getPlayer();
		if(!p.hasPermission("abilityhead.createsign"))
		{
			return;
		}
		List<String> temp = new ArrayList<String>();
		for(String s : e.getLines())
		{
			if(s != null && !s.equalsIgnoreCase(""))
			{
				temp.add(s);
			}
		}
		if(e.getLines()[0].equalsIgnoreCase("[Ability]") && temp.size() == 4)
		{
			try
			{
				String aName = e.getLines()[1];
				String moe = e.getLines()[2];
				int price = Integer.parseInt(e.getLines()[3]);
				String correct = aName.toLowerCase();
				String def = null;
				switch(correct)
				{
				case "beast":
					def = "beast";
					break;
				case "heal":
					def = "heal";
					break;
				case "flash":
					def = "flash";
					break;
				case "exhaust":
					def = "exhaust";
					break;
				case "ignite":
					def = "ignite";
					break;
				case "ghost":
					def = "ghost";
					break;
				case "barrier":
					def = "barrier";
					break;
				case "explosive":
					def = "explosive";
					break;
				case "deadman":
					def = "deadman";
					break;
				default:
					p.sendMessage("§cInvalid Ability Head name!");
					return;
				}
				if(moe.contains("$"))
				{
					AbilitySign sign = new AbilitySign(def,price,0,e.getBlock().getLocation());
					SignStorage.getSigns().add(sign);
					p.sendMessage(Utils.color("&aSuccesfully created AbilitySign!"));
					e.getLines()[0] = Utils.color("&b" + e.getLines()[0]);
					return;
				}
				else if(moe.contains("EXP"))
				{
					AbilitySign sign = new AbilitySign(def,price,1,e.getBlock().getLocation());
					SignStorage.getSigns().add(sign);
					p.sendMessage(Utils.color("&aSuccesfully created AbilitySign!"));
					e.getLines()[0] = Utils.color("&b" + e.getLines()[0]);
					return;
				}
			}catch(NumberFormatException ex)
			{
				p.sendMessage(Utils.color("&cPrice must be a number!"));
			}
		}
		else if(e.getLines()[0].equalsIgnoreCase("[Ability]") && temp.size() == 3)
		{
			try
			{
				String moe = e.getLines()[1];
				int price = Integer.parseInt(e.getLines()[2]);
				if(moe.contains("$"))
				{
					AbilitySign sign = new AbilitySign(price,0,e.getBlock().getLocation());
					SignStorage.getSigns().add(sign);
					p.sendMessage(Utils.color("&aSuccesfully created AbilitySign!"));
					e.getLines()[0] = Utils.color("&b" + e.getLines()[0]);
					return;
				}
				else if(moe.contains("EXP"))
				{
					AbilitySign sign = new AbilitySign(price,1,e.getBlock().getLocation());
					SignStorage.getSigns().add(sign);
					p.sendMessage(Utils.color("&aSuccesfully created AbilitySign!"));
					e.getLines()[0] = Utils.color("&b" + e.getLines()[0]);
					return;
				}
			}catch(NumberFormatException ex)
			{
				p.sendMessage(Utils.color("&cPrice must be a number!"));
			}
		}
	}
	
	
	//Sign
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) 
	{
            Player p = e.getPlayer();
			if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
            if (e.getClickedBlock().getState() instanceof Sign) 
            {
                Sign s = (Sign) e.getClickedBlock().getState();
        		List<String> temp = new ArrayList<String>();
        		for(String ss : s.getLines())
        		{
        			if(ss != null && !ss.equalsIgnoreCase(""))
        			{
        				temp.add(ss);
        			}
        		}
                if(temp.size() == 4)
                {
                	int price = 0;
                    int moe = 0;
                    String aName = null;
                    for(AbilitySign as : SignStorage.getSigns())
                    {
                    	if(as.getLocation().equals(s.getLocation()))
                    	{
                    		price = as.getPrice();
                    		moe = as.getMoneyOrExpBit();
                    		aName = as.getAbility();
                    	}
                    }
                    if(moe == 0)
                    {
                        if(setupEconomy())
                        {
                        	double balance = economy.getBalance(p);
                            if(balance < price)
                            {
                            	p.sendMessage(Utils.color("&cYou don't have enough money!"));
                            	return;
                            }
                            else
                            {
                            	economy.withdrawPlayer(p, price);
                        		String first = AbilityHeads.getInstance().getConfig().getString("Signs.PurchasedMessage");
                        		String second = first.replace("{ABILITY}", aName);
                        		p.sendMessage(Utils.color(second));
                            	String correct = aName.toLowerCase();
        						switch(correct)
        						{
        						case "beast":
        							p.getInventory().addItem(Utils.getBeastItem(1));
        							break;
        						case "heal":
        							p.getInventory().addItem(Utils.getHealItem(1));
        							break;
        						case "flash":
        							p.getInventory().addItem(Utils.getFlashItem(1));
        							break;
        						case "exhaust":
        							p.getInventory().addItem(Utils.getExhaustItem(1));
        							break;
        						case "ignite":
        							p.getInventory().addItem(Utils.getIgniteItem(1));
        							break;
        						case "ghost":
        							p.getInventory().addItem(Utils.getGhostItem(1));
        							break;
        						case "barrier":
        							p.getInventory().addItem(Utils.getBarrierItem(1));
        							break;
        						case "explosive":
        							p.getInventory().addItem(Utils.getExplosiveItem(1));
        							break;
        						case "deadman":
        							p.getInventory().addItem(Utils.getDeadmanItem(1));
        							break;
        						default:
        							p.sendMessage("§cThere was an error with your transaction!");
        							break;
        						}
        						return;
                            }
                        }
                    }
                    else
                    {
                    	if(p.getLevel() < price)
                    	{
                    		p.sendMessage(Utils.color("&cYou don't have enough level!"));
                        	return;
                    	}
                    	else
                    	{
                    		p.setLevel(p.getLevel() - price);
                    		String first = AbilityHeads.getInstance().getConfig().getString("Signs.PurchasedMessage");
                    		String second = first.replace("{ABILITY}", aName);
                    		p.sendMessage(Utils.color(second));
                        	String correct = aName.toLowerCase();
    						switch(correct)
    						{
    						case "beast":
    							p.getInventory().addItem(Utils.getBeastItem(1));
    							break;
    						case "heal":
    							p.getInventory().addItem(Utils.getHealItem(1));
    							break;
    						case "flash":
    							p.getInventory().addItem(Utils.getFlashItem(1));
    							break;
    						case "exhaust":
    							p.getInventory().addItem(Utils.getExhaustItem(1));
    							break;
    						case "ignite":
    							p.getInventory().addItem(Utils.getIgniteItem(1));
    							break;
    						case "ghost":
    							p.getInventory().addItem(Utils.getGhostItem(1));
    							break;
    						case "barrier":
    							p.getInventory().addItem(Utils.getBarrierItem(1));
    							break;
    						case "explosive":
    							p.getInventory().addItem(Utils.getExplosiveItem(1));
    							break;
    						case "deadman":
    							p.getInventory().addItem(Utils.getDeadmanItem(1));
    							break;
    						default:
    							p.sendMessage("§cThere was an error with your transaction!");
    							break;
    						}
    						return;
                    	}
                }
                }else if(temp.size() == 3)
                {
                	int price = 0;
                    int moe = 0;
                    for(AbilitySign as : SignStorage.getSigns())
                    {
                    	if(as.getLocation().equals(s.getLocation()))
                    	{
                    		price = as.getPrice();
                    		moe = as.getMoneyOrExpBit();
                    	}
                    }
                    if(moe == 0)
                    {
                        if(setupEconomy())
                        {
                        	double balance = economy.getBalance(p);
                            if(balance < price)
                            {
                            	p.sendMessage(Utils.color("&cYou don't have enough money!"));
                            	return;
                            }
                            else
                            {
                            	economy.withdrawPlayer(p, price);
                        		ItemStack it = Utils.randomAbility();
                        		String first = AbilityHeads.getInstance().getConfig().getString("Signs.PurchasedMessage");
                        		String second = first.replace("{ABILITY}", it.getItemMeta().getDisplayName());
                        		p.getInventory().addItem(it);
                        		p.sendMessage(Utils.color(second));
        						return;
                            }
                        }
                    }
                    else
                    {
                    	if(p.getLevel() < price)
                    	{
                    		p.sendMessage(Utils.color("&cYou don't have enough level!"));
                        	return;
                    	}
                    	else
                    	{
                    		p.setLevel(p.getLevel() - price);
                    		ItemStack it = Utils.randomAbility();
                    		String first = AbilityHeads.getInstance().getConfig().getString("Signs.PurchasedMessage");
                    		String second = first.replace("{ABILITY}", it.getItemMeta().getDisplayName());
                    		p.getInventory().addItem(it);
                    		p.sendMessage(Utils.color(second));
    						return;
                		}
                    }
                }                         
            }
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
 
        return (economy != null);
	}

}
