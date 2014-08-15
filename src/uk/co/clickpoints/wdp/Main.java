package uk.co.clickpoints.wdp;


import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.naming.CompoundName;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Score;

import uk.co.clickpoints.wdpdep.*;

import com.google.common.base.CharMatcher;

@SuppressWarnings("unused")
public class Main extends JavaPlugin implements Listener
{
	HandleWDP wdp;
	
	public String invent = "wardrobe";
	String pluginname = "WDP";
	
	
  @SuppressWarnings("deprecation")
public void onDisable()
  {
	  for(Player online : Bukkit.getOnlinePlayers()){
	  }
    System.out.println("Disabled.");
  }
  
  public void onEnable()
  { 
	  Main plugin;
	  System.out.println("Enabled");
	  PluginManager pm = getServer().getPluginManager();
	  
	  wdp = new HandleWDP(this);
    
	  String error = wdp.fetchError(5);
	  System.out.println(error);
   
	if (getCommand("wdp") != null)
	{
		getCommand("wdp").setExecutor(new Commands(this));
	}
	else
	{
		getLogger().severe("WDP: Wardrobe Plus not setup right");
		getServer().getPluginManager().disablePlugin(this);
	}
	pm.registerEvents(this, this);
    loadConfig();
  }
  public void onReload(){
  }
  
