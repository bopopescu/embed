package main.java.core.web.controller;

import io.dropwizard.auth.Auth;
import java.security.Principal;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import main.java.core.annotation.Controller;
import main.java.core.api.request.CreateInquiryRequest;
import main.java.core.api.request.CreateRandomCodeRequest;
import main.java.core.api.response.CreateInquiryResponse;
import services.inquiry.impl.InquiryServiceImpl;
import services.random.impl.RandomCodeServiceImpl;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Controller
@Path("/inquiry") @Consumes(MediaType.APPLICATION_JSON) @Produces(MediaType.APPLICATION_JSON) public class InquiryResource {

    private InquiryServiceImpl inquiryService;

    private RandomCodeServiceImpl randomCodeService;

    public InquiryResource(InquiryServiceImpl inquiryService, RandomCodeServiceImpl randomCodeService) {
        this.inquiryService = inquiryService;
        this.randomCodeService = randomCodeService;
    }

    @POST @Path("/create") public CreateInquiryResponse createInquiry(@Auth Principal user, CreateInquiryRequest request) {
        String code = randomCodeService.createRandomCode(new CreateRandomCodeRequest(0,9)).getCode();
        request.setCallingUserId(user.getName());
        return inquiryService.createInquiry(request, code);
    }
}

