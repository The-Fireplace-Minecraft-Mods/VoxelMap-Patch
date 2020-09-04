package the_fireplace.vmp.mixin;

import com.mamiyaotaru.voxelmap.util.MapChunk;
import com.mamiyaotaru.voxelmap.util.MapChunkCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MapChunkCache.class)
public abstract class MapChunkCacheMixin {
    @Shadow(remap = false)
    private int width;
    @Shadow(remap = false)
    private MapChunk[] mapChunks;
    @Shadow(remap = false)
    private int left;
    @Shadow(remap = false)
    private int right;
    @Shadow(remap = false)
    private int top;
    @Shadow(remap = false)
    private int bottom;

    @Inject(method = "isChunkSurroundedByLoaded", at = @At("HEAD"), remap = false, cancellable = true)
    private void isChunkSurroundedByLoaded(int chunkX, int chunkZ, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (chunkX >= this.left && chunkX <= this.right && chunkZ >= this.top && chunkZ <= this.bottom
            && mapChunks.length <= (chunkX - this.left) + (chunkZ - this.top) * this.width) {
            System.out.println("VoxelMap Crash Avoided");
            callbackInfo.setReturnValue(false);
        }
    }
}
