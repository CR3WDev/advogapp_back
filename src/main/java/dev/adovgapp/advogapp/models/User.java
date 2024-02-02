package dev.adovgapp.advogapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.adovgapp.advogapp.models.Lawyer;
import dev.adovgapp.advogapp.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String fullName;
    private String email;
    @JsonIgnore
    private String password;
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "lawyer_id", referencedColumnName = "id")
    private Lawyer lawyer;

    public User(String fullName,String email,String password,UserRole role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.LAWYER) return List.of(new SimpleGrantedAuthority("ROLE_LAWYER"),new SimpleGrantedAuthority("ROLE_USER") );
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));


    }

    @Override
    public String getUsername() {
        return email;
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
    public boolean isEnabled() {
        return true;
    }
}
