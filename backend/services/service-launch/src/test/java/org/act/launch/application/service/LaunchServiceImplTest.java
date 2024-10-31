package org.act.launch.application.service;

import org.act.launch.application.dto.LaunchDTO;
import org.act.launch.domain.entities.Launch;
import org.act.launch.infrastructure.repository.LaunchRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LaunchServiceImplTest {

    @InjectMocks
    private LaunchServiceImpl launchService;

    @Mock
    private LaunchRepositoryImpl launchRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createLaunch_ShouldReturnLaunchDTO() {
        LaunchDTO launchCreateDTO = new LaunchDTO();
        LaunchDTO launchDTO = new LaunchDTO();

        when(launchRepository.save(any())).thenReturn(launchDTO);

        Launch createdLaunch = launchService.createLaunch(launchDTO);

        assertNotNull(createdLaunch);
        assertEquals(BigDecimal.valueOf(100), createdLaunch.getValue());
        verify(launchRepository, times(1)).save(any());
    }

    @Test
    void updateLaunch_ShouldReturnUpdatedLaunchDTO() {
        Long launchId = 1L;
        LaunchDTO launchUpdateDTO = new LaunchDTO();
        LaunchDTO updatedLaunchDTO = new LaunchDTO();

        when(launchRepository.save(any())).thenReturn(updatedLaunchDTO);

        Launch result = launchService.updateLaunch(launchId, launchUpdateDTO);

        assertNotNull(result);
        assertEquals(launchId, result.getId());
        assertEquals(BigDecimal.valueOf(150), result.getValue());
        verify(launchRepository, times(1)).save(any());
    }

    @Test
    void deleteLaunch_ShouldCallDeleteOnRepository() {
        Long launchId = 1L;
        Launch launch = new Launch();
        launchService.deleteLaunch(launchId);

        verify(launchRepository, times(1)).delete(launch);
    }

    @Test
    void listLaunches_ShouldReturnListOfLaunchDTOs() {
        LaunchDTO launch1 = new LaunchDTO();
        LaunchDTO launch2 = new LaunchDTO();
        List<LaunchDTO> launches = Arrays.asList(launch1, launch2);


        List<Launch> result = launchService.getAllLaunches();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(launchRepository, times(1)).findAll();
    }

    @Test
    void getLaunchById_ShouldReturnLaunchDTO() {
        Long launchId = 1L;
        LaunchDTO launchDTO = new LaunchDTO();


        Launch result = launchService.getLaunchById(launchId);

        assertNotNull(result);
        assertEquals(launchId, result.getId());
        verify(launchRepository, times(1)).findById(launchId);
    }

}

