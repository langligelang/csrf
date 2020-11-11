package com.maoge.csrf.utils;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import static com.maoge.csrf.utils.EncoderConstants.CHAR_DIGITS;
import static com.maoge.csrf.utils.EncoderConstants.CHAR_LETTERS;

public class CSRFToken {

    public String getToken() throws NoSuchProviderException, NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
        StringBuilder sb = new StringBuilder();
        for (int loop = 0; loop < 32; loop++) {
            int index = secureRandom.nextInt(EncoderConstants.CHAR_ALPHANUMERICS.length);
            sb.append(EncoderConstants.CHAR_ALPHANUMERICS[index]);
        }
        String nonce = sb.toString();
        return nonce;
    }


}
