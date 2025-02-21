package one.tranic.t.utils;

@SuppressWarnings("unused")
public class Command {
    public static String getName(String name) {
        String n = name;
        if (Platform.get() == Platform.BungeeCord) {
            n = "b" + n;
        } else if (Platform.get() == Platform.Velocity) {
            n = "v" + n;
        }
        return n;
    }

    public static String getPermission(String permission) {
        String p = permission;
        if (Platform.get() == Platform.BungeeCord) {
            p = permission.replaceFirst("\\.([^.]+)$", ".b$1");
        } else if (Platform.get() == Platform.Velocity) {
            p = permission.replaceFirst("\\.([^.]+)$", ".v$1");
        }

        return p;
    }
}
