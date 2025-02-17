package app.sync.domain.product.db;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProductStatusType {

    /**
     *
     */
    READY(""),

    /**
     *
     */
    DELETED("");

    private final String value;
}