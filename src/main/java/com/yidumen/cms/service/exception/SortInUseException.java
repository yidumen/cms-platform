package com.yidumen.cms.service.exception;

/**
 *
 * @author 蔡迪旻
 */
public class SortInUseException extends Exception {

    /**
     * Creates a new instance of <code>SortInUseException</code> without detail
     * message.
     */
    public SortInUseException() {
    }

    /**
     * Constructs an instance of <code>SortInUseException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SortInUseException(String msg) {
        super(msg);
    }
}
