package org.ysaspb.dynds;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ysaspb.dynds.domain.Item;
import org.ysaspb.dynds.repository.ItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ItemRepositoryTest {
    @Autowired
    private ItemRepository repository;
    @Autowired
    private DynamicDataSource dataSource;

    @Test
    void positiveTest(){
        Long userId = 1L;
        /**
         * The main point: assign a lookup key to the current thread
         * before work with a database.
         */
        String lookupKey = userId.toString();
        dataSource.setCurrentLookupKey(lookupKey);
        try {
            List<Item> items = repository.findAllByUserId(userId);
            /**
             * 2 - a number of records in resources/data1.sql
             */
            assertEquals(2, items.size());
            Item item = items.get(0);
            /**
             * as userId is a lookup key then
             * all records in a database has the same value of userId
             */
            assertEquals(userId, item.getUserId());
        } finally {
            dataSource.removeCurrentLookupKey();
        }
    }

    /**
     * try to assign unknown lookup key.
     * Valid lookup keys as 1,2.
     * See {@link org.ysaspb.dynds.config.DataSourceConfig#dataSource()}
     */
    @Test
    void negativeTest(){
        Long userId = 3L;
        String lookupKey = userId.toString();
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            dataSource.setCurrentLookupKey(lookupKey);
        });
        String expectedMessage = "Unknown key "+lookupKey;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }

}
