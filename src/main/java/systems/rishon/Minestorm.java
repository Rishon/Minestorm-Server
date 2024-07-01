package systems.rishon;

import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

@Getter
public class Minestorm {

    // Logger
    private static final Logger logger = LogManager.getLogger(Minestorm.class);

    // Server instance
    private static MinecraftServer minecraftServer;
    private static InstanceContainer instanceContainer;

    public static void main(String[] args) {
        init();
    }

    private static void init() {
        // Initialization
        logger.info("Initializing server...");
        minecraftServer = MinecraftServer.init();

        // Create instance
        logger.info("Creating instance...");
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        instanceContainer = instanceManager.createInstanceContainer();

        // Generate a chunk
        generateChunk();

        // Default server port
        int serverPort = 25565;

        // server.properties file
        File file = new File("server.properties");
        if (file.exists()) {
            logger.info("Assigning server port from server.properties file");
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("server-port=")) {
                        serverPort = Integer.parseInt(line.split("=")[1]);
                    }
                }
                reader.close();
            } catch (IOException e) {
                logger.warn("Failed to read server.properties file.", e);
            }
        }

        // Start the server
        logger.info("Starting server on port {}", serverPort);
        minecraftServer.start("0.0.0.0", serverPort);
        logger.info("Server started on port {}", serverPort);

        // Start reading action commands
        readActionCommands();
    }

    private static void readActionCommands() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("stop")) {
                logger.info("Stopping server...");
                if (MinecraftServer.isStopping()) {
                    logger.warn("Server is already stopping.");
                    continue;
                }
                MinecraftServer.stopCleanly();
                logger.info("Server stopped.");
                break;
            }
        }
    }

    private static void generateChunk() {
        logger.info("Generating chunk...");
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