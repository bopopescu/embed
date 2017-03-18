package main.java.mao.user;

import com.mongodb.WriteResult;
import main.java.core.dto.WsUser;
import main.java.core.vo.AuthTokenVO;
import main.java.core.vo.DeviceFingerprintVO;
import main.java.core.vo.UserVO;

/**
 * Created by digvijaysharma on 18/12/16.
 */
public interface IUserMao {
    WriteResult removeUser(UserVO user);

    UserVO getUser(WsUser userDetails);

    UserVO getUserByUserCode(String userId);

    UserVO getUserById(String id);

    UserVO getUserByUsernameOrMobileNumber(String username);

    void createUser(UserVO user);

    void setOtp(WsUser userDetails, int otp);

    void saveAuthToken(AuthTokenVO authToken);

    void saveFingerprint(DeviceFingerprintVO deviceFingerprint);

    void resetPassword(String userCode, String password);

    void updateUser(UserVO user);

    AuthTokenVO getAuthTokenByUserCode(String userCode);
}
