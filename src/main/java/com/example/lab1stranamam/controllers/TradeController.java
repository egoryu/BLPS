package com.example.lab1stranamam.controllers;

import com.example.lab1stranamam.dto.request.OrderDto;
import com.example.lab1stranamam.dto.request.WalletDto;
import com.example.lab1stranamam.dto.response.HumanResponseDto;
import com.example.lab1stranamam.dto.response.UserResponseDto;
import com.example.lab1stranamam.entity.*;
import com.example.lab1stranamam.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/trade")
public class TradeController {
    private final HumanRepository humanRepository;
    private final ContractRepository contractRepository;
    private final UsersRepository usersRepository;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    public TradeController(HumanRepository humanRepository, ContractRepository contractRepository, UsersRepository usersRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, ItemRepository itemRepository) {
        this.humanRepository = humanRepository;
        this.contractRepository = contractRepository;
        this.usersRepository = usersRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAllTrader() {
        try {
            List<UsersEntity> trader = usersRepository.findAllByRole("trader");
            List<UserResponseDto> response = trader.stream().map(val -> new UserResponseDto(val.getId(), val.getUsername(), val.getEmail(), val.getRole())).toList();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findTraderContractor(@PathVariable int id) {
        try {
            Optional<UsersEntity> trader = usersRepository.findById(id);

            if (trader.isEmpty()) {
                throw new Exception("Trader with id " + id + " not found");
            }

            List<ContractEntity> contractor = (List<ContractEntity>) trader.get().getContractsById();
            List<UsersEntity> users = contractor.stream().map(ContractEntity::getUsersByContractor).toList();
            List<HumanEntity> humans = users.stream().map(humanRepository::findByUserId).toList();
            List<HumanResponseDto> response = humans.stream().map(HumanResponseDto::new).toList();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
        try {
            Optional<UsersEntity> user = usersRepository.findById(orderDto.getUserId());

            if (user.isEmpty()) {
                throw new Exception("User with id " + orderDto.getUserId() + " not found");
            }

            Optional<UsersEntity> trader = usersRepository.findById(orderDto.getTraderId());

            if (trader.isEmpty()) {
                throw new Exception("Trader with id " + orderDto.getUserId() + " not found");
            }

            Optional<UsersEntity> contactor = usersRepository.findById(orderDto.getContractorId());

            if (contactor.isEmpty()) {
                throw new Exception("Contractor with id " + orderDto.getUserId() + " not found");
            }

            if (!contractRepository.existsByUsersByContractorAndUsersByMaster(contactor.get(), trader.get())) {
                throw new Exception("Contract between " + trader.get().getId() + " and " + contactor.get().getId() + " not found");
            }

            OrderEntity order = new OrderEntity(orderDto.getPaymentType(), orderDto.getSum(),
                    orderDto.getData(), 0, user.get(), trader.get(), contactor.get());

            orderRepository.save(order);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/order/{id}/{status}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable int id, @PathVariable int status) {
        try {
            Optional<OrderEntity> order = orderRepository.findById(id);

            if (order.isEmpty()) {
                throw new Exception("Order with id " + id + " not found");
            }

            OrderEntity orderEntity = order.get();
            orderEntity.setStatus(status);
            orderRepository.save(orderEntity);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<?> updateOrderStatus(@RequestBody WalletDto walletDto, @PathVariable int id) {
        try {
            Optional<OrderEntity> order = orderRepository.findById(id);

            if (order.isEmpty()) {
                throw new Exception("Order with id " + id + " not found");
            }

            OrderEntity orderEntity = order.get();
            orderItemRepository.deleteAllByOrderId(orderEntity);

            walletDto.getItems().forEach(val -> orderItemRepository.save(new OrderItemEntity(orderEntity, itemRepository.findById(val.getId()).get())));
            orderEntity.setStatus(3);

            orderRepository.save(orderEntity);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            Map<Object, Object> response = new HashMap<>();
            response.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
