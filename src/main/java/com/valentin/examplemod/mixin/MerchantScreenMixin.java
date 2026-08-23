package com.valentin.examplemod.mixin;

import com.valentin.examplemod.network.RerollPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MerchantScreen.class, priority = 1500) // Prioridade máxima
public abstract class MerchantScreenMixin extends Screen {

    protected MerchantScreenMixin(Text title) { super(title); }

    @Inject(method = "init", at = @At("TAIL"))
    private void addRerollButton(CallbackInfo ci) {
        MerchantScreen self = (MerchantScreen)(Object)this;

        ButtonWidget btn = ButtonWidget.builder(Text.literal("§a⟳ Reroll"), b -> {
            int id = self.getScreenHandler().syncId;
            ClientPlayNetworking.send(new RerollPayload(id));
        }).dimensions(self.width / 2 + 95, self.height / 2 - 80, 60, 20).build();

        this.addDrawableChild(btn);
    }
}
