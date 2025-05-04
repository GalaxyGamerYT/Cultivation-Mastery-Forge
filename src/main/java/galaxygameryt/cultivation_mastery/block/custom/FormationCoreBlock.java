package galaxygameryt.cultivation_mastery.block.custom;

import galaxygameryt.cultivation_mastery.block.entity.ModBlockEntities;
import galaxygameryt.cultivation_mastery.block.entity.custom.FormationCoreBlockEntity;
import galaxygameryt.cultivation_mastery.util.Logger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class FormationCoreBlock extends BaseEntityBlock {
    public static final BooleanProperty ACTIVE_STATE = BooleanProperty.create("active");

    public FormationCoreBlock(Properties pProperties) {
        super(pProperties);

    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new FormationCoreBlockEntity(pos, state);
    }



    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof FormationCoreBlockEntity blockEntity) {
                blockEntity.drops();
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof FormationCoreBlockEntity blockEntity) {
                NetworkHooks.openScreen(((ServerPlayer) player), blockEntity, pos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) return createTickerHelper(blockEntityType, ModBlockEntities.FORMATION_CORE_BE.get(),
                (level1, pos1, state1, blockEntity) -> blockEntity.ClientTick(level1, pos1, state1));

        return createTickerHelper(blockEntityType, ModBlockEntities.FORMATION_CORE_BE.get(),
                (level1, pos1, state1, blockEntity) -> blockEntity.ServerTick(level1, pos1, state1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE_STATE);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return shape();
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        return true;
    }

    public VoxelShape shape() {
        return Stream.of(
                Block.box(7, 2, 7, 9, 4, 9),
                Block.box(4, 4, 5, 11, 6, 11),
                Block.box(3, 6, 3, 12, 8, 13),
                Block.box(2, 8, 2, 4, 10, 14),
                Block.box(12, 8, 2, 14, 10, 14),
                Block.box(4, 8, 2, 12, 10, 4),
                Block.box(4, 8, 12, 12, 10, 14),
                Block.box(12, 7, 6, 13, 8, 9),
                Block.box(5, 5, 11, 9, 6, 12),
                Block.box(6, 3, 7, 7, 4, 8)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }
}
