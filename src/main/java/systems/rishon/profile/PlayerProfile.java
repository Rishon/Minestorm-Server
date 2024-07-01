package systems.rishon.profile;


import net.minestom.server.entity.PlayerSkin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerProfile {

    // Player profile
    private static PlayerProfile playerProfile;
    // Caching
    private final Map<UUID, String[][]> playerSkinCache = new ConcurrentHashMap<>();

    public PlayerProfile() {
        playerProfile = this;
    }

    public static PlayerProfile getPlayerProfile() {
        return playerProfile;
    }

    public void cachePlayerSkin(UUID playerUUID, PlayerSkin playerSkin) {
        String[][] playerSkinData = new String[][]{{playerSkin.textures(), playerSkin.signature()}};
        this.playerSkinCache.put(playerUUID, playerSkinData);
    }

    public PlayerSkin getPlayerSkin(UUID playerUUID) {
        if (this.playerSkinCache.containsKey(playerUUID)) {
            String[][] cachedSkin = this.playerSkinCache.get(playerUUID);
            return new PlayerSkin(cachedSkin[0][0], cachedSkin[0][1]);
        }
        return null;
    }

    public void removePlayerSkin(UUID playerUUID) {
        this.playerSkinCache.remove(playerUUID);
    }

    public void clearPlayerSkinCache() {
        this.playerSkinCache.clear();
    }

    public boolean hasPlayerSkin(UUID playerUUID) {
        return this.playerSkinCache.containsKey(playerUUID);
    }

    public Map<UUID, String[][]> getPlayerSkinCache() {
        return this.playerSkinCache;
    }
}
