package com.iamforyydev.rocketparkour.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserHandler {

    private final Map<UUID, User> userMap = new HashMap<>();
    public User getUserByUuid(UUID uuid){
        if (getUserMap().containsKey(uuid)) {
            return getUserMap().get(uuid);
        }

        User user = new User(uuid);
        getUserMap().put(uuid, user);
        return user;
    }

    public void deleteUser(
        User user
    ){
        getUserMap().remove(user.getUuid());

    }
    public Map<UUID, User> getUserMap(
    ){
        return this.userMap;
    }
}
