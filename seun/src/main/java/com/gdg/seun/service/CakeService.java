package com.gdg.seun.service;

import com.gdg.seun.domain.Cake;
import com.gdg.seun.dto.CakeDto;
import com.gdg.seun.repository.CakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CakeService {

    private final CakeRepository cakeRepository;

    @Transactional
    public void addCake(CakeDto cakeDto) {
        Cake cake = cakeDto.toEntity();
        cakeRepository.save(cake);
    }

    @Transactional(readOnly = true)
    public Cake getCakeById(long id) {
        return cakeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("베이글을 찾을 수 없습니다 : " + id));
    }

    @Transactional(readOnly = true)
    public List<Cake> getAllCakes() {
        return cakeRepository.findAll();
    }

    @Transactional
    public void updateCake(Long id, String name, Long price) {
        if (id == null) {
            throw new IllegalArgumentException("ID는 null일 수 없습니다.");
        }

        Cake cake = cakeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ID " + id + "의 케이크를 찾을 수 없습니다."));

        if (name != null) cake.setName(name);
        if (price != null) cake.setPrice(price);

        cakeRepository.save(cake);
    }

    @Transactional
    public void removeCakeById(Long id) {
        Cake cake = cakeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID " + id + "의 디저트가 존재하지 않습니다."));

        cakeRepository.delete(cake);
    }

    @Transactional
    public void removeAllCakes() {
        cakeRepository.deleteAll();
    }


}
