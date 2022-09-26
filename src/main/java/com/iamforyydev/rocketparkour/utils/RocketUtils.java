package com.iamforyydev.rocketparkour.utils;

import org.bukkit.ChatColor;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RocketUtils {
    public static String toFormat(
            String message
    ) {
        return message != null ? ChatColor.translateAlternateColorCodes('&', message) : null;
    }

    public static Map<String, Integer> sort(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());

        list.sort((m1, m2) -> (m2.getValue()).compareTo(m1.getValue()));

        Map<String, Integer> result = new LinkedHashMap<>();
        for(int index = list.size(); index > (list.size()-10) ; index--){
            Map.Entry<String, Integer> entry = list.get((index-1));
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }



}
