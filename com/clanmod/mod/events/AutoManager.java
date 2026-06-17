/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_304
 *  net.minecraft.class_310
 *  net.minecraft.class_3675$class_306
 *  net.minecraft.class_3675$class_307
 *  org.lwjgl.glfw.GLFW
 *  org.lwjgl.glfw.GLFWMouseButtonCallback
 *  org.lwjgl.glfw.GLFWMouseButtonCallbackI
 */
package com.clanmod.mod.events;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_3675;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public class AutoManager {
    private static final class_310 mc = class_310.method_1551();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
    public static volatile boolean clickEnabled = false;
    public static volatile int clickCount = 0;
    private static ScheduledFuture<?> clickTask;
    public static volatile boolean adEnabled;
    public static volatile String adCommand;
    public static volatile int adCount;
    private static ScheduledFuture<?> adTask;
    public static List<String> multiSendEditorLines;
    public static volatile long multiSendDelayRaw;
    public static volatile boolean multiSendUseMillis;
    public static volatile boolean multiSendRunning;
    public static volatile boolean multiSendLoop;
    public static volatile int multiSendIndex;
    private static List<String> activeSendLines;
    private static volatile long multiSendDelayMs;
    private static ScheduledFuture<?> multiSendTask;
    public static volatile Runnable onMultiSendFinish;

    private static long randomInterval() {
        return 120000L + (long)(Math.random() * 60000.0);
    }

    public static boolean isClickRunning() {
        return clickTask != null && !clickTask.isDone();
    }

    public static void startClickOnClose() {
        AutoManager.stopClick();
        if (clickEnabled) {
            scheduler.execute(AutoManager::doDoubleClick);
            AutoManager.scheduleNextClick();
        }
    }

    public static void stopClick() {
        if (clickTask != null) {
            clickTask.cancel(false);
            clickTask = null;
        }
    }

    private static void scheduleNextClick() {
        if (clickEnabled) {
            clickTask = scheduler.schedule(() -> {
                AutoManager.doDoubleClick();
                AutoManager.scheduleNextClick();
            }, AutoManager.randomInterval(), TimeUnit.MILLISECONDS);
        }
    }

    private static long getGLFWHandle() {
        try {
            Field[] fields;
            Field[] var1 = fields = mc.method_22683().getClass().getDeclaredFields();
            int var2 = fields.length;
            for (int var3 = 0; var3 < var2; ++var3) {
                Field f = var1[var3];
                if (f.getType() != Long.TYPE) continue;
                f.setAccessible(true);
                long v = (Long)f.get(mc.method_22683());
                if (v == 0L) continue;
                return v;
            }
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
        return 0L;
    }

    private static void doDoubleClick() {
        if (clickEnabled) {
            scheduler.schedule(() -> mc.execute(() -> {
                if (clickEnabled && AutoManager.mc.field_1724 != null) {
                    class_304.method_1420((class_3675.class_306)class_3675.class_307.field_1672.method_1447(0));
                }
            }), 200L, TimeUnit.MILLISECONDS);
            int[] attempts = new int[]{0};
            ScheduledFuture[] pollRef = new ScheduledFuture[]{null};
            pollRef[0] = scheduler.scheduleAtFixedRate(() -> {
                int var10003 = attempts[0];
                int var10000 = attempts[0];
                attempts[0] = var10003 + 1;
                if (attempts[0] > 30) {
                    pollRef[0].cancel(false);
                } else {
                    boolean screenOpen;
                    CompletableFuture screenCheck = new CompletableFuture();
                    mc.execute(() -> screenCheck.complete(AutoManager.mc.field_1755 != null));
                    try {
                        screenOpen = (Boolean)screenCheck.get(50L, TimeUnit.MILLISECONDS);
                    }
                    catch (Exception var6) {
                        return;
                    }
                    if (screenOpen) {
                        pollRef[0].cancel(false);
                        mc.execute(() -> {
                            long handle;
                            if (clickEnabled && (handle = AutoManager.getGLFWHandle()) != 0L) {
                                double[] xArr = new double[1];
                                double[] yArr = new double[1];
                                GLFW.glfwGetCursorPos((long)handle, (double[])xArr, (double[])yArr);
                                GLFW.glfwSetCursorPos((long)handle, (double)xArr[0], (double)(yArr[0] - 100.0));
                            }
                        });
                        scheduler.schedule(() -> mc.execute(() -> {
                            long handle;
                            if (clickEnabled && (handle = AutoManager.getGLFWHandle()) != 0L) {
                                if (AutoManager.mc.field_1755 != null) {
                                    GLFWMouseButtonCallback cb = GLFW.glfwSetMouseButtonCallback((long)handle, (GLFWMouseButtonCallbackI)null);
                                    if (cb != null) {
                                        GLFW.glfwSetMouseButtonCallback((long)handle, (GLFWMouseButtonCallbackI)cb);
                                        cb.invoke(handle, 0, 1, 0);
                                        cb.invoke(handle, 0, 0, 0);
                                    }
                                } else {
                                    class_304.method_1420((class_3675.class_306)class_3675.class_307.field_1672.method_1447(0));
                                }
                                ++clickCount;
                            }
                        }), 150L, TimeUnit.MILLISECONDS);
                    }
                }
            }, 800L, 100L, TimeUnit.MILLISECONDS);
        }
    }

    public static boolean isAdRunning() {
        return adTask != null && !adTask.isDone();
    }

    public static void startAdOnClose() {
        AutoManager.stopAd();
        if (adEnabled) {
            scheduler.execute(AutoManager::doSendAd);
            AutoManager.scheduleNextAd();
        }
    }

    public static void stopAd() {
        if (adTask != null) {
            adTask.cancel(false);
            adTask = null;
        }
    }

    private static void scheduleNextAd() {
        if (adEnabled) {
            adTask = scheduler.schedule(() -> {
                AutoManager.doSendAd();
                AutoManager.scheduleNextAd();
            }, AutoManager.randomInterval(), TimeUnit.MILLISECONDS);
        }
    }

    private static void doSendAd() {
        String cmd;
        if (adEnabled && !(cmd = adCommand.trim()).isEmpty()) {
            mc.execute(() -> {
                if (AutoManager.mc.field_1724 != null) {
                    if (cmd.startsWith("/")) {
                        AutoManager.mc.field_1724.field_3944.method_45730(cmd.substring(1));
                    } else {
                        AutoManager.mc.field_1724.field_3944.method_45729(cmd);
                    }
                    ++adCount;
                }
            });
        }
    }

    public static void startMultiSend(List<String> lines, long delayMs) {
        AutoManager.stopMultiSend();
        activeSendLines = new ArrayList<String>(lines);
        multiSendDelayMs = Math.max(delayMs, 1L);
        multiSendIndex = 0;
        multiSendRunning = true;
        multiSendTask = scheduler.schedule(() -> {
            while (multiSendRunning && !activeSendLines.isEmpty()) {
                if (!multiSendLoop && multiSendIndex >= activeSendLines.size()) {
                    multiSendRunning = false;
                    AutoManager.fireFinish();
                    return;
                }
                int idx = multiSendIndex % activeSendLines.size();
                String line = activeSendLines.get(idx);
                mc.execute(() -> {
                    if (multiSendRunning && AutoManager.mc.field_1724 != null) {
                        if (line.startsWith("/")) {
                            AutoManager.mc.field_1724.field_3944.method_45730(line.substring(1));
                        } else {
                            AutoManager.mc.field_1724.field_3944.method_45729(line);
                        }
                    }
                });
                if (!multiSendLoop && ++multiSendIndex >= activeSendLines.size()) {
                    multiSendRunning = false;
                    AutoManager.fireFinish();
                    return;
                }
                try {
                    Thread.sleep(multiSendDelayMs);
                }
                catch (InterruptedException var3) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            multiSendRunning = false;
            AutoManager.fireFinish();
        }, 0L, TimeUnit.MILLISECONDS);
    }

    public static void stopMultiSend() {
        multiSendRunning = false;
        if (multiSendTask != null) {
            multiSendTask.cancel(false);
            multiSendTask = null;
        }
    }

    public static int getActiveSendTotal() {
        return activeSendLines.size();
    }

    private static void fireFinish() {
        Runnable cb = onMultiSendFinish;
        if (cb != null) {
            mc.execute(cb);
        }
    }

    public static void shutdownAll() {
        AutoManager.stopClick();
        AutoManager.stopAd();
        AutoManager.stopMultiSend();
        scheduler.shutdownNow();
    }

    static {
        adEnabled = false;
        adCommand = "";
        adCount = 0;
        multiSendEditorLines = new ArrayList<String>(Arrays.asList("", "", "", "", "", "", "", ""));
        multiSendDelayRaw = 1L;
        multiSendUseMillis = false;
        multiSendRunning = false;
        multiSendLoop = true;
        multiSendIndex = 0;
        activeSendLines = new ArrayList<String>();
        multiSendDelayMs = 1000L;
        onMultiSendFinish = null;
    }
}
