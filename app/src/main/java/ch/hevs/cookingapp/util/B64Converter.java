package ch.hevs.cookingapp.util;

import android.util.Base64;

/**
 * A class that contains methods to convert a string to a base64 string and vice versa.
 * This class is used to convert the email of the user to a base64 string to be able to use it as a key in the database.
 * This class is used to convert the base64 string of the key to a string to be able to display it in the app.
 */
public class B64Converter
{
    /**
     * Encode the email address using Base64 encoding to avoid special characters in the key name (e.g. ".")
     * that are not allowed in Firebase keys
     * @param userEmail : the email address to be encoded : example : "xolo@gmail.com"
     * @return : the encoded email address : example of result : "dGVzdEB0ZXN0LmNvbQ=="
     */
    public static String encodeEmailB64(String userEmail)
    {
        return Base64.encodeToString(userEmail.getBytes(), Base64.NO_WRAP);
    }

    /**
     * Decode the email address using Base64 decoding to display it in the app.
     * @param userEmail : the email address to be decoded : example of input : "dGVzdEB0ZXN0LmNvbQ=="
     * @return : the decoded email address : example of result : "xolo@gmail.com"
     */
    public static String decodeEmailB64(String userEmail)
    {
        return new String(Base64.decode(userEmail, Base64.NO_WRAP));
    }
}
