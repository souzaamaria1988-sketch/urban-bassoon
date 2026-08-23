package com.valentin.examplemod.handler;

import com.valentin.examplemod.ExampleMod;
import com.valentin.examplemod.mixin.VillagerEntityAccessor;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerData;

public class VillagerRerollHandler {
    public static void reroll(VillagerEntity villager, ServerPlayerEntity player) {
        if (villager == null || villager.isDead()) {
            ExampleMod.LOGGER.error("Villager is null or dead");
            return;
        }

        VillagerData data = villager.getVillagerData();
        int level = data.level();
        int xp = villager.getExperience();
        ServerWorld world = (ServerWorld) villager.getEntityWorld();

        ExampleMod.LOGGER.info("Starting reroll for villager {} (level {}, xp {})", 
            villager.getId(), level, xp);

        VillagerEntityAccessor accessor = (VillagerEntityAccessor) villager;
        accessor.setLastRestockTime(0L);
        accessor.setRestocksToday(0);
        accessor.setLastRestockCheckTime(0L);

        villager.setOffers(new TradeOfferList());
        ExampleMod.LOGGER.info("Cleared offers");

        accessor.invokeFillRecipes(world);
        ExampleMod.LOGGER.info("Regenerated recipes");

        villager.setVillagerData(data.withLevel(level));
        villager.setExperience(xp);

        villager.sendOffers(player, villager.getDisplayName(), level);
        
        ExampleMod.LOGGER.info("Reroll completed! Villager has {} offers", 
            villager.getOffers().size());
    }
}
