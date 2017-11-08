package com.example.a1dordj54.findapub.helpers;

/**
 * Created by Dordoy on 07/11/2017.
 */

public class Functions {

    public static String formatDoubleToString(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }

}