  public String prefix = "§8[§5Wardrobe§a+§8] §c";
  
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void ClickListener(InventoryClickEvent event)
  {
    Player player = (Player)event.getWhoClicked();
    String inv = getInvName();
    String name = getConfig().getString(invent + ".ward.name");
    int paslot = getConfig().getInt(invent + ".lines");
    int pfslot = paslot * 9;
    
    if (event.getInventory().getName() == name){

    	for (int pslot=0;pslot<pfslot;pslot++)
    	{	
    		int add = 1;
    		int fslot = pslot + add;
    		if (event.getRawSlot() == pslot)
	    	{
        		event.setCancelled(true);
	    		if(this.getConfig().getBoolean(invent + ".slot." + fslot + ".enable"))
	    		{	
	    			if(this.getConfig().getBoolean(invent + ".slot."+ fslot + ".head")){
	    				   String h2 = getConfig().getString(invent + ".slot." + fslot + ".name");
	    				  // OfflinePlayer head =  getConfig().getOfflinePlayer(invent +".slot." + fslot + ".name");
	    				  // String pname = head.getUniqueId().toString();
	    				   player.getInventory().setHelmet(getHead(new ItemStack(Material.SKULL_ITEM, 1, (short)3), h2, fslot));
	    		        player.sendMessage("§5Set your Head.");
	    				} else if(this.getConfig().getBoolean(invent + ".slot."+ fslot + ".armor.enable"))
	    				{
	    					if(this.getConfig().getBoolean(invent + ".slot."+ fslot + ".armor.leather"))
	    					{
	    						if(this.getConfig().getBoolean(invent + ".slot."+ fslot + ".armor.head"))
	    						{
	    							String h2 = getConfig().getString(invent +".slot." + fslot + ".name");
	    							int R = getConfig().getInt(invent + ".slot." + fslot + ".armor.color.R");
	    							int G = getConfig().getInt(invent + ".slot." + fslot + ".armor.color.G");
	    							int B = getConfig().getInt(invent + ".slot." + fslot + ".armor.color.B");
	    							String cols = getConfig().getString(invent + ".slot." + fslot + ".armor.color.pre");
	    							player.getInventory().setHelmet(wdp.getArmor(new ItemStack(Material.LEATHER_HELMET), invent, fslot, h2, cols, R, G, B));
	    							player.sendMessage("§5Set your Head.");
	    						}
	    						if(this.getConfig().getBoolean(invent + ".slot."+ fslot + ".armor.chestplate"))
	    						{
	    							String h2 = getConfig().getString(invent +".slot." + fslot + ".name"); 
	    							int R = getConfig().getInt(invent + ".slot." + fslot + ".armor.color.R");
	    							int G = getConfig().getInt(invent + ".slot." + fslot + ".armor.color.G");
	    							int B = getConfig().getInt(invent + ".slot." + fslot + ".armor.color.B");
	    							String cols = getConfig().getString(invent + ".slot." + fslot + ".armor.color.pre");
	    							player.getInventory().setChestplate(wdp.getArmor(new ItemStack(Material.LEATHER_CHESTPLATE), invent, fslot, h2, cols, R, G, B));
	    							player.sendMessage("§5Set your Chestplate.");
	    						}
	    						if(this.getConfig().getBoolean(invent + ".slot."+ fslot + ".armor.leggings"))
	    						{
	    							String h2 = getConfig().getString(invent +".slot." + fslot + ".name"); 
	    							String mat = getConfig().getString(invent + ".slot." + fslot + ".item");   
	    							int R = getConfig().getInt(invent + ".slot." + fslot + ".armor.color.R");
	    							int G = getConfig().getInt(invent + ".slot." + fslot + ".armor.color.G");
	    							int B = getConfig().getInt(invent + ".slot." + fslot + ".armor.color.B");
	    							String cols = getConfig().getString(invent + ".slot." + fslot + ".armor.color.pre");
	    							player.getInventory().setLeggings(wdp.getArmor(new ItemStack(Material.LEATHER_LEGGINGS), invent, fslot, h2, cols, R, G, B));
	    							player.sendMessage("§5Set your Leggings.");
	    						}
	    						if(this.getConfig().getBoolean(invent + ".slot."+ fslot + ".armor.boots"))
	    						{
	    							String h2 = getConfig().getString(invent +".slot." + fslot + ".name");
	    							int R = getConfig().getInt(invent + ".slot." + fslot + ".armor.color.R");
	    							int G = getConfig().getInt(invent + ".slot." + fslot + ".armor.color.G");
	    							int B = getConfig().getInt(invent + ".slot." + fslot + ".armor.color.B");
	    							String cols = getConfig().getString(invent + ".slot." + fslot + ".armor.color.pre");
	    							player.getInventory().setBoots(wdp.getArmor(new ItemStack(Material.LEATHER_BOOTS), invent, fslot, h2, cols, R, G, B));
	    							player.sendMessage("§5Set your Boots.");
	    						}
	    					}
	    					else if(this.getConfig().getBoolean(invent + ".slot."+ fslot + ".armor.head"))
	    					{
	    						String h2 = getConfig().getString(invent +".slot." + fslot + ".name");
	    						String h3 = getConfig().getString(invent +".slot." + fslot + ".item"); 
	    						player.getInventory().setHelmet(new ItemStack(Material.matchMaterial(h3)));
	    						player.sendMessage("§5Set your Head.");
	    					}
	    					else if(this.getConfig().getBoolean(invent + ".slot."+ fslot + ".armor.chestplate"))
	    					{
	    						String h2 = getConfig().getString(invent +".slot." + fslot + ".name"); 
	    						String h3 = getConfig().getString(invent +".slot." + fslot + ".item"); 
	    						player.getInventory().setChestplate(new ItemStack(Material.matchMaterial(h3)));
	    						player.sendMessage("§5Set your Chestplate.");
	    					}
	    					else if(this.getConfig().getBoolean(invent + ".slot."+ fslot + ".armor.leggings"))
	    					{
	    						String h2 = getConfig().getString(invent +".slot." + fslot + ".name"); 
	    						String h3 = getConfig().getString(invent + ".slot." + fslot + ".item");
	    						player.getInventory().setLeggings(new ItemStack(Material.matchMaterial(h3)));
	    						player.sendMessage("§5Set your Leggings.");
	    					}
	    					else if(this.getConfig().getBoolean(invent + ".slot."+ fslot + ".armor.boots"))
	    					{
	    						String h2 = getConfig().getString(invent +".slot." + fslot + ".name");
	    						String h3 = getConfig().getString(invent +".slot." + fslot + ".item"); 
	    						player.getInventory().setBoots(new ItemStack(Material.matchMaterial(h3)));
	    						player.sendMessage("§5Set your Boots.");
	    					}
	    		        } else {  
	    		        this.getConfig().addDefault(invent + ".slot." + fslot + ".action", "");
	    		        String action = getConfig().getString(invent + ".slot." + fslot + ".action");
	    		        if(action.contains("/give ")){
	    		        	String faction = action.replace("player",player.getName()), ffaction = faction.replace("/", "");
	    		        	if(player.isOp()){player.chat(faction);}
	    		        	else{Bukkit.getServer().dispatchCommand(player.getServer().getConsoleSender(), ffaction);}
	    		        }else{player.chat(action);}
	    			}
	    		}
	    	}
	    	else
	    	{
	    	}
		}
    }
  	}
  
  
 /*
@EventHandler
  public void PlayerInteract(PlayerInteractEvent e)
  {
    Player p = e.getPlayer();
    if (e.getAction() == Action.PHYSICAL)
    {
      int x1 = e.getClickedBlock().getLocation().getBlockX();
      int y1 = e.getClickedBlock().getLocation().getBlockY() - 1;
      int z1 = e.getClickedBlock().getLocation().getBlockZ();
      Block b1 = p.getWorld().getBlockAt(x1, y1, z1);
      int y2 = e.getClickedBlock().getLocation().getBlockY() - 2;
      Block b2 = p.getWorld().getBlockAt(x1, y2, z1);
      
      String b3 = getConfig().getString("wardrobe.blocks.stone");
      String b4 = getConfig().getString("wardrobe.blocks.sponge");
      if ((b2.getType().name().equals(b4)) ||
        (b1.getType().name().equals(b3)) && 
        (e.getClickedBlock().getTypeId()==70) && 
        (p.hasPermission("wardrobe.pressureplate"))) {
    	  inv(p,invent);
      }
    }
  }
  */
  
