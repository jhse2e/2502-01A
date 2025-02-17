package app.sync.domain.payment.db;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PaymentStatusType {

    /**
     * 결제 준비
     */
    READY("결제 준비"),

    /**
     * 결제 완료
     */
    COMPLETED("결제 완료"),

    /**
     * 결제 취소
     */
    CANCELED("결제 취소"),

    /**
     * 결제 실패
     */
    FAILED("결제 실패");

    private final String value;

    public Boolean isReady() {
        return this == PaymentStatusType.READY;
    }

    public Boolean isCompleted() {
        return this == PaymentStatusType.COMPLETED;
    }

    public Boolean isCanceled() {
        return this == PaymentStatusType.CANCELED;
    }

    public Boolean isFailed() {
        return this == PaymentStatusType.FAILED;
    }
}