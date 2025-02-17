package app.sync.domain.user.service;

import app.sync.global.session.SessionUserDto;
import app.sync.global.session.SessionUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {

    /**
     * 로그인 회원 값을 지정한다.
     */
    public static void setCurrentUser(Long userId, String userEmail) {
        SessionUtils.setAttribute(SessionUtils.SESSION_USER_KEY, new SessionUserDto(userId, userEmail));
    }

    /**
     * 로그인 회원 값을 가져온다.
     */
    public static Long getCurrentUser() {
        return (!hasCurrentUser()) ? null : SessionUtils.getAttribute(SessionUtils.SESSION_USER_KEY).userId();
    }

    /**
     * 로그인 회원 값을 확인한다.
     */
    public static Boolean hasCurrentUser() {
        return SessionUtils.hasAttribute(SessionUtils.SESSION_USER_KEY);
    }
}