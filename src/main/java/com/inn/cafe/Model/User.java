package com.inn.cafe.Model;


import com.inn.cafe.Service.UserService;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.Serializable;

@Data
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "users")
public class User implements Serializable {

/* @Autowired
 private transient UserService service;*/


 private static final long serialVersionUID = 1L;

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "id")
 private Integer id;

 @Column(name = "name")
 private String name;

 @Column(name = "contactNumber")
 private String contactNumber;

 @Column(name = "email")
 private String email;

 @Column(name = "password")
 private String password;

 @Column(name = "status")
 private String status;

 @Column(name = "role")
 private String role;

 private String ab;
}
