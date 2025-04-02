package galaxygameryt.cultivation_mastery.item.custom;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.networking.ModMessages;
import galaxygameryt.cultivation_mastery.networking.packet.C2S.CultivationC2SPacket;
import galaxygameryt.cultivation_mastery.screen.custom.SpiritualMirrorScreen;
import galaxygameryt.cultivation_mastery.util.player_data.ClientPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpiritualMirrorItem extends Item {
    public SpiritualMirrorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack item = player.getItemInHand(hand);
        if (world.isClientSide) {
            boolean cultivation = CultivationMastery.CLIENT_PLAYER_DATA.getCultivation();
            if (cultivation) {
                Minecraft.getInstance().setScreen(new SpiritualMirrorScreen());
            } else {
                CultivationMastery.CLIENT_PLAYER_DATA.setCultivation(true);
                ModMessages.sendToServer(new CultivationC2SPacket(true, player.getUUID()));
                Minecraft.getInstance().setScreen(new SpiritualMirrorScreen());
            }
        }
        return InteractionResultHolder.success(item);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);

        pTooltipComponents.add(Component.translatable("item.cultivation_mastery.spiritual_mirror.tooltip"));
    }
}
