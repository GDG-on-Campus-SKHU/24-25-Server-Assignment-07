package com.example.assignment07.service;

import com.example.assignment07.domain.book.Book;
import com.example.assignment07.dto.BookDto.BookRequestDto;
import com.example.assignment07.dto.BookDto.BookResponseDto;
import com.example.assignment07.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BookService {
    private final BookRepository bookRepository;

    // 책 정보 생성
    @Transactional
    public BookResponseDto saveBook(BookRequestDto bookRequestDto) {
        // BookRequestDto를 Book 엔티티로 변환 후 저장
        Book book = bookRepository.save(Book.builder()
                .title(bookRequestDto.getTitle())
                .author(bookRequestDto.getAuthor())
                .price(bookRequestDto.getPrice())
                .stock(bookRequestDto.getStock())
                .build());

        // 저장된 책 정보를 BookResponseDto로 반환
        return BookResponseDto.from(book);
    }

    // 책 정보 호출
    @Transactional(readOnly = true)
    public BookResponseDto findBookById(Long id) {
        // ID로 책 조회, 없으면 예외 발생
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 책이 존재하지 않습니다."));

        return BookResponseDto.from(book);
    }

    // 책 정보 수정
    @Transactional
    public BookResponseDto updateBook(Long id, BookRequestDto bookRequestDto) {
        // 기존 책 엔티티 조회
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 책이 존재하지 않습니다."));

        // 책 정보 업데이트
        book.updateBook(
                bookRequestDto.getTitle(),
                bookRequestDto.getAuthor(),
                bookRequestDto.getPrice(),
                bookRequestDto.getStock()
        );

        return BookResponseDto.from(book);
    }

    // 책 정보 삭제
    @Transactional
    public void deleteBook(Long id) {
        // 책 엔티티 조회
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 책이 존재하지 않습니다."));

        // 책 삭제
        bookRepository.delete(book);
    }

    // 책 목록을 조회하는 기능
    @Transactional(readOnly = true)
    public List<BookResponseDto> findAll() {
        List<Book> books = bookRepository.findAll();

        // Dto를 저장할 새로운 리스트를 생성한다.
        List<BookResponseDto> bookResponseDtos = new ArrayList<>();

        // entity를 dto로 저장
        for (Book book : books) {
            bookResponseDtos.add(BookResponseDto.from(book));
        }
        return bookResponseDtos;
    }

}
