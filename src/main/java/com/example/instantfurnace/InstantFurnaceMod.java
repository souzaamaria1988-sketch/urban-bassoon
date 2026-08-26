package com.example.instantfurnace;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstantFurnaceMod implements ModInitializer {
	public static final String MOD_ID = "instant-furnace";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Instant Furnace mod initialized! Todas as fornalhas agora são instantâneas.");
	}
}
