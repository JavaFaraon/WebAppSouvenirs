package com.example.webappsouvenirs.controllers;

import com.example.webappsouvenirs.entities.Country;
import com.example.webappsouvenirs.entities.Image;
import com.example.webappsouvenirs.entities.Manufacturer;
import com.example.webappsouvenirs.entities.Souvenir;
import com.example.webappsouvenirs.repositories.CountryRepository;
import com.example.webappsouvenirs.repositories.ImageRepository;
import com.example.webappsouvenirs.repositories.ManufacturerRepository;
import com.example.webappsouvenirs.repositories.SouvenirRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    private final SouvenirRepository souvenirRepository;
    private final ManufacturerRepository manufacturerRepository;

    private final ImageRepository imageRepository;
    private final CountryRepository countryRepository;

    public MainController(SouvenirRepository souvenirRepository,
                          ManufacturerRepository manufacturerRepository, ImageRepository imageRepository, CountryRepository countryRepository) {
        this.souvenirRepository = souvenirRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.imageRepository = imageRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/souvenirs")
    public String showSouvenirs(Model model) {
        model.addAttribute("souvenirs", souvenirRepository.findAll());
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        model.addAttribute("images", imageRepository.findAll());
        return "souvenirs";
    }

    @GetMapping("/manufacturers")
    public String showManufacturers(Model model) {
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        model.addAttribute("countries", countryRepository.findAll());
        return "manufacturers";
    }

    @GetMapping("/souvenir_edit/{id}")
    public String showSouvenirEditPage(@PathVariable Long id, Model model) {
        Optional<Souvenir> souvenir = souvenirRepository.findById(id);
        Collection<Manufacturer> manufacturers = manufacturerRepository.findAll();
        if (souvenir.isPresent()) {
            model.addAttribute("souvenir", souvenir.get());
            model.addAttribute("manufacturers", manufacturers);
            model.addAttribute("image", souvenir.get().getImage());
            return "souvenir_edit";
        } else {
            return "redirect:/souvenirs";
        }
    }

    @PostMapping(value ="/souvenir_update/{id}")
    public String updateSouvenir(@PathVariable Long id,
                                 @RequestParam String souvenirName,
                                 @RequestParam Double souvenirPrice,
                                 @RequestParam Integer quantity,
                                 @RequestParam LocalDate dateOfManufacturing,
                                 @RequestParam Manufacturer manufacturer,
                                 @RequestParam("image") MultipartFile image
    ) throws IOException {
        Souvenir souvenir = souvenirRepository.findById(id).orElseThrow(null);
        Image img = null;

        if (image.getSize() != 0) {
            img = new Image(image.getName(),image
                    .getOriginalFilename(),image.getSize(),image
                    .getContentType(),image.getBytes());
            imageRepository.save(img);
        }
        souvenir.setSouvenirName(souvenirName);
        souvenir.setSouvenirPrice(souvenirPrice);
        souvenir.setQuantity(quantity);
        souvenir.setDateOfManufacturing(dateOfManufacturing);
        souvenir.setManufacturer(manufacturer);
        if(img != null) {
            souvenir.setImage(img);
        }
        try {
            souvenirRepository.save(souvenir);
        } catch (Exception ignored) {
        }
        return "redirect:/souvenirs";
    }

    @GetMapping("/souvenir_delete/{id}")
    public String deleteSouvenir(@PathVariable Long id) {
        Optional<Souvenir> souvenir = souvenirRepository.findById(id);
        souvenir.ifPresent(souvenirRepository::delete);
        if (souvenir.isEmpty()) {
            return "souvenir_delete_error";
        }
        return "redirect:/souvenirs";
    }

    @PostMapping(value = "/add_souvenir")
    public String addSouvenir(
            @RequestParam("image") MultipartFile image,
            @RequestParam String souvenirName,
            @RequestParam Double souvenirPrice,
            @RequestParam Integer quantity,
            @RequestParam LocalDate dateOfManufacturing,
            @RequestParam Manufacturer manufacturer
    ) throws IOException {
        Image img = null;

        if (image.getSize() != 0) {
            img = new Image(image.getName(),image
                    .getOriginalFilename(),image.getSize(),image
                    .getContentType(),image.getBytes());
            imageRepository.save(img);
        }

        Souvenir souvenir = new Souvenir();
        souvenir.setSouvenirName(souvenirName);
        souvenir.setSouvenirPrice(souvenirPrice);
        souvenir.setQuantity(quantity);
        souvenir.setDateOfManufacturing(dateOfManufacturing);
        souvenir.setManufacturer(manufacturer);
        if(img != null) {
            souvenir.setImage(img);
        }
        souvenirRepository.save(souvenir);
        return "redirect:/souvenirs";
    }

    @PostMapping("/add_manufacturer")
    public String addManufacturer(@RequestParam String name, @RequestParam Country country) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturer.setCountry(country);
        manufacturerRepository.save(manufacturer);
        return "redirect:/manufacturers";
    }

    @GetMapping("/manufacturer_edit/{id}")
    public String showManufacturerEditPage(@PathVariable Long id, Model model) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        Collection<Country> countries = countryRepository.findAll();
        if (manufacturer.isPresent()) {
            model.addAttribute("manufacturer", manufacturer.get());
            model.addAttribute("countries", countries);
            return "manufacturer_edit";
        } else {
            return "redirect:/manufacturers";
        }
    }

    @PostMapping("/manufacturer_update/{id}")
    public String updateManufacturer(Manufacturer manufacturer) {
        try {
            manufacturerRepository.save(manufacturer);
        } catch (Exception ignored) {
        }
        return "redirect:/manufacturers";
    }

    @GetMapping("/manufacturer_delete/{id}")
    public String deleteManufacturer(@PathVariable Long id, Model model) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        //manufacturer.ifPresent(manufacturerRepository::delete);
        if (manufacturer.isPresent()) {
            model.addAttribute("souvenirsByManufacturer", souvenirRepository.findAllByManufacturer(manufacturer.get()));
            model.addAttribute("manufacturer", manufacturer.get());
            return "delete_manufacturer";
        } else {
            return "manufacturer_delete_error";
        }
    }

    @PostMapping("/agree_delete_manufacturer/{id}")
    public String agreeDeleteManufacturer(@PathVariable Long id, Model model) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        if (manufacturer.isPresent()) {
            List<Souvenir> souvenirs = souvenirRepository.findAllByManufacturer(manufacturer.get());
            souvenirRepository.deleteAll(souvenirs);
            manufacturerRepository.delete(manufacturer.get());
            return "redirect:/manufacturers";
        } else {
            return "redirect:/manufacturers";
        }
    }

    @GetMapping("/show_souvenirs/{id}")
    public String showAllSouvenirsOfManufacturer(@PathVariable Long id, Model model) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        if (manufacturer.isPresent()) {
            model.addAttribute("souvenirsByManufacturer", souvenirRepository.findAllByManufacturer(manufacturer.get()));
            model.addAttribute("manufacturer", manufacturer.get());
            return "all_souvenirs_of_manufacturer";
        } else {
            return "redirect:/manufacturers";
        }
    }

    @PostMapping("/show_souvenirs_by_period")
    public String showSouvenirsByPeriod(@RequestParam LocalDate start, LocalDate finish, Model model) {
        model.addAttribute("souvenirsByPeriod", souvenirRepository.findAllByPeriod(start, finish));
        model.addAttribute("manufacturer", manufacturerRepository.findAll());
        model.addAttribute("start", start);
        model.addAttribute("finish", finish);
        return "souvenirs_by_period";
    }

    @PostMapping("/show_souvenirs_by_country")
    public String showSouvenirsByCountry(@RequestParam Country country, Model model) {
        model.addAttribute("souvenirsByCountry", souvenirRepository.findAllByManufacturerCountry(country));
        model.addAttribute("manufacturer", manufacturerRepository.findAll());
        model.addAttribute("country", country);
        return "souvenirs_by_country";
    }
}

