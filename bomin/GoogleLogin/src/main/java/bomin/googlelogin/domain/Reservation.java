package bomin.googlelogin.domain;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation { //예약
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String place; //예약한 장소

    @Column(nullable = false)
    private Long number; //예약한곳 번호

    @Column(nullable = false)
    private String date; //예약한 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Reservation(String place, Long number, String date, User user) {
        this.place = place;
        this.number = number;
        this.date = date;
        this.user =user;
    }

    public void update(String place, Long number, String date) {
        this.place = place;
        this.number = number;
        this.date = date;
    }
}
