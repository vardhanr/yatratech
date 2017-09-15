package com.yatra.tech.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpMethod;

import com.yatra.tech.client.RestClient;
import com.yatra.tech.dao.SpringTemplateDAO;
import com.yatra.tech.dao.impl.SpringTemplateDaoImpl;
import com.yatra.tech.dto.RestRequest;
import com.yatra.tech.dto.RestResponse;
import com.yatra.tech.service.impl.UserProfileServiceImpl;

public class ApplicationRunner {
	
	public static void main(String[] args) {
//		getSearchResults();
//		makeBooking();
		new UserProfileServiceImpl().getUserProfile("a26e7ed9-4420-4863-bac3-9495e6b84157");
	}

	public static void testRest() {
		RestClient client = new RestClient();

		String url = "http://local.yatra.com/air-pay-book-service/dom2/payment/validatePayment";
		RestRequest<String, String> request = new RestRequest<>(String.class, url, HttpMethod.POST);
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("superPnr", "11596333");
		parameterMap.put("paymentOption", "mw");
		request.setParameterMap(parameterMap);
		RestResponse<String> response = client.sendRestRequest(request);
		System.out.println(response.getResponseBody());
	}

	public static void getSearchResults() {
		String url = "https://flight.yatra.com/air-service/b2bdom/search?type=O&viewName=normal&flexi=0&noOfSegments=1&origin=DEL&originCountry=IN&destination=BOM&destinationCountry=IN&flight_depart_date=30/11/2017&ADT=1&CHD=0&INF=0&class=Economy&hb=0&source=fresco-home&booking-type=official";
		RestClient client = new RestClient();

		RestRequest<String, String> request = new RestRequest<>(String.class, url, HttpMethod.GET);
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Cookie", "ssoToken=12ec18de-83cc-4930-9b2c-229015789aa6");
		request.setHeaderMap(headerMap);
		RestResponse<String> response = client.sendRestRequest(request);
		System.out.println(response.getResponseBody());
	}
	
