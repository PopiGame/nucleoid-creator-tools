package xyz.nucleoid.creator_tools.command;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.serialization.MapCodec;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public final class ChunkGeneratorArgument {
    public static final DynamicCommandExceptionType GENERATOR_NOT_FOUND = new DynamicCommandExceptionType(arg ->
            Text.stringifiedTranslatable("text.nucleoid_creator_tools.chunk_generator.generator_not_found", arg)
    );

    public static RequiredArgumentBuilder<ServerCommandSource, Identifier> argument(String name) {
        return CommandManager.argument(name, IdentifierArgumentType.identifier())
                .suggests((context, builder) -> CommandSource.suggestIdentifiers(
                        Registries.CHUNK_GENERATOR.getIds().stream(),
                        builder
                ));
    }

    public static MapCodec<? extends ChunkGenerator> get(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
        var identifier = IdentifierArgumentType.getIdentifier(context, name);

        var generator = Registries.CHUNK_GENERATOR.get(identifier);
        if (generator == null) {
            throw GENERATOR_NOT_FOUND.create(identifier);
        }

        return generator;
    }
}
