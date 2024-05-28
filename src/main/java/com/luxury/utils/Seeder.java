package com.luxury.utils;


import com.luxury.dao.*;
import com.luxury.models.Admin;
import com.luxury.models.CustomUser;
import com.luxury.models.Product;
import org.apache.catalina.User;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Seeder {
    private ProductDAO productDAO;
    private UserRepository userRepository;
    private OrderDAO orderDAO;
    private ProductRepository productRepository;
    private AdminRepository adminRepository;


    public Seeder(ProductDAO productDAO, UserRepository userRepository, OrderDAO orderDAO, ProductRepository productRepository,AdminRepository adminRepository) {
        this.productDAO = productDAO;
        this.userRepository = userRepository;
        this.orderDAO = orderDAO;
        this.productRepository = productRepository;
        this.adminRepository = adminRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event){
        List<CustomUser> userList = userRepository.findAll();
        if (!userList.isEmpty()){
            return;
        }
        this.seedProducts();
        this.seedAdmin();
    }

    private void seedProducts(){
        Product product1 = new Product("Rolex Cosmograph Daytona", 55000.00, "Deze Rolex Daytona, bekend om zijn ongeëvenaarde precisie en iconische status, is het ultieme statussymbool voor de elite.", "https://example.com/rolex-daytona.jpg", "18kt goud", "N/A", "N/A");
        Product product2 = new Product("Burberry Bespoke Trench Coat", 10000.00, "Handgemaakt in Engeland met exclusieve materialen, deze Burberry trenchcoat biedt tijdloze elegantie en luxe.", "https://example.com/burberry-trench.jpg", "Waterbestendige katoen", "N/A", "N/A");
        Product product3 = new Product("Chanel Classic Flap Bag", 8000.00, "Deze Chanel tas, met zijn iconische quiltdesign en kettingschouderband, is een symbool van luxueuze mode.", "https://example.com/chanel-flap-bag.jpg", "Lamshuid leder", "N/A", "N/A");
        Product product4 = new Product("Audemars Piguet Royal Oak Offshore", 40000.00, "De Royal Oak Offshore, met zijn gedurfde, innovatieve design, vertegenwoordigt de hoogste standaard van luxueuze horlogerie.", "https://example.com/ap-royal-oak.jpg", "Roestvrijstaal", "N/A", "N/A");
        Product product5 = new Product("Hermès Silk Scarf", 1200.00, "Deze Hermès sjaal, vervaardigd uit de fijnste zijde, toont een kunstzinnig ontwerp dat elegantie en cultuur uitstraalt.", "https://example.com/hermes-scarf.jpg", "100% zijde", "N/A", "N/A");
        Product product6 = new Product("Tesla Model S Plaid", 130000.00, "De Tesla Model S Plaid, een topmodel in elektrische luxeauto's, biedt ongeëvenaarde prestaties en toonaangevende technologie.", "https://example.com/tesla-model-s.jpg", "Elektrisch", "N/A", "N/A");
        Product product7 = new Product("Cartier Love Bracelet", 6500.00, "De Cartier Love Bracelet, versierd met diamanten, is een teken van tijdloze toewijding en luxe.", "https://example.com/cartier-love.jpg", "18kt goud", "N/A", "N/A");
        Product product8 = new Product("Louis Vuitton Keepall Bandoulière 55", 4500.00, "De Keepall Bandoulière 55 van Louis Vuitton, perfect voor de wereldreiziger, combineert luxe met functionaliteit.", "https://example.com/lv-keepall.jpg", "Monogram canvas", "N/A", "N/A");
        Product product9 = new Product("Giorgio Armani Black Label Suit", 3500.00, "Deze Giorgio Armani pak, uit de Black Label collectie, biedt ongeëvenaarde Italiaanse vakmanschap en stijl.", "https://example.com/armani-suit.jpg", "Wol", "N/A", "N/A");
        this.productDAO.createProduct(product1);
        this.productDAO.createProduct(product2);
        this.productDAO.createProduct(product3);
        this.productDAO.createProduct(product4);
        this.productDAO.createProduct(product5);
        this.productDAO.createProduct(product6);
        this.productDAO.createProduct(product7);
        this.productDAO.createProduct(product8);
        this.productDAO.createProduct(product9);
    }

    private void seedAdmin(){
        Admin admin = new Admin();
        admin.setName("Admin");
        admin.setInfix("");
        admin.setLastName("Admin");
        admin.setEmail("admin@admin.com");
        admin.setPassword(new BCryptPasswordEncoder().encode("Admin@123"));
        adminRepository.save(admin);
    }
}
