package com.klef.ms.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.ms.sdp.entity.Order;
import com.klef.ms.sdp.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController
{
	@Autowired
	private OrderService service;

	@GetMapping("/")
	public String test()
	{
		return "Digital Asset Marketplace Order Service";
	}

	@PostMapping("/add")
	public ResponseEntity<Order> addOrder(@RequestBody Order order)
	{
		Order o = service.saveOrder(order);

		return ResponseEntity.status(201).body(o);
	}

	@GetMapping("/displayall")
	public ResponseEntity<List<Order>> displayAllOrders()
	{
		List<Order> orders = service.getAllOrders();

		return ResponseEntity.status(200).body(orders);
	}


	@GetMapping("/display")
	public ResponseEntity<?> displayOrderById(@RequestParam Long id)
	{
		Order order = service.getOrderById(id);

		if(order != null)
		{
			return ResponseEntity.status(200).body(order);
		}
		else
		{
			return ResponseEntity.status(404).body("Order ID Not Found");
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateOrder(@RequestBody Order order)
	{
		Order o = service.updateOrder(order.getOrderId(), order);

		if(o != null)
		{
			return ResponseEntity.ok(o);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Order ID Not Found");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteOrderById(@PathVariable Long id)
	{
		String message = service.deleteOrder(id);

		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@GetMapping("/displaybyuser/{userId}")
	public ResponseEntity<List<Order>> displayOrdersByUserId(
			@PathVariable Long userId)
	{
		List<Order> orders = service.getOrdersByUserId(userId);

		return ResponseEntity.status(200).body(orders);
	}
}