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
import main.java.core.api.request.CreateCustomerProfileRequest;
import main.java.core.api.request.FindNearbySellersRequest;
import main.java.core.api.request.GetInquiriesRequest;
import main.java.core.api.request.GetOrdersRequest;
import main.java.core.api.request.GetProfileRequest;
import main.java.core.api.request.NotificationRequest;
import main.java.core.api.request.UpdateCustomerProfileRequest;
import main.java.core.api.response.CreateCustomerProfileResponse;
import main.java.core.api.response.FindNearbySellersResponse;
import main.java.core.api.response.GetCustomerProfileResponse;
import main.java.core.api.response.GetInquiriesResponse;
import main.java.core.api.response.GetOrdersResponse;
import main.java.core.api.response.NotificationResponse;
import main.java.core.api.response.SetProfileImageResponse;
import main.java.core.api.response.UpdateCustomerProfileResponse;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import services.customer.impl.CustomerServiceImpl;
import services.file.impl.FileServiceImpl;
import services.inquiry.impl.InquiryServiceImpl;
import services.notification.impl.NotificationServiceImpl;
import services.seller.impl.SellerServiceImpl;
import services.userProfile.impl.UserProfileServiceImpl;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Controller
@Path("/customer") @Consumes(MediaType.APPLICATION_JSON) @Produces(MediaType.APPLICATION_JSON) public class CustomerResource {

    private CustomerServiceImpl customerService;

    private NotificationServiceImpl notificationService;

    private InquiryServiceImpl inquiryService;

    private UserProfileServiceImpl userProfileService;

    private FileServiceImpl fileService;

    private SellerServiceImpl sellerService;

    public CustomerResource(FileServiceImpl fileService, UserProfileServiceImpl userProfileService, NotificationServiceImpl notificationService, SellerServiceImpl sellerService, CustomerServiceImpl customerService, InquiryServiceImpl inquiryService) {
        this.customerService = customerService;
        this.fileService = fileService;
        this.userProfileService = userProfileService;
        this.notificationService = notificationService;
        this.sellerService = sellerService;
        this.inquiryService = inquiryService;
    }

    @POST @Path("/notifySellers") public NotificationResponse notifySellers(@Auth Principal user, NotificationRequest request) {
        request.setCallingUserId(user.getName());
        return notificationService.notifySellers(request);
    }

    @POST @Path("/inquiries") public GetInquiriesResponse getInquiriesOfCustomer(@Auth Principal user, GetInquiriesRequest request) {
        request.setCallingUserId(user.getName());
        return inquiryService.getInquiriesOfCustomer(request);
    }

    @POST @Path("/orders") public GetOrdersResponse getOrdersOfCustomer(@Auth Principal user, GetOrdersRequest request) {
        request.setCallingUserId(user.getName());
        return customerService.getOrdersOfCustomer(request);
    }

    @POST @Path("/findNearbySellers") public FindNearbySellersResponse findNearbySellers(@Auth Principal user, FindNearbySellersRequest request) {
        request.setCallingUserId(user.getName());
        return customerService.findNearbySellers(request);
    }

    @POST @Path("/getProfile") public GetCustomerProfileResponse getUserProfile(
            @Auth Principal user, GetProfileRequest request)
    {
        request.setCallingUserId(user.getName());
        return customerService.getCurrentCustomerProfile(request);
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

    @POST @Path("/createProfile") public CreateCustomerProfileResponse createCustomerProfile(
            @Auth Principal user, CreateCustomerProfileRequest request)
    {
        request.setCallingUserId(user.getName());
        return customerService.createCustomerProfile(request);
    }

    @POST @Path("/updateProfile") public UpdateCustomerProfileResponse updateCustomerProfile(
            @Auth Principal user, UpdateCustomerProfileRequest request)
    {
        request.setCallingUserId(user.getName());
        return customerService.updateCustomerProfile(request);
    }
}

