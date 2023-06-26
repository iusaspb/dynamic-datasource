package org.ysaspb.dynds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ysaspb.dynds.DynamicDataSource;
import org.ysaspb.dynds.domain.Item;
import org.ysaspb.dynds.repository.ItemRepository;

import java.util.Collection;

/**
 * A test controller.
 */
@RestController
@RequestMapping("item/")
public class ItemController {
    @Autowired
    private DynamicDataSource dataSource;
    @Autowired
    private ItemRepository repository;
    @GetMapping("user/{userId}")
    Collection<Item> getUserOrders(@PathVariable Long userId){
        /**
         * The main point: assign a lookup key with current thread.
         * This thread is from thread pool
         * that is used for request processing.
         */
        String lookupKey = userId.toString();
        dataSource.setCurrentLookupKey(lookupKey);
        try{
            return repository.findAllByUserId(userId);
        } finally{
            /**
             * remove a lookup key from the current thread.
             */
            dataSource.removeCurrentLookupKey();
        }
    }
}
