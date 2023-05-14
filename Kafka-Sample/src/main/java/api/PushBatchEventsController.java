package api;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/events")
public class PushBatchEventsController {

    @PostMapping("/new-load")
    public ResponseEntity<Void> pushNewEvents(@RequestParam(required = false) int count) {
//        Add business logic
        return ResponseEntity.noContent().build();
    }

}
