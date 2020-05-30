package com.example.Poller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
            resultFileName = uuidFile + ".jpg";
            File file = new File(uploadPath + "/autos/" + resultFileName);
            OutputStream os = new FileOutputStream(file);
            os.write(img.getBody());
            os.close();
            parkingService.processPollEvent(resultFileName, parkingId);
            System.out.println("12345");
        }
    }
}
