package main.java.services.user.impl;

import com.mongodb.MongoTimeoutException;
import core.annotation.FetchBean;
import core.annotation.Secured;
import core.annotation.Service;
import core.api.request.AssertFingerprintRequest;
import core.api.request.GenerateValidTokenRequest;
import core.api.request.ResetOtpRequest;
import core.api.request.ResetPasswordRequest;
import core.api.request.SignupRequest;
import core.api.request.UserLoginRequest;
import core.api.request.UserRollbackRequest;
import core.api.request.VerifyOtpRequest;
import core.api.response.AssertFingerprintResponse;
import core.api.response.ResetOtpResponse;
import core.api.response.ResetPasswordResponse;
import core.api.response.SignupResponse;
import core.api.response.UserLoginResponse;
import core.api.response.UserRollbackResponse;
import core.api.response.VerifyOtpResponse;
import core.api.validation.ResponseCode;
import core.dto.WsDeviceFingerprint;
import core.dto.WsUser;
import core.vo.AuthTokenVO;
import core.vo.DeviceFingerprintVO;
import core.vo.UserVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import mao.user.impl.UserMaoImpl;
import services.jawt.impl.JawtServiceImpl;
import services.user.IUserService;
import utils.http.OtpServer;

/**
 * Created by digvijaysharma on 16/12/16.
 */
@Service public class UserServiceImpl implements IUserService {

