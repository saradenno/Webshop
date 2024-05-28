package com.luxury.dao;

import com.luxury.models.CustomUser;
import com.luxury.models.PlacedOrder;
import com.luxury.models.ProductVariants;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDAO {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductVariantsRepo productVariantsRepo;

    public OrderDAO(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, ProductVariantsRepo productVariantsRepo) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productVariantsRepo = productVariantsRepo;
    }

    public List<PlacedOrder> getAllOrders(){
        return  this.orderRepository.findAll();
    }


    @Transactional
    public void createOrder(PlacedOrder placedOrder){
        this.userRepository.save(placedOrder.getUser() );

        this.orderRepository.save(placedOrder);

    }


    @Transactional
    public void saveOrderWithProducts(PlacedOrder order, String userEmail) {
        CustomUser user = userRepository.findByEmail(userEmail);
        order.setUser(user);

        // Bereken het totaal aantal producten (bestaande logica)
        int totalProducts = order.getProducts().size();
        order.setTotalProducts(totalProducts);

        // Stel de besteldatum in op de huidige datum en tijd
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);
        // updating stock
        order.getProducts().forEach(product -> {
            ProductVariants pv = productVariantsRepo.findFirstByProductId(product.getId());
            pv.setStock(pv.getStock()-product.getAmount());
            productVariantsRepo.save(pv);
        });
    }





    public List<PlacedOrder> getOrdersByUserId(long userId){
        Optional<List<PlacedOrder>> orderList = this.orderRepository.findByUserId(userId);
        if (orderList.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No products found with that category id"
            );
        }
        return orderList.get(); // return the list of orders
    }




}
