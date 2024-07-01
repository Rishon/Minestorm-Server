package systems.rishon;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import systems.rishon.listeners.EventHandler;
import systems.rishon.profile.PlayerProfile;
import systems.rishon.utils.Logger;
import systems.rishon.world.WorldHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Minestorm {

    // Logger
    private static final Logger logger = new Logger();
    // Server instance
    public static MinecraftServer minecraftServer;
    public static InstanceContainer instanceContainer;
    // Global event handler
    public static GlobalEventHandler globalEventHandler;
    // Instance
    private static Minestorm instance;
    // World Handler
    private static WorldHandler worldHandler;
    // Event Handler
    private static EventHandler eventHandler;
    // Player Profile
    private static PlayerProfile playerProfile;

    protected Minestorm() {
        instance = this;
    }

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

        // Global event handler
        globalEventHandler = MinecraftServer.getGlobalEventHandler();

        // World Handler
        worldHandler = new WorldHandler(instance);

        // Event Handler
        eventHandler = new EventHandler(instance);

        // Player Profile
        playerProfile = new PlayerProfile();

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
                logger.warn("Failed to read server.properties file.");
            }
        }

        // Start the server
        logger.info("Starting server on port " + serverPort);
        minecraftServer.start("0.0.0.0", serverPort);
        logger.info("Server started on port " + serverPort);

        // Start reading action commands
        readActionCommands();
    }

    private static void endProcess() {
        logger.info("Stopping server...");
        if (MinecraftServer.isStopping()) {
            logger.warn("Server is already stopping.");
            return;
        }
        MinecraftServer.stopCleanly();
        logger.info("Server stopped.");
        logger.end();
    }

    private static void readActionCommands() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("stop")) {
                endProcess();
                break;
            }
        }
    }

    public static Minestorm getInstance() {
        return instance;
    }
}