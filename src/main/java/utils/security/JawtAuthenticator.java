package main.java.utils.security;

import core.vo.JawtUserVO;
import core.vo.UserVO;
import io.dropwizard.auth.Authenticator;
import static java.math.BigDecimal.ONE;
import java.util.Optional;
import mao.user.impl.UserMaoImpl;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.JwtContext;

/**
 * Created by digvijaysharma on 08/01/17.
 */
public class JawtAuthenticator implements Authenticator<JwtContext, JawtUserVO> {

    private UserMaoImpl userMao;

    public JawtAuthenticator(UserMaoImpl userMao) {
        this.userMao = userMao;
    }

    @Override
    public Optional<JawtUserVO> authenticate(JwtContext context) {
        try {
            final String subject = context.getJwtClaims().getSubject();
            UserVO user = userMao.getUserById(subject);
            if(user != null) {
                return Optional.of(new JawtUserVO(ONE, subject));
            }
            return Optional.empty();
        }
        catch (MalformedClaimException e) { return Optional.empty(); }
    }
}
