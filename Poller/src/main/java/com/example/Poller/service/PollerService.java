package com.example.Poller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        String uri = snapshotAddress;
        ResponseEntity<byte[]> img = restTemplate.exchange(uri, HttpMethod.GET, null, byte[].class);
        if (img.getBody() != null) {
            String uuidFile = UUID.randomUUID().toString();
            List<String> fileNames = new ArrayList<>();
            List<String> numbers = new ArrayList<>();
            resultFileName = uuidFile + ".jpg";
            File file = new File(uploadPath + "/autos/" + resultFileName);
            OutputStream os = new FileOutputStream(file);
            os.write(img.getBody());
            os.close();
            File file1 = new File(uploadPath + "/autos/" + resultFileName);
            BufferedImage originalImgage = ImageIO.read(file1);
            BufferedImage subImgage = originalImgage.getSubimage(350, 700, 730, 500);
            ImageIO.write(subImgage, "jpg", new File(uploadPath + "/autos/" + resultFileName ));
            fileNames.add(resultFileName);
            BufferedImage subImgage1 = originalImgage.getSubimage(1080, 700, 660, 500);
            uuidFile = UUID.randomUUID().toString();
            String resultFileName1 = uuidFile + ".jpg";
            ImageIO.write(subImgage1, "jpg", new File(uploadPath + "/autos/" + resultFileName1 ));
            fileNames.add(resultFileName1);
            BufferedImage subImgage2 = originalImgage.getSubimage(1680, 700, 900, 500);
            uuidFile = UUID.randomUUID().toString();
            String resultFileName2 = uuidFile + ".jpg";
            ImageIO.write(subImgage2, "jpg", new File(uploadPath + "/autos/" + resultFileName2 ));
            fileNames.add(resultFileName2);
            for (String fileName : fileNames) {
                numbers.addAll(parkingService.checkAndProcessImage(fileName));
            }
            parkingService.processPollEvent(resultFileName, numbers, parkingId);
        }
    }
}