    private org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);

    private UserMaoImpl userMao;

    @FetchBean public JawtServiceImpl jawtService;

    public UserServiceImpl(UserMaoImpl userMao) {
        this.userMao = userMao;
    }

    @Secured @Override public SignupResponse userSignUp(SignupRequest request, UserRollbackRequest userRollbackRequest) throws MongoTimeoutException {
        LOG.info("Signing Up");
        SignupResponse response = new SignupResponse();
        UserVO user = new UserVO();
        WsUser userDetails = request.getUserDetails();
        if (validateUserCredentials(userDetails.getUsername(), userDetails.getMobileNumber())) {
            user.setMobileNumber(userDetails.getMobileNumber());
            user.setUsername(userDetails.getUsername());
            if (userDetails.getType().toUpperCase().equals(UserVO.Type.CUSTOMER.name()) || userDetails.getType().toUpperCase().equals(UserVO.Type.SELLER.name())) {
                response.setType(userDetails.getType());
            } else {
                response.addError(ResponseCode.INVALID_USER_TYPE, "User Type is neither seller nor customer : " + userDetails.getType());
            }
            if (!response.hasErrors()) {
                user.setType(userDetails.getType().toUpperCase().equals(UserVO.Type.CUSTOMER.name()) ? UserVO.Type.CUSTOMER.name() : UserVO.Type.SELLER.name());
                user.setCode(userDetails.getUsername());
                try {
                    UserVO existingUser = userMao.getUser(userDetails);
                    if (existingUser != null) {
                        response.setSuccess(false);
                        response.addError(ResponseCode.USER_ALREADY_EXISTS, "User already exists");
                    } else {
                        int otp = ThreadLocalRandom.current().nextInt(99999, 999999);
                        if (OtpServer.sendOtp(userDetails.getMobileNumber(), otp)) {
                            user.setOtp(Integer.toString(otp));
                        }
                        user.setCreated(new Date());
                        user.setUpdated(new Date());
                        userMao.createUser(user);
                        userRollbackRequest.setUser(userMao.getUserByUsernameOrMobileNumber(user.getUsername()));
                        response.setSuccess(true);
                        response.setMessage("User is created successfully");
                    }
                } catch (MongoTimeoutException e) {
                    response.setSuccess(false);
                    response.setMessage(e.getMessage());
                    return response;
                }
            }
        } else {
            response.setSuccess(false);
            response.addError(ResponseCode.USER_CREDENTIALS_NOT_FORMATTED, "User credentials are not properly formatted");
        }
        return response;
    }

    @Secured @Override public UserRollbackResponse userRollback(UserRollbackRequest request) throws MongoTimeoutException {
        LOG.info("Rolling back the user");
        UserRollbackResponse response = new UserRollbackResponse();
        if (request.getUser() != null) {
            response.setN(userMao.removeUser(request.getUser()).getN());
        }
        return response;
    }

    @Secured @Override public ResetOtpResponse resetOTP(ResetOtpRequest request) {
        ResetOtpResponse response = new ResetOtpResponse();
        WsUser userDetails = request.getUserDetails();
        if (validateUserCredentials(userDetails.getUsername(), userDetails.getMobileNumber())) {
            UserVO user = userMao.getUser(userDetails);
            if (user != null) {
                response.setSuccess(true);
                response.setMessage("Otp Reset Successful");
                int otp = ThreadLocalRandom.current().nextInt(99999, 999999);
                if (OtpServer.sendOtp(userDetails.getMobileNumber(), otp)) {
                    userMao.setOtp(userDetails, otp);
                }
            } else {
                response.addError(ResponseCode.USER_DOES_NOT_EXIST, "User does not exist");
            }
            return response;
        } else {
            response.setSuccess(false);
            response.addError(ResponseCode.USER_CREDENTIALS_NOT_FORMATTED, "User credentials are not properly formatted");
            return response;
        }
    }

    @Secured @Override public VerifyOtpResponse verifyOTP(VerifyOtpRequest request) {
        VerifyOtpResponse response = new VerifyOtpResponse();
        WsUser userDetails = request.getUserDetails();
        UserVO user = userMao.getUser(userDetails);
        if (user != null) {
            if (user.getOtp().equals(userDetails.getOtp())) {
                response.setSuccess(true);
                response.setMessage("Otp Verified");
            } else {
                response.setSuccess(false);
                response.addError(ResponseCode.OTP_INCORRECT, "Entered OTP is incorrect");
            }
            return response;
        } else {
            response.setSuccess(false);
            response.addError(ResponseCode.USER_DOES_NOT_EXIST, "User does not exist");
            return response;
        }
    }

    @Override @Secured public ResetPasswordResponse userResetPassword(ResetPasswordRequest request) {
        ResetPasswordResponse response = new ResetPasswordResponse();
        String username = request.getUsername();
        String password = request.getPassword();
        UserVO user = userMao.getUserByUsernameOrMobileNumber(username);
        if (password != null && !password.isEmpty() && user != null) {
            userMao.resetPassword(user.getId(), password);
            AuthTokenVO authToken = new AuthTokenVO();
            String mAuthToken = null;
            if (jawtService != null) {
                try {
                    mAuthToken = jawtService.generateValidToken(
                            new GenerateValidTokenRequest(user.getUsername(), user.getPassword(), GenerateValidTokenRequest.Type.AUTH.name())).getToken().get("token");
                } catch (Exception e) {
                    response.addError(ResponseCode.NO_PASSWORD_SET, "Please set a password first");
                }
                if (!response.hasErrors()) {
                    String refreshToken = jawtService.generateValidToken(
                            new GenerateValidTokenRequest(user.getUsername(), user.getPassword(), GenerateValidTokenRequest.Type.REFRESH.name())).getToken().get("token");
                    authToken.setRefreshToken(refreshToken);
                    authToken.setAuthToken(mAuthToken);
                    authToken.setExpiry(new Date(System.currentTimeMillis() + 1800000));
                    authToken.setUserCode(user.getCode());
                    authToken.setUserName(user.getUsername());
                    authToken.setCreated(new Date());
                    authToken.setUpdated(new Date());
                    userMao.saveAuthToken(authToken);
                    response.setRefreshToken(refreshToken);
                    response.setAuthToken(mAuthToken);
                }
            } else {
                response.addError(ResponseCode.INTERNAL_SERVER_ERROR, "Internal Server Problem");
            }
            if (!response.hasErrors()) {
                response.setAuthToken(authToken.getAuthToken());
                response.setRefreshToken(authToken.getRefreshToken());
                response.setSuccess(true);
            }
        } else {
            response.addError(ResponseCode.USER_DOES_NOT_EXIST, "User does not exist");
        }
        response.setSuccess(!response.hasErrors());
        return response;
    }

    @Secured @Override public UserLoginResponse userLogin(UserLoginRequest request) {
        UserLoginResponse response = new UserLoginResponse();
        String username = request.getUsername();
        String password = request.getPassword();
        AuthTokenVO authToken = new AuthTokenVO();
        UserVO user = userMao.getUserByUsernameOrMobileNumber(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                if (jawtService != null) {
                    String mAuthToken = jawtService.generateValidToken(
                            new GenerateValidTokenRequest(user.getUsername(), user.getPassword(), GenerateValidTokenRequest.Type.AUTH.name())).getToken().get("token");
                    String refreshToken = jawtService.generateValidToken(
                            new GenerateValidTokenRequest(user.getUsername(), user.getPassword(), GenerateValidTokenRequest.Type.REFRESH.name())).getToken().get("token");
                    authToken.setRefreshToken(refreshToken);
                    authToken.setAuthToken(mAuthToken);
                    authToken.setUserCode(user.getCode());
                    authToken.setUserName(user.getUsername());
                    authToken.setExpiry(new Date(System.currentTimeMillis() + 1800000));
                    authToken.setCreated(new Date());
                    authToken.setUpdated(new Date());
                    userMao.saveAuthToken(authToken);
                    response.setSuccess(true);
                    response.setRefreshToken(refreshToken);
                    response.setAuthToken(mAuthToken);
                    response.setSuccess(true);
                } else {
                    response.addError(ResponseCode.INTERNAL_SERVER_ERROR, "Internal Server Problem");
                }
            } else {
                response.addError(ResponseCode.INVALID_CREDENTIALS, "Invalid Credentials");
            }
        } else {
            response.setSuccess(false);
            response.addError(ResponseCode.USER_DOES_NOT_EXIST, "User does not exist");
        }
        return response;
    }

    @Secured @Override public AssertFingerprintResponse assertFingerprint(AssertFingerprintRequest request) {
        AssertFingerprintResponse response = new AssertFingerprintResponse();
        String userCode = request.getCallingUserId();
        UserVO user = userMao.getUserByUserCode(userCode);
        if (user != null) {
            List<DeviceFingerprintVO> fingerprints = new ArrayList<DeviceFingerprintVO>();
            DeviceFingerprintVO deviceFingerprint = new DeviceFingerprintVO();
            WsDeviceFingerprint wsDeviceFingerprint = request.getDeviceFingerprint();
            deviceFingerprint.setGcmId(wsDeviceFingerprint.getGcmId());
            deviceFingerprint.setMacAddress(wsDeviceFingerprint.getMacAddress());
            deviceFingerprint.setCreated(new Date());
            deviceFingerprint.setUpdated(new Date());
            userMao.saveFingerprint(deviceFingerprint);
            fingerprints.add(deviceFingerprint);
            user.setFingerprints(fingerprints);
            userMao.updateUser(user);
            response.setMessage("Fingerprint Updated");
            response.setSuccess(true);
        } else {
            response.addError(ResponseCode.USER_DOES_NOT_EXIST, "User does not exist");
        }
        return response;
    }

    private boolean validateUserCredentials(String userName, String mobileNumber) {
        String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (mobileNumber.length() == 10 && mobileNumber.matches("[0-9]+") && userName.matches(EMAIL_REGEX)) {
            return true;
        } else {
            return false;
        }
    }
}
