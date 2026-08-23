package com.valentin.examplemod.handler;

import com.valentin.examplemod.ExampleMod;
import com.valentin.examplemod.mixin.VillagerEntityAccessor;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerData;

public class VillagerRerollHandler {
    public static void reroll(VillagerEntity villager, ServerPlayerEntity player) {
        if (villager == null || villager.isDead()) {
            player.sendMessage(Text.literal("§c[ExampleMod] §fErro: Aldeão não encontrado!"), false);
            ExampleMod.LOGGER.error("Villager is null or dead");
            return;
        }

        VillagerData data = villager.getVillagerData();
        int level = data.level();
        int xp = villager.getExperience();
        ServerWorld world = (ServerWorld) villager.getEntityWorld();

        player.sendMessage(Text.literal("§e[ExampleMod] §fIniciando reroll do aldeão..."), false);
        ExampleMod.LOGGER.info("Starting reroll for villager {} (level {}, xp {})", 
            villager.getId(), level, xp);

        VillagerEntityAccessor accessor = (VillagerEntityAccessor) villager;
        accessor.setLastRestockTime(0L);
        accessor.setRestocksToday(0);
        accessor.setLastRestockCheckTime(0L);

        villager.setOffers(new TradeOfferList());
        player.sendMessage(Text.literal("§a[ExampleMod] §fOfertas antigas limpas!"), false);
        ExampleMod.LOGGER.info("Cleared offers");

        accessor.invokeFillRecipes(world);
        player.sendMessage(Text.literal("§a[ExampleMod] §fNovas ofertas geradas!"), false);
        ExampleMod.LOGGER.info("Regenerated recipes");

        villager.setVillagerData(data.withLevel(level));
        villager.setExperience(xp);

        villager.sendOffers(player, villager.getDisplayName(), level);
        
        int offerCount = villager.getOffers().size();
        player.sendMessage(Text.literal("§a[ExampleMod] §fReroll completo! §e" + offerCount + " §fnovas ofertas disponíveis! ✓"), false);
        ExampleMod.LOGGER.info("Reroll completed! Villager has {} offers", offerCount);
    }
}
