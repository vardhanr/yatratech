package com.yatra.tech.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpMethod;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yatra.tech.client.RestClient;
import com.yatra.tech.dao.SpringTemplateDAO;
import com.yatra.tech.dao.impl.SpringTemplateDaoImpl;
import com.yatra.tech.dto.RestRequest;
import com.yatra.tech.dto.RestResponse;
import com.yatra.tech.entities.YatraAddon;
import com.yatra.tech.service.impl.ABFUserAuthorizationServiceImpl;

public class ApplicationRunner {
	
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//		testSaveReview(context);
//		testBookingJson();
//		testEmailChanges(context);
		
		testAddon(context);
	}
	
	public static void testAddon(ApplicationContext context) {
		ABFUserAuthorizationServiceImpl addOnDao = context.getBean(ABFUserAuthorizationServiceImpl.class);
		YatraAddon addon = addOnDao.getAddon("280287123903", 8l);
		System.out.println(addon);
	}
	
	public static void testSaveReview(ApplicationContext context) {
		RestClient client = context.getBean(RestClient.class);
		String reviewUrl = "http://local.yatra.com/air-pay-book-service/b2bdom/booking/pool/trigger";
		RestRequest<String, String> request = new RestRequest<String, String>(String.class, reviewUrl, HttpMethod.POST);
		Map<String, String> parameterMap = new HashMap<>();
		String superPnr = "69963141";
		String pricingId = "804bbb8c-ea59-424c-85fa-bf8e4a450d63";
		parameterMap.put("superPnr", superPnr);
		parameterMap.put("pricingId", pricingId);
		parameterMap.put("reviewJson", "{\"globalParams\":{\"prq\":\"\",\"ADTcreator\":\"\",\"pricingId\":\"" + pricingId + "\",\"searchId\":\"004ee432-df43-4481-87c1-bfe219c577e8\",\"superPnr\":\"" + superPnr + "\",\"channel\":\"b2b\",\"product\":\"flight\",\"ftype\":\"O\",\"org\":\"DEL\",\"dest\":\"JAI\",\"isPartial\":false,\"ebs_accountId\":\"1026\",\"ebs_sessionId\":\"89e3493afeaa182610c6b542b88b5b7\",\"moProfileType\":\"CASH\",\"childTenant\":\"b2bdom\",\"variation\":0},\"addOnParams\":[],\"hotelCrossSellParams\":{\"isHotelCrosssellBooking\":false,\"hotelBookingRequestJSON\":\"\"},\"productParams\":{\"tripType\":\"O\",\"amountDisp\":\"3104\",\"displayMarkup\":\"0\"},\"promoParams\":{\"status\":false,\"promoCode\":\"\",\"isReadonly\":false,\"amount\":0,\"ecashAmount\":0,\"promoType\":\"\",\"msg\":\"\",\"authCode\":\"\",\"category\":\"\"},\"userParams\":{\"additionalContact\":{\"email\":\"salesdemo@tsi-yatra.com\",\"mobile\":\"1246768100\",\"mobileISD\":\"91\"},\"emailId\":\"salesdemo@tsi-yatra.com\",\"mobileNo\":\"1246768100\",\"userId\":\"21975\",\"title\":\"\",\"firstName\":\"Akash\",\"lastName\":\"Poddar\",\"mobileNoISD\":\"91\"},\"travellerParams\":[{\"paxID\":1,\"travellerDetails\":{\"id\":3928495,\"title\":\"Mr\",\"firstName\":\"raj\",\"middleName\":\"\",\"lastName\":\"kumar\",\"paxClass\":\"Adult\",\"passengerClass\":\"ADT\",\"dateOfBirth\":\"\",\"passport\":{\"nationality\":\"\",\"number\":\"\",\"issuingCountryCode\":\"\",\"issuingCountryName\":\"\",\"expiryDate\":\"\"},\"frequentFlyer\":{}},\"ssrDetails\":{\"ssrMealDetails\":[],\"ssrBaggageDetails\":[],\"ssrSeatDetails\":[],\"ssrOtherDetails\":[]}},{\"paxID\":2,\"travellerDetails\":{\"id\":4107819,\"title\":\"Mr\",\"firstName\":\"Prakash\",\"middleName\":\"\",\"lastName\":\"GV\",\"paxClass\":\"Adult\",\"passengerClass\":\"ADT\",\"dateOfBirth\":\"\",\"passport\":{\"nationality\":\"\",\"number\":\"\",\"issuingCountryCode\":\"\",\"issuingCountryName\":\"\",\"expiryDate\":\"\"},\"frequentFlyer\":{}},\"ssrDetails\":{\"ssrMealDetails\":[],\"ssrBaggageDetails\":[],\"ssrSeatDetails\":[],\"ssrOtherDetails\":[]}}],\"gstDetails\":{},\"discountParams\":{},\"totalBreakup\":{\"meals\":{\"amount\":0,\"label\":\"Meals Charges\"},\"baggage\":{\"amount\":0,\"label\":\"Baggage Charges\"},\"seats\":{\"amount\":0,\"label\":\"Seats Charges\"},\"others\":{\"amount\":0,\"label\":\"Others Charges\"},\"insurance\":{\"amount\":0,\"label\":\"Travel Assistance and Insurance\"},\"beingHuman\":{\"amount\":0,\"label\":\"Being Human Contribution\"},\"markup\":{\"amount\":0,\"label\":\"Markup\"}},\"tourCode\":[{\"tourCode\":\"\",\"airline\":\"6E\",\"sector\":\"DELJAI\"}],\"advancedPricing\":{},\"upSellParams\":[],\"gaResponse\":{\"data\":[{\"id\":\"31506190\",\"name\":\"DOM Flights\",\"category\":\"DOMFlights/Economy/Evening/0/DEL-JAI\",\"brand\":\"Indigo\",\"variant\":\"One Way\",\"price\":\"3104\"}],\"action\":\"event\",\"actionData\":\"checkout\",\"actionObj\":{\"step\":1,\"option\":\"Pay Now : Credit Card - Quickbook\"},\"event\":\"Flights - Payment - DOM|Current Selection|Pay Now\"}}");
		
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("content-length", "5007");
		headerMap.put("accept-language", "en-US,en;q=0.8");
		headerMap.put("cookie", "ssoToken=8b160911-7be4-4252-ae32-80a1cb95fe3f; userName='rajnish test'; userType=B2B_AGENT; SessionVX=6c6c74f1-a153-47b8-8c21-7d1fcad6542d;currencyId=1; lang=en");
		headerMap.put("postman-token", "8cc2f5b7-36b6-dae5-052d-78f5d52c580f");
		headerMap.put("origin", "chrome-extension://aicmkgpgakddgnaphhhpliifpcfhicfo");
		headerMap.put("x-forwarded-for", "192.168.68.201");
		headerMap.put("x-postman-interceptor-id", "8e8287f1-3e6f-6f69-f006-a5d86cae7eca");
		headerMap.put("accept", "*/*");
		headerMap.put("x-forwarded-server", "secure.yatra.com");
		headerMap.put("x-forwarded-host", "dev.yatra.com");
		headerMap.put("host", "dev.yatra.com");
		headerMap.put("content-type", "application/x-www-form-urlencoded");
		headerMap.put("connection", "Keep-Alive");
		headerMap.put("cache-control", "no-cache");
		headerMap.put("accept-encoding", "");
		headerMap.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
		
		request.setParameterMap(parameterMap);
		request.setHeaderMap(headerMap);
		RestResponse<String> response = client.sendRestRequest(request);
		System.out.println(response.getResponseBody());
	}
	
	public static void testBookingJson() {
		String json = "{\"data\":{\"specialFareType\":\"\",\"fltSchedule\":{\"JAIDEL20171210\":[{\"ID\":\"1\",\"org\":\"JAI\",\"dest\":\"DEL\",\"OD\":[{\"FS\":[{\"TourCode\":\"\",\"RetailTourCode\":\"\",\"travelClass\":\"\",\"ac\":\"6E\",\"mac\":\"6E\",\"fl\":\"203\",\"dac\":\"JAI\",\"aac\":\"DEL\",\"ddt\":\"2017-12-10\",\"adt\":\"2017-12-10\",\"dd\":\"10:45\",\"ad\":\"11:50\",\"eq\":\"\",\"du\":\"01:05\",\"gdspnr\":\"\",\"arpnr\":\"UNCONFIRMED\",\"dct\":\"Jaipur\",\"act\":\"New Delhi\",\"cct\":\"IATA\",\"an\":\"Indigo\",\"dap\":\"Sanganeer\",\"aap\":\"Indira Gandhi\",\"mc\":\"6E\",\"dcc\":\"IN\",\"acc\":\"IN\",\"bag\":\"15 kgs\",\"st\":\"Non Stop\",\"ft\":\"Refundable\",\"cabin\":\"Economy\",\"fid\":\"JAIDEL6E20320171210\",\"dt\":\"T-2\",\"at\":\"T-1\"}],\"FareType\":\"NF\"}]}]},\"fltOrder\":{\"1\":\"JAIDEL20171210\"},\"cityNames\":{\"JAI\":\"Jaipur\",\"DEL\":\"New Delhi\"},\"airportNames\":{\"JAI\":\"Sanganeer\",\"DEL\":\"Indira Gandhi\"},\"airlineNames\":{\"6E\":\"Indigo\"},\"ssrStatus\":{\"0\":{\"Meal\":{},\"Baggage\":{},\"Others\":{},\"seats\":{}}},\"fareDetails\":{\"ylp\":\"0\",\"icb\":\"0\",\"JAIDEL20171210\":{\"1\":{\"O\":{\"ADT\":{\"bf\":\"1833\",\"tf\":2946,\"fsc\":\"525\",\"px\":\"ADT\",\"qt\":\"1\",\"YQ\":\"525\",\"GST\":\"0\",\"GAST\":\"122\",\"PSF\":\"153\",\"UDF\":\"413\",\"TF\":\"0\",\"OC\":\"222\",\"YST\":\"0\",\"crmkp\":\"0\",\"OTROnly\":\"100\",\"OTR\":100},\"revisedFareDetails\":{\"ylp\":\"\",\"bf\":\"1833\",\"fsc\":\"525\",\"totcrmkp\":0,\"tf\":2946,\"nf\":2946,\"tsc\":\"0\",\"tds\":\"0\",\"OTROnly\":\"100\",\"OTR\":100,\"tbf\":\"0\",\"sr\":\"0\",\"dmark\":\"0\",\"crfee\":\"0\",\"crdis\":0,\"dismrk\":\"0\",\"mtf\":\"0\",\"af\":\"2358\",\"pta\":\"6E\",\"servicetax\":\"0\",\"commision\":0,\"YQ\":\"525\",\"GST\":\"0\",\"GAST\":\"122\",\"PSF\":\"153\",\"UDF\":\"413\",\"TF\":\"0\",\"OC\":\"222\",\"YSF\":\"630\",\"YST\":\"0\",\"dismark\":0,\"distmark\":\"0.0\",\"gst\":\"0\",\"igst\":\"0\",\"cgst\":\"0\",\"sgst\":\"0\",\"utgst\":\"0\",\"wholeSalerFee\":\"0\",\"wsfIgst\":\"0\",\"wsfCgst\":\"0\",\"wsfSgst\":\"0\",\"wsfUtgst\":\"0\",\"sdistmark\":\"0.0\",\"totalcrdis\":0}}}}},\"airPnrList\":[\"\"],\"gdsPnrList\":[\"\"],\"supplierList\":[\"6EAPI\"],\"airlineCodeList\":[\"6E\"],\"passengerTickets\":[[{\"title\":\"Mr\",\"fname\":\"test\",\"mname\":\"\",\"lname\":\"test\",\"org\":\"\",\"des\":\"\",\"status\":\"FAILED\",\"ticketNo\":\"\"}]],\"wflow\":\"PFB\",\"ftype\":\"O\",\"showSaleAlertFailureMessage\":\"true\",\"isSaleAirline\":\"false\",\"saleAlertFailureHeaderMessage\":\"Your payment has been charged however we regret that we are unable to book your tickets with the airline.\",\"saleAlertFailureDescriptionMessage\":\"We have initiated the refund process and the money will be refunded into the account charged. It would take 4-5 business days for the credit to reflect in your account. We sincerely regret any inconvenience this might have caused you.\",\"saleAlertFailureLinkMessage\":\"Please <a href='http://www.yatra.com' />click here<\\/a> to make a fresh booking.\",\"isApprovalPending\":false},\"bookingStatus\":\"failed\",\"bookingResponseTimedOut\":\"true\",\"bookingDate\":\"10-10-2017\",\"paymentStatus\":\"1\",\"sc\":{\"payProp\":{\"paymentId\":\"a040689b-0930-4dc5-bfb0-62213835bb9f\",\"superPnr\":\"23087659\",\"ttid\":\"1123087659\",\"type\":\"FULL\",\"pricingId\":\"ddeb00e3-31c2-4f30-823a-778ec06868cc\",\"mode\":\"CreditPool\",\"curr\":\"INR\",\"tenantScope\":\"b2bdom\"},\"charges\":{\"displayMarkupChange\":\"0\",\"tfp\":2946,\"nfp\":2946,\"gst\":0,\"serviceFee\":0,\"crpDiscount\":0,\"crpFee\":0,\"pgSurcharge\":0,\"wholeSalerFee\":0,\"wsfCgst\":0,\"wsfSgst\":0,\"wsfIgst\":0,\"wsfUtgst\":0,\"balAmt\":0,\"convFee\":0,\"meal\":0,\"bag\":0,\"seat\":0,\"other\":\"0\",\"ecash\":0,\"thf\":0,\"amtPaid\":2946,\"total\":2946},\"addons\":{}},\"passengerList\":[{\"title\":\"Mr\",\"fname\":\"test\",\"mname\":\"\",\"lname\":\"test\",\"paxid\":65161,\"nomineeName\":\"\",\"tickets\":[],\"reportingDetails\":[],\"paxClass\":\"Adult 0 0\",\"dob\":\"\",\"addons\":{\"seats\":{\"o\":[],\"r\":[]},\"ppt\":{\"no\":\"\",\"ic\":\"\",\"in\":\"\",\"nat\":\"\",\"exp\":{}}}}],\"user\":{\"username\":\"Akash Poddar\",\"email\":\"salesdemo@tsi-yatra.com\",\"isd\":\"91\",\"mobile\":\"1246768100\"},\"webcheckin\":{},\"ewalletinfo\":{\"balance\":0,\"ewalletOff\":\"false\"}}";
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(json).getAsJsonObject();
		JsonElement element = jsonObject.get("bookingStatus");
		String value = element.getAsString();
		System.out.println(value);
		
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
	
	public static void testTicketingData(ApplicationContext context) {
		SpringTemplateDAO templateDAO = context.getBean(SpringTemplateDaoImpl.class);
		templateDAO.testTicketingData();
	}
	
}
