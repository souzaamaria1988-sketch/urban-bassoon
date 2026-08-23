package com.valentin.examplemod.handler;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.village.VillagerData;

public class VillagerRerollHandler {
    public static void reroll(VillagerEntity villager, ServerPlayerEntity player) {
        if (villager == null || villager.isDead()) return;
        VillagerData data = villager.getVillagerData();
        int level = data.getLevel();
        int xp = villager.getExperience();
        villager.getOffers().clear();
        villager.prepareOffers();
        villager.setVillagerData(data.withLevel(level));
        villager.setExperience(xp);
        villager.sendOffers(player, villager.getOffers(), level);
    }
}
