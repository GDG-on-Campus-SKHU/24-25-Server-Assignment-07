package bomin.googlelogin.dto.request;
import bomin.googlelogin.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationRequestDto {
    private String place;
    private Long number;
    private String date;

    public Reservation toEntity(){
        return Reservation.builder()
                .place(place)
                .number(number)
                .date(date)
                .build();
    }
}
