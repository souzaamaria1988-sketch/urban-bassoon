package com.valentin.examplemod;

import com.valentin.examplemod.handler.VillagerRerollHandler;
import com.valentin.examplemod.network.RerollPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "examplemod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playC2S().register(RerollPayload.ID, RerollPayload.CODEC);
        
        ServerPlayNetworking.registerGlobalReceiver(RerollPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                LOGGER.info("Received reroll request for entity ID: {}", payload.villagerId());
                
                Entity entity = context.player().getEntityWorld().getEntityById(payload.villagerId());
                LOGGER.info("Entity found: {} (type: {})", 
                    entity != null ? entity.getId() : "null",
                    entity != null ? entity.getType().toString() : "null");
                
                if (entity instanceof VillagerEntity villager) {
                    VillagerRerollHandler.reroll(villager, context.player());
                } else {
                    LOGGER.error("Entity is not a villager!");
                }
            });
        });
        
        LOGGER.info("Villager Reroll Mod initialized!");
    }
}
