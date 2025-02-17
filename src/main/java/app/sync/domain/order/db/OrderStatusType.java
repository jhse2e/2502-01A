package app.sync.domain.order.db;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatusType {

    /**
     * 주문 준비
     */
    READY("주문 준비"),

    /**
     * 주문 완료
     */
    COMPLETED("주문 완료"),

    /**
     * 주문 취소
     */
    CANCELED("주문 취소");

    private final String value;

    public Boolean isReady() {
        return this == OrderStatusType.READY;
    }

    public Boolean isCompleted() {
        return this == OrderStatusType.COMPLETED;
    }

    public Boolean isCanceled() {
        return this == OrderStatusType.CANCELED;
    }
}