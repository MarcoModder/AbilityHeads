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

import com.github.marcomodder.AbilityHeads;
import com.github.marcomodder.Utils;

public class ExhaustAH extends Enchantment implements Listener {
	
	public ExhaustAH(int id) {
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
		return "Exhaust";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}
	
	@Override
	public int getId()
	{
		return 104;
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
			for(Player pz :Utils.getNearbyPlayers(p))
			{
				pz.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,(AbilityHeads.getInstance().getConfig().getInt("Abilities.Exhaust.SlownessDuration") * 20),1));
				pz.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,(AbilityHeads.getInstance().getConfig().getInt("Abilities.Exhaust.WeaknessDuration") * 20),1));
			}
			String first = AbilityHeads.getInstance().getConfig().getString("Messages.Activate");
			String second = first.replace("{ABILITY}", this.getName());
			p.sendMessage(Utils.color(second));		
			p.getInventory().remove(p.getItemInHand());
		}
	}
}
