package main.java.core.vo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by digvijaysharma on 24/01/17.
 */
@Entity("customer_profile")
public class CustomerProfileVO {

    @Id
    private String Id;

    private String userProfile;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }
}
