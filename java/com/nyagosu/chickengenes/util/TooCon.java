package com.nyagosu.chickengenes.util;

import cpw.mods.fml.client.FMLClientHandler;

public class TooCon {
	
	public static void log(String val){
		FMLClientHandler.instance().getClient().thePlayer.sendChatMessage(val);
	}
	
	public static void log(int val){
		TooCon.log(String.valueOf(val));
	}

	public static void log(double val){
		TooCon.log(String.valueOf(val));
	}
	
	public static void log(float val){
		TooCon.log(String.valueOf(val));
	}
	
	public static void log(boolean val){
		TooCon.log(String.valueOf(val));
	}
	
}
