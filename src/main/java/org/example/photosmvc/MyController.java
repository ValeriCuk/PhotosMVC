package org.example.photosmvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("/delete/{photo_id}")
    public String onDelete(@PathVariable("photo_id") long id) {
        if(photos.remove(id)==null)
            throw new PhotoNotFoundException();
        else
            return "/index";
    }
}
