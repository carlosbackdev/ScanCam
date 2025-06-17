package com.scancam.service;

import com.scancam.model.CaptureModel;
import com.scancam.repository.CaptureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    @Autowired
    private CaptureRepository captureRepository;

    public void saveData(CaptureModel captureModel) {
        if (captureModel != null) {
            captureRepository.save(captureModel);
            System.out.println("Data saved successfully: " + captureModel);
        } else {
            System.out.println("CaptureModel is null, cannot save data.");
        }
    }

    public List<CaptureModel> getData(){
        try {
            List<CaptureModel> captures = captureRepository.findAll();
            if (captures.isEmpty()) {
                System.out.println("No data found.");
            }
            return captures;
        } catch (Exception e) {
            System.err.println("Error retrieving data: " + e.getMessage());
            return null;
        }
    }

    public void deleteAllData() {
        try {
            captureRepository.deleteAll();
            System.out.println("All data deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting data: " + e.getMessage());
        }
    }


}
