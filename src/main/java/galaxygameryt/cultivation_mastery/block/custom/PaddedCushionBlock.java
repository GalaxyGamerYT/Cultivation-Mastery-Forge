package galaxygameryt.cultivation_mastery.block.custom;

import galaxygameryt.cultivation_mastery.entity.ModEntities;
import galaxygameryt.cultivation_mastery.entity.custom.SittingEntity;
import galaxygameryt.cultivation_mastery.item.ModCreativeModeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class PaddedCushionBlock extends Block {
    public PaddedCushionBlock(Properties pProperties) {
        super(pProperties.noOcclusion());
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
//        return Block.box(0, 0, 0, 16, 8, 16);
        return shape();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.translatable("block.cultivation_mastery.padded_cushion.tooltip"));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (player.getItemInHand(hand) == ItemStack.EMPTY && !level.isClientSide()) {
            Entity entity = null;
            List<SittingEntity> entities = level.getEntities(ModEntities.SITTING.get(), new AABB(pos), sitting -> true);
            if (entities.isEmpty()) {
                entity = ModEntities.SITTING.get().spawn((ServerLevel) level, pos, MobSpawnType.TRIGGERED);
            } else {
                entity = entities.get(0);
            }

            player.startRiding(entity);
        }

        return InteractionResult.SUCCESS;
    }

    public static VoxelShape shape() {
        return Stream.of(
                Block.box(0, 0, 3, 16, 6, 13),
                Block.box(1, 6, 3, 15, 8, 13),
                Block.box(3, 0, 0, 13, 6, 3),
                Block.box(3, 0, 13, 13, 6, 16),
                Block.box(13, 1, 13, 14, 5, 14),
                Block.box(13, 1, 2, 14, 5, 3),
                Block.box(2, 1, 2, 3, 5, 3),
                Block.box(2, 1, 13, 3, 5, 14),
                Block.box(3, 6, 1, 13, 8, 3),
                Block.box(3, 6, 13, 13, 8, 15)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }


}
