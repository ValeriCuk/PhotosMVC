package org.example.photosmvc;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/")
public class MyController {

    private final Map<Long, byte[]> photos = new HashMap<Long, byte[]>();

    public MyController() {
        System.out.println("MyController created");
    }

    @RequestMapping("/")
    public String onIndex() {
        System.out.println("onIndex");
        return "index";
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    public ModelAndView onView(@RequestParam("photo_id") long id) {
        if (photos.containsKey(id)) {
            return new ModelAndView("result", "photo_id", id);
        } else
            throw new PhotoNotFoundException();
    }

    @RequestMapping(value = "/add_photo", method = RequestMethod.POST)
    public ModelAndView onAddPhoto(@RequestParam MultipartFile photo) {
        if (photo.isEmpty()) {
            throw new PhotoNotFoundException();
        }
        try {
            long id = System.currentTimeMillis();
            photos.put(id, photo.getBytes());
            return new ModelAndView("result", "photo_id", id);
        } catch (IOException e) {
            System.out.println("Error adding photo");
            throw new PhotoNotFoundException();
        }
    }

    @RequestMapping("/all_photos")
    public String allPhotos(Model model) {
        System.out.println("allPhotos");
        model.addAttribute("photos", photos);
        return "photos";
    }

    @RequestMapping("/all_photos/downloadZip")
    public ResponseEntity<InputStreamResource> downloadPhotosAsZip() throws IOException {
        if (photos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            for (Map.Entry<Long, byte[]> entry : photos.entrySet()) {
                ZipEntry zipEntry = new ZipEntry(entry.getKey() + ".jpg");
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(entry.getValue());
                zipOutputStream.closeEntry();
            }
        }

        byte[] zipBytes = byteArrayOutputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(zipBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=photos.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(zipBytes.length)
                .body(new InputStreamResource(inputStream));
    }

    @RequestMapping("/photo/{photo_id}")
    public ResponseEntity<byte[]> onPhoto(@PathVariable("photo_id") long id) {
        byte[] bytes = photos.get(id);
        if(bytes ==null)
            throw new
                    PhotoNotFoundException();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(bytes,headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/deletePhotos", method = RequestMethod.POST)
    public String deletePhotos(@RequestParam("photoIds") List<Long> photoIds) {
        for (Long id : photoIds) {
            photos.remove(id);
        }
        return "redirect:/all_photos";
    }

    @RequestMapping("/delete/{photo_id}")
    public String onDelete(@PathVariable("photo_id") long id) {
        if(photos.remove(id)==null)
            throw new PhotoNotFoundException();
        else
            return "index";
    }
}
