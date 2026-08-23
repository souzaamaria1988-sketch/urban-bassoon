package com.valentin.examplemod.network;

import com.valentin.examplemod.ExampleMod;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record RerollPayload(int villagerId) implements CustomPayload {
    public static final Id<RerollPayload> ID = new Id<>(Identifier.of(ExampleMod.MOD_ID, "reroll"));
    public static final PacketCodec<PacketByteBuf, RerollPayload> CODEC =
        PacketCodec.tuple(PacketCodec.INTEGER, RerollPayload::villagerId, RerollPayload::new);
    @Override public Id<? extends CustomPayload> getId() { return ID; }
}
