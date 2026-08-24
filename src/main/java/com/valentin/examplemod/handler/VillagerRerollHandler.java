package com.valentin.examplemod.handler;

import com.valentin.examplemod.ExampleMod;
import com.valentin.examplemod.mixin.VillagerEntityAccessor;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerData;

public class VillagerRerollHandler {
    public static void reroll(VillagerEntity villager, ServerPlayerEntity player) {
        if (villager == null || villager.isDead()) {
            player.sendMessage(Text.literal("§c[ExampleMod] §fErro: Aldeão não encontrado!"), false);
            return;
        }

        VillagerData data = villager.getVillagerData();
        int level = data.level();
        int xp = villager.getExperience();
        ServerWorld world = (ServerWorld) villager.getEntityWorld();

        player.sendMessage(Text.literal("§e[ExampleMod] §fIniciando reroll..."), false);

        // Reseta os contadores de restock
        VillagerEntityAccessor accessor = (VillagerEntityAccessor) villager;
        accessor.setLastRestockTime(0L);
        accessor.setRestocksToday(0);
        accessor.setLastRestockCheckTime(0L);

        // Limpa e regenera as ofertas
        villager.setOffers(new TradeOfferList());
        accessor.invokeFillRecipes(world);

        // Restaura nível e XP
        villager.setVillagerData(data.withLevel(level));
        villager.setExperience(xp);

        // 🎯 FECHA E REABRE A TELA (efeito "piscar")
        villager.sendOffers(player, villager.getDisplayName(), level);

        player.sendMessage(Text.literal("§a[ExampleMod] §fReroll completo! §e" + 
            villager.getOffers().size() + " §fnovas ofertas! ✓"), false);
        
        ExampleMod.LOGGER.info("Reroll completed for villager {}", villager.getId());
    }
            }
