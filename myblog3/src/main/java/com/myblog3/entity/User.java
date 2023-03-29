package com.myblog3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users",uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}),@UniqueConstraint(columnNames = {"userName"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    private String email;
    private String name;
    private String password;
    private String userName;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name ="user_roles",joinColumns = @JoinColumn(name = "user_id" ,referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "roles_id",referencedColumnName = "id"))
    private Set<Role> roles;
}
