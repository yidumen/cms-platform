package com.yidumen.cms.service.exception;

/**
 *
 * @author 蔡迪旻
 */
public final class IllDataException extends Exception {

    /**
     * Creates a new instance of <code>SortInUseException</code> without detail
     * message.
     */
    public IllDataException() {
    }

    /**
     * Constructs an instance of <code>SortInUseException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IllDataException(String msg) {
        super(msg);
    }
}
