package main.java.mao.userProfile;

import main.java.core.dto.UserProfileDTO;
import core.vo.UserProfileVO;
import java.util.List;

/**
 * Created by digvijaysharma on 18/12/16.
 */
public interface IUserProfileMao {

    UserProfileVO getUserProfileByEmail(String email);

    void createUserProfile(UserProfileVO userProfile);

    void deleteUserProfile(UserProfileVO userProfile);

    void updateUserProfile(UserProfileDTO userProfile, String userCode);

    List<UserProfileVO> getAllUserProfiles();
}
