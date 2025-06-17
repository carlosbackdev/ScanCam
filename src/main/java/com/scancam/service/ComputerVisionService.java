package com.scancam.service;

import com.azure.ai.vision.imageanalysis.models.ImageAnalysisResult;
import com.azure.ai.vision.imageanalysis.models.VisualFeatures;
import com.azure.core.util.BinaryData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.azure.ai.vision.imageanalysis.*;
import com.azure.core.credential.AzureKeyCredential;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ComputerVisionService {

    private final ImageAnalysisClient client;

    @Value("${azure.vision.endpoint}")
    private String endpoint;

    @Value("${azure.vision.key}")
    private String key;

    public ComputerVisionService() {
        this.client = new ImageAnalysisClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(key))
                .buildClient();
    }
    public long countPeopleInImage(String imagePath) {
        try {
            BinaryData imageData = BinaryData.fromBytes(Files.readAllBytes(Path.of(imagePath)));

            ImageAnalysisResult result = client.analyze(
                    imageData,
                    List.of(VisualFeatures.PEOPLE),
                    null
            );

            var peopleResult = result.getPeople();
            int peopleCount=peopleResult.getValues().size();

            return peopleCount;
        } catch (Exception e) {
            System.err.println("Error al analizar la imagen: " + e.getMessage());
            return -1;
        }
    }


}
