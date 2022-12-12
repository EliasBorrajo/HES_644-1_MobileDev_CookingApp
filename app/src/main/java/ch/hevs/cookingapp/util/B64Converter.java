package ch.hevs.cookingapp.util;

import android.util.Base64;

public class B64Converter
{
    /**
     * Encode the email address using Base64 encoding to avoid special characters in the key name (e.g. ".")
     * that are not allowed in Firebase keys
     * @param userEmail : the email address to be encoded
     * @return : the encoded email address : example of result : "dGVzdEB0ZXN0LmNvbQ=="
     */
    public static String encodeEmailB64(String userEmail)
    {
        return Base64.encodeToString(userEmail.getBytes(), Base64.NO_WRAP);
    }

    public static String decodeEmailB64(String userEmail)
    {
        return new String(Base64.decode(userEmail, Base64.NO_WRAP));
    }
}
