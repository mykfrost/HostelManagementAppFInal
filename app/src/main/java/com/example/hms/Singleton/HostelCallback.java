package com.example.hms.Singleton;

import com.example.hms.utils.Hostel;

import java.util.List;

public interface HostelCallback {
    void onSuccess(List<Hostel> hostels);
    void onFailure(String errorMessage);
}
