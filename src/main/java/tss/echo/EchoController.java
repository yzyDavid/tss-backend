package tss.echo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yzy
 */
@RestController
public class EchoController {
    @GetMapping("/echo")
    public String echo() {
        return "Team A TSS is running.";
    }
}
