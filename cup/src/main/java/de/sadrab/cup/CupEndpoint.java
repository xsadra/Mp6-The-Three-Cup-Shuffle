package de.sadrab.cup;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coin")
public class CupEndpoint {
    private Boolean coin;

    public CupEndpoint() {
        this.coin = false;
    }

    @GetMapping
    Boolean getCoin() {
        return coin;
    }

    @PutMapping
    void putCoin() {
        coin = true;
    }

    @DeleteMapping
    void removeCoin() {
        coin = false;
    }
}
