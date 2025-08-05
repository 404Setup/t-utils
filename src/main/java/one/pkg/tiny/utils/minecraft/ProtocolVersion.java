package one.pkg.tiny.utils.minecraft;

import one.pkg.tiny.utils.Collections;

import java.util.Map;

/**
 * Represents the Minecraft Java Edition protocol version mapping table.
 *
 * <p>
 * This mapping table is used to match protocol numbers to the lowest formal version when
 * multiple versions share the same protocol number.
 * </p>
 *
 * <p>
 * The mapping only includes protocol numbers for official release versions. It excludes
 * protocol numbers for snapshot versions, pre-release versions, and April Fools' versions.
 * </p>
 *
 * <p>
 * Note: For the most recent Minecraft releases, the protocol number may be unrecognized
 * unless the dependency library is updated. In such cases, the protocol number is assumed
 * to be that of the most recent release.
 * </p>
 */
@SuppressWarnings("unused")
public enum ProtocolVersion {
    UNKNOWN(-1),
    PLAY_1_7(3), PLAY_1_7_2(4), PLAY_1_7_6(5),
    PLAY_1_8(47),
    PLAY_1_9(107), PLAY_1_9_1(108), PLAY_1_9_2(109), PLAY_1_9_3(110),
    PLAY_1_10(210),
    PLAY_1_11(315), PLAY_1_11_1(316),
    PLAY_1_12(335), PLAY_1_12_1(338), PLAY_1_12_2(340),
    PLAY_1_13(393), PLAY_1_13_1(401), PLAY_1_13_2(404),
    PLAY_1_14(477), PLAY_1_14_1(480), PLAY_1_14_2(485), PLAY_1_14_3(490), PLAY_1_14_4(498),
    PLAY_1_15(573), PLAY_1_15_1(575), PLAY_1_15_2(578),
    PLAY_1_16(735), PLAY_1_16_1(736), PLAY_1_16_2(751), PLAY_1_16_3(753), PLAY_1_16_4(754),
    PLAY_1_17(755), PLAY_1_17_1(756),
    PLAY_1_18(757), PLAY_1_18_2(758),
    PLAY_1_19(759), PLAY_1_19_1(760), PLAY_1_19_3(761), PLAY_1_19_4(762),
    PLAY_1_20(763), PLAY_1_20_2(764), PLAY_1_20_3(765), PLAY_1_20_5(766),
    PLAY_1_21(767), PLAY_1_21_2(768), PLAY_1_21_4(769), PLAY_1_21_5(770), PLAY_1_21_6(771), PLAY_1_21_7(772), PLAY_1_21_9(773),
    ;

    private static final int MINIMUM_SUPPORTED_PROTOCOL_VERSION = 3;
    private static final Map<Integer, ProtocolVersion> PROTOCOL_VERSION_MAP = Collections.newHashMap();

    static {
        for (ProtocolVersion version : ProtocolVersion.values()) {
            if (version.getProtocolVersion() >= MINIMUM_SUPPORTED_PROTOCOL_VERSION) {
                PROTOCOL_VERSION_MAP.put(version.getProtocolVersion(), version);
            }
        }
    }

    private final int protocolVersion;

    ProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    /**
     * Returns the corresponding ProtocolVersion for the given protocol number.
     *
     * @param protocolVersion the protocol number to look up
     * @return the ProtocolVersion mapped to the given protocol number, or UNKNOWN if the protocol
     * is not supported or recognized
     */
    public static ProtocolVersion fromProtocolVersion(int protocolVersion) {
        if (protocolVersion < MINIMUM_SUPPORTED_PROTOCOL_VERSION) {
            return UNKNOWN;
        }
        return PROTOCOL_VERSION_MAP.getOrDefault(protocolVersion, UNKNOWN);
    }

    /**
     * Returns the protocol number for this ProtocolVersion.
     *
     * @return the protocol number
     */
    public int getProtocolVersion() {
        return protocolVersion;
    }
}