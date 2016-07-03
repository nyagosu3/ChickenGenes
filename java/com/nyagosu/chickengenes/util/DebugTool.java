package com.nyagosu.chickengenes.util;

import cpw.mods.fml.client.FMLClientHandler;

public class DebugTool {
	public static void print(String str){
		FMLClientHandler.instance().getClient().thePlayer.sendChatMessage(str);
	}
}
