package com.yidumen.cms;

/**
 * @author 蔡迪旻
 *         2015年11月28日
 */
public class JacksonView {
    public interface Normal {
    }

    public interface WithAssociations extends Normal {
    }

    public interface WithSpecialAssociations extends WithAssociations {
    }
}
