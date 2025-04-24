package galaxygameryt.cultivation_mastery.block.custom;

import galaxygameryt.cultivation_mastery.item.ModCreativeModeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
        return Block.box(0, 0, 0, 16, 8, 16);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.translatable("block.cultivation_mastery.padded_cushion.tooltip"));
    }

    public static VoxelShape shape() {
        return Stream.of(
                Stream.of(
                        Block.box(15, 2, 5, 16, 4, 11),
                        Block.box(5, 2, 15, 11, 4, 16),
                        Block.box(5, 2, 0, 11, 4, 1),
                        Block.box(3, 2, 14, 5, 4, 15),
                        Block.box(3, 2, 1, 5, 4, 2),
                        Block.box(11, 2, 14, 13, 4, 15),
                        Block.box(11, 2, 1, 13, 4, 2),
                        Block.box(2, 2, 13, 3, 4, 14),
                        Block.box(2, 2, 2, 3, 4, 3),
                        Block.box(13, 2, 13, 14, 4, 14),
                        Block.box(13, 2, 2, 14, 4, 3),
                        Block.box(0, 2, 5, 1, 4, 11),
                        Block.box(14, 2, 11, 15, 4, 13),
                        Block.box(14, 2, 3, 15, 4, 5),
                        Block.box(1, 2, 11, 2, 4, 13),
                        Block.box(1, 2, 3, 2, 4, 5)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Stream.of(
                        Block.box(6, 4, 0, 10, 5, 1),
                        Block.box(6, 4, 15, 10, 5, 16),
                        Block.box(10, 4, 14, 12, 5, 15),
                        Block.box(10, 4, 1, 12, 5, 2),
                        Block.box(12, 4, 12, 14, 5, 14),
                        Block.box(12, 4, 2, 14, 5, 4),
                        Block.box(14, 4, 10, 15, 5, 12),
                        Block.box(14, 4, 4, 15, 5, 6),
                        Block.box(15, 4, 6, 16, 5, 10),
                        Block.box(0, 4, 6, 1, 5, 10),
                        Block.box(4, 4, 1, 6, 5, 2),
                        Block.box(4, 4, 14, 6, 5, 15),
                        Block.box(2, 4, 12, 4, 5, 14),
                        Block.box(2, 4, 2, 4, 5, 4),
                        Block.box(1, 4, 10, 2, 5, 12),
                        Block.box(1, 4, 4, 2, 5, 6)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Stream.of(
                        Block.box(6, 1, 15, 10, 2, 16),
                        Block.box(6, 1, 0, 10, 2, 1),
                        Block.box(10, 1, 1, 12, 2, 2),
                        Block.box(10, 1, 14, 12, 2, 15),
                        Block.box(12, 1, 2, 14, 2, 4),
                        Block.box(12, 1, 12, 14, 2, 14),
                        Block.box(14, 1, 4, 15, 2, 6),
                        Block.box(14, 1, 10, 15, 2, 12),
                        Block.box(15, 1, 6, 16, 2, 10),
                        Block.box(0, 1, 6, 1, 2, 10),
                        Block.box(4, 1, 14, 6, 2, 15),
                        Block.box(4, 1, 1, 6, 2, 2),
                        Block.box(2, 1, 2, 4, 2, 4),
                        Block.box(2, 1, 12, 4, 2, 14),
                        Block.box(1, 1, 4, 2, 2, 6),
                        Block.box(1, 1, 10, 2, 2, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Stream.of(
                        Block.box(7, 5, 0, 9, 6, 1),
                        Block.box(7, 5, 15, 9, 6, 16),
                        Block.box(9, 5, 14, 12, 6, 15),
                        Block.box(12, 5, 13, 13, 6, 14),
                        Block.box(13, 5, 12, 14, 6, 13),
                        Block.box(15, 5, 7, 16, 6, 9),
                        Block.box(0, 5, 7, 1, 6, 9),
                        Block.box(4, 5, 1, 7, 6, 2),
                        Block.box(4, 5, 14, 7, 6, 15),
                        Block.box(2, 5, 3, 3, 6, 4),
                        Block.box(2, 5, 12, 3, 6, 13),
                        Block.box(1, 5, 4, 2, 6, 7),
                        Block.box(1, 5, 9, 2, 6, 12),
                        Block.box(14, 5, 4, 15, 6, 7),
                        Block.box(14, 5, 9, 15, 6, 12),
                        Block.box(3, 5, 2, 4, 6, 3),
                        Block.box(3, 5, 13, 4, 6, 14),
                        Block.box(13, 5, 3, 14, 6, 4),
                        Block.box(12, 5, 2, 13, 6, 3),
                        Block.box(9, 5, 1, 12, 6, 2)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Stream.of(
                        Block.box(7, 0, 0, 9, 1, 16),
                        Block.box(15, 0, 7, 16, 1, 9),
                        Block.box(0, 0, 7, 1, 1, 9),
                        Block.box(4, 0, 1, 7, 1, 15),
                        Block.box(2, 0, 3, 3, 1, 13),
                        Block.box(1, 0, 4, 2, 1, 12),
                        Block.box(14, 0, 4, 15, 1, 12),
                        Block.box(3, 0, 2, 4, 1, 14),
                        Block.box(13, 0, 3, 14, 1, 13),
                        Block.box(12, 0, 2, 13, 1, 14),
                        Block.box(9, 0, 1, 12, 1, 15),
                        Shapes.join(Stream.of(
                                Block.box(15, 0, 9, 15, 1, 12),
                                Block.box(15, 0, 4, 15, 1, 7),
                                Block.box(14, 0, 12, 14, 1, 13),
                                Block.box(14, 0, 3, 14, 1, 4),
                                Block.box(13, 0, 13, 13, 1, 14),
                                Block.box(13, 0, 2, 13, 1, 3),
                                Block.box(12, 0, 14, 12, 1, 15),
                                Block.box(9, 0, 15, 9, 1, 16),
                                Block.box(9, 0, 0, 9, 1, 1),
                                Block.box(12, 0, 1, 12, 1, 2)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), Stream.of(
                                Block.box(4, 0, 1, 4, 1, 2),
                                Block.box(7, 0, 0, 7, 1, 1),
                                Block.box(3, 0, 2, 3, 1, 3),
                                Block.box(2, 0, 3, 2, 1, 4),
                                Block.box(2, 0, 12, 2, 1, 13),
                                Block.box(3, 0, 13, 3, 1, 14),
                                Block.box(4, 0, 14, 4, 1, 15),
                                Block.box(7, 0, 15, 7, 1, 16),
                                Block.box(1, 0, 4, 1, 1, 7),
                                Block.box(1, 0, 9, 1, 1, 12)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), BooleanOp.OR)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Stream.of(
                        Block.box(14, 6, 6, 15, 7, 10),
                        Block.box(3, 6, 12, 5, 7, 14),
                        Block.box(11, 6, 12, 13, 7, 14),
                        Block.box(11, 6, 2, 13, 7, 4),
                        Block.box(2, 6, 4, 3, 7, 6),
                        Block.box(13, 6, 4, 14, 7, 6),
                        Block.box(13, 6, 10, 14, 7, 12),
                        Block.box(2, 6, 10, 3, 7, 12),
                        Block.box(1, 6, 6, 2, 7, 10),
                        Block.box(5, 6, 14, 11, 7, 15),
                        Block.box(5, 6, 1, 11, 7, 2),
                        Block.box(3, 6, 2, 5, 7, 4)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Stream.of(
                        Block.box(7, 7, 1, 9, 8, 15),
                        Block.box(9, 7, 2, 12, 8, 14),
                        Block.box(12, 7, 3, 13, 8, 13),
                        Block.box(13, 7, 4, 14, 8, 12),
                        Block.box(14, 7, 7, 15, 8, 9),
                        Block.box(4, 7, 2, 7, 8, 14),
                        Block.box(2, 7, 4, 3, 8, 12),
                        Block.box(1, 7, 7, 2, 8, 9),
                        Block.box(3, 7, 3, 4, 8, 13),
                        Shapes.join(Stream.of(
                                Block.box(14, 7, 4, 14, 8, 7),
                                Block.box(14, 7, 9, 14, 8, 12),
                                Block.box(13, 7, 3, 13, 8, 4),
                                Block.box(13, 7, 12, 13, 8, 13),
                                Block.box(12, 7, 2, 12, 8, 3),
                                Block.box(12, 7, 13, 12, 8, 14),
                                Block.box(9, 7, 1, 9, 8, 2),
                                Block.box(9, 7, 14, 9, 8, 15)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), Stream.of(
                                Block.box(7, 7, 14, 7, 8, 15),
                                Block.box(4, 7, 13, 4, 8, 14),
                                Block.box(3, 7, 12, 3, 8, 13),
                                Block.box(3, 7, 3, 3, 8, 4),
                                Block.box(4, 7, 2, 4, 8, 3),
                                Block.box(7, 7, 1, 7, 8, 2),
                                Block.box(2, 7, 9, 2, 8, 12),
                                Block.box(2, 7, 4, 2, 8, 7)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), BooleanOp.OR)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }


}
