package com.ucl.imaginethisserver.Controller;


import com.ucl.imaginethisserver.DAO.ConversionDto;
import com.ucl.imaginethisserver.DAO.FeedbackDto;
import com.ucl.imaginethisserver.Service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ConversionController {

    private final ConversionService conversionService;

    @Autowired
    public ConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @GetMapping("/projects/{project-id}/conversions")
    @ResponseBody
    public List<ConversionDto> getConversions(@PathVariable("project-id") String projectID) {
        return conversionService.getConversions(projectID);
    }

}
