package galaxygameryt.cultivation_mastery.datagen;

import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CultivationMastery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // https://www.youtube.com/watch?v=enzKJWq0vNI&list=PLKGarocXCE1H9Y21-pxjt5Pt8bW14twa-&index=12&t=4m30s
    }
}
