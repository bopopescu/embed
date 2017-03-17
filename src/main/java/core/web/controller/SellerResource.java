package main.java.core.web.controller;

import io.dropwizard.auth.Auth;
import java.io.InputStream;
import java.security.Principal;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import main.java.core.annotation.Controller;
import main.java.core.api.request.CreateActionRequest;
import main.java.core.api.request.CreateSellerProfileRequest;
import main.java.core.api.request.GetActionsRequest;
import main.java.core.api.request.GetInquiriesRequest;
import main.java.core.api.request.GetOrdersRequest;
import main.java.core.api.request.GetProfileRequest;
import main.java.core.api.request.UpdateSellerProfileRequest;
import main.java.core.api.response.CreateActionResponse;
import main.java.core.api.response.CreateSellerProfileResponse;
import main.java.core.api.response.GetActionsResponse;
import main.java.core.api.response.GetInquiriesResponse;
import main.java.core.api.response.GetOrdersResponse;
import main.java.core.api.response.GetSellerProfileResponse;
import main.java.core.api.response.SetProfileImageResponse;
import main.java.core.api.response.UpdateSellerProfileResponse;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import services.file.impl.FileServiceImpl;
import services.inquiry.impl.InquiryServiceImpl;
import services.notification.impl.NotificationServiceImpl;
import services.seller.impl.SellerServiceImpl;
import services.userProfile.impl.UserProfileServiceImpl;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Controller @Path("/seller") @Consumes(MediaType.APPLICATION_JSON) @Produces(MediaType.APPLICATION_JSON) public class SellerResource {

    private UserProfileServiceImpl userProfileService;

    private SellerServiceImpl sellerService;

    private NotificationServiceImpl notificationService;

    private FileServiceImpl fileService;

    private InquiryServiceImpl inquiryService;

    public SellerResource(SellerServiceImpl sellerService, NotificationServiceImpl notificationService,
            FileServiceImpl fileService, UserProfileServiceImpl userProfileService, InquiryServiceImpl inquiryService)
    {
        this.inquiryService = inquiryService;
        this.sellerService = sellerService;
        this.userProfileService = userProfileService;
        this.notificationService = notificationService;
        this.fileService = fileService;
    }

    @POST @Path("/inquiries") public GetInquiriesResponse getInquiriesOfSeller(@Auth Principal user, GetInquiriesRequest request) {
        request.setCallingUserId(user.getName());
        return sellerService.getInquiriesOfSeller(request);
    }

    @POST @Path("/getProfile") public GetSellerProfileResponse getUserProfile(
            @Auth Principal user, GetProfileRequest request)
    {
        request.setCallingUserId(user.getName());
        return sellerService.getCurrentSellerProfile(request);
    }

    @Consumes(MediaType.MULTIPART_FORM_DATA) @POST @Path("/setProfileImage") public SetProfileImageResponse setProfileImage(
            @Auth Principal user, @FormDataParam("uploadFile") InputStream fileInputStream,
            @FormDataParam("uploadFile") FormDataContentDisposition fileFormDataContentDisposition)
    {
        SetProfileImageResponse response = userProfileService.setProfileImage(user.getName());
        if (!response.hasErrors()) {
            fileService.uploadImageFile(fileInputStream, fileFormDataContentDisposition);
        }
        return response;
    }

    @POST @Path("/orders") public GetOrdersResponse getOrdersOfSeller(@Auth Principal user, GetOrdersRequest request) {
        request.setCallingUserId(user.getName());
        return sellerService.getOrdersOfSeller(request);
    }

    @POST @Path("/actions") public GetActionsResponse getActionsOfSeller(@Auth Principal user, GetActionsRequest request) {
        request.setCallingUserId(user.getName());
        return sellerService.getActionsOfSeller(request);
    }

    @POST @Path("/createAction") public CreateActionResponse createAction(@Auth Principal user, CreateActionRequest request) {
        request.setCallingUserId(user.getName());
        return sellerService.createAction(request);
    }
    
    @POST @Path("/createProfile") public CreateSellerProfileResponse createSellerProfile(
            @Auth Principal user, CreateSellerProfileRequest request)
    {
        request.setCallingUserId(user.getName());
        return sellerService.createSellerProfile(request);
    }

    @POST @Path("/updateProfile") public UpdateSellerProfileResponse updateSellerProfile(
            @Auth Principal user, UpdateSellerProfileRequest request)
    {
        request.setCallingUserId(user.getName());
        return sellerService.updateSellerProfile(request);
    }
}
