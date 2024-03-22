package com.example.lab1stranamam.controllers;

import com.example.lab1stranamam.dto.request.UserDto;
import com.example.lab1stranamam.dto.request.WalletDto;
import com.example.lab1stranamam.dto.response.HumanResponseDto;
import com.example.lab1stranamam.entity.*;
import com.example.lab1stranamam.repositories.ItemRepository;
import com.example.lab1stranamam.repositories.OrderRepository;
import com.example.lab1stranamam.repositories.UsersRepository;
import com.example.lab1stranamam.repositories.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class WalletController {
    private final WalletRepository walletRepository;
    private final UsersRepository usersRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    public WalletController(WalletRepository walletRepository, UsersRepository usersRepository, ItemRepository itemRepository, OrderRepository orderRepository) {
        this.walletRepository = walletRepository;
        this.usersRepository = usersRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> makeDeposit(@RequestBody WalletDto walletDto) {
        try {
            Optional<UsersEntity> user = usersRepository.findById(walletDto.getUserId());

            if (user.isEmpty()) {
                throw new Exception("User with id " + walletDto.getUserId() + " not found");
            }

            Optional<WalletEntity> walletEntityOptional = walletRepository.findByUsersByUserId(user.get());

            WalletEntity walletEntity = walletEntityOptional.orElseGet(() -> new WalletEntity(user.get()));

            walletEntity.setAmount(walletDto.getSum() + walletEntity.getAmount());

            walletRepository.save(walletEntity);

            walletDto.getItems().forEach(val -> itemRepository.save(new ItemEntity(val.getName(), val.getPrice(), val.getDescription(), walletEntity)));

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<?> payForOrder(@RequestBody UserDto userDto, @PathVariable int id) {
        try {
            String username = userDto.getUsername();
            Optional<UsersEntity> user = usersRepository.findByUsername(username);

            if (user.isEmpty()) {
                throw new Exception("User with username " + username + " not found");
            }

            if (!user.get().getPassword().equals(userDto.getPassword())) {
                throw new Exception("Wrong password");
            }

            Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);

            if (orderEntityOptional.isEmpty()) {
                throw new Exception("Order with id " + id + " not found");
            }

            OrderEntity order = orderEntityOptional.get();
            if (!user.get().equals(order.getUsersByCostumer())) {
                throw new Exception("Other person order");
            }

            if (order.getStatus() != 5) {
                throw new Exception("Order not ready for pay");
            }

            Optional<WalletEntity> consumerWalletOptional = walletRepository.findByUsersByUserId(user.get());
            Optional<WalletEntity> traderWalletOptional = walletRepository.findByUsersByUserId(order.getUsersBySeller());

            if (consumerWalletOptional.isEmpty()) {
                throw new Exception("Consumer wallet not found");
            }

            if (traderWalletOptional.isEmpty()) {
                throw new Exception("Trader wallet not found");
            }

            WalletEntity consumerWallet = consumerWalletOptional.get();
            WalletEntity traderWallet = traderWalletOptional.get();

            if (order.getPaymentType() == 0) {
                consumerWallet.setAmount(consumerWallet.getAmount() - order.getSum());
                traderWallet.setAmount(traderWallet.getAmount() + order.getSum());

                walletRepository.save(consumerWallet);
                walletRepository.save(traderWallet);
            } else {
                order.getOrderItemsById().forEach(val -> {
                    ItemEntity item = itemRepository.findById(val.getId()).get();
                    item.setWalletByWalletId(traderWallet);
                    itemRepository.save(item);
                });
            }

            order.setStatus(6);
            orderRepository.save(order);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/wallet/{userId}")
    public ResponseEntity<?> getHuman(@PathVariable int userId) {
        try {
            Optional<UsersEntity> user = usersRepository.findById(userId);

            if (user.isEmpty()) {
                throw new Exception("User with id " + userId + " not found");
            }
            Optional<WalletEntity> wallet = walletRepository.findByUsersByUserId(user.get());

            if (wallet.isEmpty()) {
                throw new Exception("Wallet not found");
            }

            return ResponseEntity.ok(new WalletDto(wallet.get()));
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
