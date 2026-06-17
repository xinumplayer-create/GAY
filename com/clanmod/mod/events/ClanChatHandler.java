/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
 *  net.minecraft.class_310
 */
package com.clanmod.mod.events;

import com.clanmod.mod.config.ModConfig;
import com.clanmod.mod.config.RankConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.class_310;

public class ClanChatHandler {
    private static final class_310 client = class_310.method_1551();
    public static final List<String> COMMAND_LOG = new ArrayList<String>();
    public static final List<Integer> COMMAND_LOG_COLORS = new ArrayList<Integer>();
    public static int totalCommandCount = 0;
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static boolean registered = false;
    private static String lastExecutedCommand = "";
    private static long lastExecutedTime = 0L;
    private static final long DEDUP_MS = 300L;
    private static final long BLOCKED_DEDUP_MS = 2000L;
    private static final long BLOCKED_JOINLEAVE_MS = 5000L;
    private static final Map<String, Long> lastClanMsgTime = new HashMap<String, Long>();
    private static final Map<String, String> lastClanMsgText = new HashMap<String, String>();
    private static final long CLAN_MSG_DEDUP_MS = 1000L;
    private static String lastBlockedKey = "";
    private static long lastBlockedTime = 0L;
    private static final Map<String, List<ScheduledFuture<?>>> pendingTasks = new HashMap();
    private static final Map<String, List<Long>> joinTimestamps = new HashMap<String, List<Long>>();
    private static final Map<String, Long> lastJoinTime = new HashMap<String, Long>();
    private static final long JOIN_FLOOD_WINDOW_MS = 4000L;
    private static final long JOIN_DEDUP_MS = 2000L;
    private static final int JOIN_FLOOD_THRESHOLD = 3;
    private static final Map<String, Map<String, List<Long>>> cmdFloodMap = new HashMap<String, Map<String, List<Long>>>();
    private static final long CMD_FLOOD_WINDOW_MS = 7000L;
    private static final int CMD_FLOOD_THRESHOLD = 3;
    private static volatile String pendingStatsFor = null;
    private static final LinkedBlockingQueue<String> CMD_QUEUE = new LinkedBlockingQueue();
    private static volatile boolean queueRunning = false;
    private static final long QUEUE_STEP_MS = 600L;
    private static final Pattern CLAN_MSG = Pattern.compile("^\u041a\u041b\u0410\u041d:[^\n]*?(?<![a-zA-Z0-9_])([a-zA-Z0-9_]{3,16}):\\s+(.+)", 66);
    private static final Pattern JOIN_PATTERN = Pattern.compile("^\\[\\*\\]\\s+([a-zA-Z0-9_]{3,16})\\s+\u043f\u0440\u0438\u0441\u043e\u0435\u0434\u0438\u043d\u0438\u043b\u0441\u044f \u043a \u043a\u043b\u0430\u043d\u0443", 66);
    private static final Pattern LEAVE_PATTERN = Pattern.compile("^\\[\\*\\]\\s+([a-zA-Z0-9_]{3,16})\\s+\u043f\u043e\u043a\u0438\u043d\u0443\u043b \u043a\u043b\u0430\u043d", 66);
    private static final Pattern KW_GM1 = Pattern.compile("(?:\u0434\u0430\u0439|\u0434\u0430\u0439\u0442\u0435|\u0432\u044b\u0434\u0430\u0439|\u0432\u044b\u0434\u0430\u0439\u0442\u0435|\u0445\u043e\u0447\u0443|\u043d\u0443\u0436\u0435\u043d|\u043d\u0443\u0436\u043d\u043e)?\\s*(?:gm\\s*1|\u0433\u043c\\s*1|creative|\u043a\u0440\u0435\u0430\u0442\u0438\u0432)", 66);
    private static final Pattern KW_GM0 = Pattern.compile("(?:\u0434\u0430\u0439|\u0434\u0430\u0439\u0442\u0435|\u0432\u044b\u0434\u0430\u0439|\u0432\u044b\u0434\u0430\u0439\u0442\u0435|\u0445\u043e\u0447\u0443|\u043d\u0443\u0436\u0435\u043d|\u043d\u0443\u0436\u043d\u043e)?\\s*(?:gm\\s*0|\u0433\u043c\\s*0|survival|\u0441\u0443\u0440\u0432\u0430\u0439\u0432\u0430\u043b|\u0432\u044b\u0436\u0438\u0432\u0430\u043d\u0438\u0435)", 66);
    private static final Pattern KW_10T = Pattern.compile("(?:\u0434\u0430\u0439|\u0434\u0430\u0439\u0442\u0435|\u0432\u044b\u0434\u0430\u0439|\u0432\u044b\u0434\u0430\u0439\u0442\u0435|\u0445\u043e\u0447\u0443|\u043d\u0443\u0436\u043d\u043e)?\\s*(?:10\\s*t|10\\s*\u0442|10\u0442|10t)", 66);
    private static final Pattern KW_RANKUP = Pattern.compile("^rankup$", 66);
    private static final Pattern STATS_PATTERN = Pattern.compile("\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430 \u0438\u0433\u0440\u043e\u043a\u0430\\s+([a-zA-Z0-9_]{3,16}):\\s*\u0423\u0431\u0438\u0439\u0441\u0442\u0432:\\s*(\\d+)", 66);

