package org.act.launch.infrastructure.controller;

import org.act.launch.application.dto.LaunchDTO;
import org.act.launch.application.interfaces.LaunchServiceInterface;
import org.act.launch.domain.entities.Launch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/launches")
public class LaunchController {

    private final LaunchServiceInterface launchService;

    @Autowired
    public LaunchController(LaunchServiceInterface launchService) {
        this.launchService = launchService;
    }

    @PostMapping
    public ResponseEntity<Launch> createLaunch(@RequestBody LaunchDTO launchDTO) {
        Launch createdLaunch = launchService.createLaunch(launchDTO);
        return ResponseEntity.ok(createdLaunch);
    }

    @GetMapping
    public ResponseEntity<List<Launch>> getAllLaunches() {
        List<Launch> launches = launchService.getAllLaunches();
        return ResponseEntity.ok(launches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Launch> getLaunchById(@PathVariable Long id) {
        Launch launch = launchService.getLaunchById(id);
        return ResponseEntity.ok(launch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Launch> updateLaunch(@PathVariable Long id, @RequestBody LaunchDTO launchDTO) {
        Launch updatedLaunch = launchService.updateLaunch(id, launchDTO);
        return updatedLaunch != null ? ResponseEntity.ok(updatedLaunch) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaunch(@PathVariable Long id) {
        launchService.deleteLaunch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/launches/filter")
    public ResponseEntity<List<Launch>> getLaunchesByAccountAndDate(
            @RequestParam Long accountId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<Launch> launches = launchService.getLaunchesByAccountAndDateRange(accountId, startDate, endDate);
        return ResponseEntity.ok(launches);
    }
}


