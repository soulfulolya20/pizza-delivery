package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.aspect.AuthRole;
import org.example.models.dto.UserAdminPageDTO;
import org.example.models.entity.CourierRequest;
import org.example.models.entity.DispatcherRequest;
import org.example.models.enums.ScopeType;
import org.example.service.api.CourierService;
import org.example.service.api.DispatcherService;
import org.example.service.api.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final CourierService courierService;

    private final DispatcherService dispatcherService;

    @AuthRole(roles = ScopeType.ADMIN)
    @GetMapping("/search")
    public UserAdminPageDTO getUserAdminForm(String phone) {
        return userService.getUserAdminForm(phone.replaceAll("[+ -/.(){}\\[\\]]", ""));
    }

    @AuthRole(roles = ScopeType.ADMIN)
    @PostMapping("/{userId}/courier")
    public void makeCourier(@PathVariable("userId") Long userId) {
        courierService.createCourier(new CourierRequest(userId));
    }

    @AuthRole(roles = ScopeType.ADMIN)
    @PostMapping("/{userId}/dispatcher")
    public void makeDispatcher(@PathVariable("userId") Long userId) {
        dispatcherService.createDispatcher(new DispatcherRequest(userId));
    }

    @AuthRole(roles = ScopeType.ADMIN)
    @DeleteMapping("/{userId}/courier")
    public void fireCourier(@PathVariable("userId") Long userId) {
        courierService.deleteCourierByUserId(userId);
    }

    @AuthRole(roles = ScopeType.ADMIN)
    @DeleteMapping("/{userId}/dispatcher")
    public void fireDispatcher(@PathVariable("userId") Long userId) {
        dispatcherService.deleteDispatcherByUserId(userId);
    }
}
