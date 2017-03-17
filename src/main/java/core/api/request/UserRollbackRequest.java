package main.java.core.api.request;

import main.java.core.api.base.ServiceRequest;
import main.java.core.vo.UserVO;

/**
 * Created by digvijaysharma on 26/01/17.
 */
public class UserRollbackRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    public UserVO user;

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }
}

