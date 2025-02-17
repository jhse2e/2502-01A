package app.sync.global.session;

import app.sync.global.exception.ServerException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionUtils {
    public static final String SESSION_USER_KEY = "USER";

    /**
     * 세션 값을 세팅한다.
     */
    public static void setAttribute(String key, Object value) {
        HttpSession httpSession = SessionUtils.getHttpSession();

        httpSession.setAttribute(key, value);
    }

    /**
     * 세션 값을 조회한다.
     */
    public static SessionUserDto getAttribute(String key) {
        HttpSession httpSession = SessionUtils.getHttpSession();
        if (httpSession.getAttribute(key) == null) {
            throw new ServerException(null);
        }

        return (SessionUserDto) httpSession.getAttribute(key);
    }

    /**
     * 세션 값을 조회한다.
     */
    public static Optional<SessionUserDto> getAttributeOpt(String key) {
        HttpSession httpSession = SessionUtils.getHttpSession();

        return Optional.ofNullable((SessionUserDto) httpSession.getAttribute(key));
    }

    /**
     * 세션 값을 확인한다.
     */
    public static Boolean hasAttribute(String key) {
        HttpSession httpSession = SessionUtils.getHttpSession();

        return httpSession.getAttribute(key) != null;
    }

    /**
     * 세션 값을 삭제한다.
     */
    public static void unsetAttribute(String key) {
        HttpSession httpSession = SessionUtils.getHttpSession();

        httpSession.removeAttribute(key);
    }

    /**
     *
     */
    public static HttpSession getHttpSession() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        return attributes.getRequest().getSession();
    }

    /**
     *
     */
    public static HttpServletRequest getHttpRequest() {
        ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());

        return attributes.getRequest();
    }
}