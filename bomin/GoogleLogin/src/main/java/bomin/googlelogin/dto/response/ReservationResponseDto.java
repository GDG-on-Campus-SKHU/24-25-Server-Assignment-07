package bomin.googlelogin.dto.response;
import bomin.googlelogin.domain.Reservation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponseDto {
    private String place;
    private Long number;
    private String date;

    public static ReservationResponseDto from(Reservation reservation){
        return ReservationResponseDto.builder()
                .place(reservation.getPlace())
                .number(reservation.getNumber())
                .date(reservation.getDate())
                .build();
    }
}
