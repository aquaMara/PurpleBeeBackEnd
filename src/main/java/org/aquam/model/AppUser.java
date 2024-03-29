package org.aquam.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private String username;
    private String password;
    private String instagram;
    private String email;
    private LocalDateTime registrationDate;

    private Boolean locked;     // locked because user deleted it
    private Boolean enabled;    // enabled when approved by email

    @OneToOne
    private AppUserSettings appUserSettings;
    @OneToOne
    private Account account;
    @OneToMany(mappedBy = "creator")
    private List<Pattern> createdPatterns;
    @OneToMany(mappedBy = "appUser")
    private List<Rate> rates;
    @OneToMany(mappedBy = "appUser")
    private List<Comment> comments;
    @OneToMany(mappedBy = "appUser")    // patterns I bought from payment
    private List<Payment> payments;

    public AppUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(id, appUser.id) && appUserRole == appUser.appUserRole && Objects.equals(username, appUser.username) && Objects.equals(password, appUser.password) && Objects.equals(instagram, appUser.instagram) && Objects.equals(email, appUser.email) && Objects.equals(locked, appUser.locked) && Objects.equals(enabled, appUser.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appUserRole, username, password, instagram, email, locked, enabled);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", appUserRole=" + appUserRole +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + instagram + '\'' +
                ", email='" + email + '\'' +
                ", locked=" + locked +
                ", enabled=" + enabled +
                '}';
    }
}
