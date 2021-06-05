package com.messaging.app.repository.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Data
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

}
