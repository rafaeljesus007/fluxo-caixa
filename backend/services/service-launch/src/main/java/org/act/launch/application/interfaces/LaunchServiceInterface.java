package org.act.launch.application.interfaces;

import org.act.launch.application.dto.LaunchDTO;
import org.act.launch.domain.entities.Launch;

import java.time.LocalDate;
import java.util.List;

public interface LaunchServiceInterface {
    Launch createLaunch(LaunchDTO launchDTO);
    List<Launch> getAllLaunches();
    Launch getLaunchById(Long id);
    Launch updateLaunch(Long id, LaunchDTO launchDTO);
    void deleteLaunch(Long id);
    List<Launch> getLaunchesByAccountAndDateRange(Long accountId, LocalDate startDate, LocalDate endDate);
}

