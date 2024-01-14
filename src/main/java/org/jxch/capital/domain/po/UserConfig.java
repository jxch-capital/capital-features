package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "user_config")
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_username", columnList = "username"),
        @Index(name = "index_email", columnList = "email")})
public class UserConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.COMM_SEQ)
    private Long id;
    private String username;
    private String email;
    private String nickname;
}