	public static void makeBooking() {
		RestClient client = new RestClient();

		String url = "http://local.yatra.com/air-pay-book-service/b2bdom/booking/pool/trigger";

		RestRequest<String, String> request = new RestRequest<>(String.class, url, HttpMethod.POST);
		
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Cookie", "ssoToken=6d72e326-727f-413d-aca8-18bdea234d8e");
		headerMap.put("Content-Type", "application/x-www-form-urlencoded");
		request.setHeaderMap(headerMap);
		
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("pricingId", "365cc5b4-4102-4a7d-93b8-e66fc826df18");
		parameterMap.put("superPnr", "3108791198737");
		parameterMap.put("reviewJson", "{\"globalParams\":{\"prq\":\"\",\"ADTcreator\":\"\",\"pricingId\":\"365cc5b4-4102-4a7d-93b8-e66fc826df18\",\"searchId\":\"51c0e4c9-55a4-4eb8-81a8-253c08b93d4e\",\"superPnr\":\"3108791198737\",\"channel\":\"b2c\",\"product\":\"flight\",\"url\":\"http%3A%2F%2Flocal.yatra.com%2Fcheckout-ui%2Fdom2%2Fprice%3FsearchId%3D51c0e4c9-55a4-4eb8-81a8-253c08b93d4e%26msid%3D51c0e4c9-55a4-4eb8-81a8-253c08b93d4e%26mode%3DBackground%26bpc%3Dfalse%26isSR%3Dfalse%26unique%3D1504172345160%26variation%3D0%26flightIdCSV%3DDELBOMG853020171219_G8API%26flightPrice%3D2220%26sc%3DG8API%26pid%3D365cc5b4-4102-4a7d-93b8-e66fc826df18%26ref%3D3108791198737%26cf%3D0\",\"ftype\":\"O\",\"org\":\"DEL\",\"dest\":\"BOM\",\"changeFlightUrl\":\"http%3A%2F%2Flocal.yatra.com%2Fair-search-ui%2Fdom2%2Ftrigger%3Ftype%3DO%26viewName%3Dnormal%26flexi%3D0%26noOfSegments%3D1%26origin%3DDEL%26originCountry%3DIN%26destination%3DBOM%26destinationCountry%3DIN%26flight_depart_date%3D19%2F12%2F2017%26ADT%3D1%26CHD%3D0%26INF%3D0%26class%3DEconomy%26unique%3D1504172359265\",\"isPartial\":false,\"ebs_accountId\":\"1026\",\"ebs_sessionId\":\"93afda9cece0526f9b412b39d8790c8\",\"rurl\":\"https://secure.yatra.com/checkout-ui/dom2/confirmation\",\"childTenant\":\"dom2\"},\"addOnParams\":[],\"hotelCrossSellParams\":{\"isHotelCrosssellBooking\":false,\"hotelBookingRequestJSON\":\"\"},\"productParams\":{\"tripType\":\"O\",\"amountDisp\":\"2220\",\"displayMarkup\":\"0\"},\"promoParams\":{\"status\":false,\"promoCode\":\"\",\"isReadonly\":false,\"amount\":0,\"ecashAmount\":0,\"promoType\":\"\",\"msg\":\"\",\"authCode\":\"\",\"category\":\"\"},\"userParams\":{\"additionalContact\":{\"email\":\"rajnish.yatra@gmail.com\",\"mobile\":\"9717459939\",\"mobileISD\":\"91\"},\"emailId\":\"rajnish.yatra@gmail.com\",\"mobileNo\":\"9717459939\",\"userId\":\"undefined\",\"title\":\"\",\"firstName\":\"\",\"lastName\":\"\",\"mobileNoISD\":\"91\"},\"travellerParams\":[{\"paxID\":1,\"travellerDetails\":{\"id\":\"\",\"title\":\"Mrs\",\"firstName\":\"test\",\"middleName\":\"\",\"lastName\":\"test\",\"paxClass\":\"Adult\",\"passengerClass\":\"ADT\",\"dateOfBirth\":\"\",\"passport\":{\"nationality\":\"\",\"number\":\"\",\"issuingCountryCode\":\"\",\"issuingCountryName\":\"\",\"expiryDate\":\"\"},\"frequentFlyer\":{}},\"ssrDetails\":{\"ssrMealDetails\":[],\"ssrBaggageDetails\":[],\"ssrSeatDetails\":[],\"ssrOtherDetails\":[]}}],\"gstDetails\":{},\"discountParams\":{},\"totalBreakup\":{\"meals\":{\"amount\":0,\"label\":\"Meals Charges\"},\"baggage\":{\"amount\":0,\"label\":\"Baggage Charges\"},\"seats\":{\"amount\":0,\"label\":\"Seats Charges\"},\"others\":{\"amount\":0,\"label\":\"Others Charges\"},\"insurance\":{\"amount\":0,\"label\":\"Travel Assistance and Insurance\"},\"beingHuman\":{\"amount\":0,\"label\":\"Being Human Contribution\"},\"markup\":{\"amount\":0,\"label\":\"Markup\"}},\"advancedPricing\":{},\"upSellParams\":[{\"type\":\"regular\",\"id\":\"DELBOM20171219\",\"rph\":\"DELBOMG853020171219\",\"sequence\":\"1\"}],\"gaResponse\":{\"data\":[{\"id\":\"3108791198737\",\"name\":\"DOM Flights\",\"category\":\"DOMFlights/Economy/Morning/0/DEL-BOM\",\"brand\":\"Go Air\",\"variant\":\"One Way\",\"price\":\"2220.00\"}],\"action\":\"event\",\"actionData\":\"checkout\",\"actionObj\":{\"step\":1,\"option\":\"Pay Now : Credit Card - Quickbook\"},\"event\":\"Flights - Payment - DOM|Current Selection|Pay Now\"}}");
		request.setParameterMap(parameterMap);
		
		RestResponse<String> response = client.sendRestRequest(request);
		System.out.println(response.getResponseBody());
	}
	
	public static void testTicketingData() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		SpringTemplateDAO templateDAO = context.getBean(SpringTemplateDaoImpl.class);
		templateDAO.testTicketingData();
	}
	
}
