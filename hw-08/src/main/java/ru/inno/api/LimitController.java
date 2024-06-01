package ru.inno.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.inno.api.dto.ApiLimitPage;
import ru.inno.service.LimitService;

@RestController
@RequestMapping("limits")
@RequiredArgsConstructor
public class LimitController {

    private final LimitService service;

    @GetMapping
    public ApiLimitPage getByUserId(@RequestParam("userId")Long userId) {
        return new ApiLimitPage(userId, service.getByUserId(userId));
    }
}
