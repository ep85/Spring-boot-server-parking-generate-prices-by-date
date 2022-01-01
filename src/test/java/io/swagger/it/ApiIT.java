package io.swagger.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.ItemPrice;
import io.swagger.model.RateItem;
import io.swagger.repository.RateRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiIT {
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RateRepository rateRepository;

    @LocalServerPort
    int randomServerPort;

    private void BeforeEachSetup(){
        rateRepository.deleteAll();
    }

    @Test
    public void testGetAllRates_success() throws URISyntaxException {
        BeforeEachSetup();

        // Mock
        RateItem rateItem = new RateItem();
        rateItem.setDays("mon,tues,thurs");
        rateItem.setTimes("0900-2100");
        rateItem.setTz("America/Chicago");
        rateItem.setPrice(1500L);
        rateRepository.save(rateItem);

        List<RateItem> rateItemList = new ArrayList<>();
        rateItemList.add(rateItem);

        // ACT
        final String baseUrl = "http://localhost:" + randomServerPort + "/ep85/spot-hero/1.0.0/ep85/spot-hero/1.0.0/rates";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        // ASSERT
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("[{\"days\":\"mon,tues,thurs\",\"times\":\"0900-2100\",\"tz\":\"America/Chicago\",\"price\":1500}]", result.getBody());
    }

    @Test
    public void testGetAllRates_Empty() throws URISyntaxException, IOException {
        BeforeEachSetup();

        // ACT
        final String baseUrl = "http://localhost:" + randomServerPort + "/ep85/spot-hero/1.0.0/ep85/spot-hero/1.0.0/rates";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        // ASSERT
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("[]", result.getBody());
    }

    @Test
    public void testPutRates_Success() throws URISyntaxException {
        BeforeEachSetup();

        // Mock
        RateItem rateItem = new RateItem();
        rateItem.setDays("mon,tues,thurs");
        rateItem.setTimes("0900-2100");
        rateItem.setTz("America/Chicago");
        rateItem.setPrice(1500L);
        List<RateItem> rateItems = new ArrayList<>();
        rateItems.add(rateItem);

        // ACT
        // Put Items
        final String baseUrl = "http://localhost:" + randomServerPort + "/ep85/spot-hero/1.0.0/ep85/spot-hero/1.0.0/rates";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        HttpEntity<List<RateItem>> requestEntity = new HttpEntity<List<RateItem>>(rateItems, headers);
        ResponseEntity<Void> result = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, Void.class);

        // GET ITEMS
        final String baseUrl2 = "http://localhost:" + randomServerPort + "/ep85/spot-hero/1.0.0/ep85/spot-hero/1.0.0/rates";
        URI uri2 = new URI(baseUrl2);

        ResponseEntity<String> result2 = restTemplate.getForEntity(uri2, String.class);

        // ASSERT
        // Put Items Check
        Assert.assertEquals(200, result.getStatusCodeValue());

        // Get Items Check for the Put
        Assert.assertEquals(200, result2.getStatusCodeValue());
        Assert.assertEquals("[{\"days\":\"mon,tues,thurs\",\"times\":\"0900-2100\",\"tz\":\"America/Chicago\",\"price\":1500}]", result2.getBody());
    }

    @Test
    public void testGetPrice_Success() throws URISyntaxException {
        BeforeEachSetup();

        // Mock
        RateItem rateItem = new RateItem();
        rateItem.setDays("mon,tues,thurs");
        rateItem.setTimes("0900-2100");
        rateItem.setTz("America/Chicago");
        rateItem.setPrice(1500L);
        rateRepository.save(rateItem);

        RateItem rateItem2 = new RateItem();
        rateItem2.setDays("wed");
        rateItem2.setTimes("0600-1800");
        rateItem2.setTz("America/Chicago");
        rateItem2.setPrice(1750L);
        rateRepository.save(rateItem2);


        List<RateItem> rateItemList = new ArrayList<>();
        rateItemList.add(rateItem);

        // ACT
        final String baseUrl = "http://localhost:" + randomServerPort +"/ep85/spot-hero/1.0.0/ep85/spot-hero/1.0.0/price?start=2015-07-01T07:00:00-05:00&end=2015-07-01T12:00:00-05:00";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        // ASSERT
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals("{\"price\":1750}", result.getBody());
    }
}
