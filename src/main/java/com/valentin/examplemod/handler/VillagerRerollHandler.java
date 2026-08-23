package com.valentin.examplemod.handler;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.village.VillagerData;

public class VillagerRerollHandler {
    public static void reroll(VillagerEntity villager, ServerPlayerEntity player) {
        if (villager == null || villager.isDead()) return;

        VillagerData data = villager.getVillagerData();
        int level = data.level();
        int xp = villager.getExperience();

        // Limpa e regenera trades mantendo profissão/nível
        villager.resetTrades();

        // Restaura nível e XP explicitamente
        villager.setVillagerData(data.withLevel(level));
        villager.setExperience(xp);

        // Sincroniza novas ofertas com o cliente
        villager.sendOffers(player, villager.getOffers(), level);
    }
}
