package xyz.nucleoid.creator_tools.item;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import dev.popigame.mod.api.CreatorToolApi;
import org.jetbrains.annotations.Nullable;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import xyz.nucleoid.creator_tools.component.CreatorToolsDataComponentTypes;
import xyz.nucleoid.creator_tools.workspace.MapWorkspaceManager;

public final class RegionVisibilityFilterItem extends Item {
    public RegionVisibilityFilterItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient()) {
            return super.use(world, player, hand);
        }

        if (player instanceof ServerPlayerEntity serverPlayer) {
            var workspaceManager = MapWorkspaceManager.get(Objects.requireNonNull(world.getServer()));
            var editor = workspaceManager.getEditorFor(serverPlayer);
            if (editor != null) {
                CreatorToolApi.visibleToggle(serverPlayer);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        var regions = getRegions(stack);

        if (regions != null) {
            for (var region : regions) {
                textConsumer.accept(Text.literal(region).formatted(Formatting.GRAY));
            }
        }
    }

    @Nullable
    private static List<String> getRegions(ItemStack stack) {
        var component = stack.get(CreatorToolsDataComponentTypes.REGION_VISIBILITY_FILTER);
        return component == null ? null : component.regions();
    }
}
