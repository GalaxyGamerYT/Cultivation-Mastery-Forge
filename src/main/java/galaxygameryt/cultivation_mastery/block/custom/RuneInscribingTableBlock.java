package galaxygameryt.cultivation_mastery.block.custom;

import galaxygameryt.cultivation_mastery.client.gui.screens.custom.rune_inscribing_table.RuneInscribingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class RuneInscribingTableBlock extends Block {
    private static final Component CONTAINER_TITLE = Component.translatable("menu.title.cultivation_mastery.rune_inscribing_table");

    public RuneInscribingTableBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return shape();
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos));
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        return new SimpleMenuProvider((containerId, playerInventory, player) -> new RuneInscribingTableMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos)), CONTAINER_TITLE);
    }

    @Override
    public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        return true;
    }

    public static VoxelShape shape() {
        return Stream.of(
                Shapes.join(Block.box(0, 0, 0, 16, 1, 16), Block.box(2, 1, 2, 14, 2, 14), BooleanOp.OR),
                Shapes.join(Block.box(5, 2, 5, 11, 14, 11), Stream.of(
                        Shapes.join(Block.box(7, 6, 4, 9, 8, 5), Stream.of(
                                Block.box(6, 5, 4.5, 10, 6, 5),
                                Block.box(6, 8, 4.5, 10, 9, 5),
                                Block.box(6, 6, 4.5, 7, 8, 5),
                                Block.box(9, 6, 4.5, 10, 8, 5)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), BooleanOp.OR),
                Shapes.join(Block.box(4, 6, 7, 5, 8, 9), Stream.of(
                        Block.box(4.5, 5, 6, 5, 6, 10),
                        Block.box(4.5, 8, 6, 5, 9, 10),
                        Block.box(4.5, 6, 9, 5, 8, 10),
                        Block.box(4.5, 6, 6, 5, 8, 7)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), BooleanOp.OR),
                Shapes.join(Block.box(7, 6, 11, 9, 8, 12), Stream.of(
                        Block.box(6, 5, 11, 10, 6, 11.5),
                        Block.box(6, 8, 11, 10, 9, 11.5),
                        Block.box(9, 6, 11, 10, 8, 11.5),
                        Block.box(6, 6, 11, 7, 8, 11.5)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), BooleanOp.OR),
                Shapes.join(Block.box(11, 6, 7, 12, 8, 9), Stream.of(
                        Block.box(11, 5, 6, 11.5, 6, 10),
                        Block.box(11, 8, 6, 11.5, 9, 10),
                        Block.box(11, 6, 6, 11.5, 8, 7),
                        Block.box(11, 6, 9, 11.5, 8, 10)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), BooleanOp.OR)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), BooleanOp.OR),
                Stream.of(
                        Block.box(3, 15, 3, 4, 15.5, 13),
                        Block.box(12, 15, 3, 13, 15.5, 13),
                        Block.box(4, 15, 3, 12, 15.5, 4),
                        Block.box(4, 15, 12, 12, 15.5, 13),
                        Block.box(3, 14, 3, 13, 15, 13)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }
}
