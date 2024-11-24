package bomin.googlelogin.service;
import bomin.googlelogin.domain.Reservation;
import bomin.googlelogin.dto.request.ReservationRequestDto;
import bomin.googlelogin.dto.response.ReservationResponseDto;
import bomin.googlelogin.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private  final ReservationRepository reservationRepository;

    //예약한곳 생성
    @Transactional
    public ReservationResponseDto save(ReservationRequestDto reservationRequestDto){
        Reservation reservation = reservationRequestDto.toEntity();
        reservationRepository.save(reservation);
        return ReservationResponseDto.from(reservation);
    }

    //예약한 곳 조회
    @Transactional(readOnly = true)
    public ReservationResponseDto findByReservation(Long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        return ReservationResponseDto.from(reservation);
    }

    //예약한 곳 수정
    @Transactional
    public ReservationResponseDto updateByReservation(Long id, ReservationRequestDto reservationRequestDto){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));

        reservation.update(reservationRequestDto.getPlace(), reservationRequestDto.getNumber(), reservation.getDate());
        return ReservationResponseDto.from(reservation);
    }

    //예약 삭제
    @Transactional
    public void deleteByReservation(Long id){
        reservationRepository.deleteById(id);
    }
}