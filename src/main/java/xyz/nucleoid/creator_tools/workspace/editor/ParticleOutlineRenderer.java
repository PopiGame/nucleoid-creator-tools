package xyz.nucleoid.creator_tools.workspace.editor;

import dev.popigame.mod.api.CreatorToolApi;
import net.minecraft.util.Identifier;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public final class ParticleOutlineRenderer {
    public static void render(Identifier namespace, ServerPlayerEntity player, BlockPos min, BlockPos max, float red, float green, float blue) {
        CreatorToolApi.add(player, namespace, min, max, red, green, blue, 1.0f);
    }
}
