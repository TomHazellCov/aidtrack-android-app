package com.tomhazell.aidtrackerapp.additem;

import java.util.List;

/**
 * Thisis h te model returned from the API when creating a campaign, we ignore the field campaign_name as we dont need it
 */
public class CampaignResponse {
    private boolean status;

    private List<Campaign> info;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Campaign> getInfo() {
        return info;
    }

    public void setInfo(List<Campaign> info) {
        this.info = info;
    }
}
