package com.github.marcomodder.storageutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.github.marcomodder.AbilityHeads;

public class SignStorage {
	
	private final static List<AbilitySign> PROTECTED_AbilitySignS = new ArrayList<AbilitySign>();
	  
	  public void add(AbilitySign s)
	  {
	    if (!PROTECTED_AbilitySignS.contains(s)) {
	      PROTECTED_AbilitySignS.add(s);
	    }
	  }
	  
	  public void remove(AbilitySign s)
	  {
	    PROTECTED_AbilitySignS.remove(s);
	  }
	  
	  public static List<AbilitySign> getSigns()
	  {
		  return PROTECTED_AbilitySignS;
	  }
	  
	  public void saveAbilitySigns()
	    throws IOException
	  {
	    File file = new File(AbilityHeads.getSignDataFolder() + File.separator + "AbilitySigns.flat");
	    if (PROTECTED_AbilitySignS.isEmpty()) {
	      return;
	    }
	    FileWriter fstream = new FileWriter(file);
	    BufferedWriter out = new BufferedWriter(fstream);
	    for (AbilitySign AbilitySign : PROTECTED_AbilitySignS)
	    {
	      out.write("[AbilitySign Data]");
	      out.newLine();
	      String s = AbilitySign.getAbility();
	      out.write("AbilityName:" + s);
	      out.newLine();
	      out.write("WorldLoc:" + AbilitySign.getLocation().getWorld().getName());
	      out.newLine();
	      out.write("XLoc:" + AbilitySign.getLocation().getBlockX());
	      out.newLine();
	      out.write("YLoc:" + AbilitySign.getLocation().getBlockY());
	      out.newLine();
	      out.write("ZLoc:" + AbilitySign.getLocation().getBlockZ());
	      out.newLine();
	      out.write("Price:" + AbilitySign.getPrice());
	      out.newLine();
	      out.write("MoneyOrExp:" + AbilitySign.getMoneyOrExpBit());
	      out.newLine();
	    }
	    out.close();
	  }
	  
	  public void loadHolo()
	    throws IOException
	  {
	    File file = new File(AbilityHeads.getSignDataFolder() + File.separator + "AbilitySigns.flat");
	    if (!file.exists()) {
	      return;
	    }
	    FileReader fstream = new FileReader(file);
	    BufferedReader reader = new BufferedReader(fstream);
	    String line = "";
	    while ((line = reader.readLine()) != null) {
	      if (line.startsWith("[AbilitySign Data]"))
	      {
	        String name = reader.readLine().split(":")[1];
	        String world = reader.readLine().split(":")[1];
	        int x = Integer.parseInt(reader.readLine().split(":")[1]);
	        int y = Integer.parseInt(reader.readLine().split(":")[1]);
	        int z = Integer.parseInt(reader.readLine().split(":")[1]);
	        int cost = Integer.parseInt(reader.readLine().split(":")[1]);
	        int moe = Integer.parseInt(reader.readLine().split(":")[1]);
	        Location loc = new Location(Bukkit.getWorld(world),x,y,z);
	        AbilitySign d = new AbilitySign(name,cost,moe,loc);
	        add(d);
	      }
	    }
	    reader.close();
	  }
	  
	  
	  public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		    for (Entry<T, E> entry : map.entrySet()) {
		        if (Objects.equals(value, entry.getValue())) {
		            return entry.getKey();
		        }
		    }
		    return null;
		}

}