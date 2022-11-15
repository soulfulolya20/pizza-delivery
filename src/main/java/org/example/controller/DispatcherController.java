package org.example.controller;

import org.example.models.entity.DispatcherEntity;
import org.example.models.entity.DispatcherRequest;
import org.example.service.api.DispatcherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/dispatcher")
public class DispatcherController {
    private final DispatcherService dispatcherService;

    public DispatcherController(DispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;
    }

    @GetMapping(value = "/{dispatcherId}")
    public Optional<DispatcherEntity> getDispatcher(@PathVariable("dispatcherId") Long dispatcherId) {
        return dispatcherService.getDispatcher(dispatcherId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createDispatcher(@Valid @RequestBody DispatcherRequest request) {
        dispatcherService.createDispatcher(request);
    }

    @PutMapping(value = "/{dispatcherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDispatcher(@Valid @RequestBody DispatcherRequest request, @PathVariable("dispatcherId") Long dispatcherId) {
        dispatcherService.updateDispatcher(request, dispatcherId);
    }

    @DeleteMapping(value = "/{dispatcherId}")
    public void deleteDispatcher(@PathVariable Long dispatcherId) {
        dispatcherService.deleteDispatcher(dispatcherId);
    }
}
