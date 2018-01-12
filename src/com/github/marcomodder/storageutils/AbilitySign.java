package com.github.marcomodder.storageutils;

import org.bukkit.Location;

public class AbilitySign {
	
	private int price;
	private String ability;
	private int moneyOrExp;
	private Location loc;
	
	
	public AbilitySign(String a,int i,int b,Location l)
	{
		price = i;
		ability = a;
		moneyOrExp = b;
		loc = l;
	}
	
	public AbilitySign(int i,int b,Location l)
	{
		price = i;
		ability = "random";
		moneyOrExp = b;
		loc = l;
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public String getAbility()
	{
		return ability;
	}
	
	
	public int getMoneyOrExpBit()
	{
		return moneyOrExp;
	}
	
	public Location getLocation()
	{
		return loc;
	}

}
