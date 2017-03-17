package main.java.services.userProfile.impl;

import core.annotation.Secured;
import core.annotation.Service;
import core.api.response.SetProfileImageResponse;
import core.api.validation.ResponseCode;
import core.dto.UserProfileDTO;
import core.dto.WsAddress;
import core.vo.AddressVO;
import core.vo.UserProfileVO;
import java.util.Date;
import mao.user.impl.UserMaoImpl;
import mao.userProfile.impl.UserProfileMaoImpl;
import services.userProfile.IUserProfileService;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Service public class UserProfileServiceImpl implements IUserProfileService {

    private org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(UserProfileServiceImpl.class);

    private UserProfileMaoImpl userProfileMao;

    private UserMaoImpl userMao;

    public UserProfileServiceImpl(UserMaoImpl userMao, UserProfileMaoImpl userProfileMao) {
        this.userMao = userMao;
        this.userProfileMao = userProfileMao;
    }

    @Secured @Override public UserProfileDTO getCurrentUserProfile(String userCode) {
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        String email = userMao.getUserById(userCode).getUsername();
        UserProfileVO userProfileVO = userProfileMao.getUserProfileByEmail(email);
        userProfileDTO.setEmail(userProfileVO.getEmail());
        userProfileDTO.setPhoneNumber(userProfileVO.getMobileNumber());
        userProfileDTO.setAddress(new WsAddress(userProfileVO.getAddress()));
        userProfileDTO.setLandLine(userProfileVO.getLandLine());
        userProfileDTO.setName(userProfileVO.getName());
        return userProfileDTO;
    }

    @Secured @Override public SetProfileImageResponse setProfileImage(String userCode) {
        SetProfileImageResponse response = new SetProfileImageResponse();
        String email = userMao.getUserById(userCode).getUsername();
        UserProfileVO userProfile = userProfileMao.getUserProfileByEmail(email);
        if (userProfile != null) {
            response.setSuccess(true);
            response.setMessage("Profile Picture Set!");
        }
        else {
            response.addError(ResponseCode.USER_PROFILE_DOES_NOT_EXIST, "User Profile does not exist");
        }
        return response;
    }

    @Secured @Override public UserProfileVO createUserProfile(UserProfileDTO userProfileDTO) {
        UserProfileVO userProfileVO = new UserProfileVO();
        WsAddress address = userProfileDTO.getAddress();
        AddressVO addressVO = new AddressVO();
        if (address != null) {
            addressVO = new AddressVO(address);
        }
        userProfileVO.setAddress(addressVO);
        userProfileVO.setCreated(new Date());
        userProfileVO.setUpdated(new Date());
        userProfileVO.setEmail(userProfileDTO.getEmail());
        userProfileVO.setLandLine(userProfileDTO.getLandLine());
        userProfileVO.setMobileNumber(userProfileDTO.getPhoneNumber());
        userProfileVO.setName(userProfileDTO.getName());
        userProfileMao.createUserProfile(userProfileVO);
        return userProfileVO;
    }

    @Secured @Override public UserProfileVO updateUserProfile(UserProfileDTO userProfile, String userCode) {
        String email = userMao.getUserById(userCode).getUsername();
        UserProfileVO userProfileVO = userProfileMao.getUserProfileByEmail(email);
        if (userProfileVO != null) {
            String profileCode = userProfileVO.getId();
            if (userProfile.getLandLine() == null)
                userProfile.setLandLine("");
            userProfileMao.updateUserProfile(userProfile, profileCode);
        }
        return userProfileVO;
    }
}
