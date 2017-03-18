package main.java.mao.user.impl;

import com.mongodb.WriteResult;
import main.java.core.annotation.Mao;
import main.java.core.dto.WsUser;
import main.java.core.vo.AuthTokenVO;
import main.java.core.vo.DeviceFingerprintVO;
import main.java.core.vo.UserVO;
import java.util.Date;
import main.java.mao.user.IUserMao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Mao
public class UserMaoImpl implements IUserMao{

    private Datastore store;

    public UserMaoImpl(DatastoreImpl store) {
        this.store = store;
    }

    @Override
    public WriteResult removeUser(UserVO user) {
        return store.delete(user);
    }

    @Override
    public UserVO getUser(WsUser userDetails) {
        return store.find(UserVO.class, "username", userDetails.getUsername()).get();
    }

    @Override
    public UserVO getUserByUserCode(String code) {
        return getUserById(code);
    }

    @Override
    public UserVO getUserById(String id) {
        return store.find(UserVO.class).field("id").equal(new ObjectId(id)).get();
    }

    @Override
    public UserVO getUserByUsernameOrMobileNumber(String username) {
        UserVO user = store.find(UserVO.class, "mobileNumber", username).get();
        if(user != null) {
            return user;
        }
        return store.find(UserVO.class, "username", username).get();
    }

    @Override
    public void createUser(UserVO user) {
        store.save(user);
    }

    @Override
    public void setOtp(WsUser userDetails, int otp) {
        Query<UserVO> query = store.createQuery(UserVO.class).filter("username =", userDetails.getUsername()).
                filter("mobileNumber =", userDetails.getMobileNumber());
        UpdateOperations<UserVO> ops = store.createUpdateOperations(UserVO.class).set("otp", Integer.toString(otp)).set("updated", new Date());
        store.update(query, ops);
    }

    @Override
    public void saveAuthToken(AuthTokenVO authToken) {
        store.save(authToken);
    }

    @Override
    public void saveFingerprint(DeviceFingerprintVO deviceFingerprint) {
        store.save(deviceFingerprint);
    }

    @Override
    public void resetPassword(String userCode, String password) {
        Query<UserVO> query = store.createQuery(UserVO.class).field("id").equal(new ObjectId(userCode));
        UpdateOperations<UserVO> ops = store.createUpdateOperations(UserVO.class).set("password", password).set("updated", new Date());
        store.update(query, ops);
    }

    @Override
    public void updateUser(UserVO user) {
        store.save(user);
    }

    @Override
    public AuthTokenVO getAuthTokenByUserCode(String userCode) {
        return store.find(AuthTokenVO.class, "userCode", userCode).get();
    }
}
