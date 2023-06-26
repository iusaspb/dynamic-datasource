package org.ysaspb.dynds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ysaspb.dynds.domain.Item;
import org.ysaspb.dynds.repository.ItemRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * A test service
 */
@Service
public class ItemService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ItemRepository repository;
    /**
     * Use repository
     * @param item to be saved
     * @return saved item
     */
    @Transactional
    public Item save (Item item) {
        return repository.save(item);
    }
    /**
     * Use Entity Manager
     * @param item to be inserted
     * @return inserted item
     */
    @Transactional
    public Item insert (Item item) {
        entityManager.persist(item);
        return entityManager.find(Item.class,item.getId());
    }
}
