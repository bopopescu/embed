package main.java.core.api.response;

import java.util.List;
import main.java.core.api.base.ServiceResponse;
import main.java.core.dto.InquiryDTO;

/**
 * Created by digvijaysharma on 02/02/17.
 */
public class GetInquiriesResponse extends ServiceResponse {

    private static final long serialVersionUID = -7191710901031348258L;

    private List<InquiryDTO> inquiries;

    public List<InquiryDTO> getInquiries() {
        return inquiries;
    }

    public void setInquiries(List<InquiryDTO> inquiries) {
        this.inquiries = inquiries;
    }
}


