package app.sync.domain.user.service;

import app.sync.AppStatic;
import app.sync.domain.user.db.entity.User;
import app.sync.domain.user.db.repository.UserRepository;
import app.sync.domain.user.dto.request.UserAuthenticateDto;
import app.sync.domain.user.dto.request.UserCreateDto;
import app.sync.domain.user.dto.request.UserEmailCodeAuthenticateDto;
import app.sync.domain.user.dto.request.UserEmailCodeCreateDto;
import app.sync.domain.user.dto.response.UserAuthenticateResultDto;
import app.sync.domain.user.dto.response.UserCreateResultDto;
import app.sync.domain.user.dto.response.UserEmailCodeAuthenticateResultDto;
import app.sync.domain.user.dto.response.UserEmailCodeCreateResultDto;
import app.sync.global.db.redis.client.RedisClient;
import app.sync.infra.mail.client.MailClient;
import app.sync.global.exception.ServerException;
import app.sync.global.exception.ServerExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final MailClient mailClient;
    private final RedisClient redisClient;
    private final UserRepository userRepository;

    /**
     * 회원 생성
     */
    @Transactional
    public UserCreateResultDto createUser(UserCreateDto userDto) {
        String userEmail = userDto.email();
        String userPassword = userDto.password();

        // 회원 중복 확인
        if (userRepository.existsByEmail(userEmail)) {
            throw new ServerException(ServerExceptionType.USER_ALREADY_EXISTED);
        }

        User user = userRepository.save(User.create(userEmail, userPassword));

        return new UserCreateResultDto(user.getId());
    }

    /**
     * 회원 인증 메일 생성
     */
    public UserEmailCodeCreateResultDto createUserEmailCode(UserEmailCodeCreateDto userDto) {
        String userEmail = userDto.email();
        String userEmailCode = String.valueOf(ThreadLocalRandom.current().nextInt(AppStatic.USER_EMAIL_CODE_RANGE_START, AppStatic.USER_EMAIL_CODE_RANGE_STOP));

        // 이메일 발송 및 인증 번호 캐싱
        mailClient.mail(userEmail, AppStatic.USER_EMAIL_CODE_TITLE, userEmailCode);
        redisClient.cache(userEmail, userEmailCode, AppStatic.USER_EMAIL_CODE_TIME_LIMIT, AppStatic.USER_EMAIL_CODE_TIME_LIMIT_UNIT);

        return new UserEmailCodeCreateResultDto(userEmail);
    }

    /**
     * 회원 인증
     */
    @Transactional(readOnly = true)
    public UserAuthenticateResultDto authenticateUser(UserAuthenticateDto userDto) {
        String userEmail = userDto.email();
        String userPassword = userDto.password();

        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ServerException(ServerExceptionType.USER_NOT_EXISTED));

        // 회원 비밀번호 비교
        if (!user.equalsPassword(userPassword)) {
            throw new ServerException(ServerExceptionType.USER_AUTHENTICATE_FAILED);
        }

        // 회원 세션 저장
        UserUtils.setCurrentUser(user.getId(), userEmail);

        return new UserAuthenticateResultDto(user.getId(), userEmail);
    }

    /**
     * 회원 인증 메일 번호 확인
     */
    public UserEmailCodeAuthenticateResultDto authenticateUserEmailCode(UserEmailCodeAuthenticateDto userDto) {
        String userEmail = userDto.email();
        String userEmailCode = userDto.emailCode();

        // 회원 인증 번호 비교
        if (!redisClient.equalsValue(userEmail, userEmailCode)) {
            throw new ServerException(ServerExceptionType.USER_EMAIL_AUTHENTICATE_FAILED);
        }

        return new UserEmailCodeAuthenticateResultDto(userEmail);
    }
}