package me.itsy.pixelqueue.Objects;

import me.itsy.pixelqueue.Formats.AnythingGoes;
import me.itsy.pixelqueue.Formats.OverUsed;
import me.itsy.pixelqueue.PixelQueue;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.util.concurrent.TimeUnit;

public class TimerObject {


    public static void startTimer(){
        Task matchingTimer = Task.builder()
                .execute(() -> OverUsed.matchOUPlayers())
                .execute(() -> AnythingGoes.matchAGPlayers())
                .execute(() -> PixelQueue.timer = 30)
                .execute(() -> Sponge.getServer().getBroadcastChannel().send(Text.of("Timer Running")))
                .async()
                .interval(30, TimeUnit.SECONDS)
                .name("Match players together in a queue").submit(PixelQueue.getInstance());


        Task timerQuee = Task.builder()
                .execute(() -> PixelQueue.timer = PixelQueue.timer - 1)
                .async()
                .interval(1, TimeUnit.SECONDS)
                .name("Match  together in a queue").submit(PixelQueue.getInstance());

    }





}
