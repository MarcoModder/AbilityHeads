package com.github.marcomodder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.marcomodder.abilities.BarrierAH;
import com.github.marcomodder.abilities.BeastAH;
import com.github.marcomodder.abilities.DeadmanAH;
import com.github.marcomodder.abilities.ExhaustAH;
import com.github.marcomodder.abilities.ExplosiveAH;
import com.github.marcomodder.abilities.FlashAH;
import com.github.marcomodder.abilities.GhostAH;
import com.github.marcomodder.abilities.HealAH;
import com.github.marcomodder.abilities.IgniteAH;
import com.github.marcomodder.storageutils.SignStorage;

public class AbilityHeads extends JavaPlugin {
	
	
	
	public static AbilityHeads instance;
	
	//Abilities
	public BeastAH beastAH = new BeastAH(101);
	public HealAH healAH = new HealAH(102);
	public FlashAH flashAH = new FlashAH(103);
	public ExhaustAH exhaustAH = new ExhaustAH(104);
	public IgniteAH igniteAH = new IgniteAH(105);
	public GhostAH ghostAH = new GhostAH(106);
	public BarrierAH barrierAH = new BarrierAH(107);
	public ExplosiveAH expAH = new ExplosiveAH(108);
	public DeadmanAH dmAH = new DeadmanAH(109);
	public List<Enchantment> enchList = new ArrayList<>();
	
	//Cooldown Maps
	public Map<Player,Integer> beastCooldown = new HashMap<>();
	public Map<Player,Integer> healCooldown = new HashMap<>();
	public Map<Player,Integer> flashCooldown = new HashMap<>();
	public Map<Player,Integer> igniteCooldown = new HashMap<>();
	public Map<Player,Integer> ghostCooldown = new HashMap<>();
	public Map<Player,Integer> barrierCooldown = new HashMap<>();
	public Map<Player,Integer> explosionCooldown = new HashMap<>();
	public Map<Player,Boolean> explosionMap = new HashMap<>();
	public Map<Player,Integer> deadmanDuration = new HashMap<>();
	public Map<Player,Boolean> deadmanMap = new HashMap<>();
	
	//File
	private static File SIGN_DATA_FOLDER;
	private static SignStorage storage;
	
