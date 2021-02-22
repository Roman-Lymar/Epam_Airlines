package ua.nure.lymar.airlines.entity;

import java.io.Serializable;

/**
 * Base class for all entity
 */
public class Entity implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
