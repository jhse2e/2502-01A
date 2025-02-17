package app.sync.infra.mail.client;

import app.sync.AppStatic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailClient {
    private final Environment env;
    private final JavaMailSender mailSender;

    @Async(value = "mailExecutor")
    @Retryable(retryFor = {MailSendException.class}, backoff = @Backoff(delay = 1000))
    public void mail(String email, String title, String content) {
        int retryCount = RetrySynchronizationManager.getContext().getRetryCount();
        log.info("[메일 전송] email: {}, retry: {}/{}", email, ++retryCount, AppStatic.USER_EMAIL_RETRY_COUNT_MAX);

        mailSender.send(createMailMessage(email, title, content));
        log.info("[메일 전송 완료] email: {}", email);
    }

    @Recover
    public void recoverMail(MailException exception) {
        log.error("[메일 전송 실패] {}", exception.getMessage(), exception);
    }

    private SimpleMailMessage createMailMessage(String email, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(env.getProperty("spring.mail.username"));
        message.setTo(email);
        message.setSubject(title);
        message.setText(content);

        return message;
    }
}