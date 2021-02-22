package ua.nure.lymar.airlines.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Class for calculation of SHA256.
 */
public class PasswordToHash {
    /**
     * Calculates the SHA-256 digest and returns the value as a hex string.
     *
     * @param text string that needs to be convert
     * @return hex string
     */
    public static String getHashSha256(String text) {
        return DigestUtils.sha256Hex(text);
    }
}
