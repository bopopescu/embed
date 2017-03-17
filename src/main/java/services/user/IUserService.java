package main.java.services.user;

import com.mongodb.MongoTimeoutException;
import core.annotation.Secured;
import core.api.request.AssertFingerprintRequest;
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

/**
 * Created by digvijaysharma on 16/12/16.
 */
public interface IUserService {

    @Secured SignupResponse userSignUp(SignupRequest request, UserRollbackRequest userRollbackRequest) throws MongoTimeoutException;

    @Secured UserRollbackResponse userRollback(UserRollbackRequest request) throws MongoTimeoutException;

    ResetOtpResponse resetOTP(ResetOtpRequest request);

    VerifyOtpResponse verifyOTP(VerifyOtpRequest request);

    ResetPasswordResponse userResetPassword(ResetPasswordRequest request);

    UserLoginResponse userLogin(UserLoginRequest request);

    @Secured AssertFingerprintResponse assertFingerprint(AssertFingerprintRequest request);
}
