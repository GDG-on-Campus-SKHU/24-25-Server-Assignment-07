package com.example.mydatecourse.repository;

import com.example.mydatecourse.domain.DateCourse;
import com.example.mydatecourse.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DateCourseRepository extends JpaRepository<DateCourse, Long> {
    Optional<DateCourse> findByUser(User user);
}
