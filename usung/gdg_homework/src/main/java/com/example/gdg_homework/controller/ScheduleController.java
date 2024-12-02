package com.example.gdg_homework.controller;

import com.example.gdg_homework.dto.ScheduleDto;
import com.example.gdg_homework.service.ScheduleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/gdg/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 스케줄 등록
    @PostMapping
    public ResponseEntity<ScheduleDto.Response> createSchedule(
            @RequestBody ScheduleDto.Request request,
            Principal principal
    ) {
        return ResponseEntity.ok(
                scheduleService.scheduleEnroll(request, principal.getName())
        );
    }

    // 스케줄 목록 조회
    @GetMapping
    public ResponseEntity<List<ScheduleDto.Response>> getSchedules(Principal principal) {
        return ResponseEntity.ok(scheduleService.schduleRead(principal.getName()));
    }

    // 스케줄 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto.Response> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleDto.Request request,
            Principal principal
    ) {
        return ResponseEntity.ok(scheduleService.scheduleUpdate(id, request, principal.getName()));
    }

    // 스케줄 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleDto.Response> deleteSchedule(
            @PathVariable Long id,
            Principal principal
    ) {
        scheduleService.scheduleDelete(id, principal.getName());  // getName()으로 이메일 전달
        return ResponseEntity.noContent().build();
    }
}
