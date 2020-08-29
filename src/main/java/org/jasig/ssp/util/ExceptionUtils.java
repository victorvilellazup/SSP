package org.jasig.ssp.util;

public class ExceptionUtils {

    public static <E extends Exception> void validateNotNull(Object obj, E exception) throws E {
        if(obj == null) {
            throw exception;
        }
    }
}
