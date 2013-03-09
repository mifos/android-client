package org.mifos.androidclient.util;

import java.util.List;

/**
 * An utility class which helps checking for empty values.
 */
public class ValueUtils {

    private ValueUtils() { }

    /**
     * Checks if a given double has a value.
     *
     * @param field the double value to check
     * @return true if the passed variable is not null and has a value greater different than 0.0
     */
    public static boolean hasValue(Double field) {
        return (field != null && field != 0.0);
    }

    /**
     * Checks if a given lists is not empty.
     *
     * @param list the list to check
     * @return true if the passed variable is not null and has size greater than 0
     */
    public static boolean hasElements(List<?> list) {
        return (list != null && list.size() > 0);
    }

}
