package net.bikerboys.insanity.mixins;



import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mixin(Screen.class)
public class ScreenMixin {

    @Mutable
    @Shadow @Final protected Component title;

    @Shadow @Nullable protected Minecraft minecraft;
    List<String> crazywords = new ArrayList<String>();

    public void addwords() {
        crazywords.add("NO ONE WILL BELIEVE YOU");
        crazywords.add("THIS ISN'T REAL");
        crazywords.add("YOU DID THIS");
        crazywords.add("WAKE UP WAKE UP WAKE UP WAKE UP");
        crazywords.add("DON'T TRUST THEM");
        crazywords.add("TRUST NO ONE");
        crazywords.add("DON'T TRUST THE TREES");
        crazywords.add("DON'T TRUST HIM");
        crazywords.add("RUN");
        crazywords.add("is someone reading this?");
        crazywords.add("HELP ME");
        crazywords.add("Are you sure that you're alone?");
        crazywords.add("It can hear you");
        crazywords.add("ITS WATCHING");
        crazywords.add("THATS NOT HIM");
        crazywords.add("HE GOT REPLACED");


    }
    long startTime = System.currentTimeMillis();
    // Tracks the active replacement title and its expiration time (in ms)
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    @Inject(method = "getTitle", at = @At("TAIL"), cancellable = true)

    private void changetitle(CallbackInfoReturnable<Component> cir) {

        addwords();
        long currentTime = System.currentTimeMillis();
        Random random = new Random();
        if (random.nextInt(1, 300) == 3) {
            Component oldtitle = this.title;


          //  @NotNull LazyOptional<ISanity> sanity = minecraft.player.getCapability(SanityProvider.CAP);
          //  System.out.println(sanity);

            int randomindex = random.nextInt(1, crazywords.size());

            this.title = Component.literal(crazywords.get(randomindex));
            executor.schedule(() -> {
                this.title = oldtitle;

            }, 3500, TimeUnit.MILLISECONDS);



        }

    }


}

