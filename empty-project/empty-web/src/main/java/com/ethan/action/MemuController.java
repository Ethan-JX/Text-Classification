package com.ethan.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ethan on 2017/utf-8File/29.
 */
@Controller
@RequestMapping()
public class MemuController {

    @RequestMapping("/")
    public String toIndexPage(){
        return "/index";
    }

}