	@Override
	public void onEnable()
	{
		instance = this;
		System.out.println("[AbilityHeads] Loading Abilities...");
		config();
		commands();
		events();
		enchants();
		SIGN_DATA_FOLDER = new File(getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator);
	    storage = new SignStorage();
	    files();
		System.out.println("[AbilityHeads] Succesfully loaded AbilityHeads!");
	}
	

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void onDisable() 
	{
		instance = null;
		System.out.println("[AbilityHeads]Shutting down...");
		try
	    {
	      storage.saveAbilitySigns();
	    }
	    catch (IOException localIOException)
	    {
	      System.out.println("[SkyHash] There was an error saving sign!");
	    }
		try {
			Field byIdField = Enchantment.class.getDeclaredField("byId");
			Field byNameField = Enchantment.class.getDeclaredField("byName");

			byIdField.setAccessible(true);
			byNameField.setAccessible(true);

			Map<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
			Map<Integer, Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);
			
			for(Enchantment e : enchList)
			{
				if(byId.containsKey(e.getId()))
				{
					byId.remove(e.getId());
				}
				if (byName.containsKey(e.getName())) 
				{
					byName.remove(e.getName());
				}
			}
			
		} catch (Exception ignored) {
		}
	}
	
	public static AbilityHeads getInstance()
	{
		return instance;
	}
	
	public void commands()
	{
		getCommand("ability").setExecutor(new AbilityCMD());
	}
	
	public void config()
	{
		String lore[] = {"Default","Lore"};
		//Messages
		getConfig().addDefault("Messages.Activate", "&aYou have activated {ABILITY}!");
		getConfig().addDefault("Messages.HaveToWait", "&aYou have to wait {TIME} seconds!");
		getConfig().addDefault("Messages.CooldownFinished", "&cThe cooldown of {ABILITY} is off!");
		getConfig().addDefault("Messages.HeadRecieved", "&aYou recieved an ability head from {PLAYER}");
		//Abilities
		getConfig().addDefault("Abilities.Beast.Cooldown", 180);
		getConfig().addDefault("Abilities.Beast.StrenghtDuration", 3);
		getConfig().addDefault("Abilities.Heal.Cooldown", 180);
		getConfig().addDefault("Abilities.Heal.HeartsToHeal", 5);
		getConfig().addDefault("Abilities.Flash.Cooldown", 240);
		getConfig().addDefault("Abilities.Flash.BlockRange", 20);
		getConfig().addDefault("Abilities.Exhaust.BlockRadius", 5);
		getConfig().addDefault("Abilities.Exhaust.WeaknessDuration", 10);
		getConfig().addDefault("Abilities.Exhaust.SlownessDuration", 10);
		getConfig().addDefault("Abilities.Ignite.Cooldown", 180);
		getConfig().addDefault("Abilities.Ignite.OnFireDuration", 60);
		getConfig().addDefault("Abilities.Ghost.Cooldown", 180);
		getConfig().addDefault("Abilities.Ghost.SpeedDuration", 10);
		getConfig().addDefault("Abilities.Ghost.InvisibleDuration", 5);
		getConfig().addDefault("Abilities.Barrier.Cooldown", 180);
		getConfig().addDefault("Abilities.Barrier.Duration", 5);
		getConfig().addDefault("Abilities.Explosive.Cooldown", 180);
		getConfig().addDefault("Abilities.Explosive.Duration", 10);
		getConfig().addDefault("Abilities.Deadman.Duration", 120);
		//Items
		getConfig().addDefault("Items.Beast.Name", "&aBeast");
		getConfig().addDefault("Items.Beast.Lore", Arrays.asList(lore));
		getConfig().addDefault("Items.Heal.Name", "&aHeal");
		getConfig().addDefault("Items.Heal.Lore", Arrays.asList(lore));
		getConfig().addDefault("Items.Flash.Name", "&aFlash");
		getConfig().addDefault("Items.Flash.Lore", Arrays.asList(lore));
		getConfig().addDefault("Items.Exhaust.Name", "&aExhaust");
		getConfig().addDefault("Items.Exhaust.Lore", Arrays.asList(lore));
		getConfig().addDefault("Items.Ignite.Name", "&aIgnite");
		getConfig().addDefault("Items.Ignite.Lore", Arrays.asList(lore));
		getConfig().addDefault("Items.Ghost.Name", "&aGhost");
		getConfig().addDefault("Items.Ghost.Lore", Arrays.asList(lore));
		getConfig().addDefault("Items.Barrier.Name", "&aBarrier");
		getConfig().addDefault("Items.Barrier.Lore", Arrays.asList(lore));
		getConfig().addDefault("Items.Explosive.Name", "&aExplosive");
		getConfig().addDefault("Items.Explosive.Lore", Arrays.asList(lore));
		getConfig().addDefault("Items.Deadman.Name", "&aDeadman");
		getConfig().addDefault("Items.Deadman.60SecondsMessage", "&a60 seconds remaining!");
		getConfig().addDefault("Items.Deadman.30SecondsMessage", "&a30 seconds remaining!");
		getConfig().addDefault("Items.Deadman.5SecondsMessage", "&a5 seconds remaining!");
		getConfig().addDefault("Items.Deadman.Lore", Arrays.asList(lore));
		//Signs
		getConfig().addDefault("Signs.Abilities.Beast.Percentage", 40);
		getConfig().addDefault("Signs.Abilities.Heal.Percentage", 20);
		getConfig().addDefault("Signs.Abilities.Flash.Percentage", 20);
		getConfig().addDefault("Signs.Abilities.Exhaust.Percentage", 10);
		getConfig().addDefault("Signs.Abilities.Ignite.Percentage", 2);
		getConfig().addDefault("Signs.Abilities.Ghost.Percentage", 2);
		getConfig().addDefault("Signs.Abilities.Barrier.Percentage", 2);
		getConfig().addDefault("Signs.Abilities.Explosive.Percentage", 2);
		getConfig().addDefault("Signs.Abilities.Deadman.Percentage", 2);
		getConfig().addDefault("Signs.PurchasedMessage", "&aYou won {ABILITY}!");
		getConfig().options().copyDefaults(true);
	    saveConfig();
	}
	
	public void events()
	{
		registerEvent(new BeastAH(101));
		registerEvent(new HealAH(102));
		registerEvent(new FlashAH(103));
		registerEvent(new ExhaustAH(104));
		registerEvent(new IgniteAH(105));
		registerEvent(new GhostAH(106));
		registerEvent(new BarrierAH(107));
		registerEvent(new ExplosiveAH(108));
		registerEvent(new DeadmanAH(109));
		registerEvent(new Listeners());
	}
	
	private void registerEvent(Listener listener)
	{
		Bukkit.getServer().getPluginManager().registerEvents(listener, this);
	}
	
	private void enchants()
	{	
		try 
		{
			try 
			{
				Field f = Enchantment.class.getDeclaredField("acceptingNew");
				f.setAccessible(true);
				f.set(null, true);
			} catch (Exception e) {
				e.printStackTrace();
			}

			try 
			{
				Enchantment.registerEnchantment(beastAH);
				Enchantment.registerEnchantment(healAH);
				Enchantment.registerEnchantment(flashAH);
				Enchantment.registerEnchantment(exhaustAH);
				Enchantment.registerEnchantment(igniteAH);
				Enchantment.registerEnchantment(ghostAH);
				Enchantment.registerEnchantment(barrierAH);
				Enchantment.registerEnchantment(expAH);
				Enchantment.registerEnchantment(dmAH);
				
				enchList.add(beastAH);
				enchList.add(healAH);
				enchList.add(flashAH);
				enchList.add(exhaustAH);
				enchList.add(igniteAH);
				enchList.add(ghostAH);
				enchList.add(barrierAH);
				enchList.add(expAH);
				enchList.add(dmAH);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static SignStorage getDungeonStorage()
	{
	    return storage;
	}
	  
	public static File getSignDataFolder()
	{
	    return SIGN_DATA_FOLDER;
	}
	
	public void files()
	{
		if (!SIGN_DATA_FOLDER.exists())
	    {
	      if (SIGN_DATA_FOLDER.mkdirs()) {
	        getLogger().info("[SkyHash] Created sign data folder.");
	      }
	    }
	    else {
	      try
	      {
	        storage.loadHolo();
	      }
	      catch (IOException localIOException) {}
	    }
	}

}
