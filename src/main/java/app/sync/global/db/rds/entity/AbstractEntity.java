package app.sync.global.db.rds.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class AbstractEntity implements Serializable {

    /**
     * 생성 시점
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    /**
     * 수정 시점
     */
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updateAt;
}