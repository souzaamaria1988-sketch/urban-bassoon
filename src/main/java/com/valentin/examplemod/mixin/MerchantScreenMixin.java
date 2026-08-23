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
        ExampleMod.LOGGER.info("Adding Reroll button to MerchantScreen");
        
        MerchantScreen self = (MerchantScreen)(Object)this;

        ButtonWidget btn = ButtonWidget.builder(Text.literal("§a⟳ Reroll"), button -> {
            ExampleMod.LOGGER.info("Reroll button clicked!");
            try {
                MerchantScreenHandlerAccessor handler = 
                    (MerchantScreenHandlerAccessor) self.getScreenHandler();
                Merchant merchant = handler.getMerchant();
                
                if (merchant instanceof VillagerEntity villager) {
                    int entityId = villager.getId();
                    ExampleMod.LOGGER.info("Sending reroll for villager ID: {}", entityId);
                    ClientPlayNetworking.send(new RerollPayload(entityId));
                } else {
                    ExampleMod.LOGGER.error("Merchant is null or not a villager!");
                    if (self.getClient().player != null) {
                        self.getClient().player.sendMessage(
                            Text.literal("§c[ExampleMod] §fErro: Não foi possível encontrar o aldeão!"), 
                            false
                        );
                    }
                }
            } catch (Exception e) {
                ExampleMod.LOGGER.error("Error sending reroll packet", e);
                if (self.getClient().player != null) {
                    self.getClient().player.sendMessage(
                        Text.literal("§c[ExampleMod] §fErro ao tentar executar reroll!"), 
                        false
                    );
                }
            }
        })
        .dimensions(10, 10, 80, 20)
        .build();

        this.addDrawableChild(btn);
        ExampleMod.LOGGER.info("Reroll button added successfully");
    }
            }
