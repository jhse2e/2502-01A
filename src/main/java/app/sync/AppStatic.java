package app.sync;

import java.util.concurrent.TimeUnit;

public class AppStatic {
    public static final String USER_EMAIL_CODE_TITLE = "인증 번호";
    public static final Integer USER_EMAIL_CODE_RANGE_START = 1000;
    public static final Integer USER_EMAIL_CODE_RANGE_STOP = 10000;
    public static final Integer USER_EMAIL_RETRY_COUNT_MAX = 3;
    public static final Long USER_EMAIL_CODE_TIME_LIMIT = 180L;
    public static final TimeUnit USER_EMAIL_CODE_TIME_LIMIT_UNIT = TimeUnit.SECONDS;

    public static final Integer ORDER_ID_RANGE_START = 100;
    public static final Integer ORDER_ID_RANGE_STOP = 1000;
    public static final Integer PAYMENT_ID_RANGE_START = 100;
    public static final Integer PAYMENT_ID_RANGE_STOP = 1000;
    public static final String PAYMENT_COMPLETE_FAILED_MESSAGE = "결제 승인 오류";
}