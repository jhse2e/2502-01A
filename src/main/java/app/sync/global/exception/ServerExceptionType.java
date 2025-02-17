package app.sync.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ServerExceptionType {
    USER_NOT_EXISTED("USER_NOT_EXISTED", "회원 정보를 찾지 못하였습니다.", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTED("USER_ALREADY_EXISTED", "이미 등록된 회원입니다.", HttpStatus.CONFLICT),
    USER_AUTHENTICATE_FAILED("USER_AUTHENTICATE_FAILED", "회원 인증을 실패하였습니다.", HttpStatus.FORBIDDEN),
    USER_EMAIL_AUTHENTICATE_FAILED("USER_EMAIL_AUTHENTICATE_FAILED", "회원 이메일 인증을 실패하였습니다.", HttpStatus.FORBIDDEN),
    PRODUCT_NOT_EXISTED("PRODUCT_NOT_EXISTED", "상품 정보를 찾지 못하였습니다.", HttpStatus.NOT_FOUND),
    PRODUCT_STOCK_NOT_ENOUGH("PRODUCT_STOCK_NOT_ENOUGH", "상품 재고가 부족합니다.", HttpStatus.BAD_REQUEST),
    ORDER_NOT_EXISTED("ORDER_NOT_EXISTED", "주문 정보를 찾지 못하였습니다.", HttpStatus.NOT_FOUND),
    ORDER_USER_NOT_MATCHED("ORDER_USER_NOT_MATCHED", "주문 회원이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    PAYMENT_NOT_EXISTED("PAYMENT_NOT_EXISTED", "결제 정보를 찾지 못하였습니다.", HttpStatus.NOT_FOUND),
    PAYMENT_TRANSACTION_USER_NOT_MATCHED("PAYMENT_TRANSACTION_USER_NOT_MATCHED", "결제 거래 회원이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    PAYMENT_TRANSACTION_KEY_NOT_MATCHED("PAYMENT_TRANSACTION_KEY_NOT_MATCHED", "결제 거래 키가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    PAYMENT_TRANSACTION_AMOUNT_NOT_MATCHED("PAYMENT_TRANSACTION_AMOUNT_NOT_MATCHED", "결제 거래 금액이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    PAYMENT_NOT_CANCELABLE("PAYMENT_NOT_CANCELABLE", "결제 취소할 수 없습니다.", HttpStatus.BAD_REQUEST),
    PAYMENT_CANCEL_FAILED("PAYMENT_CANCEL_FAILED", "결제 취소를 처리하지 못하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FAILED("FAILED", "요청을 처리하지 못하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}