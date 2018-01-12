package com.github.marcomodder.abilities;

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

public class BarrierAH extends Enchantment implements Listener {
	
	public BarrierAH(int id) {
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
		return "Barrier";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}
	
	@Override
	public int getId()
	{
		return 107;
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
			if(!AbilityHeads.getInstance().barrierCooldown.containsKey(p))
			{
				AbilityHeads.getInstance().barrierCooldown.put(p, AbilityHeads.getInstance().getConfig().getInt("Abilities.Barrier.Cooldown"));
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,(AbilityHeads.getInstance().getConfig().getInt("Abilities.Barrier.Duration") * 20),2));
				String first = AbilityHeads.getInstance().getConfig().getString("Messages.Activate");
				String second = first.replace("{ABILITY}", this.getName());
				p.sendMessage(Utils.color(second));
				String abilityName = this.getName();
				new BukkitRunnable()
				{

					@Override
					public void run() 
					{
						AbilityHeads.getInstance().barrierCooldown.put(p, AbilityHeads.getInstance().barrierCooldown.get(p) - 1);
						if(AbilityHeads.getInstance().barrierCooldown.get(p) == 0)
						{
							AbilityHeads.getInstance().barrierCooldown.remove(p);
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
				int i = AbilityHeads.getInstance().barrierCooldown.get(p);
				String first = AbilityHeads.getInstance().getConfig().getString("Messages.HaveToWait");
				String second = first.replace("{TIME}", String.valueOf(i));
				p.sendMessage(Utils.color(second));
			}
		}
	}
}
