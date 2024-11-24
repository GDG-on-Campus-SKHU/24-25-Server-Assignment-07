package com.example.assignment07.controller;

import com.example.assignment07.dto.BookDto.BookRequestDto;
import com.example.assignment07.dto.BookDto.BookResponseDto;
import com.example.assignment07.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    // 책 생성
    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(@RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto book = bookService.saveBook(bookRequestDto);
        return ResponseEntity.ok(book);
    }

    // 책 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id) {
        BookResponseDto book = bookService.findBookById(id);
        return ResponseEntity.ok(book);
    }

    // 책 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<BookResponseDto>> getBooks() {
        List<BookResponseDto> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    // 책 수정(관리자만 가능한 기능)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto book = bookService.updateBook(id, bookRequestDto);
        return ResponseEntity.ok(book);
    }

    // 책 삭제(관리자만 가능한 기능)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
