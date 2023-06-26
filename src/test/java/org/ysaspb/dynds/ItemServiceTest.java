package org.ysaspb.dynds;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ysaspb.dynds.domain.Item;
import org.ysaspb.dynds.repository.ItemRepository;
import org.ysaspb.dynds.service.ItemService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemService service;
    @Autowired
    private ItemRepository repository;

    @Autowired
    private DynamicDataSource dataSource;

    /**
     * Test of using EntityManager.
     * Insert a new entity.
     */
    @Test
    void insert(){
        long userId = 1L;
        long total = 9999L;
        /**
         * Important!!!!
         */
        String lookupKey = Long.toString(userId);
        dataSource.setCurrentLookupKey(lookupKey);
        try {
             long nextId = repository.findAll().stream().mapToLong(Item::getId).max().orElse(0L)+1;
             Item item = new Item(nextId,userId,total);
             Item newItem = service.insert(item);
             assertEquals(nextId, newItem.getId());
             assertEquals(total, newItem.getTotal());
        } finally {
            dataSource.removeCurrentLookupKey();
        }
    }
}
