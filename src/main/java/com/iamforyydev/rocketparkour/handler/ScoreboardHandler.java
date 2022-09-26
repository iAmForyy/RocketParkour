package com.iamforyydev.rocketparkour.handler;

import com.iamforyydev.rocketparkour.RocketParkour;
import com.iamforyydev.rocketparkour.lib.FastBoard;
import com.iamforyydev.rocketparkour.user.User;
import com.iamforyydev.rocketparkour.utils.RocketUtils;

import java.util.*;

public class ScoreboardHandler {

    private final Map<UUID, FastBoard> fastBoardMap = new HashMap<>();
    private final RocketParkour plugin;
    public ScoreboardHandler(
            RocketParkour plugin
    ) {
        this.plugin = plugin;
    }

    public void loadScoreboard(
            User user
    ) {

        if (getFastBoardMap().containsKey(user.getUuid())) {
            return;
        }

        FastBoard fastBoard = new FastBoard(user.getPlayer());

        fastBoard.updateTitle(RocketUtils.toFormat("&6&lPARKOUR"));
        Map<String, Integer> valuesMap = RocketUtils.sort(
                plugin.getStorage().getBestTime()
        );

        List<String> stringList = new ArrayList<>();
        stringList.add("");

        valuesMap.forEach((name, time) ->
                stringList.add(
                        RocketUtils.toFormat("&a" + name + " &7- &a" + time)
                )
        );

        stringList.add("");
        stringList.add(RocketUtils.toFormat("&emc.minecraft.net"));

        fastBoard.updateLines(stringList);
        getFastBoardMap().put(user.getUuid(), fastBoard);
    }

    public void unloadScoreboard(
            User user
    ) {
        if (!getFastBoardMap().containsKey(user.getUuid())) {
            return;
        }

        FastBoard fastBoard = getFastBoardMap().get(user.getUuid());
        if (fastBoard != null) {
            fastBoard.delete();
            getFastBoardMap().remove(user.getUuid());
        }
    }

    public Map<UUID, FastBoard> getFastBoardMap() {
        return fastBoardMap;
    }
}
