package io.swagger.api;

import io.swagger.model.ItemPrice;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-12-27T23:23:02.463Z")

@Controller
public class PriceApiController implements PriceApi {
    private final PriceService priceService;

    private static final Logger log = LoggerFactory.getLogger(PriceApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PriceApiController(ObjectMapper objectMapper, HttpServletRequest request, PriceService priceService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.priceService = priceService;
    }

    public ResponseEntity getPrice(@ApiParam(value = "input date/times as ISO-8601 with timezones") @Valid @RequestParam(value = "start", required = false) String start,@ApiParam(value = "input date/times as ISO-8601 with timezones") @Valid @RequestParam(value = "end", required = false) String end) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Long price = priceService.getPrice(start, end);
                ItemPrice itemPrice = new ItemPrice();
                itemPrice.setPrice(price);
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(itemPrice);
                return ResponseEntity.ok(json);
            } catch (IOException e) {
                log.error(e.getMessage());
                return ResponseEntity.ok("unavaialbe");
            } catch (Exception e) {
                log.error(e.getMessage());
                return (ResponseEntity) ResponseEntity.badRequest();
            }
        }

        return new ResponseEntity<ItemPrice>(HttpStatus.NOT_IMPLEMENTED);
    }

}
