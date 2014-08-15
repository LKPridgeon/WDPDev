package uk.co.clickpoints.wdp;

import org.bukkit.plugin.Plugin;

public class Legacy {
	
	@SuppressWarnings("unused")
	private final Plugin plugin;
	
	public Legacy(Plugin plugin){
		this.plugin = plugin;
	}
	
	public void openPreGen(String pregen, String oItem, String pName){
		if(pregen.equalsIgnoreCase("Legacy")){
			preGen_Legacy(oItem, pName);
		}
		else if(pregen.equalsIgnoreCase("")){
			
		}
		else{
		}
	}
	
	public void listenPreGen(){
		
	}
	
	//PreGens
	
	public void preGen_Legacy(String oItem, String pName){
		
	}

}
