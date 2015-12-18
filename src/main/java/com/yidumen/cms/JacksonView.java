package com.yidumen.cms;

/**
 * @author 蔡迪旻
 *         2015年11月28日
 */
public class JacksonView {
    public interface MostLess {
    }

    public interface Less extends MostLess {
    }

    public interface Normal extends Less {
    }

    public interface More extends Normal {
    }

    public interface MuchMore extends More {
    }

    public interface Special {
    }
}
