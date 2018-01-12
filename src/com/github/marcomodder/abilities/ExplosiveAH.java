package com.github.marcomodder.abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.marcomodder.AbilityHeads;
import com.github.marcomodder.Utils;

public class ExplosiveAH extends Enchantment implements Listener {
	
	public ExplosiveAH(int id) {
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
		return "Explosive";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}
	
	@Override
	public int getId()
	{
		return 108;
	}
	
	
	@EventHandler
	public void onInteract(EntityDamageEvent e)
	{
		if(!(e.getEntity() instanceof Player))
		{
			return;
		}
		Player p = (Player) e.getEntity();
		if(AbilityHeads.getInstance().explosionMap.containsKey(p))
		{
			if(e.getCause() == DamageCause.BLOCK_EXPLOSION)
			{
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		if(!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			return;
		}
		if(!(e.getClickedBlock() != null))
		{
			return;
		}
		if(!(e.getClickedBlock().getType() != Material.AIR))
		{
			return;
		}
		if(p.getItemInHand().containsEnchantment(this))
		{
			if(!AbilityHeads.getInstance().explosionCooldown.containsKey(p))
			{
				if(!AbilityHeads.getInstance().explosionMap.containsKey(p))
				{
					AbilityHeads.getInstance().explosionMap.put(p, true);
					p.getWorld().createExplosion(p.getLocation(), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX() + 1,p.getLocation().getBlockY(),p.getLocation().getBlockZ()), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX() - 1,p.getLocation().getBlockY(),p.getLocation().getBlockZ()), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ() + 1), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ() - 1), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX() + 1,p.getLocation().getBlockY(),p.getLocation().getBlockZ() + 1), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX() + 1,p.getLocation().getBlockY(),p.getLocation().getBlockZ() - 1), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX() - 1,p.getLocation().getBlockY(),p.getLocation().getBlockZ() + 1), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX() - 1,p.getLocation().getBlockY(),p.getLocation().getBlockZ() - 1), 3,false);
					String first = AbilityHeads.getInstance().getConfig().getString("Messages.Activate");
					String second = first.replace("{ABILITY}", this.getName());
					p.sendMessage(Utils.color(second));
					String s = getName();
					new BukkitRunnable()
					{

						@Override
						public void run() 
						{
							AbilityHeads.getInstance().explosionMap.remove(p);
							AbilityHeads.getInstance().explosionCooldown.put(p, AbilityHeads.getInstance().getConfig().getInt("Abilities.Explosive.Cooldown"));
							runCooldown(p,s);
						}
						
					}.runTaskLater(AbilityHeads.getInstance(), AbilityHeads.getInstance().getConfig().getInt("Abilities.Explosive.Duration") * 20);
				}
				else
				{
					p.getWorld().createExplosion(p.getLocation(), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX() + 1,p.getLocation().getBlockY(),p.getLocation().getBlockZ()), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX() - 1,p.getLocation().getBlockY(),p.getLocation().getBlockZ()), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ() + 1), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX(),p.getLocation().getBlockY(),p.getLocation().getBlockZ() - 1), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX() + 1,p.getLocation().getBlockY(),p.getLocation().getBlockZ() + 1), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX() + 1,p.getLocation().getBlockY(),p.getLocation().getBlockZ() - 1), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX() - 1,p.getLocation().getBlockY(),p.getLocation().getBlockZ() + 1), 3,false);
					p.getWorld().createExplosion(new Location(p.getWorld(),p.getLocation().getBlockX() - 1,p.getLocation().getBlockY(),p.getLocation().getBlockZ() - 1), 3,false);
					return;
				}
			}
			else
			{
				int i = AbilityHeads.getInstance().explosionCooldown.get(p);
				String first = AbilityHeads.getInstance().getConfig().getString("Messages.HaveToWait");
				String second = first.replace("{TIME}", String.valueOf(i));
				p.sendMessage(Utils.color(second));
			}
		}
	}
	
	
	void runCooldown(Player p,String abilityName)
	{
		new BukkitRunnable()
		{

			@Override
			public void run() 
			{
				AbilityHeads.getInstance().explosionCooldown.put(p, AbilityHeads.getInstance().explosionCooldown.get(p) - 1);
				if(AbilityHeads.getInstance().explosionCooldown.get(p) == 0)
				{
					AbilityHeads.getInstance().explosionCooldown.remove(p);
					this.cancel();
					String first = AbilityHeads.getInstance().getConfig().getString("Messages.CooldownFinished");
					String second = first.replace("{ABILITY}", abilityName);
					p.sendMessage(Utils.color(second));
				}
			}
			
		}.runTaskTimerAsynchronously(AbilityHeads.getInstance(), 20L, 20L);
	}
}
