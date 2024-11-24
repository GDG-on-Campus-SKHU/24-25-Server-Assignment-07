package bomin.googlelogin.controller;
import bomin.googlelogin.dto.request.ReservationRequestDto;
import bomin.googlelogin.dto.response.ReservationResponseDto;
import bomin.googlelogin.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/save")
    public ResponseEntity<ReservationResponseDto> save(@RequestBody ReservationRequestDto reservationRequestDto){
        return new ResponseEntity<>(reservationService.save(reservationRequestDto), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ReservationResponseDto> findByReservation(@PathVariable(name="id") Long id){
        return new ResponseEntity<>(reservationService.findByReservation(id), HttpStatus.OK);
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<ReservationResponseDto> updateByReservation(@PathVariable Long id,
                                                                      @RequestBody ReservationRequestDto reservationRequestDto){
        return new ResponseEntity<>(reservationService.updateByReservation(id, reservationRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteByReservation(@PathVariable Long id){
        reservationService.deleteByReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
