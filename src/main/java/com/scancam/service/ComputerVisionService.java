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

@Service
public class ComputerVisionService {

    private final ImageAnalysisClient client;


    public ComputerVisionService() {
        this.client = new ImageAnalysisClientBuilder()
                .endpoint("https://aifoundryresour.cognitiveservices.azure.com/")
                .credential(new AzureKeyCredential("FqP6zfUPmfGnpHqoKwhdPIX9kHD7OX7xA0uvrl5dROhW5YeRAhkpJQQJ99BFACYeBjFXJ3w3AAAAACOGa1WJ"))
                .buildClient();
    }
    public long countPeopleInImage(String imagePath) {
        // Si la ruta empieza con "/assets/", conviértela a la ruta real
        if (imagePath.startsWith("/assets/")) {
            imagePath = "src/main/resources/static" + imagePath;
        }
        System.out.println("Iniciando análisis de imagen: " + imagePath);
        Path path = Path.of(imagePath).toAbsolutePath();
        System.out.println("Ruta absoluta: " + path);
        try {
            if (!Files.exists(path)) {
                System.err.println("El archivo no existe: " + path);
                return -1;
            }
            BinaryData imageData = BinaryData.fromBytes(Files.readAllBytes(path));
            System.out.println("Analizando imagen: " + path);
            System.out.println("Tamaño de la imagen: " + imageData.getLength());

            ImageAnalysisResult result = client.analyze(
                    imageData,
                    List.of(VisualFeatures.PEOPLE),
                    null
            );

            var peopleResult = result.getPeople();
            int peopleCount = peopleResult.getValues().size();

            return peopleCount;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al analizar la imagen: " + e.getMessage());
            return -1;
        }
    }


}
