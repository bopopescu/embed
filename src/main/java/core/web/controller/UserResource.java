package main.java.core.web.controller;

import io.dropwizard.auth.Auth;
import java.security.Principal;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import main.java.core.annotation.Controller;
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
import main.java.core.api.response.VerifyOtpResponse;
import main.java.core.api.validation.ResponseCode;
import main.java.core.vo.UserVO;
import services.customer.impl.CustomerServiceImpl;
import services.seller.impl.SellerServiceImpl;
import services.user.impl.UserServiceImpl;

/**
 * Created by admin on 4/27/16.
 */
@Controller
@Path("/user") @Consumes(MediaType.APPLICATION_JSON) @Produces(MediaType.APPLICATION_JSON) public class UserResource {

    private UserServiceImpl userService;

    private SellerServiceImpl sellerService;

    private CustomerServiceImpl customerService;

    public UserResource(UserServiceImpl userService, SellerServiceImpl sellerService, CustomerServiceImpl customerService) {
        this.customerService = customerService;
        this.sellerService = sellerService;
        this.userService = userService;
    }

    @POST @Path("/signup") public SignupResponse userSignUp(SignupRequest request) {
        UserRollbackRequest userRollbackRequest = new UserRollbackRequest();
        SignupResponse response = userService.userSignUp(request, userRollbackRequest);
        if(!response.hasErrors()) {
            if(response.getType() != null) {
                if(response.getType().equals(UserVO.Type.SELLER.name())) {
                    sellerService.createSeller(userRollbackRequest);
                }
                else if(response.getType().equals(UserVO.Type.CUSTOMER.name())) {
                    customerService.createCustomer(userRollbackRequest);
                }
                else {
                    String type = response.getType();
                    SignupResponse signupResponse = new SignupResponse();
                    signupResponse.setSuccess(false);
                    signupResponse.addError(ResponseCode.INVALID_USER_TYPE, "User Type is neither seller nor customer : " + type);
                    return signupResponse;
                }
            }
            else {
                userService.userRollback(userRollbackRequest);
                String type = response.getType();
                SignupResponse signupResponse = new SignupResponse();
                signupResponse.setSuccess(false);
                signupResponse.addError(ResponseCode.INVALID_USER_TYPE, "User Type is neither seller nor customer : " + type);
                return signupResponse;
            }
        }
        return response;
    }

    @POST @Path("/verifyOtp") public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) {
        return userService.verifyOTP(request);
    }

    @POST @Path("/resetotp") public ResetOtpResponse resetOTP(ResetOtpRequest request) {
        return userService.resetOTP(request);
    }

    @POST @Path("/resetpassword") public ResetPasswordResponse userResetPassword(ResetPasswordRequest request) {
        return userService.userResetPassword(request);
    }

    @POST @Path("/login") public UserLoginResponse userLogin(UserLoginRequest request) {
        return userService.userLogin(request);
    }

    @POST @Path("/assertFingerprint") public AssertFingerprintResponse assertFingerprint(@Auth Principal user,
            AssertFingerprintRequest request) {
        request.setCallingUserId(user.getName());
        return userService.assertFingerprint(request);
    }
}
