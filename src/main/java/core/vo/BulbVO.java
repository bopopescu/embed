package main.java.core.vo;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by digvijaysharma on 18/03/17.
 */
@Entity("bulb")
public class BulbVO extends DeviceVO {

    private int level;

    private boolean state;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
