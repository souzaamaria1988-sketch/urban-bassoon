package com.valentin.examplemod.mixin;

import com.valentin.examplemod.ExampleMod;
import com.valentin.examplemod.network.RerollPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.Text;
import net.minecraft.village.Merchant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MerchantScreen.class, priority = 2000)
public abstract class MerchantScreenMixin extends Screen {

    protected MerchantScreenMixin(Text title) { super(title); }

    @Inject(method = "init", at = @At("TAIL"))
    private void addRerollButton(CallbackInfo ci) {
        MerchantScreen self = (MerchantScreen)(Object)this;

        ButtonWidget btn = ButtonWidget.builder(Text.literal("§a⟳ Reroll"), button -> {
            try {
                MerchantScreenHandlerAccessor handler = 
                    (MerchantScreenHandlerAccessor) self.getScreenHandler();
                Merchant merchant = handler.getMerchant();
                
                if (merchant instanceof VillagerEntity villager) {
                    int entityId = villager.getId();
                    ExampleMod.LOGGER.info("Sending reroll for villager ID: {}", entityId);
                    ClientPlayNetworking.send(new RerollPayload(entityId));
                } else if (merchant != null) {
                    ExampleMod.LOGGER.error("Merchant is not a villager! Type: {}", 
                        merchant.getClass().getName());
                } else {
                    ExampleMod.LOGGER.error("Merchant is null!");
                }
            } catch (Exception e) {
                ExampleMod.LOGGER.error("Error sending reroll packet", e);
            }
        }).dimensions(self.width / 2 + 95, self.height / 2 - 80, 60, 20).build();

        this.addDrawableChild(btn);
    }
}
