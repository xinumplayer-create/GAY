/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 */
package com.clanmod.mod.events;

import com.clanmod.mod.events.ClanChatHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.class_310;

public class ChatSender {
    private static final class_310 mc = class_310.method_1551();
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "ChatSender");
        t.setDaemon(true);
        return t;
    });
    public static volatile boolean isRunning = false;
    public static volatile String linesText = "";
    public static volatile long delayMs = 1000L;
    public static volatile boolean useSeconds = false;
    private static final List<ScheduledFuture<?>> tasks = new ArrayList();
    private static volatile int sentCount = 0;
    private static volatile int totalLines = 0;

    public static int getSentCount() {
        return sentCount;
    }

    public static int getTotalLines() {
        return totalLines;
    }

    public static void start() {
        if (isRunning) {
            return;
        }
        ChatSender.stopInternal();
        String[] lines = linesText.split("\n", -1);
        ArrayList<String> toSend = new ArrayList<String>();
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            toSend.add(trimmed);
        }
        if (toSend.isEmpty()) {
            return;
        }
        isRunning = true;
        sentCount = 0;
        totalLines = toSend.size();
        for (int i = 0; i < toSend.size(); ++i) {
            String msg = (String)toSend.get(i);
            long delay = delayMs * (long)i;
            ScheduledFuture<?> f = scheduler.schedule(() -> {
                if (!isRunning) {
                    return;
                }
                mc.execute(() -> {
                    if (ChatSender.mc.field_1724 != null) {
                        if (msg.startsWith("/")) {
                            ChatSender.mc.field_1724.field_3944.method_45730(msg.substring(1));
                        } else {
                            ChatSender.mc.field_1724.field_3944.method_45729(msg);
                        }
                        ++sentCount;
                        ClanChatHandler.addLog("[SENDER] " + msg, -10092391);
                    }
                });
            }, delay, TimeUnit.MILLISECONDS);
            tasks.add(f);
        }
        long totalDelay = delayMs * (long)toSend.size() + 500L;
        scheduler.schedule(() -> {
            isRunning = false;
        }, totalDelay, TimeUnit.MILLISECONDS);
    }

    public static void stop() {
        isRunning = false;
        ChatSender.stopInternal();
        ClanChatHandler.addLog("[SENDER] \u041e\u0442\u043f\u0440\u0430\u0432\u043a\u0430 \u043e\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d\u0430 (" + sentCount + "/" + totalLines + ")", -48060);
    }

    private static void stopInternal() {
        for (ScheduledFuture<?> f : tasks) {
            f.cancel(false);
        }
        tasks.clear();
    }

    public static void shutdownAll() {
        ChatSender.stop();
        scheduler.shutdownNow();
    }
}
