package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.repositories.*;

@Controller
@RequestMapping(path = "/major")
public class MajorController {
    private final CourseRepository courseRepository;
    private final MajorRepository majorRepository;

    @Autowired
    public MajorController(CourseRepository courseRepository, MajorRepository majorRepository) {
        this.courseRepository = courseRepository;
        this.majorRepository = majorRepository;
    }
}
