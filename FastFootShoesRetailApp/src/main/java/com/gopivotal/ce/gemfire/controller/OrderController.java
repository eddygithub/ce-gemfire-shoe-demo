package com.gopivotal.ce.gemfire.controller;

import io.pivotal.ce.gemfire.fastfootshoes.model.Product;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gopivotal.ce.gemfire.services.TransactionDataService;

@Controller
public class OrderController {
	
	@Autowired
	private TransactionDataService transactionDataService;
	
	@Autowired
	private ProductRepository productRepository;

	
	@RequestMapping("/collectOrder")
    public String collectOrder(@RequestParam(value="id", required=false) String productId, Model model) {
		if (productId != null) {
			Product product = productRepository.findOne(productId);
			if (product != null) {
				model.addAttribute("productString", " Brand: " + product.getBrand() + " Type: " + product.getType() + " Color: " + product.getColor() + " Size: " + product.getSize() + (product.getGender().equals("M") ? " Mens" : " Womens "));
				model.addAttribute("productId", productId);
			}
		}
        return "collectOrder";
    }

	@RequestMapping("/placeOrder")
    public String placeOrder(@RequestParam(value="customerId", required=true) String customerId, @RequestParam(value="productId", required=true) String productId, @RequestParam(value="quantity", required=true) int quantity, Model model) {
		System.out.println("Processing order for Customer: " + customerId + " Product: " + productId + " Quantity: " + quantity);
		if (transactionDataService.placeOrder(customerId, productId, quantity)) {
			model.addAttribute("id", customerId);
			return "completedOrder";
		}
		else {
			return "failedOrder";
		}
    }
	
	@RequestMapping("/orderSearch")
    public String orderSearch(Model model) {
		return "orderSearch";
    }
	
	@RequestMapping("/cancelOrder")
    public String cancelOrder(@RequestParam(value="id", required=true) int id, Model model) {
		model.addAttribute("id", id);
        return "cancelOrder";
    }
	
	@RequestMapping("/listAllOrders")
    public String listAllOrders(Model model) {
		model.addAttribute("transactions", transactionDataService.getAllOrder());
        return "listTransactions";
    }
	
	@RequestMapping("/listAllOpenOrders")
    public String listOpenOrders(Model model) {
		model.addAttribute("transactions", transactionDataService.getOpenOrder());
        return "listTransactions";
    }
}
