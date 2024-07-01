package systems.rishon.listeners;

import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerPreLoginEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import systems.rishon.Minestorm;
import systems.rishon.profile.PlayerProfile;

import java.util.UUID;

public class PlayerConnection implements Listener {

    private final GlobalEventHandler globalEventHandler = Minestorm.globalEventHandler;

    @Override
    @IEvent(priority = EventPriority.HIGHEST)
    public void registerEvent() {
        this.globalEventHandler.addListener(AsyncPlayerPreLoginEvent.class, event -> {
            Player player = event.getPlayer();
            UUID playerUUID = player.getUuid();
            PlayerSkin playerSkin;
            PlayerProfile playerProfile = PlayerProfile.getPlayerProfile();

            System.out.println("Player UUID: " + playerUUID);

            if (playerProfile.hasPlayerSkin(playerUUID)) {
                String[][] cachedSkin = playerProfile.getPlayerSkinCache().get(playerUUID);
                playerSkin = new PlayerSkin(cachedSkin[0][1], cachedSkin[0][2]);
                System.out.println("Player skin has been cached");
            } else {
                playerSkin = PlayerSkin.fromUsername(player.getUsername());
                if (playerSkin == null) {
                    System.out.println("Player skin is null");
                    return;
                }

                playerProfile.cachePlayerSkin(playerUUID, playerSkin);
            }
            // Call the event
            this.globalEventHandler.call(new PlayerSkinInitEvent(player, playerSkin));
            System.out.println("Player skin has been initialized");
        });
    }
}
