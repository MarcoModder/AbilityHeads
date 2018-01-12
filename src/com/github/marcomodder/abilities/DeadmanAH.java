package com.github.marcomodder.abilities;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.marcomodder.AbilityHeads;
import com.github.marcomodder.Utils;

public class DeadmanAH extends Enchantment implements Listener {
	
	public DeadmanAH(int id) {
		super(id);
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public String getName() {
		return "DeadMan";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}
	
	@Override
	public int getId()
	{
		return 109;
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e)
	{
		if(!(e.getEntity() instanceof Player))
		{
			return;
		}
		if(!(e.getDamager() instanceof Player))
		{
			return;
		}
		Player p = (Player) e.getEntity();
		if(AbilityHeads.getInstance().deadmanMap.containsKey(p))
		{
			if(p.getHealth() < 5.0D)
			{
				p.setMaxHealth(40.0D);
				p.setHealth(40.0D);
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,100,2));
				removeHearts(p,5);
				AbilityHeads.getInstance().deadmanMap.remove(p);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e)
	{
		if(!(e.getEntity() instanceof Player))
		{
			return;
		}
		Player p = (Player) e.getEntity();
		if(AbilityHeads.getInstance().deadmanMap.containsKey(p))
		{
			if(p.getHealth() < 5.0D)
			{
				p.setMaxHealth(40.0D);
				p.setHealth(40.0D);
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,100,2));
				removeHearts(p,5);
				AbilityHeads.getInstance().deadmanMap.remove(p);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		if(!(e.getAction() == Action.RIGHT_CLICK_AIR))
		{
			return;
		}
		if(p.getItemInHand().containsEnchantment(this))
		{
			if(AbilityHeads.getInstance().deadmanDuration.containsKey(p))
			{
				p.sendMessage(Utils.color("&aThis spell is already in use!"));
				return;
			}
			AbilityHeads.getInstance().deadmanMap.put(p, true);
			String first = AbilityHeads.getInstance().getConfig().getString("Messages.Activate");
			String second = first.replace("{ABILITY}", this.getName());
			p.sendMessage(Utils.color(second));		
			p.getInventory().remove(p.getItemInHand());
			AbilityHeads.getInstance().deadmanDuration.put(p, AbilityHeads.getInstance().getConfig().getInt("Abilities.Deadman.Duration"));
			new BukkitRunnable()
			{

				@Override
				public void run() 
				{
					AbilityHeads.getInstance().deadmanDuration.put(p, AbilityHeads.getInstance().deadmanDuration.get(p) - 1);
					if(AbilityHeads.getInstance().deadmanDuration.get(p) == 0)
					{
						AbilityHeads.getInstance().deadmanDuration.remove(p);
						AbilityHeads.getInstance().deadmanMap.remove(p);
						this.cancel();	
					}else if(AbilityHeads.getInstance().deadmanDuration.get(p) == 60)
					{
						p.sendMessage(Utils.color(AbilityHeads.getInstance().getConfig().getString("Items.Deadman.60SecondsMessage")));
						
					}else if(AbilityHeads.getInstance().deadmanDuration.get(p) == 30)
					{
						p.sendMessage(Utils.color(AbilityHeads.getInstance().getConfig().getString("Items.Deadman.30SecondsMessage")));
						
					}else if(AbilityHeads.getInstance().deadmanDuration.get(p) == 5)
					{
						p.sendMessage(Utils.color(AbilityHeads.getInstance().getConfig().getString("Items.Deadman.5SecondsMessage")));
					}
				}
				
			}.runTaskTimerAsynchronously(AbilityHeads.getInstance(), 20L, 20L);
		}
	}
	
	
	void removeHearts(Player p,int i)
	{
		new BukkitRunnable()
		{

			@Override
			public void run() 
			{
				p.setMaxHealth(20.0D);
				p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
			}
			
		}.runTaskLaterAsynchronously(AbilityHeads.getInstance(), i * 20);
	}
	
}
