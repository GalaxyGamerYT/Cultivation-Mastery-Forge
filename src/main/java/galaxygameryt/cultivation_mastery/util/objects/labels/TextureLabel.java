package galaxygameryt.cultivation_mastery.util.objects.labels;

import net.minecraft.resources.ResourceLocation;

public class TextureLabel extends LabelObject{
    ResourceLocation texture;
    int width;
    int height;
    int left;
    int top;
    int xOffset;
    int yOffset;
    int textureWidth;
    int textureHeight;
    boolean visible = false;

    public TextureLabel(ResourceLocation texture) {
        this(texture, 0, 0, 0, 0);
    }

    public TextureLabel(ResourceLocation texture, int left, int top, int width, int height) {
        this(texture, left, top, width, height, 0, 0);
    }

    public TextureLabel(ResourceLocation texture, int left, int top, int width, int height, int xOffset, int yOffset) {
        this(texture, left, top, width, height, xOffset, yOffset, width, height);
    }

    public TextureLabel(ResourceLocation texture, int left, int top, int width, int height, int xOffset, int yOffset, int textureWidth, int textureHeight) {
        super(left, top);
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }
}
