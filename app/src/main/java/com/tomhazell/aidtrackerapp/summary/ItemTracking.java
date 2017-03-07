package com.tomhazell.aidtrackerapp.summary;

import java.util.Date;

/**
 * Created by Tom Hazell on 07/03/2017.
 */

public class ItemTracking {
    private Date date;
    private String status;
    private String location;

    public ItemTracking() {
    }

    public ItemTracking(Date date, String status, String location) {
        this.location = location;
        this.date = date;
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
