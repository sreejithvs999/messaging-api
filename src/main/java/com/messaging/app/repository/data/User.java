package com.messaging.app.repository.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity()
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(name = "idx_nick_name_unique", columnNames = "nick_name"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer userId;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

}
