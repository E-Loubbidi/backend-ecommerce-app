package com.ecommerce.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.order.CheckoutItemDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class OrderService {
	
	@Value("${BASE_URL}")
	private String baseUrl;
	
	@Value("${STRIPE_SECRET_KEY}")
	private String apiKey;
	
	public Session createSession(List<CheckoutItemDto> checkoutItemDtos) throws StripeException {
		
		String successUrl = baseUrl + "payment/sucess";
		
		String failureUrl = baseUrl + "payment/failed";
		
		Stripe.apiKey = apiKey;
		
		List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();
		
		for(CheckoutItemDto checkoutItemDto : checkoutItemDtos) {
			sessionItemList.add(createSessionListItem(checkoutItemDto));
		}
		
		SessionCreateParams params = SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT)
				.setCancelUrl(failureUrl)
				.setSuccessUrl(successUrl)
				.addAllLineItem(sessionItemList)
				.build();
		
		return Session.create(params);
	}

	private SessionCreateParams.LineItem createSessionListItem(CheckoutItemDto checkoutItemDto) {
		return SessionCreateParams.LineItem.builder()
				.setPriceData(createPriceData(checkoutItemDto))
				.setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
				.build();
	}

	private SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
		return SessionCreateParams.LineItem.PriceData.builder()
				.setCurrency("usd")
				.setUnitAmount((long)(checkoutItemDto.getPrice() * 100))
				.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
						.setName(checkoutItemDto.getProductName())
						.build()
						)
				.build();
	}
	
}
