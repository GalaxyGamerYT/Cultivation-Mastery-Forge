package galaxygameryt.cultivation_mastery.item.custom;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.client.CultivationMasteryClient;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.CultivationC2SPacket;
import galaxygameryt.cultivation_mastery.util.enums.Screens;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpiritualMirrorItem extends Item {
    public SpiritualMirrorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (world.isClientSide()) {
            boolean cultivation = CultivationMastery.CLIENT_PLAYER_DATA.getCultivation();
            if (!cultivation) {
                CultivationMastery.CLIENT_PLAYER_DATA.setCultivation(true);
                ModMessages.sendToServer(new CultivationC2SPacket(true, player.getUUID()));
            }
            CultivationMasteryClient.openScreen(Screens.SPIRITUAL_MIRROR);
        }
        return InteractionResultHolder.sidedSuccess(item, world.isClientSide());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        pTooltipComponents.add(Component.translatable("item.cultivation_mastery.spiritual_mirror.tooltip"));
    }
}
