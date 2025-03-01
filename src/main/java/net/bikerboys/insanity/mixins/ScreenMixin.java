package net.bikerboys.insanity.mixins;




import net.bikerboys.insanity.ExampleMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Mutable @Shadow @Final protected Component title;
    @Shadow public abstract Component getTitle();
    @Unique private Component originalTitle = null;
    @Unique private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void changetitle(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        // Store the original title the first time we run


        if (originalTitle == null) {
            originalTitle = getTitle();
        }

        // Check if we should display the crazy word
        if (ExampleMod.currentcrazyword != null && ExampleMod.iscrazyword) {
            // Set the title to the crazy word
            this.title = ExampleMod.currentcrazyword;

            // Schedule a task to revert the title after 5 seconds.
            executor.schedule(() -> {
                // IMPORTANT: If you need to update GUI elements,
                // ensure you run this on the main thread.
                this.title = originalTitle;
                // Reset our flag if needed.
                ExampleMod.iscrazyword = false;
            }, 5, TimeUnit.SECONDS);
        }



    }

}




