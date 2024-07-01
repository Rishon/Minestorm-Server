package systems.rishon.world;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import systems.rishon.Minestorm;
import systems.rishon.utils.Logger;

public class ChunkGenerator {

    // Logger
    private final Logger logger = Logger.getLogger();

    // World Handler
    private final WorldHandler worldHandler;

    protected ChunkGenerator(WorldHandler worldHandler) {
        this.worldHandler = worldHandler;
    }

    public void generateChunk() {
        logger.info("Generating chunk...");

        // Instance container
        InstanceContainer instanceContainer = Minestorm.instanceContainer;

        // Generate a chunk
        instanceContainer.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });
        logger.info("World chunk generated.");
    }
}
