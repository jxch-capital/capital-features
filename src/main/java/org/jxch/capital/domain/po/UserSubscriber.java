package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name = "user_subscriber")
@Table(indexes = {@Index(name = "user_subscriber_userId", columnList = "user_id")})
public class UserSubscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IdGenerators.COMM_SEQ)
    @SequenceGenerator(name = IdGenerators.COMM_SEQ, sequenceName = IdGenerators.COMM_SEQ, allocationSize = 1)
    private Long id;
    private Long userId;
    private Long subscriberConfigGroupId;
    private String cron;
    private Date lastSubscribeTime;
    @Column(columnDefinition = "TEXT")
    private String remark;
}
