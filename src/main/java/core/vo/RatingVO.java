package main.java.core.vo;

import java.math.BigInteger;
import java.util.Date;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Entity("rating")
public class RatingVO {

    @Id
    private String id;

    private BigInteger downVotes;

    private BigInteger upVotes;

    private Integer value;

    private Date created;

    private Date updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigInteger getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(BigInteger downVotes) {
        this.downVotes = downVotes;
    }

    public BigInteger getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(BigInteger upVotes) {
        this.upVotes = upVotes;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
