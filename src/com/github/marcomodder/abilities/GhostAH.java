package com.github.marcomodder.abilities;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.marcomodder.AbilityHeads;
import com.github.marcomodder.Utils;

public class GhostAH extends Enchantment implements Listener {
	
	public GhostAH(int id) {
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
		return "Ghost";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}
	
	@Override
	public int getId()
	{
		return 106;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		if(!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			return;
		}
		if(p.getItemInHand().containsEnchantment(this))
		{
			if(!AbilityHeads.getInstance().ghostCooldown.containsKey(p))
			{
				AbilityHeads.getInstance().ghostCooldown.put(p, AbilityHeads.getInstance().getConfig().getInt("Abilities.Ghost.Cooldown"));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,(AbilityHeads.getInstance().getConfig().getInt("Abilities.Ghost.SpeedDuration") * 20),2));
				for(Player pz : Bukkit.getOnlinePlayers())
				{
					pz.hidePlayer(p);
				}
				new BukkitRunnable()
				{

					@Override
					public void run() 
					{
						for(Player pz : Bukkit.getOnlinePlayers())
						{
							pz.showPlayer(p);
						}
					}				
					
				}.runTaskLater(AbilityHeads.getInstance(), AbilityHeads.getInstance().getConfig().getInt("Abilities.Ghost.InvisibleDuration") * 20);
				String first = AbilityHeads.getInstance().getConfig().getString("Messages.Activate");
				String second = first.replace("{ABILITY}", this.getName());
				p.sendMessage(Utils.color(second));
				String abilityName = this.getName();
				new BukkitRunnable()
				{

					@Override
					public void run() 
					{
						AbilityHeads.getInstance().ghostCooldown.put(p, AbilityHeads.getInstance().ghostCooldown.get(p) - 1);
						if(AbilityHeads.getInstance().ghostCooldown.get(p) == 0)
						{
							AbilityHeads.getInstance().ghostCooldown.remove(p);
							this.cancel();
							String first = AbilityHeads.getInstance().getConfig().getString("Messages.CooldownFinished");
							String second = first.replace("{ABILITY}", abilityName);
							p.sendMessage(Utils.color(second));
						}
					}
					
				}.runTaskTimerAsynchronously(AbilityHeads.getInstance(), 20L, 20L);
			}
			else
			{
				int i = AbilityHeads.getInstance().ghostCooldown.get(p);
				String first = AbilityHeads.getInstance().getConfig().getString("Messages.HaveToWait");
				String second = first.replace("{TIME}", String.valueOf(i));
				p.sendMessage(Utils.color(second));
			}
		}
	}
}
