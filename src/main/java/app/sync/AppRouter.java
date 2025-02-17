package app.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class AppRouter {

    @GetMapping(value = {"/"})
    public String getPage(
        // ...
    ) {
        return "/index";
    }

    @GetMapping(value = {"/cart"})
    public String getCartPage(
        // ...
    ) {
        return "/cart";
    }

    @GetMapping(value = {"/order/{orderId}"})
    public String getOrderPage(
        @PathVariable(required = true) String orderId
    ) {
        return "/order";
    }

    @GetMapping(value = {"/orders"})
    public String getOrderListPage(
        // ...
    ) {
        return "/orderList";
    }
}