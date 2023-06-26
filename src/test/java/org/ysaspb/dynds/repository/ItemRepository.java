package org.ysaspb.dynds.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ysaspb.dynds.domain.Item;

import java.util.Collection;
import java.util.List;

/**
 * A test repository
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByUserId(Long userId);
}
