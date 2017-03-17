package main.java.core.api.validation;

/**
 * Created by admin on 4/30/16.
 */
public enum ResponseCode {

    USER_ALREADY_EXISTS(100),
    USER_DOES_NOT_EXIST(101),
    OTP_INCORRECT(102),
    USER_CREDENTIALS_NOT_FORMATTED(103),
    UNAUTHORISED_USER(104),
    INVALID_CREDENTIALS(105),
    PROFILE_NOT_UPDATED(106),
    PROFILE_ALREADY_EXISTS(107),
    USER_PROFILE_DOES_NOT_EXIST(108),
    INVALID_CUSTOMER(109),
    EMPTY_FILE_LINK(110),
    UNSUPPORTED_FILE_FORMAT(111),
    MEDICINES_CANNOT_BE_BLANK(112),
    INVALID_PRIORITY(113),
    INVALID_USER_TYPE(114),
    SELLER_PROFILE_DOES_NOT_EXIST(115),
    INTERNAL_SERVER_ERROR(116),
    AUTH_TOKEN_STILL_VALID(117),
    INVALID_REFRESH_TOKEN(118),
    NO_PASSWORD_SET(119),
    INVALID_SELLER(120),
    INVALID_PAGE_NO(121),
    INVALID_CUSTOMER_LOCALTION(122),
    INVALID_INQUIRY(123);

    int    code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
