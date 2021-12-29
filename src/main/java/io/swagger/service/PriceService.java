package io.swagger.service;

import io.swagger.model.RateItem;
import io.swagger.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PriceService {
    private final RateRepository rateRepository;

    private Map<Integer,String> daysOfWeek = new HashMap<Integer, String>() {{
            put(0, "sun");
            put(1, "mon");
            put(2, "tue");
            put(3, "wed");
            put(4, "thu");
            put(5, "fri");
            put(6, "sat");
        }};

    @Autowired
    public PriceService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public Long getPrice(String start, String end) throws Exception{
        try {
            Date incomingDateStart = formatDateIncoming(start);
            Date incomingDateEnd = formatDateIncoming(end);
            System.out.println("==Date Start is ==" + incomingDateStart +"==Date End is ==" +incomingDateEnd);
            if (incomingDateEnd.getDay() != incomingDateEnd.getDay()) {
                throw new Exception("Date is not on the same day");
            }
            if (incomingDateEnd.getYear() != incomingDateEnd.getYear()) {
                throw new Exception("Date is not in the same year");
            }
            return findDateInRange(start, end);
        }
        catch (Exception e){
            throw new IOException(e.getMessage());
        }
    }

    private Date formatDateIncoming(String date) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        return df.parse(date);
    }

    private Date formatDateIncomingWithTimeZone(String date, String timezone) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        TimeZone tz = TimeZone.getTimeZone(timezone);
        df.setTimeZone(tz);
        String newDate = df.format(date);
        return df.parse(newDate);
    }


    private Long findDateInRange(String start, String end) throws IOException {
        List<RateItem> rateItemsList = rateRepository.getAllItems();
        for (RateItem rateItem: rateItemsList) {
            try {
                // convert timezone to check
                Date incomingStartDateFormatted = formatDateIncomingWithTimeZone(start, rateItem.getTz());
                Date incomingEndDateFormatted = formatDateIncomingWithTimeZone(end, rateItem.getTz());

                String incomingStartDay = daysOfWeek.get(incomingStartDateFormatted.getDay());
                String incomingEndDay = daysOfWeek.get(incomingEndDateFormatted.getDay());
                // compare if this is the same day
                List<String> daysInDbRow = Arrays.asList(rateItem.getDays().split(","));
                if (daysInDbRow.contains(incomingStartDay) && daysInDbRow.contains(incomingEndDay)) {
                    //compare if in time

                    // rateItem from db
                    List<String> times = Arrays.asList(rateItem.getTimes().split("-"));
                    // from incoming request
                    int incomingStartHour = incomingStartDateFormatted.getHours();
                    int incomingStartMin = incomingStartDateFormatted.getMinutes();
                    int incomingEndHour = incomingEndDateFormatted.getHours();
                    int incomingEndMin = incomingEndDateFormatted.getMinutes();
                    // From Db
                    int startHour = Integer.parseInt(times.get(0).substring(0,2));
                    int startMin = Integer.parseInt(times.get(0).substring(2,4));
                    int endHour = Integer.parseInt(times.get(1).substring(0,2));
                    int endMin =  Integer.parseInt(times.get(1).substring(2,4));
                    //compare
                    if (incomingStartHour >= startHour && incomingStartMin >= startMin
                        && incomingEndHour <= endHour && incomingEndMin <= endMin)
                        return rateItem.getPrice();
                }
            } catch (Exception e){
                System.out.println(String.format("Error in findDateInRange %s", e.getMessage()));
                throw new IOException(e.getMessage());
            }
        }
        throw new IOException("NONE FOUND");
    }
}
