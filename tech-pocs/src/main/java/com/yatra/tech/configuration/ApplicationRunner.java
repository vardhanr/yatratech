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

public class ApplicationRunner {

	public static void main(String[] args) {
//		getSearchResults();
		makeBooking();
//		testTicketingData();
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

		String url = "http://local.yatra.com/air-pay-book-service/dom2/booking/pool/trigger";

		RestRequest<String, String> request = new RestRequest<>(String.class, url, HttpMethod.POST);
		
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Cookie", "ssoToken=fdb51de0-05b1-4846-a542-6a9ba21e3682");
		headerMap.put("Content-Type", "application/x-www-form-urlencoded");
		request.setHeaderMap(headerMap);
		
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("pricingId", "51cf92e7-88b1-4917-8a56-8b0002df93c2");
		parameterMap.put("superPnr", "14066466");
		parameterMap.put("reviewJson", "{\"globalParams\":{\"prq\":\"\",\"ADTcreator\":\"\",\"pricingId\":\"51cf92e7-88b1-4917-8a56-8b0002df93c2\",\"searchId\":\"c9f17da6-d7da-421a-a0b5-f7e491166e02\",\"superPnr\":\"14066466\",\"channel\":\"b2c\",\"product\":\"flight\",\"url\":\"https%3A%2F%2Fsecure.yatra.com%2Fcheckout-ui%2Fdom2%2Fprice%3FsearchId%3Dc9f17da6-d7da-421a-a0b5-f7e491166e02%26msid%3Dc9f17da6-d7da-421a-a0b5-f7e491166e02%26mode%3DBackground%26bpc%3Dfalse%26isSR%3Dfalse%26unique%3D1504265180400%26variation%3D0%26flightIdCSV%3DDELIXJ6E367HB20171210_%26flightPrice%3D5154%26sc%3D6EAPI%26pid%3D51cf92e7-88b1-4917-8a56-8b0002df93c2%26ref%3D14066466\",\"ftype\":\"O\",\"org\":\"DEL\",\"dest\":\"IXJ\",\"changeFlightUrl\":\"https%3A%2F%2Fflight.yatra.com%2Fair-search-ui%2Fdom2%2Ftrigger%3Ftype%3DO%26viewName%3Dnormal%26flexi%3D0%26noOfSegments%3D1%26origin%3DDEL%26originCountry%3DIN%26destination%3DIXJ%26destinationCountry%3DIN%26flight_depart_date%3D10%2F12%2F2017%26ADT%3D1%26CHD%3D1%26INF%3D0%26class%3DEconomy%26unique%3D1504265192355\",\"isPartial\":false,\"ebs_accountId\":\"1026\",\"ebs_sessionId\":\"bbfc62724c942b27396183de2a637e1\",\"rurl\":\"https://secure.yatra.com/checkout-ui/dom2/confirmation\",\"childTenant\":\"dom2\"},\"addOnParams\":[],\"hotelCrossSellParams\":{\"isHotelCrosssellBooking\":false,\"hotelBookingRequestJSON\":\"\"},\"productParams\":{\"tripType\":\"O\",\"amountDisp\":\"5154\",\"displayMarkup\":\"0\"},\"promoParams\":{\"status\":false,\"promoCode\":\"\",\"isReadonly\":false,\"amount\":0,\"ecashAmount\":0,\"promoType\":\"\",\"msg\":\"\",\"authCode\":\"\",\"category\":\"\"},\"userParams\":{\"additionalContact\":{\"email\":\"rajnishyatra@gmail.com\",\"mobile\":\"9717459939\",\"mobileISD\":\"91\"},\"emailId\":\"rajnishyatra@gmail.com\",\"mobileNo\":\"9717459939\",\"userId\":\"undefined\",\"title\":\"\",\"firstName\":\"\",\"lastName\":\"\",\"mobileNoISD\":\"91\"},\"travellerParams\":[{\"paxID\":1,\"travellerDetails\":{\"id\":\"\",\"title\":\"Mr\",\"firstName\":\"test\",\"middleName\":\"\",\"lastName\":\"test\",\"paxClass\":\"Adult\",\"passengerClass\":\"ADT\",\"dateOfBirth\":\"\",\"passport\":{\"nationality\":\"\",\"number\":\"\",\"issuingCountryCode\":\"\",\"issuingCountryName\":\"\",\"expiryDate\":\"\"},\"frequentFlyer\":{}},\"ssrDetails\":{\"ssrMealDetails\":[],\"ssrBaggageDetails\":[],\"ssrSeatDetails\":[],\"ssrOtherDetails\":[]}},{\"paxID\":1,\"travellerDetails\":{\"id\":\"\",\"title\":\"Master\",\"firstName\":\"test\",\"middleName\":\"\",\"lastName\":\"test\",\"paxClass\":\"Child\",\"passengerClass\":\"CHD\",\"dateOfBirth\":\"\",\"passport\":{\"nationality\":\"\",\"number\":\"\",\"issuingCountryCode\":\"\",\"issuingCountryName\":\"\",\"expiryDate\":\"\"},\"frequentFlyer\":{}},\"ssrDetails\":{\"ssrMealDetails\":[],\"ssrBaggageDetails\":[],\"ssrSeatDetails\":[],\"ssrOtherDetails\":[]}}],\"gstDetails\":{},\"discountParams\":{},\"totalBreakup\":{\"meals\":{\"amount\":0,\"label\":\"Meals Charges\"},\"baggage\":{\"amount\":0,\"label\":\"Baggage Charges\"},\"seats\":{\"amount\":0,\"label\":\"Seats Charges\"},\"others\":{\"amount\":0,\"label\":\"Others Charges\"},\"insurance\":{\"amount\":0,\"label\":\"Travel Assistance and Insurance\"},\"beingHuman\":{\"amount\":0,\"label\":\"Being Human Contribution\"},\"markup\":{\"amount\":0,\"label\":\"Markup\"}},\"advancedPricing\":{},\"upSellParams\":[],\"gaResponse\":{\"data\":[{\"id\":\"14066466\",\"name\":\"DOM Flights\",\"category\":\"DOMFlights/Economy/Afternoon/0/DEL-IXJ\",\"brand\":\"Indigo\",\"variant\":\"One Way\",\"price\":\"5154.00\"}],\"action\":\"event\",\"actionData\":\"checkout\",\"actionObj\":{\"step\":1,\"option\":\"Pay Now : Credit Card - Quickbook\"},\"event\":\"Flights - Payment - DOM|Current Selection|Pay Now\"}}");
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
