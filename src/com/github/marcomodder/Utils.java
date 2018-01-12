package com.github.marcomodder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
	
	
	public static String color(String s)
	{
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	
	public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
         if (0 <= rotation && rotation < 22.5) {
            return "W";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "NW";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "N";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "NE";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "E";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "SE";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "S";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "SW";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "N";
        } else {
            return null;
        }
    }
	
	
	public static ArrayList<Player> getNearbyPlayers(Player pl){
        ArrayList<Player> nearby = new ArrayList<Player>();
        double range = AbilityHeads.getInstance().getConfig().getInt("Abilities.Exhaust.BlockRadius");
        for (Entity e : pl.getNearbyEntities(range, range, range)){
            if (e instanceof Player){
                nearby.add((Player) e);
            }
        }
        return nearby;
    }
	
	
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	
	
	public static ItemStack getBeastItem(int i)
	{
		ItemStack it = Head.Mob.MAGMA_CUBE.getHead();
		it.setAmount(i);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(Utils.color(AbilityHeads.getInstance().getConfig().getString("Items.Beast.Name")));
		List<String> temp = new ArrayList<>();
		for(String s : AbilityHeads.getInstance().getConfig().getStringList("Items.Beast.Lore"))
		{
			temp.add(color(s));
		}
		im.setLore(temp);
		it.setItemMeta(im);
		it.addUnsafeEnchantment(AbilityHeads.getInstance().beastAH, 1);
		return it;
	}
	
	public static ItemStack getHealItem(int i)
	{
		ItemStack it = Head.Mob.PIG.getHead();
		it.setAmount(i);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(Utils.color(AbilityHeads.getInstance().getConfig().getString("Items.Heal.Name")));
		List<String> temp = new ArrayList<>();
		for(String s : AbilityHeads.getInstance().getConfig().getStringList("Items.Heal.Lore"))
		{
			temp.add(color(s));
		}
		im.setLore(temp);
		it.setItemMeta(im);
		it.addUnsafeEnchantment(AbilityHeads.getInstance().healAH, 1);
		return it;
	}

	public static ItemStack getFlashItem(int i)
	{
		ItemStack it = Head.Mob.ENDERMAN.getHead();
		it.setAmount(i);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(Utils.color(AbilityHeads.getInstance().getConfig().getString("Items.Flash.Name")));
		List<String> temp = new ArrayList<>();
		for(String s : AbilityHeads.getInstance().getConfig().getStringList("Items.Flash.Lore"))
		{
			temp.add(color(s));
		}
		im.setLore(temp);
		it.setItemMeta(im);
		it.addUnsafeEnchantment(AbilityHeads.getInstance().flashAH, 1);
		return it;
	}
	
	public static ItemStack getExhaustItem(int i)
	{
		ItemStack it = Head.Mob.WITHER_SKELETON.getHead();
		it.setAmount(i);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(Utils.color(AbilityHeads.getInstance().getConfig().getString("Items.Exhaust.Name")));
		List<String> temp = new ArrayList<>();
		for(String s : AbilityHeads.getInstance().getConfig().getStringList("Items.Exhaust.Lore"))
		{
			temp.add(color(s));
		}
		im.setLore(temp);
		it.setItemMeta(im);
		it.addUnsafeEnchantment(AbilityHeads.getInstance().exhaustAH, 1);
		return it;
	}
	
	public static ItemStack getIgniteItem(int i)
	{
		ItemStack it = Head.Mob.BLAZE.getHead();
		it.setAmount(i);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(Utils.color(AbilityHeads.getInstance().getConfig().getString("Items.Ignite.Name")));
		List<String> temp = new ArrayList<>();
		for(String s : AbilityHeads.getInstance().getConfig().getStringList("Items.Ignite.Lore"))
		{
			temp.add(color(s));
		}
		im.setLore(temp);
		it.setItemMeta(im);
		it.addUnsafeEnchantment(AbilityHeads.getInstance().igniteAH, 1);
		return it;
	}
	
	public static ItemStack getGhostItem(int i)
	{
		ItemStack it = Head.Mob.GHAST.getHead();
		it.setAmount(i);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(Utils.color(AbilityHeads.getInstance().getConfig().getString("Items.Ghost.Name")));
		List<String> temp = new ArrayList<>();
		for(String s : AbilityHeads.getInstance().getConfig().getStringList("Items.Ghost.Lore"))
		{
			temp.add(color(s));
		}
		im.setLore(temp);
		it.setItemMeta(im);
		it.addUnsafeEnchantment(AbilityHeads.getInstance().ghostAH, 1);
		return it;
	}
	
	public static ItemStack getBarrierItem(int i)
	{
		ItemStack it = Head.Mob.IRON_GOLEM.getHead();
		it.setAmount(i);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(Utils.color(AbilityHeads.getInstance().getConfig().getString("Items.Barrier.Name")));
		List<String> temp = new ArrayList<>();
		for(String s : AbilityHeads.getInstance().getConfig().getStringList("Items.Barrier.Lore"))
		{
			temp.add(color(s));
		}
		im.setLore(temp);
		it.setItemMeta(im);
		it.addUnsafeEnchantment(AbilityHeads.getInstance().barrierAH, 1);
		return it;
	}
	
	public static ItemStack getExplosiveItem(int i)
	{
		ItemStack it = Head.Mob.CREEPER.getHead();
		it.setAmount(i);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(Utils.color(AbilityHeads.getInstance().getConfig().getString("Items.Explosive.Name")));
		List<String> temp = new ArrayList<>();
		for(String s : AbilityHeads.getInstance().getConfig().getStringList("Items.Explosive.Lore"))
		{
			temp.add(color(s));
		}
		im.setLore(temp);
		it.setItemMeta(im);
		it.addUnsafeEnchantment(AbilityHeads.getInstance().expAH, 1);
		return it;
	}
	
	public static ItemStack getDeadmanItem(int i)
	{
		ItemStack it = Head.Mob.ZOMBIE.getHead();
		it.setAmount(i);
		ItemMeta im = it.getItemMeta();
		im.setDisplayName(Utils.color(AbilityHeads.getInstance().getConfig().getString("Items.Deadman.Name")));
		List<String> temp = new ArrayList<>();
		for(String s : AbilityHeads.getInstance().getConfig().getStringList("Items.Deadman.Lore"))
		{
			temp.add(color(s));
		}
		im.setLore(temp);
		it.setItemMeta(im);
		it.addUnsafeEnchantment(AbilityHeads.getInstance().dmAH, 1);
		return it;
	}
	
	
	
	public static ItemStack randomAbility()
	{
		int a = AbilityHeads.getInstance().getConfig().getInt("Signs.Abilities.Beast.Percentage");
		int b = AbilityHeads.getInstance().getConfig().getInt("Signs.Abilities.Heal.Percentage");
		int c = AbilityHeads.getInstance().getConfig().getInt("Signs.Abilities.Flash.Percentage");
		int d = AbilityHeads.getInstance().getConfig().getInt("Signs.Abilities.Exhaust.Percentage");
		int e = AbilityHeads.getInstance().getConfig().getInt("Signs.Abilities.Ignite.Percentage");
		int f = AbilityHeads.getInstance().getConfig().getInt("Signs.Abilities.Ghost.Percentage");
		int g = AbilityHeads.getInstance().getConfig().getInt("Signs.Abilities.Barrier.Percentage");
		int h = AbilityHeads.getInstance().getConfig().getInt("Signs.Abilities.Explosive.Percentage");
		int i = AbilityHeads.getInstance().getConfig().getInt("Signs.Abilities.Deadman.Percentage");
		int size = (a+b+c+d+e+f+g+h+i);
		String array[] = new String[size];
		String beast = "beast";
		String heal = "heal";
		String flash = "flash";
		String exhaust = "exhaust";
		String ignite = "ignite";
		String ghost = "ghost";
		String barrier = "barrier";
		String explosive = "explosive";
		String deadman = "deadman";
		int sum = 0;
		sum = sum + a;
		Arrays.fill(array, 0, sum,beast);
		Arrays.fill(array,sum,sum+b,heal);
		Arrays.fill(array,sum+b,sum+b+c,flash);
		Arrays.fill(array,sum+b+c,sum+b+c+d,exhaust);
		Arrays.fill(array,sum+b+c+d,sum+b+c+d+e,ignite);
		Arrays.fill(array,sum+b+c+d+e,sum+b+c+d+e+f,ghost);
		Arrays.fill(array,sum+b+c+d+e+f,sum+b+c+d+e+f+g,barrier);
		Arrays.fill(array,sum+b+c+d+e+f+g,sum+b+c+d+e+f+g+h,explosive);
		Arrays.fill(array,sum+b+c+d+e+f+g+h,sum+b+c+d+e+f+g+h+i,deadman);
		Random rand = new Random();
		String pick = array[rand.nextInt(size-1)];
		switch(pick)
		{
			case "beast":
				return Utils.getBeastItem(1);
			case "heal":
				return Utils.getHealItem(1);
			case "flash":
				return Utils.getFlashItem(1);
			case "exhaust":
				return Utils.getExhaustItem(1);
			case "ignite":
				return Utils.getIgniteItem(1);
			case "ghost":
				return Utils.getGhostItem(1);
			case "barrier":
				return Utils.getBarrierItem(1);
			case "explosive":
				return Utils.getExplosiveItem(1);
			case "deadman":
				return Utils.getDeadmanItem(1);
			default:
				return null;
		}
	}
	

}
