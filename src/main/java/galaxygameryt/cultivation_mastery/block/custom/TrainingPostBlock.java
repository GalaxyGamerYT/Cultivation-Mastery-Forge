package galaxygameryt.cultivation_mastery.block.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class TrainingPostBlock extends Block {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final VoxelShape SHAPE_UPPER = Stream.of(
            Block.box(4, 0, 4, 12, 16, 12),
            Block.box(4, 16, 4, 12, 16, 12),
            Block.box(4, 0, 4, 12, 0, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape SHAPE_LOWER = Stream.of(
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(2, 2, 2, 14, 4, 14),
            Block.box(4, 4, 4, 12, 16, 12),
            Block.box(4, 16, 4, 12, 16, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();


    public float trainingMultiplier;

    public TrainingPostBlock(Properties pProperties, float trainingMultiplier) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HALF, DoubleBlockHalf.LOWER));
        this.trainingMultiplier = trainingMultiplier;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

        pTooltip.add(Component.translatable("block.cultivation_mastery.training_post.tooltip.1"));
        pTooltip.add(Component.translatable("block.cultivation_mastery.training_post.tooltip.2"));
        pTooltip.add(Component.literal(String.format(" - +%.2f to Body Tempering",trainingMultiplier)));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        boolean lower = pState.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER;

        return lower ? SHAPE_LOWER : SHAPE_UPPER;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();

        if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(pContext)) {
            return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        DoubleBlockHalf half = pState.getValue(HALF);
        BlockPos otherPart = half == DoubleBlockHalf.LOWER ? pPos.above() : pPos.below();
        BlockState otherState = pLevel.getBlockState(otherPart);

        if (otherState.getBlock() == this && otherState.getValue(HALF) != half) {
            pLevel.destroyBlock(otherPart, false);
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }
}
