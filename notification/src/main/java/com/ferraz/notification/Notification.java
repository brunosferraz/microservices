package com.ferraz.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notificationId", nullable = false)
    private Integer notificationId;

    private String message;
    private String sender;
    private LocalDateTime sendAt;
    private String toCustomerEmail;
    private Integer toCustomerId;

}
