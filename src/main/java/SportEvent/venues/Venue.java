package SportEvent.venues;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venue {
    private int id;
    private String name;
    private String location;
    private int capacity;
    private List<Date> bookedDates;

    public Venue(int id, String name, String location, int capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.bookedDates = new ArrayList<>();
    }

    public boolean isAvailable(Date date) {
        // Simple availability check - could be enhanced for real implementation
        for (Date bookedDate : bookedDates) {
            if (isSameDay(bookedDate, date)) {
                return false;
            }
        }
        return true;
    }

    public void book(Date date) {
        if (isAvailable(date)) {
            bookedDates.add(date);
        } else {
            System.out.println("Venue " + name + " is already booked on " + date);
        }
    }

    private boolean isSameDay(Date date1, Date date2) {
        return date1.getYear() == date2.getYear() &&
                date1.getMonth() == date2.getMonth() &&
                date1.getDate() == date2.getDate();
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getId() {
        return id;
    }
}