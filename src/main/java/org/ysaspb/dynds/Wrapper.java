package org.ysaspb.dynds;

import java.util.Objects;
import java.util.concurrent.Callable;

public class Wrapper {
    private final DynamicDataSource ds;

    public Wrapper(DynamicDataSource ds) {
        this.ds = ds;
    }

    public Runnable wrap (Runnable r) {
        final Object key = ds.getCurrentLookupKey();
        if (Objects.isNull(key)){
            return r;
        } else {
            return () -> {
                ds.setCurrentLookupKey(key);
                r.run();
                ds.removeCurrentLookupKey();
            };
        }
    }
    public <V> Callable<V> wrap (Callable<V> c) {
        final Object key = ds.getCurrentLookupKey();
        if (Objects.isNull(key)){
            return c;
        } else {
            return () -> {
                ds.setCurrentLookupKey(key);
                V res = c.call();
                ds.removeCurrentLookupKey();
                return res;
            };
        }
    }

}
