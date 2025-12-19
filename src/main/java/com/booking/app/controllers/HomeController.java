package com.booking.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Renders the home page.
     *
     * @return view name of the home page
     */
    @GetMapping("/")
    public String renderIndex() {
        return "pages/home/index";
    }

    /**
     * Renders the "About Us" page.
     *
     * @return view name of the about-us page
     */
    @GetMapping("/about-us")
    public String renderAboutUs() {
        return "pages/home/about-us";
    }
}
