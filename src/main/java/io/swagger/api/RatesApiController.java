package io.swagger.api;

import io.swagger.model.RateItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-12-27T23:23:02.463Z")

@Controller
public class RatesApiController implements RatesApi {

    private static final Logger log = LoggerFactory.getLogger(RatesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final RateService rateService;

    @org.springframework.beans.factory.annotation.Autowired
    public RatesApiController(ObjectMapper objectMapper, HttpServletRequest request, RateService rateService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.rateService = rateService;
    }

    public ResponseEntity<List<RateItem>> getRates() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<RateItem> rateItems = rateService.getRates();
                String json = mapper.writeValueAsString(rateItems);
                return new ResponseEntity<List<RateItem>>(objectMapper.readValue(json, List.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<RateItem>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<RateItem>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> submitRates(@ApiParam(value = "Rate items to replace the current stored rates"  )  @Valid @RequestBody List<RateItem> rateItems) {
        String accept = request.getHeader("Accept");
        try {
            rateService.insertAll(rateItems);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error Inserting Records ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
