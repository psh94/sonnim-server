package com.psh94.sonnim_server.service;

import com.psh94.sonnim_server.common.exception.GuesthouseNotFoundException;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;
import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.guesthouse.repository.GuesthouseRepository;
import com.psh94.sonnim_server.domain.guesthouse.service.GuesthouseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GuesthouseServiceImplTest {

    @Mock
    private GuesthouseRepository guesthouseRepository;

    @InjectMocks
    private GuesthouseServiceImpl guesthouseService;

    private GuesthouseEnrollRequest guesthouseEnrollRequest;
    private Guesthouse guesthouse;
    private GuesthouseDTO guesthouseDTO;

    @BeforeEach
    void setUp() throws Exception {
        guesthouseEnrollRequest = GuesthouseEnrollRequest.builder()
                .guesthouseName("SEOUL")
                .ownerName("kim")
                .address("jeonju")
                .phone("010-1234-1234")
                .build();

        guesthouse = Guesthouse.builder()
                .guesthouseName("SEOUL")
                .ownerName("kim")
                .address("jeonju")
                .phone("010-1234-1234")
                .build();

        Field idField = guesthouse.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(guesthouse, 1L);

        guesthouseDTO = GuesthouseDTO.builder()
                .id(1L)
                .guesthouseName("SEOUL")
                .ownerName("kim")
                .address("jeonju")
                .phone("010-1234-1234")
                .build();
    }

    @Test
    void 게스트하우스_등록_성공() {
        when(guesthouseRepository.save(any(Guesthouse.class))).thenAnswer(invocation -> {
            Guesthouse savedGuesthouse = invocation.getArgument(0);
            Field idField = savedGuesthouse.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(savedGuesthouse, 1L);
            return savedGuesthouse;
        });

        GuesthouseDTO result = guesthouseService.enrollGuesthouse(guesthouseEnrollRequest);

        assertNotNull(result);
        assertEquals(guesthouseDTO.getId(), result.getId());
        verify(guesthouseRepository, times(1)).save(any(Guesthouse.class));
    }

    @Test
    void 게스트하우스_조회_성공() {
        when(guesthouseRepository.findById(1L)).thenReturn(Optional.of(guesthouse));

        GuesthouseDTO result = guesthouseService.getGuesthouse(1L);

        assertNotNull(result);
        assertEquals(guesthouseDTO.getId(), result.getId());
        verify(guesthouseRepository, times(1)).findById(1L);
    }

    @Test
    void 게스트하우스_조회_실패() {
        when(guesthouseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(GuesthouseNotFoundException.class, () -> {
            guesthouseService.getGuesthouse(1L);
        });

        verify(guesthouseRepository, times(1)).findById(1L);
    }

    @Test
    void 게스트하우스_삭제_성공() {
        when(guesthouseRepository.existsById(1L)).thenReturn(true);

        guesthouseService.deleteGuesthouse(1L);

        verify(guesthouseRepository, times(1)).existsById(1L);
        verify(guesthouseRepository, times(1)).deleteById(1L);
    }

    @Test
    void 게스트하우스_삭제_조회_실패() {
        when(guesthouseRepository.existsById(1L)).thenReturn(false);

        assertThrows(GuesthouseNotFoundException.class, () -> {
            guesthouseService.deleteGuesthouse(1L);
        });

        verify(guesthouseRepository, times(1)).existsById(1L);
        verify(guesthouseRepository, never()).deleteById(anyLong());
    }
}