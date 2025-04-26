package galaxygameryt.cultivation_mastery.client;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.client.gui.overlays.ModOverlays;
import galaxygameryt.cultivation_mastery.client.gui.screens.ModMenuScreens;
import galaxygameryt.cultivation_mastery.client.gui.screens.custom.SpiritualMirrorScreen;
import galaxygameryt.cultivation_mastery.client.gui.toasts.BreakthroughToast;
import galaxygameryt.cultivation_mastery.entity.ModEntities;
import galaxygameryt.cultivation_mastery.entity.client.SitableRenderer;
import galaxygameryt.cultivation_mastery.particles.ModParticles;
import galaxygameryt.cultivation_mastery.particles.custom.QiParticle;
import galaxygameryt.cultivation_mastery.util.KeyBinding;
import galaxygameryt.cultivation_mastery.util.enums.Screens;
import galaxygameryt.cultivation_mastery.util.enums.Toasts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CultivationMasteryClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModMenuScreens.register(event);

        EntityRenderers.register(ModEntities.MEDITATION.get(), SitableRenderer::new);
    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        KeyBinding.register(event);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        ModOverlays.register(event);
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.QI_PARTICLE.get(), QiParticle.Provider::new);
    }

    public static void openScreen(Screens screen) {
        Minecraft instance = Minecraft.getInstance();

        switch (screen) {
            case spiritual_mirror:
                instance.setScreen(new SpiritualMirrorScreen());
        }
    }

    public static void addToast(Toasts toast) {
        Minecraft instance = Minecraft.getInstance();
        ToastComponent toastComponent = instance.getToasts();

        switch (toast) {
            case breakthrough:
                toastComponent.addToast(new BreakthroughToast());
                instance.player.sendSystemMessage(Component.literal("Sending Breakthrough Toast!"));
        }
    }
}

