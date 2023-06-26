package org.ysaspb.dynds.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ysaspb.dynds.DynamicDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
public class DataSourceConfig {
    /**
     * This bean is an example how to build a dynamic datasource.
     * There are 2 lookup keys (1,2)
     * @return DynamicDataSource
     */
    @Bean
    public DynamicDataSource dataSource(){
        String []urls ={
                "jdbc:h2:mem:db1;INIT=RUNSCRIPT FROM 'classpath:/create.sql'\\;RUNSCRIPT FROM 'classpath:/data1.sql'",
                "jdbc:h2:mem:db2;INIT=RUNSCRIPT FROM 'classpath:/create.sql'\\;RUNSCRIPT FROM 'classpath:/data2.sql'"
        };
        Map<Object,DataSource> dsMap = new HashMap<>(urls.length);
        for (int idx = 0; idx <urls.length;idx++) {
            String lookupKey = Integer.toString(idx+1);
            dsMap.put(lookupKey,
                    DataSourceBuilder.create().type(HikariDataSource.class).url(urls[idx]).build());
        }
        return new DynamicDataSource(dsMap);
    }

}
