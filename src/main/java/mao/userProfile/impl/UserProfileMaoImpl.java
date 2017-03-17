package main.java.mao.userProfile.impl;

import core.annotation.Mao;
import core.dto.UserProfileDTO;
import core.vo.UserProfileVO;
import java.util.Date;
import java.util.List;
import mao.userProfile.IUserProfileMao;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Mao
public class UserProfileMaoImpl implements IUserProfileMao {

    private Datastore store;

    public UserProfileMaoImpl(DatastoreImpl store) {
        this.store = store;
    }

    @Override
    public UserProfileVO getUserProfileByEmail(String email) {
        return store.find(UserProfileVO.class).field("email").equal(email).get();
    }

    @Override
    public void createUserProfile(UserProfileVO userProfile) {
        store.save(userProfile);
    }

    @Override
    public void deleteUserProfile(UserProfileVO userProfile) {
        store.delete(userProfile);
    }

    @Override
    public void updateUserProfile(UserProfileDTO userProfile, String profileCode) {
        Query<UserProfileVO> query = store.createQuery(UserProfileVO.class).field("id").equal(profileCode);
        UpdateOperations<UserProfileVO> ops = store.createUpdateOperations(UserProfileVO.class)
                .set("address", userProfile.getAddress())
                .set("landLine", userProfile.getLandLine())
                .set("updated", new Date());
        store.update(query, ops);
    }

    @Override
    public List<UserProfileVO> getAllUserProfiles() {
        return store.find(UserProfileVO.class).asList();
    }
}
