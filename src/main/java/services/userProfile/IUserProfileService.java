package main.java.services.userProfile;

import core.annotation.Secured;
import core.api.response.SetProfileImageResponse;
import core.dto.UserProfileDTO;
import core.vo.UserProfileVO;

/**
 * Created by digvijaysharma on 18/12/16.
 */
public interface IUserProfileService {

    @Secured UserProfileDTO getCurrentUserProfile(String userCode);

    @Secured SetProfileImageResponse setProfileImage(String userCode);

    @Secured UserProfileVO createUserProfile(UserProfileDTO userProfileDTO);

    @Secured UserProfileVO updateUserProfile(UserProfileDTO userProfile, String userCode);
}
