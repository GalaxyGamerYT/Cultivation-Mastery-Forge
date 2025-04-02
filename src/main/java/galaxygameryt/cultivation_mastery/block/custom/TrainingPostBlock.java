package galaxygameryt.cultivation_mastery.block.custom;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.capabilites.body.PlayerBodyProvider;
import galaxygameryt.cultivation_mastery.capabilites.qi.PlayerQiProvider;
import galaxygameryt.cultivation_mastery.util.player_data.ServerPlayerData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
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
//    getDestroyProgress
//    attack

    public float BodyMultiplier;
    public float QiMultiplier;

    public TrainingPostBlock(Properties pProperties, float trainingMultiplier, float qiMultiplier) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HALF, DoubleBlockHalf.LOWER));
        this.BodyMultiplier = trainingMultiplier;
        this.QiMultiplier = qiMultiplier;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

        pTooltip.add(Component.translatable("block.cultivation_mastery.training_post.tooltip.1"));
        pTooltip.add(Component.translatable("block.cultivation_mastery.training_post.tooltip.2"));
        pTooltip.add(Component.literal(String.format(" - +%.2f to Body Tempering", BodyMultiplier)));
        pTooltip.add(Component.literal(String.format(" - +%.2f to Qi Absorbing", QiMultiplier)));
    }

    @Override
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide() && pPlayer instanceof ServerPlayer player) {
            if(pPlayer.isCrouching() && player.getMainHandItem().isEmpty()) {
                pLevel.destroyBlock(pPos, true);
                boolean lower = pState.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER;
                pLevel.destroyBlock(lower ? pPos.above() : pPos.below(), false);
            } else {
                player.sendSystemMessage(Component.literal("Shift Right Click to break or punch with an empty hand to train")
                        .withStyle(ChatFormatting.RED));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        super.attack(pState, pLevel, pPos, pPlayer);
        if (!pLevel.isClientSide() && pPlayer instanceof ServerPlayer player) {
            addData(player);

            pLevel.playSound(player, pPos, SoundEvents.WOOD_BREAK, SoundSource.PLAYERS,
                    0.5f, pLevel.random.nextFloat() * 0.1F + 0.9F);
        }
    }

    public void addData(Player player) {
        Random rand = new Random();
        ServerPlayerData playerData = CultivationMastery.SERVER_PLAYER_DATA_MAP.get(player.getUUID());

        float bodyData = rand.nextFloat(0.2f, 0.5f) * BodyMultiplier;
        playerData.addBody(bodyData);
        player.getCapability(PlayerBodyProvider.PLAYER_BODY).ifPresent(capability -> {
            capability.setBody(playerData.getBody());
        });

        float qiData = rand.nextFloat(0.2f, 0.5f) * QiMultiplier;
        playerData.addQi(qiData);
        player.getCapability(PlayerQiProvider.PLAYER_QI).ifPresent(capability -> {
            capability.setQi(playerData.getQi());
        });

        CultivationMastery.SERVER_PLAYER_DATA_MAP.put(player.getUUID(), playerData);
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
