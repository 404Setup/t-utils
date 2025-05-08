package one.tranic.t.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Enum representing various Minecraft server platforms.
 */
@SuppressWarnings("unused")
public enum Platform {
    Velocity("Velocity", "com.velocitypowered.api.proxy.ProxyServer"),
    BungeeCord("BungeeCord", "net.md_5.bungee.api.CommandSender"),
    Spigot("Spigot", "org.bukkit.Bukkit"),
    Paper("Paper", "io.papermc.paper.util.MCUtil"),
    ShreddedPaper("ShreddedPaper", "io.multipaper.shreddedpaper.threading.ShreddedPaperTickThread"),
    Folia("Folia", "io.papermc.paper.threadedregions.commands.CommandServerHealth"),
    Quilt("Quilt", "org.quiltmc.loader.api.QuiltLoader"),
    Fabric("Fabric", "net.fabricmc.loader.FabricLoader"),
    NeoForge("NeoForge", "net.neoforged.neoforge.common.NeoForge"),
    Forge("Forge", "net.minecraftforge.fml.ModContainer"),
    Unknown("Unknown", "Unknown");

    /**
     * A cached value of the detected platform. The platform is only determined once using reflection
     * and the result is cached for future calls to {@link #get()}.
     */
    private static Platform platform;
    private final String name;
    private final String[] classPath;

    Platform(String name, String... classPath) {
        this.name = name;
        this.classPath = classPath;
    }

    /**
     * Retrieves the current {@link Platform} instance by detecting the underlying platform.
     *
     * @return the detected {@link Platform} instance, never null.
     */
    public static @NotNull Platform get() {
        if (platform != null) {
            return platform;
        }

        if (Velocity.is()) {
            platform = Velocity;
            return platform;
        }

        if (BungeeCord.is()) {
            platform = BungeeCord;
            return platform;
        }

        if (Folia.is()) {
            platform = Folia;
            return platform;
        }

        if (ShreddedPaper.is()) {
            platform = ShreddedPaper;
            return platform;
        }

        if (Paper.is()) {
            platform = Paper;
            return platform;
        }

        if (Spigot.is()) {
            platform = Spigot;
            return platform;
        }

        if (Quilt.is()) {
            platform = Quilt;
            return platform;
        }

        if (Fabric.is()) {
            platform = Fabric;
            return platform;
        }

        if (NeoForge.is()) {
            platform = NeoForge;
            return platform;
        }

        if (Forge.is()) {
            platform = Forge;
            return platform;
        }

        platform = Unknown;
        return platform;
    }

    /**
     * Retrieves the {@link Platform} instance corresponding to the provided platform name.
     * If the name does not match any known platform, the {@link Platform#Unknown} instance will be returned.
     *
     * @param name the name of the platform. Must not be null.
     * @return the matching {@link Platform} instance, or {@link Platform#Unknown} if no match is found.
     */
    public static @NotNull Platform of(@NotNull String name) {
        var n1 = name.toLowerCase();
        for (Platform p : values()) {
            if (p.name.toLowerCase().equals(n1))
                return p;
        }
        return Unknown;
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
     * Determines whether the current platform is a mod loader.
     *
     * @return true if the current platform is a mod loader; false otherwise.
     */
    public static boolean isModLoader() {
        return get() == Quilt || get() == Fabric || get() == NeoForge || get() == Forge;
    }

    /**
     * Determines whether the current platform is a proxy-based platform.
     *
     * @return true if the current platform is either Velocity or BungeeCord; false otherwise.
     */
    public static boolean isProxy() {
        return get() == Velocity || get() == BungeeCord;
    }

    /**
     * Determines whether the current server is a mixin of a mod loader and a Bukkit-based server.
     *
     * @return true if the server is both a mod loader (Forge, NeoForge, Quilt, or Fabric)
     * and a Bukkit-based server (Spigot or Paper); false otherwise.
     */
    public static boolean isMixinServer() {
        if (isProxy()) {
            return false;
        }

        return isModLoaderPresent() && isBukkitBasedServer();
    }

    /**
     * Checks if any mod loader is present in the current environment.
     *
     * @return true if Forge, NeoForge, Quilt, or Fabric is detected
     */
    public static boolean isModLoaderPresent() {
        return Forge.is() || NeoForge.is() || Quilt.is() || Fabric.is();
    }

    /**
     * Checks if the current server is Bukkit-based.
     *
     * @return true if Spigot or Paper is detected
     */
    public static boolean isBukkitBasedServer() {
        return Spigot.is() || Paper.is();
    }

    /**
     * Determines whether the current environment supports multithreading features.
     *
     * @return true if the environment supports multithreading, false otherwise.
     */
    public static boolean isMultithreadedBukkit() {
        return get() == Folia || get() == ShreddedPaper;
    }

    /**
     * Returns the lowercase string representation of the platform.
     *
     * @return the platform name in lowercase (e.g., "velocity", "spigot").
     */
    @Override
    public @NotNull String toString() {
        return name.toLowerCase();
    }

    public @NotNull String toRawString() {
        return name;
    }

    /**
     * Retrieves the class path as a string.
     *
     * @return the class path of the current application.
     */
    public String[] getClassPath() {
        return classPath;
    }

    /**
     * Determines whether the specified class exists in the classpath.
     *
     * @return true if the class exists in the classpath; false otherwise.
     */
    public boolean is() {
        if (classPath.length == 1) return Reflect.hasClass(classPath[0]);
        for (String classPath : classPath)
            if (Reflect.hasClass(classPath))
                return true;
        return false;
    }
}
