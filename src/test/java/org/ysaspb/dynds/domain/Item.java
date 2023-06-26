package org.ysaspb.dynds.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A test entity.
 * A field {@link Item#userId} is used as the lookup key.
 * So entities with different {@link Item#userId} belong to different databases.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    private Long id;
    /**
     * a lookup key
     */
    private long userId;
    private long total;

}
