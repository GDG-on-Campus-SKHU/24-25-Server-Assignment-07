package com.example.gdg_homework.service;

import com.example.gdg_homework.domain.Schedule;
import com.example.gdg_homework.domain.User;
import com.example.gdg_homework.dto.ScheduleDto;
import com.example.gdg_homework.repository.ScheduleRepository;
import com.example.gdg_homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ScheduleService {
    final ScheduleRepository scheduleRepository;
    final UserRepository userRepository;

    // 스케줄 등록
    public ScheduleDto.Response scheduleEnroll(ScheduleDto.Request request, String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Schedule schedule = request.toEntity(user);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleDto.Response.from(savedSchedule);
    }

    // 스케줄 확인
    public List<ScheduleDto.Response> schduleRead(String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Schedule> schedules = scheduleRepository.findAllByUserId(user.getId());

        return schedules.stream()
                .map(ScheduleDto.Response::from)
                .collect(Collectors.toList());
    }

    // 스케줄 업데이트
    public ScheduleDto.Response scheduleUpdate(Long scheduleId, ScheduleDto.Request request, String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스케줄입니다."));

        if (!schedule.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        schedule.update(request);

        return ScheduleDto.Response.from(schedule);
    }

    // 스케줄 삭제
    public void scheduleDelete(Long scheduleId, String userId) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스케줄입니다."));

        if (!schedule.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        scheduleRepository.deleteById(scheduleId);
    }
}
