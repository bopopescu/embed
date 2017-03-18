package main.java.services.user;

import com.mongodb.MongoTimeoutException;
import main.java.core.annotation.Secured;
import main.java.core.api.request.AssertFingerprintRequest;
import main.java.core.api.request.ResetOtpRequest;
import main.java.core.api.request.ResetPasswordRequest;
import main.java.core.api.request.SignupRequest;
import main.java.core.api.request.UserLoginRequest;
import main.java.core.api.request.UserRollbackRequest;
import main.java.core.api.request.VerifyOtpRequest;
import main.java.core.api.response.AssertFingerprintResponse;
import main.java.core.api.response.ResetOtpResponse;
import main.java.core.api.response.ResetPasswordResponse;
import main.java.core.api.response.SignupResponse;
import main.java.core.api.response.UserLoginResponse;
import main.java.core.api.response.UserRollbackResponse;
import main.java.core.api.response.VerifyOtpResponse;

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
