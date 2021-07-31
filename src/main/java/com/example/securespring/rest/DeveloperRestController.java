package com.example.securespring.rest;

import com.example.securespring.model.Developer;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestController {

    private List<Developer> DEVELOPERS = List.of(
        new Developer(1L, "Johny", "Green"),
        new Developer(2L, "Bart", "Dortmund"),
        new Developer(3L, "Giovanni", "Florentiona")
    );

    @GetMapping
    public List<Developer> getAll() {  
        return DEVELOPERS;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<Developer> getById(@PathVariable Long id) {  
        return DEVELOPERS
                .stream()
                .filter(d -> d.getId().equals(id))
                .collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developers:write')")
    public void addDeveloper(@RequestBody Developer dev) {
        this.DEVELOPERS = Stream.concat(DEVELOPERS.stream(), Stream.of(dev)).collect(Collectors.toList());
    }
    
    @DeleteMapping(value="{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public void delete(@PathVariable("id") Long id) {
        this.DEVELOPERS = DEVELOPERS.stream().filter(dev -> !dev.getId().equals(id)).collect(Collectors.toList());
    }

}
