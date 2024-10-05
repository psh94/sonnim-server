package com.psh94.sonnim_server.service;

import com.psh94.sonnim_server.common.exception.GuesthouseNotFoundException;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseDTO;
import com.psh94.sonnim_server.domain.guesthouse.dto.GuesthouseEnrollRequest;
import com.psh94.sonnim_server.domain.guesthouse.entity.Guesthouse;
import com.psh94.sonnim_server.domain.guesthouse.repository.GuesthouseRepository;
import com.psh94.sonnim_server.domain.guesthouse.service.AddressService;
import com.psh94.sonnim_server.domain.guesthouse.service.GuesthouseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GuesthouseServiceImplTest {

    @Mock
    private GuesthouseRepository guesthouseRepository;

    @Mock
    private AddressService addressService;

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
                .regionCode("0101")
                .detailAddress("xxx")
                .phone("010-1234-1234")
                .build();

        guesthouse = Guesthouse.builder()
                .guesthouseName("SEOUL")
                .ownerName("kim")
                .regionCode("0101")
                .detailAddress("xxx")
                .phone("010-1234-1234")
                .build();

        Field idField = guesthouse.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(guesthouse, 1L);

        guesthouseDTO = GuesthouseDTO.builder()
                .id(1L)
                .guesthouseName("SEOUL")
                .ownerName("kim")
                .regionCode("0101")
                .detailAddress("xxx")
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
    void 게스트하우스_검색_지역코드로_성공() {
        List<Guesthouse> guesthouseList = List.of(guesthouse);

        // 검색어로 지역 코드가 존재할 때의 시나리오
        when(addressService.getRegionCodeFromAddress("서울")).thenReturn("0101");
        when(guesthouseRepository.findGuesthousesByRegionCode("0101")).thenReturn(guesthouseList);

        // searchWord로 검색
        List<GuesthouseDTO> result = guesthouseService.getGuesthousesByWord("서울");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(guesthouseDTO.getRegionCode(), result.get(0).getRegionCode());
        verify(addressService, times(1)).getRegionCodeFromAddress("서울");
        verify(guesthouseRepository, times(1)).findGuesthousesByRegionCode("0101");
    }

    @Test
    void 게스트하우스_검색_이름으로_성공() {
        List<Guesthouse> guesthouseList = List.of(guesthouse);

        // 검색어로 지역 코드가 없을 때의 시나리오
        when(addressService.getRegionCodeFromAddress("SEOUL")).thenReturn(null);
        when(guesthouseRepository.findGuesthousesByWord("SEOUL")).thenReturn(guesthouseList);

        // searchWord로 검색
        List<GuesthouseDTO> result = guesthouseService.getGuesthousesByWord("SEOUL");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(guesthouseDTO.getGuesthouseName(), result.get(0).getGuesthouseName());
        verify(addressService, times(1)).getRegionCodeFromAddress("SEOUL");
        verify(guesthouseRepository, times(1)).findGuesthousesByWord("SEOUL");
    }

    @Test
    void 게스트하우스_검색_결과없음() {
        // 검색어로 지역 코드가 없고, 게스트하우스 검색 결과도 없을 때의 시나리오
        when(addressService.getRegionCodeFromAddress("SEOUL")).thenReturn(null);
        when(guesthouseRepository.findGuesthousesByWord("SEOUL")).thenReturn(List.of());

        // searchWords로 검색
        List<GuesthouseDTO> result = guesthouseService.getGuesthousesByWord("SEOUL");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(addressService, times(1)).getRegionCodeFromAddress("SEOUL");
        verify(guesthouseRepository, times(1)).findGuesthousesByWord("SEOUL");
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