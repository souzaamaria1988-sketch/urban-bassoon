package com.valentin.examplemod.mixin;

import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.screen.MerchantScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MerchantScreenHandler.class)
public interface MerchantScreenHandlerAccessor {
    @Accessor("merchant")
    MerchantEntity getMerchant();
}
