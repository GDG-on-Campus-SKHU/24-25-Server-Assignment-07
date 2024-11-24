package com.example.mydatecourse.controller;

import com.example.mydatecourse.dto.DateCourseListResponseDto;
import com.example.mydatecourse.dto.DateCourseRequestDto;
import com.example.mydatecourse.dto.DateCourseResponseDto;
import com.example.mydatecourse.service.MyDateCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dateCourse")
public class DateCourseController {
    private final MyDateCourseService myDateCourseService;

    @PostMapping("/save")
    public ResponseEntity<DateCourseResponseDto> saveDateCourse(@RequestBody DateCourseRequestDto dateCourseRequestDto) {
        return new ResponseEntity<>(myDateCourseService.save(dateCourseRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DateCourseResponseDto> findDateCourse(@PathVariable Long id) {
        return new ResponseEntity<>(myDateCourseService.findDateCourse(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DateCourseResponseDto> updateDateCourse(@PathVariable Long id, @RequestBody DateCourseRequestDto dateCourseRequestDto) {
        return new ResponseEntity<>(myDateCourseService.updateDateCourse(id, dateCourseRequestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DateCourseResponseDto> deleteDateCourse(@PathVariable Long id) {
        myDateCourseService.deleteDateCourse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<DateCourseListResponseDto> dateCourseList() {
        return new ResponseEntity<>(myDateCourseService.findAllDateCourse(), HttpStatus.OK);
    }
}
