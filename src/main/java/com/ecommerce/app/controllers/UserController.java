package com.ecommerce.app.controllers;
import com.ecommerce.app.models.Person;
import com.ecommerce.app.models.User;
import com.ecommerce.app.payload.response.MessageResponse;
import com.ecommerce.app.services.PersonService;
import com.ecommerce.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final PersonService personService;
    @Value("${upload.path}")
    private String UPLOAD_DIR;
    @Autowired
    public UserController(UserService userService, PersonService personService) {
        this.userService = userService;
        this.personService = personService;
    }

    @PostMapping("/add-new-user")
    public ResponseEntity<?> addNewUser(){
        return null;
    }
    @GetMapping("/get-user-by-id/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(
                userService.getUserById(userId),HttpStatus.OK
        );
    }
    @PostMapping("/change-profile-picture/{userId}")
    public ResponseEntity<?> changeProfilePicture(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Please select a file to upload."));
        }

        try {
            User user = userService.getUserById(userId);
            String currentProfilePicture = user.getPerson().getImage();

            File directory = new File(UPLOAD_DIR+"/users/");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            if (currentProfilePicture != null && !currentProfilePicture.isEmpty()) {
                // Delete the current profile picture
                File currentPictureFile = new File(UPLOAD_DIR +"/users/"+ currentProfilePicture);
                if (currentPictureFile.exists()) {
                    currentPictureFile.delete();
                }
            }
//            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(UPLOAD_DIR+"/users/", user.getUsername()).toString();
            file.transferTo(new File(filePath));
            user.getPerson().setImage(user.getUsername());
            userService.save(user);
            return ResponseEntity.ok(new MessageResponse("Profile picture updated successfully"));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error: " + e.getMessage()));
        }
    }
    @GetMapping("/images/{userId}/{imageName}")
    public ResponseEntity<?> getImage(@PathVariable Long userId,@PathVariable String imageName) {
        try {
            User user = userService.getUserById(userId);
            if(user.getPerson().getImage() == imageName) {
                try {
                    Path imagePath = Paths.get(UPLOAD_DIR + "/users/", imageName);
                    Resource resource = new UrlResource(imagePath.toUri());
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(resource);
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
                }
            }else {
                return null;
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error: "+e.getMessage()));
        }
    }
    @PutMapping("/update-information-user/{userId}")
    public ResponseEntity<?> updateUserInfo(@PathVariable Long userId, @RequestBody Person updateDTO){
        try {
            User user = userService.getUserById(userId);
            Person person = user.getPerson();
            person.setFirstName(updateDTO.getFirstName());
            person.setLastName(updateDTO.getLastName());
            person.setPhone(updateDTO.getPhone());
            person.setAddress(updateDTO.getAddress());
            person.setReference(updateDTO.getReference());
            personService.save(person);
            return ResponseEntity.ok(new MessageResponse("Personal information updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error: " + e.getMessage()));
        }
    }
    @PutMapping("/update-street-address/{userId}")
    public ResponseEntity<?> updateUserAddr(@PathVariable Long userId){
        return null;
    }

}
