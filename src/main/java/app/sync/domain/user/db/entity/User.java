package app.sync.domain.user.db.entity;

import app.sync.global.db.rds.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 회원
 */
@Getter
@Entity
@Table(
    name = "user",
    indexes = {
        // @Index(name = "user_idx_1", columnList = "xxx ASC|DESC"),
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "user_uk_1", columnNames = {"email"}),
    }
)
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractEntity implements Serializable {

    /**
     * 회원 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 회원 이메일
     */
    @Column(nullable = false)
    private String email;

    /**
     * 회원 비밀번호
     */
    @Column(nullable = false)
    private String password;

    @Builder(access = AccessLevel.PUBLIC)
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * 회원을 생성한다.
     */
    public static User create(String email, String password) {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }

    /**
     * 회원 비밀번호를 비교한다.
     */
    public Boolean equalsPassword(String password) {
        return this.password.equals(password);
    }
}