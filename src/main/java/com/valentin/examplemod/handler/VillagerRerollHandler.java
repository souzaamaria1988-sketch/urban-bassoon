package com.valentin.examplemod.handler;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.village.VillagerData;

public class VillagerRerollHandler {
    public static void reroll(VillagerEntity villager, ServerPlayerEntity player) {
        if (villager == null || villager.isDead()) return;

        VillagerData data = villager.getVillagerData();
        int level = data.level();
        int xp = villager.getExperience();
        ServerWorld world = (ServerWorld) villager.getEntityWorld();

        // Limpa as ofertas atuais
        villager.setOffers(new net.minecraft.village.TradeOfferList());

        // Regenera as recipes/offers baseadas na profissão e nível
        villager.fillRecipes(world);

        // Restaura nível e XP explicitamente
        villager.setVillagerData(data.withLevel(level));
        villager.setExperience(xp);

        // Envia as novas ofertas ao jogador (assinatura correta: player, Text, int)
        villager.sendOffers(player, villager.getDisplayName(), level);
    }
}
