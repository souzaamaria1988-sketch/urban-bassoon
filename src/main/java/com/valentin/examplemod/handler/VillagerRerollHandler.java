package com.valentin.examplemod.handler;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.village.VillagerData;

public class VillagerRerollHandler {
    
    /**
     * Regenera as trades mantendo profissão, nível e experiência.
     * Apenas reseta as ofertas atuais e força repopulação.
     */
    public static void rerollTrades(VillagerEntity villager, ServerPlayerEntity player) {
        if (villager == null || villager.isDead()) return;
        
        VillagerData data = villager.getVillagerData();
        int level = data.getLevel();
        int xp = villager.getExperience();
        
        // Limpa trades atuais sem resetar profissão/nível
        villager.getOffers().clear();
        
        // Força regeneração das trades baseadas no nível atual
        villager.prepareOffers();
        
        // Garante que nível e XP foram preservados
        villager.setVillagerData(data.withLevel(level));
        villager.setExperience(xp);
        
        // Sincroniza com o cliente
        villager.sendOffers(player, villager.getOffers(), level);
    }
}
