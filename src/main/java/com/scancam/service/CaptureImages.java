package com.scancam.service;

import com.scancam.data.DataSave;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CaptureImages extends Thread{
    private String url;
    private String filename;

    @Autowired
    private DataSave dataSave;

    public void start(String url) {
        this.url = url;
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver-win64/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);

        try {
            // 1. Navegar a la página de la cámara
            driver.get(url);

            // Pequeña espera para que cargue la página
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }

            // 2. Manejar el consentimiento de navegación
            try {
                WebElement consentButton = driver.findElement(By.xpath("//button[contains(.,'Aceptar') or contains(.,'Accept') or contains(.,'Consentir') or contains(.,'Aceptar todo') or contains(.,'Allow') or contains(.,'Continue') or contains(.,'Accept all')]"));
                consentButton.click();
                System.out.println("Consentimiento aceptado");
                Thread.sleep(2000); // Esperar después del consentimiento
            } catch (Exception e) {
                System.out.println("No se encontró aviso de consentimiento");
            }


            // 2. Hacer clic en el video para iniciarlo
            WebElement poster = driver.findElement(By.cssSelector("div.player-poster.clickable"));
            new Actions(driver)
                    .moveToElement(poster)
                    .click()
                    .perform();

            // Pequeña espera para que cargue la página
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }


            WebElement videoElement = driver.findElement(By.cssSelector("video[data-html5-video]"));
            File screenshot = ((TakesScreenshot)videoElement).getScreenshotAs(OutputType.FILE);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }

            // 4. Guardar la imagen con marca de tiempo
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss_dd-MM-yyyy"));
            String relativePath = "src/main/resources/static/assets/skyline_capture_" + timestamp + ".png";
            filename = "/assets/skyline_capture_" + timestamp + ".png";
            try {
                FileUtils.copyFile(screenshot, new File(relativePath));
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error al copiar el archivo: " + e.getMessage());
            }
            System.out.println("Captura guardada como: " + filename);

        } finally {
            // Cerrar el navegador
            driver.quit();
            getPeopleAndSave();
        }
    }

    public void getPeopleAndSave() {
        dataSave.saveData(filename, url);
    }

}
