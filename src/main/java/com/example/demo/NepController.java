package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/nep")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NepController {

    private final NepRepository nepRepository;
    private final VertexRepository vertexRepository;

    private final MapService mapService;

    public NepController(NepRepository nepRepository, VertexRepository vertexRepository, MapService mapService) {
        this.nepRepository = nepRepository;
        this.vertexRepository = vertexRepository;
        this.mapService = mapService;
    }

    @GetMapping("/{id}")
    public Nep2po4pgr getLocById(@PathVariable Integer id) {
        return  (Nep2po4pgr) nepRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @GetMapping("all")
    public List<Nep2po4pgr> getAll() {
        return nepRepository.findAll();
    }

    @GetMapping("vertex/all")
    public List<Nep2poVertex> getAllVertex() {
        return vertexRepository.findAll();
    }

    @GetMapping("test/{source}")
    public List<Nep2po4pgr> getTest(@PathVariable Integer source) {
        return nepRepository.findBySource(source);
    }

    @GetMapping("test2/{source}")
    public Graph getTest2(@PathVariable Integer source) {
        return mapService.getShortestPathsFromSource(source);
    }

    @GetMapping("test/{source}/{target}")
    public Node getPathTest(@PathVariable Integer source, @PathVariable Integer target) {
        return mapService.getShortestPathToDestinationNode(source, target);
    }

    @GetMapping("test3/{source}/{target}")
    public Nep2po4pgr getEdgeTest(@PathVariable Integer source, @PathVariable Integer target) {
        return nepRepository.findBySourceAndTarget(source, target).orElse(null);
    }

    @GetMapping("test4/{source}/{target}")
    public List<Nep2po4pgr> getPathTestLatLon(@PathVariable Integer source, @PathVariable Integer target) {
        return mapService.findPathLatLon(source, target);
    }
}
