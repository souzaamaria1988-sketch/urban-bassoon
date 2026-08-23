package com.valentin.examplemod.mixin;

import com.valentin.examplemod.network.RerollPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantScreen.class)
public abstract class MerchantScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void addRerollButton(CallbackInfo ci) {
        MerchantScreen s = (MerchantScreen)(Object)this;
        s.addDrawableChild(ButtonWidget.builder(Text.literal("§a⟳ Reroll"), b ->
            ClientPlayNetworking.send(new RerollPayload(s.getScreenHandler().getMerchant().getIntId()))
        ).dimensions(s.width / 2 + 95, s.height / 2 - 80, 60, 20).build());
    }
}
