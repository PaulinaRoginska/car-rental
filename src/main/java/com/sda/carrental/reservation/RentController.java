package com.sda.carrental.reservation;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rents")
public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping
    public RentModel save(@RequestBody @Valid RentDTO rent) {
        return rentService.save(rent);
    }
}
