package com.valentin.examplemod.mixin;

import com.valentin.examplemod.ExampleMod;
import com.valentin.examplemod.network.RerollPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
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
        MinecraftClient client = MinecraftClient.getInstance();

        // Calcula a posição da GUI (276x166 pixels, sempre centralizada na tela)
        int backgroundWidth = 276;
        int backgroundHeight = 166;
        int guiX = (this.width - backgroundWidth) / 2;
        int guiY = (this.height - backgroundHeight) / 2;

        // Botão na área do resultado da trade, um pouco acima
        int buttonX = guiX + 168;
        int buttonY = guiY + 28;

        ButtonWidget btn = ButtonWidget.builder(Text.literal("§a⟳ Reroll"), button -> {
            try {
                int entityId = ExampleMod.lastInteractedMerchantId;
                
                if (entityId != -1) {
                    ExampleMod.LOGGER.info("Sending reroll for villager ID: {}", entityId);
                    ClientPlayNetworking.send(new RerollPayload(entityId));
                } else {
                    if (client.player != null) {
                        client.player.sendMessage(
                            Text.literal("§c[ExampleMod] §fFeche e abra o inventário do aldeão novamente!"), 
                            false
                        );
                    }
                }
            } catch (Exception e) {
                ExampleMod.LOGGER.error("Error sending reroll packet", e);
            }
        })
        .dimensions(buttonX, buttonY, 60, 20)
        .build();

        this.addDrawableChild(btn);
        ExampleMod.LOGGER.info("Reroll button added at ({}, {})", buttonX, buttonY);
    }
}
