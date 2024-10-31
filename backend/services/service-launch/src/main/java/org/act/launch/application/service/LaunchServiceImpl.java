package org.act.launch.application.service;

import org.act.launch.application.dto.LaunchDTO;
import org.act.launch.application.interfaces.LaunchServiceInterface;
import org.act.launch.domain.entities.Launch;
import org.act.launch.infrastructure.interfaces.LaunchRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LaunchServiceImpl implements LaunchServiceInterface {

    private final LaunchRepositoryInterface launchRepository;

    @Autowired
    public LaunchServiceImpl(LaunchRepositoryInterface launchRepository) {
        this.launchRepository = launchRepository;
    }

    @Override
    public Launch createLaunch(LaunchDTO launchDTO) {
        Launch launch = new Launch();
        launch.setName(launchDTO.getName());
        launch.setType(launchDTO.getType());
        launch.setDescription(launchDTO.getDescription());
        launch.setCategoryId(launchDTO.getCategoryId());
        launch.setAccountId(launchDTO.getAccountId());
        launch.setValue(launchDTO.getValue());
        launch.setDate(launchDTO.getDate());
        launch.setStatus("SCHEDULED"); // Define status como agendado inicialmente
        return launchRepository.save(launch);
    }

    @Override
    public List<Launch> getAllLaunches() {
        return launchRepository.findAll();
    }

    @Override
    public Launch getLaunchById(Long id) {
        return launchRepository.findById(id).orElse(null);
    }

    @Override
    public Launch updateLaunch(Long id, LaunchDTO launchDTO) {
        Launch launch = getLaunchById(id);
        if (launch != null) {
            launch.setName(launchDTO.getName());
            launch.setType(launchDTO.getType());
            launch.setDescription(launchDTO.getDescription());
            launch.setCategoryId(launchDTO.getCategoryId());
            launch.setAccountId(launchDTO.getAccountId());
            launch.setValue(launchDTO.getValue());
            launch.setDate(launchDTO.getDate());
            return launchRepository.save(launch);
        }
        return null;
    }

    @Override
    public void deleteLaunch(Long id) {
        launchRepository.deleteById(id);
    }

    @Override
    public List<Launch> getLaunchesByAccountAndDateRange(Long accountId, LocalDate startDate, LocalDate endDate) {
        return launchRepository.findByAccountIdAndDateBetween(accountId, startDate, endDate);
    }
}

