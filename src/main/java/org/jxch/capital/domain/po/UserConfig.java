package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "user_config")
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_user_config_username", columnList = "username"),
        @Index(name = "index_user_config_email", columnList = "email")})
public class UserConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IdGenerators.COMM_SEQ)
    @SequenceGenerator(name = IdGenerators.COMM_SEQ, sequenceName = IdGenerators.COMM_SEQ, allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String username;
    private String email;
    private String nickname;
}
