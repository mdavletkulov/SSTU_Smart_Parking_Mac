package com.example.smartParking.model.domain;

import com.example.smartParking.validator.ValidPassword;
import org.hibernate.annotations.Nationalized;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usr")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Некорректный e-mail")
    @NotBlank(message = "Поле email не может быть пустым")
    @Nationalized
    private String username;
    @NotBlank(message = "Поле имени не может быть пустым")
    @Length(max = 60, message = "Значение имени слишком длинное")
    @Nationalized
    private String firstName;
    @NotBlank(message = "Поле фамилии не может быть пустым")
    @Length(max = 60, message = "Значение фамилии слишком длинное")
    @Nationalized
    private String secondName;
    @Length(max = 60, message = "Значение отчества слишком длинное")
    @Nationalized
    private String middleName;
    @NotBlank(message = "Пароль не может быть пустым")
    @ValidPassword
    private String password;
    @Transient
    private String password2;
    private boolean enabled;
    private String activationCode;

    public User() {
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    public String getFullName() {
        return secondName + " " + firstName + " " + middleName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }

    public boolean isManager() {
        return role.equals(Role.MANAGER);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(getRole());
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role roles) {
        this.role = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activation) {
        this.activationCode = activation;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
