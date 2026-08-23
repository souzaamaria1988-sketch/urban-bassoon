package com.valentin.examplemod.mixin;

import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(VillagerEntity.class)
public interface VillagerEntityAccessor {
    @Invoker("fillRecipes")
    void invokeFillRecipes(ServerWorld world);
    
    @Accessor("lastRestockTime")
    void setLastRestockTime(long time);
    
    @Accessor("restocksToday")
    void setRestocksToday(int count);
    
    @Accessor("lastRestockCheckTime")
    void setLastRestockCheckTime(long time);
}
