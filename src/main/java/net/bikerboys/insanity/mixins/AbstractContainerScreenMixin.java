package net.bikerboys.insanity.mixins;



import net.bikerboys.insanity.ExampleMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Mixin(GuiGraphics.class)
public abstract class AbstractContainerScreenMixin implements net.minecraftforge.client.extensions.IForgeGuiGraphics {

    @Unique
    private static final Map<Integer, Long> REPLACEMENT_TRACKER = new HashMap<>();
    @Unique
    private static final int REPLACEMENT_DURATION_MS = 10000;

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private ItemStack modifyItemStack(ItemStack originalStack) {
       if (!originalStack.isEmpty()) {
           int key = System.identityHashCode(originalStack);
           long currentTime = System.currentTimeMillis();

           // Check if replacement is already active
           if (REPLACEMENT_TRACKER.containsKey(key)) {
               long expirationTime = REPLACEMENT_TRACKER.get(key);
               if (currentTime < expirationTime) {
                   return new ItemStack(ExampleMod.EXAMPLE_ITEM.get()); // Still within replacement window
               } else {
                   REPLACEMENT_TRACKER.remove(key); // Cleanup expired entry
               }
           }


           Random random = new Random();
           if (random.nextInt(350000) == 10) {
               REPLACEMENT_TRACKER.put(key, currentTime + REPLACEMENT_DURATION_MS);
               return new ItemStack(ExampleMod.EXAMPLE_ITEM.get());
           }
       }
        return originalStack;
    }
}