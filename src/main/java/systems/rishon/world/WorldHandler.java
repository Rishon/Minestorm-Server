package systems.rishon.world;

import systems.rishon.Minestorm;
import systems.rishon.utils.Logger;

public class WorldHandler {

    // Logger
    private final Logger logger = Logger.getLogger();

    private final Minestorm instance;

    public WorldHandler(Minestorm instance) {
        this.instance = instance;
        init();
    }

    private void init() {
        // Initialization
        logger.info("Initializing world...");
        loadChunkGenerator();
    }

    public void loadChunkGenerator() {
        ChunkGenerator chunkGenerator = new ChunkGenerator(this);
        chunkGenerator.generateChunk();
    }

    public Minestorm getInstance() {
        return instance;
    }

}
