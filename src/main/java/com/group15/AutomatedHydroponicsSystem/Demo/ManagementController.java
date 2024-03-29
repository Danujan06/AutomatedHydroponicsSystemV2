package com.group15.AutomatedHydroponicsSystem.Demo;

import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/api/v1/management")
public class ManagementController {

    @GetMapping
    public String get(){
        return "GET:: management controller";
    }

    @PostMapping
    public String post(){
        return "Post:: management controller";
    }

    @PutMapping
    public String put(){
        return "PUT:: management controller";
    }

    @DeleteMapping
    public String delete(){
        return "DELETE:: management controller";
    }

}
