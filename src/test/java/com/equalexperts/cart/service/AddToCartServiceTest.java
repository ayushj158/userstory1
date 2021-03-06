package com.equalexperts.cart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.equalexperts.cart.domain.OrderVO;

@ExtendWith(MockitoExtension.class)
public class AddToCartServiceTest {

	@Nested
	@DisplayName("Given An empty Shopping Cart and a product Dove Soap having unit price 39.99")
	class AddItemTest {
		AddToCartService cart;

		@BeforeEach
		public void setup() {
			OrderVO mockOrder = OrderTestData.createMockOrder(0, 0, 0.0);
			cart = new AddToCartService(mockOrder);
		}

		@Test
		@DisplayName("When_user adds 1 item to cart with 5 qunatity_Then_Shopping cart should contain 1 item with quantity 5 and unit price 39.99")
		public void test_addItem() {
			cart.add("product 1", 39.99, 5);
			assertEquals(1, cart.getOrder().getOrderItems().size());
			assertEquals(5, cart.getOrder().getOrderItems().get("product 1").getQuantity());
			assertEquals(39.99, cart.getOrder().getOrderItems().get("product 1").getUnitPrice(),0.001);
		}
		
		@Test
		@DisplayName("When_user adds  2 different items to cart_Then_Shopping cart should contain 2 items")
		public void test_addmultipleItems() {
			cart.add("product 2", 45.66, 5);
			cart.add("product 1", 39.99, 1);
			assertEquals(2, cart.getOrder().getOrderItems().size());
			assertEquals(1, cart.getOrder().getOrderItems().get("product 1").getQuantity());
			assertEquals(5, cart.getOrder().getOrderItems().get("product 2").getQuantity());
			assertEquals(39.99, cart.getOrder().getOrderItems().get("product 1").getUnitPrice(),0.001);
			assertEquals(45.66, cart.getOrder().getOrderItems().get("product 2").getUnitPrice(),0.001);
		}

		@Test
		@DisplayName("When_user adds a item to cart with negative quantity_Then_ExceptionThrown")
		public void test_addItem_InvalidQuantity() {
			assertThrows(IllegalArgumentException.class, () -> cart.add("Dove Soap", 39.99, 0));
		}

		@Test
		@DisplayName("When_user adds a item to cart with invalid price_Then_ExceptionThrown")
		public void test_addItem_InvalidPrice() {
			assertThrows(IllegalArgumentException.class, () -> cart.add("Dove Soap", -39.99, 5));
		}
	}
}
