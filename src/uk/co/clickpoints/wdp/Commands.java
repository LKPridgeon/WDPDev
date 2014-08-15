package uk.co.clickpoints.wdp;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import uk.co.clickpoints.wdpdep.HandleWDP;


public class Commands implements CommandExecutor {
	
	private final Main plugin;
    HandleWDP wdp;
    
	public Commands(Main plugin)
	{
		this.plugin = plugin;
		wdp = new HandleWDP(plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    
		if (!(sender instanceof Player)){
			sender.sendMessage("WDP: Unfortunatly We do not offer this command to you!");
			return false;
		}
		else if(cmd.getName().equalsIgnoreCase("wdp"))
		{
	    	Player p = (Player)sender;
			if (args.length == 0)
			{
	    		p.sendMessage(wdp.fetchError(4));
				return true;
			}
			else if (args[0].equalsIgnoreCase("reload")) 
	    	{
	    		plugin.loadConfig();
	        	return true;
	    	}
			else if (args[0].equalsIgnoreCase("crafting")) 
	    	{
				 plugin.invent = "crafting";
				 p.openWorkbench(null, true);
	        	return true;
	    	}
			else if (args[0].equalsIgnoreCase("enchanting")) 
	    	{
				 plugin.invent = "enchanting";
				 p.openEnchanting(null, true);
				 
	        	return true;
	    	}
	    	else if (args[0].equalsIgnoreCase("change")&& p.hasPermission("wdp.change")) 
	    	{
	    		if (args.length == 1){p.sendMessage(wdp.fetchError(1));return true;}
	    		else if (args[1].equalsIgnoreCase("name"))
	    		{
	    			if (args.length == 2){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 3){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 4){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 5)
	    			{
	    				String inve = args[2];
	    				String area = args[3];
	    				String edit = args[4].replace("_"," ");
	        			if(plugin.getConfig().getBoolean(inve + ".slot." + area + ".enable")){
	    				String fullarea = inve + ".slot." + area + ".name";
	    				plugin.getConfig().set(fullarea, edit);
	    				plugin.saveConfig();
	    				return true;
	        			}
	        			{
	        				p.sendMessage("WDP: Slot " + area + " is not enabled!");
	        				return true;
	        			}
	       			} else {p.sendMessage(wdp.fetchError(1));return true;}

	    		}
	    		else if (args[1].equalsIgnoreCase("item"))
	    		{
	    			if (args.length == 2){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 3){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 4){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 5)
	    			{
	    				String inve = args[2];
	    				String area = args[3];
	    				String edit = args[4];
	    				if(plugin.getConfig().getBoolean(inve + ".slot." + area + ".enable")){
	    				String fullarea = inve + ".slot." + area + ".item";
	    				plugin.getConfig().set(fullarea, edit);
	    				plugin.saveConfig();
	    				return true;
	        			}
	    				else
	        			{
	        				p.sendMessage("WDP: Slot " + area + " is not enabled!");
	        				return true;
	        			}
	       			} else {p.sendMessage(wdp.fetchError(1));return true;}
	    		}
	    		else if (args[1].equalsIgnoreCase("action"))
	    		{
	    			if (args.length == 2){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 3){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 4){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 5){
	    				String invs = args[2];
	    				String area = args[3];
	    				String edit = args[4].replace("_", " ");
	    				String fullarea = invs + ".slot." + area + ".action";
	    				plugin.getConfig().set(fullarea, edit);
	    				plugin.saveConfig();
	    				return true;
	    			} else {p.sendMessage(wdp.fetchError(1));return true;}
	    		}
	    		else if (args[1].equalsIgnoreCase("enable"))
	    		{
	    			if (args.length == 2){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 3){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 4){
	    				String inve = args[2];
	    				String area = args[3];
	    				String fullarea = inve + ".slot." + area + ".enable";
	    				plugin.getConfig().set(fullarea, true);
	    				plugin.saveConfig();
	    				p.sendMessage("WDP: " + fullarea + " / " + plugin.getConfig().getBoolean(fullarea));
	    				return true;
	    			} else {p.sendMessage(wdp.fetchError(1));return true;}
	    		}
	    		else if (args[1].equalsIgnoreCase("disable"))
	    		{
	    			if (args.length == 2){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 3){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 4){
	    				String inve = args[2];
	    	         	String area = args[3];
	    	         	String fullarea = inve + ".slot." + area + ".enable";
	    	   		    plugin.getConfig().set(fullarea, false);
	    	   		    plugin.saveConfig();
	    	   		    p.sendMessage(fullarea + " / " + plugin.getConfig().getBoolean(fullarea));
	    	   		    return true;
	    			} else {p.sendMessage(wdp.fetchError(1));return true;}
	    		}
	    		else if (args[1].equalsIgnoreCase("lines"))
	    		{
	    			if (args.length == 2){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 3){p.sendMessage(wdp.fetchError(1));return true;}
	    			if (args.length == 4)
	    			{
	    				String inve = args[2];
	    				String me = "Lines Updated!";
	    				String fullarea = inve + ".lines";
	    				if(args[3].equalsIgnoreCase("1")){plugin.getConfig().set(fullarea, 1);p.sendMessage(me);}
	    				else if(args[3].equalsIgnoreCase("2")){plugin.getConfig().set(fullarea, 2);p.sendMessage(me);}
	    				else if(args[3].equalsIgnoreCase("3")){plugin.getConfig().set(fullarea, 3);p.sendMessage(me);}
	    				else if(args[3].equalsIgnoreCase("4")){plugin.getConfig().set(fullarea, 4);p.sendMessage(me);}
	    				else if(args[3].equalsIgnoreCase("5")){plugin.getConfig().set(fullarea, 5);p.sendMessage(me);}
	    				else if(args[3].equalsIgnoreCase("6")){plugin.getConfig().set(fullarea, 6);p.sendMessage(me);}
	    				else {p.sendMessage(wdp.fetchError(1));}
	    				return true;
	    			} else {p.sendMessage(wdp.fetchError(1));return true;}
	    		}
	    		else if (args[1].equalsIgnoreCase("enablecheck"))
	    		{
	         	   String inve = args[2];
	         	   String area = args[3];
	         	   String fullarea = inve + ".slot." + area + ".enable";
	   		       p.sendMessage(fullarea + " / " + plugin.getConfig().getBoolean(fullarea));
	   		       return true;
	    		} else {
	    			p.sendMessage(wdp.fetchError(1));
	    			return true;
	    		}
	    	}
			else if (args[0].equalsIgnoreCase("help")&& p.hasPermission("wdp.help"))
			{
				if (args.length == 1)
				{
					p.sendMessage("WDP Help: ");
					p.sendMessage("/wdp getItem [inventory]");
					p.sendMessage("/wdp reload");
					p.sendMessage("/wdp change item [inventory] [slot] [ItemID]");
					p.sendMessage("/wdp change enable [inventory] [slot]");
					p.sendMessage("/wdp change disable [inventory] [slot]");
					p.sendMessage("/wdp change action [inventory] [slot] [action] (please use a dot instead of space!)");
					p.sendMessage("/wdp change lines [inventory]");
					p.sendMessage("/wdp inventory [inventory]");
					p.sendMessage("/wdp inv [inventory]");
					p.sendMessage("/wdp crafting");
					p.sendMessage("/wdp enchanting");
					return true;
				}
				if (args[1].equalsIgnoreCase("config"))
				{
					p.sendMessage("WPD Config: Usage");
					p.sendMessage("");
					p.sendMessage("To use wpd's config file is simple as most of the format is pre-generated to prevent crashing.");
					p.sendMessage("Leave these configurations alone:");
					p.sendMessage("wardrobe , ward , slot and sign");
					p.sendMessage("");
					p.sendMessage("To execute a command edit the action feature removeing the '' as this cancel's out the action.");
					p.sendMessage("");
					p.sendMessage("Items can be changed by the item section of the slot this can be in item ID form or as the new format.");
					p.sendMessage("");
					p.sendMessage("To disable a slot change the enable feature to false as leaving it on and the item with nothing will");
					p.sendMessage("crash the plugin. This also applys to Air and null");
					p.sendMessage("");
					p.sendMessage("Signs can be changed but at the moment signs will not auto update so all old signs will stop working and");
					p.sendMessage("will be needed to be replaced.");
					p.sendMessage("");
					p.sendMessage("The block section is for the pressure plate making the inventory pop up this is currently being worked on.");
					return true;
				} else {p.sendMessage(wdp.fetchError(1));return true;}
			}
			else if (args[0].equalsIgnoreCase("inventory")|| args[0].equalsIgnoreCase("inv"))
			{
					plugin.invent = args[1];
					if (plugin.getConfig().contains(plugin.invent)){
						plugin.inv(p,plugin.invent);	
		    			return true;
					}
					else
					{
						p.sendMessage("WDP: Really wow...");
						return true;
					}

			}
			else if (args[0].equalsIgnoreCase("getItem")&&p.hasPermission("wardrobe.getItem"))
			{
				plugin.invent = args[1];
					if(args.length == 2){
					if (plugin.getConfig().contains(plugin.invent)){
						
						ItemStack Wardrobe = new ItemStack(Material.matchMaterial(plugin.getConfig().getString(plugin.invent + ".activation")));
				    	ItemMeta meta = Wardrobe.getItemMeta();
				    	if(!plugin.invent.equalsIgnoreCase("wardrobe")){
				    	meta.setDisplayName(ChatColor.GOLD + "Wardrobe - " + plugin.invent);
				    	}
				    	{
				    	meta.setDisplayName(ChatColor.GOLD + "Wardrobe");
				    	}
				    	List<String> lore = new ArrayList<String>();
				    	lore.add(ChatColor.WHITE + "Open your Wardrobe!");
				    	lore.add("»" + plugin.invent);
				    	meta.setLore(lore);    	
				        List<String> lores = new ArrayList<String>();
				        lores.add("Special");
				        meta.setLore(lore);
				    	Wardrobe.setItemMeta(meta);
				    	p.getInventory().addItem(Wardrobe);
				    	return true;
					}
					else
					{
						p.sendMessage("WDP: Really wow...");
						return true;
					}
					}else{p.sendMessage(wdp.fetchError(4));return true;}
					
			}
			else if (args[0].equalsIgnoreCase("create")&& p.hasPermission("wardrobe.create"))
			{
				if (args.length == 1){p.sendMessage(wdp.fetchError(1));return true;}
				if (args.length == 2){p.sendMessage(wdp.fetchError(1));return true;}
				if (args.length == 3){
					String inve = args[1];
		         	String line = args[2];
		         	
		         	int lines = 1;
		         	if (line.equalsIgnoreCase("1")){lines = 1;}
		         	else if (line.equalsIgnoreCase("2")){lines = 2;}
		         	else if (line.equalsIgnoreCase("3")){lines = 3;}
		         	else if (line.equalsIgnoreCase("4")){lines = 4;}
		         	else if (line.equalsIgnoreCase("5")){lines = 5;}
		         	else if (line.equalsIgnoreCase("6")){lines = 6;}
		         	else {lines = 1;}
		         	
		        	String name = inve + ".ward.name";
		        	plugin.getConfig().set(name, inve);
		            
		            String sign = inve + ".sign.name";
		            plugin.getConfig().set(sign, "-§5WDP§0-");
		            
		            String b3 = inve + ".blocks.stone";
		            plugin.getConfig().set(b3, "STONE");
		            
		            String b4 = inve + ".blocks.sponge";
		            plugin.getConfig().set(b4, "SPONGE");
		            
		        	String active = inve + ".activation";
		        	plugin.getConfig().set(active, "Emerald");
		            
		         	String fullarea = inve + ".lines";
		         	plugin.getConfig().set(fullarea, lines);
		   		    int fline = lines * 9 + 1;
		   		    for(int i=1;i<fline;i++){
		   		    	String slot = inve + ".slot."+ i +".item";
		   		    	plugin.getConfig().set(slot, "Diamond");	   			    
		   			    String slotname = inve + ".slot."+ i +".name";
		   			    plugin.getConfig().set(slotname, "Default");	   			    
		   			    String sloton = inve + ".slot."+ i +".enable";
		   			    plugin.getConfig().set(sloton, false);	   			    
		   			    String slothead = inve + ".slot."+ i +".head";
		   			    plugin.getConfig().set(slothead, false);
		   			    String slotinfo = inve + ".slot."+ i +".info.enable";
		   			    plugin.getConfig().set(slotinfo, false);
		   		    }   		    
		   		    plugin.saveConfig();
		   		    return true;
				} else {p.sendMessage(wdp.fetchError(1));return true;}
			}
	    	else
	    	{
	    		p.sendMessage(wdp.fetchError(4));
	    		p.sendMessage("WPD Command: /wpd change [event] [inventory] [slot] [argument]");
	    		return true;
	    	}
			
		}
		else if(cmd.getName().equalsIgnoreCase("ward")){
			System.out.println("YEAH");
			return true;
		}
		return false;
	}

}
