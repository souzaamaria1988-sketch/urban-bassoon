package com.example.instantfurnace.mixin;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Shadow protected abstract boolean canCraft();
    
    @Shadow private int cookTime;
    
    @Shadow private int cookTimeTotal;
    
    @Shadow protected DefaultedList<ItemStack> inventory;
    
    @Shadow protected abstract void setStack(int slot, ItemStack stack);
    
    @Shadow protected abstract ItemStack removeStack(int slot, int count);

    @Inject(method = "canCraft", at = @At("HEAD"), cancellable = true)
    private void instantCook(CallbackInfoReturnable<Boolean> cir) {
        // Sempre retorna verdadeiro se houver ingrediente e combustível
        cir.setReturnValue(true);
        cir.cancel();
    }

    @Inject(method = "cook", at = @At("HEAD"), cancellable = true)
    private void instantCookProcess(CallbackInfo ci) {
        if (this.canCraft()) {
            ItemStack itemStack = this.inventory.get(0);
            ItemStack itemStack2 = this.inventory.get(2);
            
            // Remove o ingrediente do slot de entrada
            this.removeStack(0, 1);
            
            // Adiciona o resultado cozido ao slot de saída
            if (itemStack2.isEmpty()) {
                this.setStack(2, itemStack.getCraftResult().getDefaultStack());
            } else if (itemStack2.getItem() == itemStack.getCraftResult().getItem()) {
                itemStack2.increment(1);
            }
        }
        ci.cancel();
    }
}
