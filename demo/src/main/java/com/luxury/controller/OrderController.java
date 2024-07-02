package com.luxury.controller;


import com.luxury.dao.OrderDAO;
import com.luxury.dao.ProductVariantsRepo;
import com.luxury.dao.UserRepository;
import com.luxury.models.CustomUser;
import com.luxury.models.PlacedOrder;
import com.luxury.models.ProductVariants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/orders")
public class OrderController {

    private final OrderDAO orderDAO;
    private final UserRepository userRepository;

    private final ProductVariantsRepo productVariantsRepo;

    public OrderController(OrderDAO orderDAO, UserRepository userRepository, ProductVariantsRepo productVariantsRepo) {
        this.orderDAO = orderDAO;
        this.userRepository = userRepository;
        this.productVariantsRepo = productVariantsRepo;
    }

    @GetMapping
    public ResponseEntity<List<PlacedOrder>> getAllOrders(){
        return ResponseEntity.ok(this.orderDAO.getAllOrders());
    }


    @GetMapping("/myOrders")
    public ResponseEntity<List<PlacedOrder>> getOrdersByUserPrincipal(Principal principal) {
        if (principal == null) {
            return ResponseEntity.badRequest().build();
        }
        String userEmail = principal.getName();
        CustomUser user = userRepository.findByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<PlacedOrder> orders = this.orderDAO.getOrdersByUserId(user);

        // Voorbeeld: Stel dat je 'totalProducts' al hebt ingesteld in je OrderDAO of ergens anders
        // Anders, hier zou je logica toevoegen om 'totalProducts' te berekenen voor elke bestelling.
        // Bijvoorbeeld, voor elke bestelling, tel het aantal producten en stel 'totalProducts' in.
        // Dit is een eenvoudige demonstratie die ervan uitgaat dat de totalen al berekend zijn.

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(orders);
    }




    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody PlacedOrder placedOrder, Principal principal) {
        String userEmail = principal.getName();
        this.orderDAO.saveOrderWithProducts(placedOrder, userEmail);
        return ResponseEntity.ok().body("{\"message\": \"Order created successfully\"}");
    }





}
