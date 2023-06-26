package org.ysaspb.dynds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
@Slf4j
public class DynamicDataSource extends AbstractDataSource implements ApplicationListener<ContextRefreshedEvent> {

    private final Map<Object,DataSource> dsMap;
    private final Object defaultKey;
    private volatile boolean inOperationState = false;

    private final ThreadLocal<Object> lookupKeyHolder = new ThreadLocal<>();

    public DynamicDataSource(Map<Object, DataSource> dsMap) {
        this.dsMap = new HashMap<>(dsMap);
        defaultKey = this.dsMap.keySet().iterator().next();
    }

    public void setCurrentLookupKey(Object key) {
        if (!dsMap.containsKey(key)) {
            throw new IllegalStateException("Unknown key "+key);
        }
        lookupKeyHolder.set(key);
    }
    protected Object getCurrentLookupKey(){
        Object key = lookupKeyHolder.get();
        if (Objects.isNull(key)){
            lookupKeyHolder.remove();
            if (inOperationState) {
                throw new IllegalStateException("Current key is not set");
            } else {
                log.info("Return the default key");
                return defaultKey;
            }
        }
        return key;
    }
    public void removeCurrentLookupKey(){
        lookupKeyHolder.remove();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dsMap.get(getCurrentLookupKey()).getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException("getConnection("+username+","+password+")");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent  event) {
        if (inOperationState)
            return;
        inOperationState = true;
        log.info("In operation state");
    }
}
