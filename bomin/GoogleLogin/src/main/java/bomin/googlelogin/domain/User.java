package bomin.googlelogin.domain;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private  String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @Builder
    public User(String email, String password, String name, String profileUrl, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileUrl =profileUrl;
        this.role = role;
    }
}