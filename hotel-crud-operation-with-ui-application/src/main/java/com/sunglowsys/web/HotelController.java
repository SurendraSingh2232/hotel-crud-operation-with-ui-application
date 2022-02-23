package com.sunglowsys.web;

import com.sunglowsys.domain.Hotel;
import com.sunglowsys.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HotelController {
    private final Logger logger = LoggerFactory.getLogger(HotelController.class);

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public ModelAndView home(){
        logger.debug("WEB request to getAll Hotels");
        Page<Hotel> page = hotelService.findAll(PageRequest.of(0,20));
        return  new  ModelAndView("index","hotels",page.getContent());
    }

    @GetMapping("/hotels")
    public ModelAndView createHotelForm(){
        logger.debug("WEB request to create Hotel form:");
        return new ModelAndView("add-hotel","hotel", new Hotel());
    }

    @PostMapping("/hotels")
    public String createHotels(@ModelAttribute Hotel hotel){
        logger.debug("WEB request to create Hotels: {}",hotel);
        hotelService.createHotel(hotel);
        return "redirect:/";
    }

    @GetMapping("/hotel/update/{id}")
    public ModelAndView updateHotel(@PathVariable Long id){
        logger.debug("WEB request to update Hotel: {}",id);
        return new ModelAndView("add-hotel","hotel",hotelService.findById(id));
    }

    @GetMapping("/hotel/delete/{id}")
    public String delete(@PathVariable Long id){
        logger.debug("WEB request to delete Hotel: {}",id);
        hotelService.delete(id);
        return "redirect:/";
    }
}
