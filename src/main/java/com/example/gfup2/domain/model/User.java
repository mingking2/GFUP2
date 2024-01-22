package com.example.gfup2.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique = true, length=254)
    private String emailId;

    @Column(nullable=false, length=30)
    private String password;

    @Column(nullable=false, unique=true, length=20)
    private String phoneNumber;

    @Column(nullable=false, unique = true, length=254)
    private String alarmEmailInfo;

    @Column(nullable=false, unique=true, length=20)
    private String alarmPhoneNumberInfo;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false, length=10)
    private UserRoleEnum role;

    @OneToMany(mappedBy="user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Alarm> alarms = new ArrayList<>();

    public User(String emailId, String password, String phoneNumber, String alarmEmailInfo, String alarmPhoneNumberInfo, UserRoleEnum role){
        this.id = null;
        this.emailId = emailId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.alarmEmailInfo = alarmEmailInfo;
        this.alarmPhoneNumberInfo = alarmPhoneNumberInfo;
        this.role = role;
    }

    public User(String emailId, String password, String phoneNumber, String alarmEmailInfo, String alarmPhoneNumberInfo){
        this(emailId, password, phoneNumber, alarmEmailInfo, alarmPhoneNumberInfo, UserRoleEnum.User);
    }
    public enum UserRoleEnum{
        User, ADMIN
    }
}

