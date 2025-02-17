package app.sync.infra.mail.client;

import app.sync.AppStatic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import java.util.Collection;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles(value = {"local-test"})
public class MailClientTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private JavaMailSender mailSender;

    @TestFactory
    @DisplayName(value = "회원 인증 메일 테스트")
    public Collection<DynamicTest> sendAsyncUserEmailCode() {
        // Given
        String userEmail = "test1@test.com";
        String userEmailCode = "123456";

        willThrow(MailSendException.class)
                .given(mailSender)
                .send(any(SimpleMailMessage.class));

        // When, Then
        return List.of(
                dynamicTest("메일 발송 시, 예외가 발생할 경우 예외 처리한다.", () -> {
                    assertThatCode(() -> mailClient.mail(userEmail, AppStatic.USER_EMAIL_CODE_TITLE, userEmailCode)).doesNotThrowAnyException();
                }),
                dynamicTest("메일 발송 실패 시, 총 3번을 재시도한다", () -> {
                    Thread.sleep(5000);
                    verify(mailSender, times(3)).send(any(SimpleMailMessage.class));
                })
        );
    }
}