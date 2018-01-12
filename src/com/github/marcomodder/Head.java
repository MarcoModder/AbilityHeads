package com.github.marcomodder;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;


//This is an open-source utility class so i can use it

/**
 * Head utility class
 * 
 * @author JuiciFruit
 */
public class Head {

	private String headName;
	private String playerName;
	private short data;
	private boolean url;

	/**
	 * @param headName user friendly name of head
	 * @param playerName player name for head
	 */
	public Head(String headName, String playerName) {
		this.headName = headName;
		this.playerName = playerName;
		this.data = (short) 3;
		this.url = false;
	}

	/**
	 * @param headName user friendly name of head
	 * @param data data value for head
	 */
	public Head(String headName, int data) {
		this.headName = headName;
		this.playerName = null;
		this.data = (short) data;
		this.url = false;
	}

	/**
	 * @return the user friendly name of the head
	 */
	public String getHeadName() {
		return headName;
	}

	/**
	 * @return the player name for the head, or null if is not a player head
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @return the head item data value
	 */
	public short getData() {
		return data;
	}

	public boolean isURLHead() {
		return url;
	}

	/**
	 * @return an itemstack representing the head
	 */
	public ItemStack getHead() {
			ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, data);
			ItemMeta meta = head.getItemMeta();
			if (getPlayerName() != null) {
				SkullMeta skullMeta = (SkullMeta) meta;
				skullMeta.setOwner(getPlayerName());
				meta = skullMeta;
			}
			meta.setDisplayName(ChatColor.WHITE + getHeadName());
			head.setItemMeta(meta);
			return head;
	}

	/**
	 * @param headName user friendly name of head
	 * @param url URL of the head texture (64x16)
	 */
	public static Head getURLHead(String headName, String url) {
		Head head = new Head(headName, url);
		head.url = true;
		return head;
	}

	/**
	 * Sign post style
	 */
	public static class Sign {
		public static final Head EXCLAMATION_MARK = new Head("Exclamation Mark", "MHF_Exclamation");
		public static final Head QUESTION_MARK = new Head("Question Mark", "MHF_Question");

		/**
		 * Directional arrows
		 */
		public static class Arrow {
			public static final Head UP = new Head("Up Arrow", "MHF_ArrowUp");
			public static final Head DOWN = new Head("Down Arrow", "MHF_ArrowDown");
			public static final Head LEFT = new Head("Left Arrow", "MHF_ArrowLeft");
			public static final Head RIGHT = new Head("Right Arrow", "MHF_ArrowRight");
		}
	}

	/**
	 * Heads of the mobs from Minecraft
	 */
	public static class Mob {
		public static final Head PIG_ZOMBIE = new Head("Zombie Pigman", "MHF_PigZombie");
		public static final Head SPIDER = new Head("Spider", "MHF_Spider");
		public static final Head CAVE_SPIDER = new Head("Cave Spider", "MHF_CaveSpider");
		public static final Head ENDERMAN = new Head("Enderman", "MHF_Enderman");
		public static final Head GHAST = new Head("Ghast", "MHF_Ghast");
		public static final Head BLAZE = new Head("Blaze", "MHF_Blaze");
		public static final Head MAGMA_CUBE = new Head("Magma Cube", "MHF_LavaSlime");
		public static final Head WITHER = new Head("Wither", "MHF_Wither");
		public static final Head IRON_GOLEM = new Head("Iron Golem", "MHF_Golem");
		public static final Head VILLAGER = new Head("Villager", "MHF_Villager");
		public static final Head COW = new Head("Cow", "MHF_Cow");
		public static final Head MUSHROOM_COW = new Head("Mooshroom", "MHF_MushroomCow");
		public static final Head SHEEP = new Head("Sheep", "MHF_Sheep");
		public static final Head CHICKEN = new Head("Chicken", "MHF_Chicken");
		public static final Head SQUID = new Head("Squid", "MHF_Squid");
		public static final Head PIG = new Head("Pig", "MHF_Pig");
		public static final Head OCELOT = new Head("Ocelot", "MHF_Ocelot");
		public static final Head SLIME = new Head("Slime", "MHF_Slime");
		public static final Head GUARDIAN = new Head("Guardian", "Creepypig7");
		public static final Head SKELETON = new Head("Skeleton", "MHF_Skeleton");
		public static final Head WITHER_SKELETON = new Head("Wither Skeleton", 1);
		public static final Head ZOMBIE = new Head("Zombie", "MHF_Zombie");
		public static final Head STEVE = new Head("Steve", "Steve");
		public static final Head CREEPER = new Head("Creeper", "Creeper");

		/**
		 * Attempts to find a head for the given entity type, will return null if the is no match
		 */
		public static Head getFromType(EntityType type) {
			switch (type) {
			case GIANT:
				return ZOMBIE;
			default:
				break;
			}

			for (Field field : Mob.class.getDeclaredFields()) {
				EntityType et = EntityType.valueOf(field.getName());
				if (et != null && et == type) {
					if (field.getType().equals(Head.class)) {
						try {
							return (Head) field.get(null);
						} catch (Exception e) {
						}
					}
				}
			}

			return null;
		}
	}

	/**
	 * Miniature versions of Minecraft blocks
	 */
	public static class Block {
		public static final Head APPLE = new Head("Apple", "MHF_Apple");
		public static final Head CAKE = new Head("Cake", "MHF_Cake");
		public static final Head MELON = new Head("Melon", "MHF_Melon");
		public static final Head PUMPKIN_NO_FACE = new Head("Pumpkin (No Face)", "MHF_Pumpkin");
		public static final Head CACTUS = new Head("Cactus", "MHF_Cactus");
		public static final Head TNT = new Head("TNT", "MHF_TNT2");
		public static final Head CHEST = new Head("Chest", "MHF_Chest");
		public static final Head STONE = new Head("Stone", "Aced");
		public static final Head COBBLESTONE = new Head("Cobblestone", "_Rience");
		public static final Head STONE_BRICK = new Head("Stone Brick", "Cakers");
		public static final Head OBSIDIAN = new Head("Obsidian", "loiwiol");
		public static final Head ICE = new Head("Ice", "icytouch");
		public static final Head DROPPER = new Head("Dropper", "xXSiiKSiiNSXx");
		public static final Head DISPENSER = new Head("Dispenser", "scemm");
		public static final Head DISPENSER_UP = new Head("Dispenser (Facing Up)", "puy33321");
		public static final Head JUKEBOX = new Head("Jukebox", "C418");
		public static final Head PUMPKIN = new Head("Pumpkin", "Koebasti");
		public static final Head ENDER_CHEST = new Head("Ender Chest", "_Brennian");
		public static final Head LAVA = new Head("Lava", "Spe");
		public static final Head WATER = new Head("Water", "emack0714");
		public static final Head SAND = new Head("Sand", "Praxis8");
		public static final Head BOOKSHELF = new Head("Bookshelf", "BowAimbot");
		public static final Head NETHERRACK = new Head("Netherrack", "Numba_one_Stunna");
		public static final Head SOUL_SAND = new Head("Soul Sand", "Njham");
		public static final Head NETHER_BRICK = new Head("Nether Brick", "ingo879");
		public static final Head BEDROCK = new Head("Bedrock", "dylansams");
		public static final Head DIRT = new Head("Dirt", "ChazOfftopic");
		public static final Head MYCELIUM = new Head("Sand", "b4url82");
		public static final Head GRASS = new Head("Grass", "107295");
		public static final Head GRASS_SNOW = new Head("Grass (Snow)", "Creeper999");
		public static final Head HAY_BALE = new Head("Hay Bale", "Bendablob");
		public static final Head QUARTZ_PILLAR = new Head("Quartz Pillar", "HesphaestusHD");
		public static final Head MISSING_TEXTURE = new Head("Missing Texture", "ddrl46");

		/**
		 * Wood blocks
		 */
		public static class Wood {
			public static final Head LOG_OAK = new Head("Oak Log", "MightyMega");
			public static final Head LOG_OAK6 = new Head("Oak Log (6 Sided)", "MHF_OakLog");
			public static final Head LOG_JUNGLE6 = new Head("Jungle Log (6 Sided)", "Trish13");
			public static final Head PLANK_OAK = new Head("Oak Wood Plank", "terryxu");
		}

		/**
		 * Ore blocks
		 */
		public static class Ore {
			public static final Head EMERALD = new Head("Emerald Ore", "Tereneckla");
			public static final Head DIAMOND = new Head("Diamond Ore", "acissejxd");
			public static final Head GOLD = new Head("Gold Ore", "Das_Ingrid");
			public static final Head REDSTONE = new Head("Redstone Ore", "annayirb");
			public static final Head IRON = new Head("Iron Ore", "EntityLag");
			public static final Head QUARTZ = new Head("Quartz Ore", "jassoccx2");
		}

		/**
		 * Ore storage blocks
		 */
		public static class StorageBlock {
			public static final Head EMERALD = new Head("Emerald Block", "ptktnt");
			public static final Head DIAMOND = new Head("Diamond Block", "AllTheDiamond");
			public static final Head GOLD = new Head("Gold Block", "teachdaire");
			public static final Head REDSTONE = new Head("Redstone Block", "Maccys_Test_Acc");
			public static final Head IRON = new Head("Iron Block", "metalhedd");
		}
	}

	/**
	 * Decoration
	 */
	public static class Decor {
		public static final Head COMPUTER = new Head("Computer", "Alistor");
		public static final Head COMPUTER2 = new Head("Computer #2", "T_h_rust");
		public static final Head CAMERA = new Head("Camera", "FHG_Cam");
		public static final Head PRESENT_RED = new Head("Present (Red)", "CruXXx");
		public static final Head PRESENT_GREEN = new Head("Present (Green)", "SeerPotion");
		public static final Head GLOBE = new Head("Globe", "Kevos");
		public static final Head GAME_CUBE = new Head("Game Cube", "Eien15");
		public static final Head TOILET_PAPER = new Head("Toilet Paper", "OrtyBortorty");
		public static final Head TOASTER = new Head("Toaster", "samstine11");
	}

	/**
	 * Miscellaneous
	 */
	public static class Misc {
		public static final Head DIE = new Head("Die", "SquareHD");
		public static final Head RUBIKS_CUBE = new Head("Rubik's Cube", "ZiGmUnDo");
		public static final Head SPACE_HELMET = new Head("Space Helmet", "ByVoltz");
		public static final Head COMPANION_CUBE = new Head("Companion Cube", "Mercury777");
		public static final Head SENTRY_TURRET = new Head("Sentry Turret", "Claush");
		public static final Head BUCKET_WATER = new Head("Water Bucket", "TagStarDude");
		public static final Head BUCKET_EMPTY = new Head("Empty Bucket", "Attacker_99");
	}
}