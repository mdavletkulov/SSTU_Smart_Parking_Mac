package com.example.Poller.service;

import com.example.Poller.domain.ParkingNumberEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.*;

@Service
public class PollerService {

    @Autowired
    @Qualifier(value = "digestRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private ParkingService parkingService;

    @Value("${upload.img.path}")
    private String uploadPath;

    @Value("${cam.snapshot.address}")
    private String snapshotAddress;

    @Value("${cam.parking.id}")
    private Long parkingId;

    public void processSnapshot() throws IOException {
        String resultFileName;
        List<ParkingNumberEvent> parkingNumberEvents = new ArrayList<>();
        parkingService.deleteLastDateEvents(parkingId);
        parkingService.deleteErrorEvents();
        String uri = snapshotAddress;
        try {
            ResponseEntity<byte[]> img = restTemplate.exchange(uri, HttpMethod.GET, null, byte[].class);
            if (img.getBody() != null) {
                String uuidFile = UUID.randomUUID().toString();
                List<String> fileNames = new ArrayList<>();
                List<String> numbers = new ArrayList<>();
                resultFileName = uuidFile + ".jpg";
                File file = new File(uploadPath + "/images/autos/" + resultFileName);
                OutputStream os = new FileOutputStream(file);
                os.write(img.getBody());
                os.close();
                File file1 = new File(uploadPath + "/images/autos/" + resultFileName);
                BufferedImage originalImage = ImageIO.read(file1);

                uuidFile = UUID.randomUUID().toString();
                String resultFileName1 = uuidFile + ".jpg";
                BufferedImage subImgage = originalImage.getSubimage(350, 700, 430, 500);
                ImageIO.write(subImgage, "jpg", new File(uploadPath + "/images/autos/" + resultFileName1));
                parkingNumberEvents.add(new ParkingNumberEvent(1, parkingService.checkAndProcessImage(resultFileName1), resultFileName1));

                subImgage = originalImage.getSubimage(800, 700, 430, 500);
                uuidFile = UUID.randomUUID().toString();
                resultFileName1 = uuidFile + ".jpg";
                ImageIO.write(subImgage, "jpg", new File(uploadPath + "/images/autos/" + resultFileName1));
                parkingNumberEvents.add(new ParkingNumberEvent(2, parkingService.checkAndProcessImage(resultFileName1), resultFileName1));

                subImgage = originalImage.getSubimage(1180, 700, 480, 500);
                uuidFile = UUID.randomUUID().toString();
                resultFileName1 = uuidFile + ".jpg";
                ImageIO.write(subImgage, "jpg", new File(uploadPath + "/images/autos/" + resultFileName1));
                parkingNumberEvents.add(new ParkingNumberEvent(3, parkingService.checkAndProcessImage(resultFileName1), resultFileName1));

                subImgage = originalImage.getSubimage(1630, 800, 510, 500);
                uuidFile = UUID.randomUUID().toString();
                resultFileName1 = uuidFile + ".jpg";
                ImageIO.write(subImgage, "jpg", new File(uploadPath + "/images/autos/" + resultFileName1));
                parkingNumberEvents.add(new ParkingNumberEvent(4, parkingService.checkAndProcessImage(resultFileName1), resultFileName1));

                subImgage = originalImage.getSubimage(2100, 800, 500, 600);
                uuidFile = UUID.randomUUID().toString();
                resultFileName1 = uuidFile + ".jpg";
                ImageIO.write(subImgage, "jpg", new File(uploadPath + "/images/autos/" + resultFileName1));
                parkingNumberEvents.add(new ParkingNumberEvent(5, parkingService.checkAndProcessImage(resultFileName1), resultFileName1));

                parkingService.processPollEvent(resultFileName, parkingNumberEvents, parkingId);
            }
        }
        catch (HttpServerErrorException e) {

        }
    }
}
