package systems.rishon.listeners;

import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import systems.rishon.Minestorm;

public class PlayerSkinEvent implements Listener {

    private final GlobalEventHandler globalEventHandler = Minestorm.globalEventHandler;

    @Override
    @IEvent(priority = EventPriority.HIGHEST)
    public void registerEvent() {
        this.globalEventHandler.addListener(PlayerSkinInitEvent.class, event -> {
            PlayerSkin skin = event.getSkin();
            event.setSkin(skin);
            System.out.println("Player skin has been initialized");
        });
    }
}
