package com.mandeep.officialcode.Utils;

/**
 * Created by vrishankgupta on 13/03/18.
 */

/**
 * Simply for sake of laziness!
 */
public class StringManipulations {

    public static String expandUsername(String username){
        return username.replace(".", " ");
    }

    public static String condenseUsername(String username){
        return username.replace(" " , ".");
    }
}
