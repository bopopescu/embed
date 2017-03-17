package services.notification.impl;

import core.annotation.Secured;
import core.annotation.Service;
import core.api.request.NotificationRequest;
import core.api.response.NotificationResponse;
import core.api.validation.ResponseCode;
import core.vo.DeviceFingerprintVO;
import core.vo.InquiryVO;
import core.vo.SellerVO;
import core.vo.UserVO;
import java.util.ArrayList;
import java.util.List;
import mao.inquiry.impl.InquiryMaoImpl;
import mao.seller.impl.SellerMaoImpl;
import mao.user.impl.UserMaoImpl;
import services.notification.INotificationService;
import utils.gcm.GcmServer;
import utils.json.JsonUtils;

/**
 * Created by digvijaysharma on 07/01/17.
 */
@Service public class NotificationServiceImpl implements INotificationService {

    private org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(NotificationServiceImpl.class);

    private UserMaoImpl userMao;

    private SellerMaoImpl sellerMao;

    private InquiryMaoImpl inquiryMao;

    public NotificationServiceImpl(SellerMaoImpl sellerMao, InquiryMaoImpl inquiryMao, UserMaoImpl userMao) {
        this.inquiryMao = inquiryMao;
        this.sellerMao = sellerMao;
        this.userMao = userMao;
    }

    /**
     * @param code Needs to be the User Id whose devices need to be notified
     * @param data Needs to be a Json Object "{"X":...}" in String form
     */
    @Override public boolean push(String code, String data) {
        UserVO user = userMao.getUserByUserCode(code);
        boolean successful = false;
        for (DeviceFingerprintVO deviceFingerprint : user.getFingerprints()) {
            if(deviceFingerprint != null) {
                GcmServer.sendMessage(deviceFingerprint.getGcmId(), data);
                successful = true;
            }
        }
        return successful;
    }

    @Override
    @Secured
    public NotificationResponse notifySellers(NotificationRequest request) {
        NotificationResponse response = new NotificationResponse();
        InquiryVO inquiry = inquiryMao.getInquiryByCode(request.getInquiry());
        List<String> pushedSellers = new ArrayList<String>();
        List<String> failedSellers = new ArrayList<String>();
        if(inquiry != null) {
            for (String sellerCode : request.getSellers()) {
                SellerVO seller = sellerMao.getSellerByUserId(sellerCode);
                if(seller != null) {
                    if(push(sellerCode, inquiry.toString())) {
                        pushedSellers.add(sellerCode);
                    }
                    else {
                        failedSellers.add(sellerCode);
                        LOG.error("Could not push on any device for seller : " + seller.getUsername());
                    }
                }
                else {
                    LOG.error("Seller does not exist");
                    failedSellers.add(sellerCode);
                }
            }
            response.setSuccess(true);
            response.setMessage("Following Sellers were Notified : " + JsonUtils.objectToString(pushedSellers) +
                    "\n Following Sellers could not be notified : " + JsonUtils.objectToString(failedSellers));
        }
        else {
            response.addError(ResponseCode.INVALID_INQUIRY, "Inquiry does not exist");
        }
        return response;
    }

}

