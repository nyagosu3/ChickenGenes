package com.nyagosu.chickengenes.util;

import com.mojang.realmsclient.gui.ChatFormatting;

import cpw.mods.fml.client.FMLClientHandler;

public class TooCon {
	
	public static void log(String str){
		FMLClientHandler.instance().getClient().thePlayer.sendChatMessage(str);
	}
}
