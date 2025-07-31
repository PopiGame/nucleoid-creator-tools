package xyz.nucleoid.creator_tools.workspace.editor;

import dev.popigame.mod.networking.packets.s2c.maptool.MapToolOutlineAddPacketS2C;
import dev.popigame.mod.util.PopiGameNetworkUtil;
import net.minecraft.util.Identifier;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public final class ParticleOutlineRenderer {
    public static void render(Identifier namespace, ServerPlayerEntity player, BlockPos min, BlockPos max, float red, float green, float blue) {
        PopiGameNetworkUtil.sendPacketS2C(player, new MapToolOutlineAddPacketS2C(namespace, min, max, red, green, blue, 1.0f));
    }
}