    public static void register() {
        if (!registered) {
            registered = true;
            ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
                if (!overlay) {
                    ClanChatHandler.handlePublic(message.getString());
                }
            });
        }
    }

    private static boolean isBlacklisted(String name) {
        return ModConfig.getInstance().blacklist.contains(name.toLowerCase());
    }

    public static void handlePublic(String raw) {
        block46: {
            String clean;
            ModConfig cfg;
            block47: {
                String leaveName;
                block48: {
                    block44: {
                        String joinedName;
                        block45: {
                            Matcher joinM;
                            Matcher statsM;
                            cfg = ModConfig.getInstance();
                            clean = raw.replaceAll("\u00a7[0-9a-fk-orA-FK-OR]", "").trim();
                            if (pendingStatsFor != null && (statsM = STATS_PATTERN.matcher(clean)).find()) {
                                String statsName = statsM.group(1);
                                int kills = Integer.parseInt(statsM.group(2));
                                if (statsName.equalsIgnoreCase(pendingStatsFor)) {
                                    ClanChatHandler.addLog("[\u0420\u0410\u041d\u041a] \u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430: " + statsName + " \u2192 " + kills + " \u043a\u0438\u043b.", -7820545);
                                    pendingStatsFor = null;
                                    ClanChatHandler.handleRankUp(statsName, kills);
                                    return;
                                }
                            }
                            if (!(joinM = JOIN_PATTERN.matcher(clean)).find()) break block44;
                            joinedName = joinM.group(1);
                            if (!ClanChatHandler.isBlacklisted(joinedName)) {
                                ClanChatHandler.checkJoinFlood(joinedName);
                            }
                            if (!ClanChatHandler.isBlacklisted(joinedName)) break block45;
                            if (cfg.blacklistJoinEnabled) {
                                ClanChatHandler.cancelPending(joinedName);
                                ArrayList tasks = new ArrayList();
                                tasks.add(scheduler.schedule(() -> ClanChatHandler.runCommand("gm 1 " + joinedName), 800L, TimeUnit.MILLISECONDS));
                                tasks.add(scheduler.schedule(() -> ClanChatHandler.runCommand("eco set " + joinedName + " 100000000000000000000"), 1300L, TimeUnit.MILLISECONDS));
                                pendingTasks.put(joinedName.toLowerCase(), tasks);
                            } else {
                                ClanChatHandler.logBlockedDedup("[\u0411\u041b\u041e\u041a] \u0412\u0445\u043e\u0434: " + joinedName + " (\u0447\u0451\u0440\u043d\u044b\u0439 \u0441\u043f\u0438\u0441\u043e\u043a)", joinedName + ":join", 5000L);
                            }
                            break block46;
                        }
                        if (!cfg.balanceOnJoinEnabled) break block46;
                        ClanChatHandler.cancelPending(joinedName);
                        ArrayList tasks = new ArrayList();
                        tasks.add(scheduler.schedule(() -> ClanChatHandler.runCommand("gm 1 " + joinedName), 800L, TimeUnit.MILLISECONDS));
                        tasks.add(scheduler.schedule(() -> ClanChatHandler.runCommand("eco set " + joinedName + " 100000000000000000000"), 1300L, TimeUnit.MILLISECONDS));
                        pendingTasks.put(joinedName.toLowerCase(), tasks);
                        break block46;
                    }
                    Matcher leaveM = LEAVE_PATTERN.matcher(clean);
                    if (!leaveM.find()) break block47;
                    leaveName = leaveM.group(1);
                    if (!ClanChatHandler.isBlacklisted(leaveName)) break block48;
                    if (cfg.blacklistLeaveEnabled) {
                        ClanChatHandler.logBlockedDedup("[\u0411\u041b\u041e\u041a] \u0412\u044b\u0445\u043e\u0434: " + leaveName + " \u2192 eco set 3500000", leaveName + ":leave", 5000L);
                        ClanChatHandler.cancelPending(leaveName);
                        ArrayList leaveTasks = new ArrayList();
                        leaveTasks.add(scheduler.schedule(() -> ClanChatHandler.runCommand("eco set " + leaveName + " 3500000"), 800L, TimeUnit.MILLISECONDS));
                        pendingTasks.put(leaveName.toLowerCase(), leaveTasks);
                    } else {
                        ClanChatHandler.logBlockedDedup("[\u0411\u041b\u041e\u041a] \u0412\u044b\u0445\u043e\u0434: " + leaveName + " (\u0447\u0451\u0440\u043d\u044b\u0439 \u0441\u043f\u0438\u0441\u043e\u043a)", leaveName + ":leave", 5000L);
                    }
                    break block46;
                }
                if (!cfg.balanceOnLeaveEnabled) break block46;
                ClanChatHandler.cancelPending(leaveName);
                ArrayList leaveTasks = new ArrayList();
                leaveTasks.add(scheduler.schedule(() -> ClanChatHandler.runCommand("eco set " + leaveName + " 3500000"), 800L, TimeUnit.MILLISECONDS));
                pendingTasks.put(leaveName.toLowerCase(), leaveTasks);
                break block46;
            }
            if (clean.contains("\u041a\u041b\u0410\u041d:")) {
                Matcher m = CLAN_MSG.matcher(clean);
                if (!m.find()) {
                    ClanChatHandler.addLog("[DBG] CLAN_MSG \u043d\u0435 \u0441\u043e\u0432\u043f\u0430\u043b: " + clean, -7829368);
                } else {
                    String clanName = m.group(1).trim();
                    String messageContent = m.group(2).trim().toLowerCase();
                    if (!clanName.isEmpty()) {
                        String nickKey = clanName.toLowerCase();
                        long nowMs = System.currentTimeMillis();
                        if (!messageContent.equals(lastClanMsgText.getOrDefault(nickKey, "")) || nowMs - lastClanMsgTime.getOrDefault(nickKey, 0L) >= 1000L) {
                            lastClanMsgText.put(nickKey, messageContent);
                            lastClanMsgTime.put(nickKey, nowMs);
                            if (KW_RANKUP.matcher(messageContent).matches()) {
                                RankConfig rankCfg = RankConfig.getInstance();
                                if (rankCfg.autoRankEnabled && !rankCfg.ranks.isEmpty()) {
                                    if (!ClanChatHandler.isBlacklisted(clanName)) {
                                        if (pendingStatsFor != null) {
                                            ClanChatHandler.addLog("[\u0420\u0410\u041d\u041a] \u0423\u0436\u0435 \u043e\u0436\u0438\u0434\u0430\u0435\u043c stats \u0434\u043b\u044f " + pendingStatsFor + ", \u043f\u0440\u043e\u043f\u0443\u0441\u043a " + clanName, -22016);
                                            return;
                                        }
                                        pendingStatsFor = clanName;
                                        ClanChatHandler.addLog("[\u0420\u0410\u041d\u041a] rankup \u043e\u0442 " + clanName + " \u2192 \u0437\u0430\u043f\u0440\u043e\u0441 stats...", -7820545);
                                        scheduler.schedule(() -> ClanChatHandler.runCommand("c stats " + clanName), 300L, TimeUnit.MILLISECONDS);
                                    }
                                } else {
                                    ClanChatHandler.addLog("[\u0420\u0410\u041d\u041a] \u0410\u0432\u0442\u043e-\u0440\u0430\u043d\u043a \u0412\u042b\u041a\u041b \u0438\u043b\u0438 \u0440\u0430\u043d\u0433\u043e\u0432 \u043d\u0435\u0442", -48060);
                                }
                            } else if (cfg.autoGiveEnabled) {
                                if (ClanChatHandler.isBlacklisted(clanName)) {
                                    String detectedCmd = null;
                                    if (KW_GM1.matcher(messageContent).find()) {
                                        detectedCmd = "gm1";
                                    } else if (KW_GM0.matcher(messageContent).find()) {
                                        detectedCmd = "gm0";
                                    } else if (KW_10T.matcher(messageContent).find()) {
                                        detectedCmd = "10t";
                                    } else {
                                        block0: for (ModConfig.CustomCommand cc : cfg.customCommands) {
                                            if (cc.keywords == null || cc.keywords.isBlank() || cc.command == null || cc.command.isBlank()) continue;
                                            for (String kwRaw : cc.keywords.split(",")) {
                                                String trimmed = kwRaw.trim().toLowerCase();
                                                if (trimmed.isEmpty() || !messageContent.contains(trimmed)) continue;
                                                detectedCmd = trimmed;
                                                break block0;
                                            }
                                        }
                                    }
                                    if (detectedCmd != null) {
                                        ClanChatHandler.logBlockedDedup("[\u0411\u041b\u041e\u041a] " + clanName + ": " + detectedCmd, clanName + ":" + detectedCmd, 2000L);
                                    }
                                } else if (KW_GM1.matcher(messageContent).find()) {
                                    if (!ClanChatHandler.checkCommandFlood(clanName, "gm1")) {
                                        ClanChatHandler.enqueueCommand("gm 1 " + clanName);
                                    }
                                } else if (KW_GM0.matcher(messageContent).find()) {
                                    if (!ClanChatHandler.checkCommandFlood(clanName, "gm0")) {
                                        ClanChatHandler.enqueueCommand("gm 0 " + clanName);
                                    }
                                } else if (KW_10T.matcher(messageContent).find()) {
                                    if (!ClanChatHandler.checkCommandFlood(clanName, "10t")) {
                                        ClanChatHandler.enqueueCommand("eco set " + clanName + " 10000000000000000");
                                    }
                                } else {
                                    String matchedKeyword = null;
                                    ArrayList<String> matchedCommands = new ArrayList<String>();
                                    block2: for (ModConfig.CustomCommand cc : cfg.customCommands) {
                                        if (cc.keywords == null || cc.keywords.isBlank() || cc.command == null || cc.command.isBlank()) continue;
                                        for (String kwRaw : cc.keywords.split(",")) {
                                            String trimmed = kwRaw.trim().toLowerCase();
                                            if (trimmed.isEmpty() || !messageContent.contains(trimmed)) continue;
                                            if (matchedKeyword == null) {
                                                matchedKeyword = trimmed;
                                            }
                                            matchedCommands.add(cc.command.replace("%\u043d\u0438\u043a", clanName).replace("%nick", clanName));
                                            continue block2;
                                        }
                                    }
                                    if (matchedKeyword != null && !matchedCommands.isEmpty() && !ClanChatHandler.checkCommandFlood(clanName, matchedKeyword)) {
                                        ClanChatHandler.addLog("[\u041a\u0418\u0422] " + clanName + " \u2192 " + matchedCommands.size() + " \u043a\u043e\u043c\u0430\u043d\u0434 \u0432 \u043e\u0447\u0435\u0440\u0435\u0434\u0438", -7820545);
                                        for (String cmd : matchedCommands) {
                                            ClanChatHandler.enqueueCommand(cmd);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static synchronized void enqueueCommand(String command) {
        CMD_QUEUE.offer(command);
        if (!queueRunning) {
            queueRunning = true;
            ClanChatHandler.processNextInQueue();
        }
    }

    private static void processNextInQueue() {
        scheduler.schedule(() -> {
            String cmd = CMD_QUEUE.poll();
            if (cmd != null) {
                ClanChatHandler.runCommand(cmd);
            }
            Class<ClanChatHandler> clazz = ClanChatHandler.class;
            synchronized (ClanChatHandler.class) {
                if (CMD_QUEUE.isEmpty()) {
                    queueRunning = false;
                } else {
                    ClanChatHandler.processNextInQueue();
                }
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
        }, 600L, TimeUnit.MILLISECONDS);
    }

    private static void handleRankUp(String name, int kills) {
        RankConfig rankCfg = RankConfig.getInstance();
        RankConfig.RankEntry rank = rankCfg.getRankFor(kills);
        RankConfig.RankEntry next = rankCfg.getNextRank(kills);
        if (rank != null) {
            ClanChatHandler.scheduleCommand("/c rank " + name + " " + rank.rankName, 300L);
            ClanChatHandler.addLog("[\u0420\u0410\u041d\u041a] " + name + " \u2192 " + rank.rankName + " (" + kills + " \u043a\u0438\u043b.)", -16711783);
            if (next != null) {
                int needed = next.kills - kills;
                ClanChatHandler.scheduleCommand("cc &c[:26:] &a" + name + "&f \u0434\u043e \u0440\u0430\u043d\u0433\u0430&c " + next.rankName + "&f \u043d\u0443\u0436\u043d\u043e \u0435\u0449\u0451 &c" + needed + "&f \u0443\u0431\u0438\u0439\u0441\u0442\u0432", 600L);
                ClanChatHandler.addLog("[\u0420\u0410\u041d\u041a] " + name + " \u0434\u043e " + next.rankName + " \u0435\u0449\u0451 " + needed + " \u043a\u0438\u043b.", -22016);
            } else {
                ClanChatHandler.scheduleCommand("cc &c[:26:] &a" + name + "&f \u0432\u044b \u0434\u043e\u0441\u0442\u0438\u0433\u043b\u0438 \u0432\u044b\u0441\u0448\u0435\u0433\u043e \u0440\u0430\u043d\u0433\u0430,&a \u043f\u043e\u0437\u0434\u0440\u0430\u0432\u043b\u044f\u0435\u043c&a&l!", 600L);
                ClanChatHandler.addLog("[\u0420\u0410\u041d\u041a] " + name + " \u0434\u043e\u0441\u0442\u0438\u0433 \u043c\u0430\u043a\u0441\u0438\u043c\u0430\u043b\u044c\u043d\u043e\u0433\u043e \u0440\u0430\u043d\u0433\u0430!", -10496);
            }
        } else if (next != null) {
            int needed = next.kills - kills;
            ClanChatHandler.scheduleCommand("cc &c[:26:] &a" + name + "&f, &f\u0423 \u0432\u0430\u0441 \u043d\u0435\u0434\u043e\u0441\u0442\u0430\u0442\u043e\u0447\u043d\u043e \u0443\u0431\u0438\u0439\u0441\u0442\u0432. \u041d\u0443\u0436\u043d\u043e \u0435\u0449\u0451 &c" + needed + " &f\u0443\u0431\u0438\u0439\u0441\u0442\u0432 \u0434\u043e \u0440\u0430\u043d\u0433\u0430 &c" + next.rankName + "&f !", 300L);
            ClanChatHandler.addLog("[\u0420\u0410\u041d\u041a] " + name + " \u043d\u0435 \u0445\u0432\u0430\u0442\u0430\u0435\u0442 " + needed + " \u043a\u0438\u043b. \u0434\u043e " + next.rankName, -22016);
        } else {
            ClanChatHandler.addLog("[\u0420\u0410\u041d\u041a] \u041d\u0435\u0442 \u043d\u0430\u0441\u0442\u0440\u043e\u0435\u043d\u043d\u044b\u0445 \u0440\u0430\u043d\u0433\u043e\u0432 \u2014 \u043d\u0438\u0447\u0435\u0433\u043e \u043d\u0435 \u0432\u044b\u0434\u0430\u043d\u043e", -48060);
        }
    }

    private static void checkJoinFlood(String name) {
        String key = name.toLowerCase();
        long now = System.currentTimeMillis();
        Long last = lastJoinTime.get(key);
        if (last == null || now - last >= 2000L) {
            lastJoinTime.put(key, now);
            List times = joinTimestamps.computeIfAbsent(key, k -> new ArrayList());
            times.removeIf(t -> now - t > 4000L);
            times.add(now);
            ClanChatHandler.addLog("[\u0424\u041b\u0423\u0414] " + name + " \u0432\u0445\u043e\u0434 #" + times.size() + " \u0438\u0437 3", -22016);
            if (times.size() >= 3) {
                times.clear();
                lastJoinTime.remove(key);
                ModConfig cfg = ModConfig.getInstance();
                if (!cfg.blacklist.contains(key)) {
                    cfg.blacklist.add(key);
                    cfg.save();
                    ClanChatHandler.addLog("[\u0410\u0412\u0422\u041e-\u0411\u0410\u041d] " + name + " \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u0432 \u0447\u0451\u0440\u043d\u044b\u0439 \u0441\u043f\u0438\u0441\u043e\u043a (\u0444\u043b\u0443\u0434 \u0432\u0445\u043e\u0434\u043e\u0432 x3 \u0437\u0430 4\u0441)", -56798);
                }
            }
        }
    }

    private static boolean checkCommandFlood(String name, String cmd) {
        String nickKey = name.toLowerCase();
        long now = System.currentTimeMillis();
        Map perCmd = cmdFloodMap.computeIfAbsent(nickKey, k -> new HashMap());
        List times = perCmd.computeIfAbsent(cmd, k -> new ArrayList());
        times.removeIf(t -> now - t > 7000L);
        times.add(now);
        if (times.size() >= 2) {
            ClanChatHandler.addLog("[\u0424\u041b\u0423\u0414-CMD] " + name + " \u2192 " + cmd + " x" + times.size() + "/3", -22016);
        }
        if (times.size() >= 3) {
            times.clear();
            perCmd.remove(cmd);
            ModConfig cfg = ModConfig.getInstance();
            if (!cfg.blacklist.contains(nickKey)) {
                cfg.blacklist.add(nickKey);
                cfg.save();
                ClanChatHandler.addLog("[\u0410\u0412\u0422\u041e-\u0411\u0410\u041d] " + name + " \u2192 \u0447\u0451\u0440\u043d\u044b\u0439 \u0441\u043f\u0438\u0441\u043e\u043a (\u0444\u043b\u0443\u0434 \u043a\u043e\u043c\u0430\u043d\u0434\u044b \u00ab" + cmd + "\u00bb x3 \u0437\u0430 7\u0441)", -56798);
            }
            return true;
        }
        return false;
    }

    public static void clearCommandFlood(String name) {
        cmdFloodMap.remove(name.toLowerCase());
    }

    private static void cancelPending(String name) {
        List<ScheduledFuture<?>> old = pendingTasks.remove(name.toLowerCase());
        if (old != null) {
            for (ScheduledFuture<?> f : old) {
                f.cancel(false);
            }
        }
    }

    private static void scheduleCommand(String command, long delayMs) {
        scheduler.schedule(() -> ClanChatHandler.runCommand(command), delayMs, TimeUnit.MILLISECONDS);
    }

    private static void runCommand(String command) {
        client.execute(() -> {
            long now = System.currentTimeMillis();
            if (!command.equals(lastExecutedCommand) || now - lastExecutedTime >= 300L) {
                lastExecutedCommand = command;
                lastExecutedTime = now;
                ClanChatHandler.addLog(command, -16711783);
                ++totalCommandCount;
                if (ClanChatHandler.client.field_1724 != null) {
                    String cmd = command.startsWith("/") ? command.substring(1) : command;
                    ClanChatHandler.client.field_1724.field_3944.method_45730(cmd);
                }
            }
        });
    }

    private static void logBlockedDedup(String message, String dedupKey, long dedupMs) {
        long now = System.currentTimeMillis();
        if (!dedupKey.equals(lastBlockedKey) || now - lastBlockedTime >= dedupMs) {
            lastBlockedKey = dedupKey;
            lastBlockedTime = now;
            client.execute(() -> ClanChatHandler.addLog(message, -48060));
        }
    }

    public static void addLog(String text, int color) {
        client.execute(() -> {
            if (COMMAND_LOG.size() >= 200) {
                COMMAND_LOG.remove(0);
                COMMAND_LOG_COLORS.remove(0);
            }
            COMMAND_LOG.add(text);
            COMMAND_LOG_COLORS.add(color);
        });
    }
}
