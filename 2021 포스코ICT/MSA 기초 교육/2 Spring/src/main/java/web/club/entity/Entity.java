package web.club.entity;

import java.util.UUID;

public abstract class Entity {
    //
    protected String id;

    protected Entity() {
        //
        this.id = UUID.randomUUID().toString();
    }

    protected Entity(String id) {
        //
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
