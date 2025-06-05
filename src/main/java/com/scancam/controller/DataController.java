package com.scancam.controller;

import com.scancam.model.CaptureModel;
import com.scancam.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("data")
public class DataController {

    @Autowired
    private DataService dataService;

    @GetMapping("obtener")
    public ResponseEntity<List<CaptureModel>> getData() {
        if(dataService.getData() != null) {
            var data = dataService.getData();
            System.out.println("Datos enviados: " + data);
            return ResponseEntity.ok(data);
        }

        return ResponseEntity.status(404).body(null);
    }

    @GetMapping("delete/all")
    public ResponseEntity<String> deleteAllData() {
        try {
            dataService.deleteAllData();
            return ResponseEntity.ok("All data deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting data: " + e.getMessage());
            return ResponseEntity.status(500).body("Error deleting data: " + e.getMessage());
        }
    }

}
