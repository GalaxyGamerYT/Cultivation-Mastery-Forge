package galaxygameryt.cultivation_mastery.event.handlers;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import galaxygameryt.cultivation_mastery.util.SavedData.FormationCoreSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = CultivationMastery.MOD_ID)
public class FormationChunkHandler {
    public static final Set<BlockPos> LOADED_FORMATION_CORES = new HashSet<>();

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        LevelChunk chunk = (LevelChunk) event.getChunk();
        ChunkPos chunkPos = chunk.getPos();

        FormationCoreSavedData data = FormationCoreSavedData.get(level);
        for (BlockPos corePos : data.getAllFormationCores()) {
            if (chunkPos.equals(new ChunkPos(corePos))) {
                LOADED_FORMATION_CORES.add(corePos);
            }
        }
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        LevelChunk chunk = (LevelChunk) event.getChunk();
        ChunkPos chunkpos = chunk.getPos();

        LOADED_FORMATION_CORES.removeIf(pos -> chunkpos.equals(new ChunkPos(pos)));
    }
}
