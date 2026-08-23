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
        // Registra payload
        PayloadTypeRegistry.playC2S().register(RerollPayload.ID, RerollPayload.CODEC);
        
        // Registra handler no servidor
        ServerPlayNetworking.registerGlobalReceiver(RerollPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                Entity entity = context.player().getWorld().getEntityById(payload.villagerId());
                if (entity instanceof VillagerEntity villager) {
                    VillagerRerollHandler.rerollTrades(villager, context.player());
                    LOGGER.info("Reroll executado para villager {} por {}", 
                        payload.villagerId(), context.player().getName().getString());
                }
            });
        });
        
        LOGGER.info("Villager Reroll Mod inicializado!");
    }
}
