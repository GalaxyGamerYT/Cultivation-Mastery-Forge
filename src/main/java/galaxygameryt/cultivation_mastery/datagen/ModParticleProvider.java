package galaxygameryt.cultivation_mastery.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import galaxygameryt.cultivation_mastery.CultivationMastery;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModParticleProvider implements DataProvider {

    private final PackOutput output;

    // Particle name -> texture path
    private final Map<String, List<String>> particleData = Map.of(
            "qi_particle", animatedParticle("qi_particle", 4)
    );

    public ModParticleProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        return CompletableFuture.allOf(
                particleData.entrySet().stream().map(entry -> {
                    String name = entry.getKey();
                    List<String> textures = entry.getValue();

                    JsonObject particleJson = new JsonObject();
                    JsonArray textureArray = new JsonArray();
                    textures.forEach(textureArray::add);
                    particleJson.add("textures", textureArray);

                    Path filePath = output.getOutputFolder()
                            .resolve("assets/"+CultivationMastery.MOD_ID+"/particles/" + name + ".json");

                    return DataProvider.saveStable(cache, particleJson, filePath);
                }).toArray(CompletableFuture[]::new)
        );
    }

    public List<String> particle(String name) {
        return List.of(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, name+"/"+name).toString());
    }

    public List<String> animatedParticle(String name, int amount) {
        List<String> textures = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            textures.add(ResourceLocation.fromNamespaceAndPath(CultivationMastery.MOD_ID, name+"/"+name+"_"+i).toString());
        }
        return textures;
    }

    @Override
    public @NotNull String getName() {
        return "Cultivation Mastery Particles";
    }
}
