package one.tranic.t.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Enum representing various Minecraft server platforms.
 * Supported platforms include:
 * <ul>
 *     <li>{@link #Velocity}</li>
 *     <li>{@link #BungeeCord}</li>
 *     <li>{@link #Spigot}</li>
 *     <li>{@link #Paper}</li>
 *     <li>{@link #ShreddedPaper}</li>
 *     <li>{@link #Folia}</li>
 * </ul>
 */
@SuppressWarnings("unused")
public enum Platform {
    /**
     * Represents the Velocity platform.
     * Velocity is a modern Minecraft proxy server designed for high performance and flexibility.
     */
    Velocity("com.velocitypowered.api.proxy.Proxy"),

    /**
     * Represents the BungeeCord platform.
     * BungeeCord is a classic Minecraft proxy server.
     */
    BungeeCord("net.md_5.bungee.api.CommandSender"),

    /**
     * Represents the Spigot platform.
     * Spigot is a highly optimized Minecraft server software, built from the vanilla Minecraft server,
     * and is widely used for plugin support and performance improvements.
     */
    Spigot("org.bukkit.Bukkit"),

    /**
     * Represents the Paper platform.
     * Paper is a fork of Spigot that further optimizes server performance and adds additional features for plugins.
     */
    Paper("io.papermc.paper.util.MCUtil"),

    /**
     * Represents the ShreddedPaper platform.
     * ShreddedPaper is a highly specialized fork of Paper, designed for improved threading in Minecraft servers.
     */
    ShreddedPaper("io.multipaper.shreddedpaper.threading.ShreddedPaperTickThread"),

    /**
     * Represents the Folia platform.
     * Folia is a specialized fork of Paper with region-based multi-threading support for high-concurrency environments.
     */
    Folia("io.papermc.paper.threadedregions.commands.CommandServerHealth");

    /**
     * A cached value of the detected platform. The platform is only determined once using reflection
     * and the result is cached for future calls to {@link #get()}.
     */
    private static Platform platform;
    private final String classPath;

    Platform(String classPath) {
        this.classPath = classPath;
    }

    /**
     * Detects and returns the current platform.
     * <p>
     * This method uses reflection to check for specific platform classes and methods in the runtime
     * environment, caching the result after the first check to avoid unnecessary overhead in future calls.
     * </p>
     *
     * @return the detected {@link Platform}, or {@link Platform#Spigot} as the default if no other platform is found.
     */
    public static @NotNull Platform get() {
        if (platform != null) {
            return platform;
        }

        // Test for Velocity platform
        if (Velocity.is()) {
            platform = Velocity;
            return platform;
        }

        // Test for BungeeCord platform
        if (BungeeCord.is()) {
            platform = BungeeCord;
            return platform;
        }

        // Test for Folia platform
        if (Folia.is()) {
            platform = Folia;
            return platform;
        }

        // Test for ShreddedPaper platform
        if (ShreddedPaper.is()) {
            platform = ShreddedPaper;
            return platform;
        }

        // Test for Paper platform
        if (Paper.is()) {
            platform = Paper;
            return platform;
        }

        platform = Spigot;
        return Spigot;
    }

    /**
     * Returns the {@link Platform} corresponding to the provided platform name.
     * <p>
     * This method compares the provided platform name (case-insensitive) to known platforms
     * and returns the appropriate enum constant.
     * </p>
     *
     * @param name the name of the platform (e.g., "velocity", "spigot")
     * @return the corresponding {@link Platform} enum constant
     * @throws IllegalArgumentException if the platform name is unknown
     */
    public static @NotNull Platform of(@NotNull String name) {
        return switch (name.toLowerCase()) {
            case "velocity" -> Velocity;
            case "bungeecord" -> BungeeCord;
            case "spigot" -> Spigot;
            case "paper" -> Paper;
            case "shreddedpaper" -> ShreddedPaper;
            case "folia" -> Folia;
            default -> throw new IllegalArgumentException("Unknown platform: " + name);
        };
    }

    /**
     * Determines whether the current platform is a Bukkit-based platform.
     *
     * @return true if the platform is one of Spiot, Paper, Folia, or ShreddedPaper; false otherwise
     */
    public static boolean isBukkit() {
        return get() == Spigot ||
                get() == Paper ||
                get() == Folia ||
                get() == ShreddedPaper;
    }

    /**
     * Determines whether the current environment supports multithreading features.
     *
     * @return true if the environment supports multithreading, false otherwise.
     */
    public static boolean isMultithreading() {
        return get() == Folia || get() == ShreddedPaper;
    }

    /**
     * Returns the lowercase string representation of the platform.
     *
     * @return the platform name in lowercase (e.g., "velocity", "spigot").
     */
    @Override
    public @NotNull String toString() {
        return switch (this) {
            case Velocity -> "velocity";
            case BungeeCord -> "bungeecord";
            case Spigot -> "spigot";
            case Paper -> "paper";
            case ShreddedPaper -> "shreddedpaper";
            case Folia -> "folia";
        };
    }

    public @NotNull String toRawString() {
        return switch (this) {
            case Velocity -> "Velocity";
            case BungeeCord -> "BungeeCord";
            case Spigot -> "Spigot";
            case Paper -> "Paper";
            case ShreddedPaper -> "ShreddedPaper";
            case Folia -> "Folia";
        };
    }

    /**
     * Retrieves the class path as a string.
     *
     * @return the class path of the current application.
     */
    public String getClassPath() {
        return classPath;
    }

    /**
     * Determines whether the specified class exists in the classpath.
     *
     * @return true if the class exists in the classpath; false otherwise.
     */
    public boolean is() {
        return Reflect.hasClass(classPath);
    }
}
