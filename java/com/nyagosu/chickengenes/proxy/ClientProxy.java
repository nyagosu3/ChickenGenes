package com.nyagosu.chickengenes.proxy;

import com.nyagosu.chickengenes.entity.EntityGeneChicken;
import com.nyagosu.chickengenes.model.ModelGeneChicken;
import com.nyagosu.chickengenes.render.RenderGeneChicken;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy {
	@Override
	public void registerRenderThings() {
		RenderingRegistry.registerEntityRenderingHandler(EntityGeneChicken.class, new RenderGeneChicken(new ModelGeneChicken(3)));
	}
	
}