  @EventHandler
  public void Open(PlayerInteractEvent e)
  {
    Player p = e.getPlayer();
    if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (
      (e.getClickedBlock().getType() == Material.SIGN) ||
      (e.getClickedBlock().getType() == Material.SIGN_POST) ||
      (e.getClickedBlock().getType() == Material.WALL_SIGN)))
    {
      Sign sign = (Sign)e.getClickedBlock().getState();
      if ((sign.getLine(3).equalsIgnoreCase("ⱺ")) && 
    	(p.hasPermission("wdp.sign"))) 
      {
          p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 1);
          String invs = sign.getLine(2).replace("@", "");
          p.chat("/wdp inventory " + invs );
          sign.setLine(1,getConfig().getString(invs + ".sign.name"));
          sign.update();       
      }
      if(sign.getLine(2).equalsIgnoreCase("enchanting"))
      {
    	if (p.hasPermission("wdp.enchanting"))
  	    {
    		p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 1);
  	        p.chat("/wdp enchanting");       
  	    }else{}
      }
      if(sign.getLine(2).equalsIgnoreCase("crafting"))
      {
    	if (p.hasPermission("wdp.crafting"))
    	{
    		p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 1);
    	    p.chat("/wdp crafting");       
    	}else{}
      }
    }
  }
  
  @EventHandler
  public void change(SignChangeEvent e)
  {
      String inv = e.getLine(1);
	  wdp.signChange(e, getConfig().getString(inv + ".sign.name"));
  }
  
  public void loadConfig()
  {  
	//name
	String name = "wardrobe.ward.name";
	getConfig().addDefault(name, "§lWardrobe+");
	
	String lines = "wardrobe.lines";
	getConfig().addDefault(lines, 1);
    
	//sign
    String sign = "wardrobe.sign.name";
    getConfig().addDefault(sign, "-§5WDP§0-");
    
    String b3 = "wardrobe.blocks.stone";
    getConfig().addDefault(b3, "STONE");
    
    String b4 = "wardrobe.blocks.sponge";
    getConfig().addDefault(b4, "SPONGE");
    
	String active = "wardrobe.activation";
    getConfig().addDefault(active, "Emerald");
    
	String invlist = "inventorys.list";
    getConfig().addDefault(invlist, 1);
    
    int aslot = getConfig().getInt("wardrobe.lines");
    int afslot = aslot * 9 + 1;
    for(int i=1;i<afslot;i++){
	    	String slot = "wardrobe.slot."+ i +".item";
		    getConfig().addDefault(slot, "Diamond");
		    
		    String slotname = "wardrobe.slot."+ i +".name";
		    getConfig().addDefault(slotname, "Default");
		    
		    String sloton = "wardrobe.slot."+ i +".enable";
		    getConfig().addDefault(sloton, false);
		    
		    String slothead = "wardrobe.slot."+ i +".head";
		    getConfig().addDefault(slothead, false);
		    
		    String slotinfo = "wardrobe.slot."+ i +".info.enable";
		    getConfig().addDefault(slotinfo, false);
		    
		    if(getConfig().getBoolean("wardrobe.slot."+ i +".info.enable")){
		    	String slotinfol1 = "wardrobe.slot."+ i +".info.line";
			    getConfig().addDefault(slotinfol1, "");
		    }
		    
		    String slotaction = "wardrobe.slot."+ i +".action";
		    getConfig().addDefault(slotaction, "");
		    
		    
	  }
    String ctop = "wardrobe.inv.name";
    wdp.getConfig("info").addDefault(ctop, "§8[§5Wardrobe§a+§8] §c");
    wdp.getConfig("info").options().copyDefaults(true);
    getConfig().options().copyDefaults(true);
    saveConfig();
    wdp.saveConfig("info");
  }
  
  @SuppressWarnings("deprecation")
  @EventHandler
  public void onRightClick(PlayerInteractEvent event) {
	  Player p = event.getPlayer();
	   if (((event.getAction() == Action.RIGHT_CLICK_AIR) || 
	    (event.getAction() == Action.RIGHT_CLICK_BLOCK) || 
	    (event.getAction() == Action.LEFT_CLICK_AIR) || 
	    (event.getAction() == Action.LEFT_CLICK_BLOCK)) && 
	    !(p.getItemInHand().getTypeId() == 0))
	    	    {
	  if (p.getItemInHand().getItemMeta().hasLore())
      	{
        if (p.getItemInHand().getItemMeta().getLore().get(1).startsWith("»"))
        	{

        	String inv = p.getItemInHand().getItemMeta().getLore().get(1).replace("»", "");
        	p.chat("/wdp inventory " + inv);
            p.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 5);
            event.setCancelled(true);
        	}else{}
        }else{}
	 }else{}
  }
  
  private ItemStack getHead(ItemStack item, String nick, int slot) {
      SkullMeta meta = (SkullMeta) item.getItemMeta();
/*    if(this.getConfig().getBoolean("wardrobe.slot." + slot + ".info.enable")){
      List<String> lore = Arrays.asList(getConfig().getString("wardrobe.slot." + slot + ".info.line"));
      meta.setLore(lore);
      }*/
      meta.setOwner(nick);
      item.setItemMeta(meta);
      return item;
  }
  public void inv(Player p, String inv)
  {
	if(getConfig().contains(inv))
	{
    String name = getConfig().getString(inv +".ward.name");
    if(p.hasPermission("wdp.inv." + name)){
    	
    int asslot = getConfig().getInt(inv +".lines");
    int afslot = asslot * 9 ;    
    Inventory x104 = Bukkit.createInventory(null, afslot, name);
	for (int slot=0;slot<afslot;slot++){
		int add = 1;
	    int thisslot = slot + add;
	    if(this.getConfig().getBoolean(inv +".slot." + thisslot + ".enable")){
	    	String item = this.getConfig().getString(inv + ".slot." +  thisslot + ".item");
		   if(item.equalsIgnoreCase("HEAD")){
			   String h2 = getConfig().getString(inv +".slot." + thisslot + ".name"); 
				x104.setItem(slot, wdp.getHead(new ItemStack(Material.SKULL_ITEM, 1, (short)3), h2, thisslot));
		   } 
		   else if(this.getConfig().getBoolean(inv +".slot."+ thisslot + ".armor.leather")){
			   String h2 = getConfig().getString(inv +".slot." + thisslot + ".name"); 
			   String mat = getConfig().getString(inv + ".slot." + thisslot + ".item");
			   x104.setItem(slot, wdp.getArmor(new ItemStack(Material.matchMaterial(mat)), inv, thisslot, h2));
		   } 
		   else if(item.equalsIgnoreCase("")||item.equalsIgnoreCase("air"))
		   {
			   x104.setItem(slot, null);
		   }
		   else 
		   {
			   String ite = getConfig().getString(inv +".slot." + thisslot + ".item");
			   String[] ites = ite.split(":");
			   if(ites.length == 1)
			   {
				   //Item
				   String Item_ID_raw = ites[0];
				   Material Item_ID = Material.matchMaterial(Item_ID_raw);
				   //meta
				   ItemStack aslot = new ItemStack(Item_ID,1);
				   ItemMeta eslot = aslot.getItemMeta();
				   //naming
				   eslot.setDisplayName(getConfig().getString(inv +".slot." + thisslot + ".name"));
				   aslot.setItemMeta(eslot);
				   //finalise
				   x104.setItem(slot, aslot);
			   }
			   else if(ites.length == 2)
			   {
				   
				   //Item
				   String Item_ID_raw = ites[0];
				   Material Item_ID = Material.matchMaterial(Item_ID_raw);
				   //Data Value 
				   int Item_Data_raw = Integer.parseInt(ites[1]);
				   short Item_Data = toShort(Item_Data_raw);
				   //meta
				   ItemStack aslot = new ItemStack(Item_ID,-1,Item_Data);
				   ItemMeta eslot = aslot.getItemMeta();
				   //naming
				   eslot.setDisplayName(getConfig().getString(inv +".slot." + thisslot + ".name"));
				   aslot.setItemMeta(eslot);
				   //finalise
				   x104.setItem(slot, aslot);
				   
			   } else{}
		   }
	    } 
	    else
	    {
	    	x104.setItem(slot, null);
	    }  
	}
    p.openInventory(x104); 
    }else{
    	p.sendMessage("WDP: Hold it there... We can't let you do that!");
    }
	}else{
		p.sendMessage("WDP: Inventory does not Exist!");
	}
    
  }
  public String getInvName(){ 
	return null;
  }
 /*
  private ItemStack getArmor(ItemStack item, String inv, int slot, String nick) {
      LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
      String cols = getConfig().getString(inv + ".slot." + slot + ".armor.color.pre");

      if(cols != null){
    	  if(cols.equalsIgnoreCase("aqua")){meta.setColor(Color.AQUA);}
    	  else if(cols.equalsIgnoreCase("black")){meta.setColor(Color.BLACK);}
    	  else if(cols.equalsIgnoreCase("blue")){meta.setColor(Color.BLUE);}
    	  else if(cols.equalsIgnoreCase("fushia")){meta.setColor(Color.FUCHSIA);}
    	  else if(cols.equalsIgnoreCase("gray")){meta.setColor(Color.GRAY);}
    	  else if(cols.equalsIgnoreCase("green")){meta.setColor(Color.GREEN);}
    	  else if(cols.equalsIgnoreCase("lime")){meta.setColor(Color.LIME);}
    	  else if(cols.equalsIgnoreCase("brown")){meta.setColor(Color.MAROON);}
    	  else if(cols.equalsIgnoreCase("navy")){meta.setColor(Color.NAVY);}
    	  else if(cols.equalsIgnoreCase("olive")){meta.setColor(Color.OLIVE);}
    	  else if(cols.equalsIgnoreCase("orange")){meta.setColor(Color.ORANGE);}
    	  else if(cols.equalsIgnoreCase("purple")){meta.setColor(Color.PURPLE);}
    	  else if(cols.equalsIgnoreCase("red")){meta.setColor(Color.RED);}
    	  else if(cols.equalsIgnoreCase("silver")){meta.setColor(Color.SILVER);}
    	  else if(cols.equalsIgnoreCase("teal")){meta.setColor(Color.TEAL);}
    	  else if(cols.equalsIgnoreCase("custom"))
    	  {
    		  int custr = getConfig().getInt(inv + ".slot." + slot + ".armor.color.R");
    	      int custg = getConfig().getInt(inv + ".slot." + slot + ".armor.color.G");
    	      int custb = getConfig().getInt(inv + ".slot." + slot + ".armor.color.B");
    		  meta.setColor(Color.fromRGB(custr,custg,custb));
    	  }
    	  else{}
      }
      else{}
      meta.setDisplayName(nick);
      item.setItemMeta(meta);
      return item;
  }
  */
  short toShort(int i)
  {
      return (short) i;
  }
}