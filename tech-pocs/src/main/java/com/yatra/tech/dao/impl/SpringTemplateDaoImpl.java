package com.yatra.tech.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.yatra.tech.dao.SpringTemplateDAO;
import com.yatra.tech.dto.ExcelLineItem;
import com.yatra.tech.dto.OtherProducts;
import com.yatra.tech.dto.TicketingQueue;
import com.yatra.tech.entities.ABFUserAuthorization;
import com.yatra.tech.entities.InsuranceProperties;
import com.yatra.tech.entities.MoTransferQueue;
import com.yatra.tech.entities.PaxDetail;
import com.yatra.tech.entities.YatraAddon;
import com.yatra.tech.rowmapper.ABFUserAuthorizationMapper;
import com.yatra.tech.rowmapper.InsurancePropertiesRowMapper;
import com.yatra.tech.rowmapper.PaxDetailRowMapper;
import com.yatra.tech.rowmapper.TicketingQueueMapper;
import com.yatra.tech.rowmapper.YatraAddonRowMapper;
import com.yatra.tech.rowmapper.YatraBookingXmlRowMapper;
import com.yatra.tech.service.ExcelConverterService;
import com.yatra.tech.service.impl.XPathReader;
import com.yatra.tech.utils.TechUtils;

@Repository("springTemplateDao")
public class SpringTemplateDaoImpl implements SpringTemplateDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ExcelConverterService excelConverterService;
	@Autowired 
	private VelocityEngine velocityEngine;

	@Override
	public List<ABFUserAuthorization> findAllUserAuthorization() {
		String sql = "select * from abf_user_authorization";
		return this.jdbcTemplate.query(sql, new ABFUserAuthorizationMapper());
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void testTicketingData() {

		List<String> supplierCodes = new ArrayList<String>();
		supplierCodes.add("1G");
		supplierCodes.add("GALDOM");

		String sql = "select * from ticketing_queue t where t.created_on>? and t.created_on<? and t.`status` in "
				+ "('FAILED_EMPTYRESPONSE', 'FAILED_NOTICKETS', 'FAILED_PARTIALTICKETS', 'FAILED') and t.attempts<3;";
		List<TicketingQueue> list = this.jdbcTemplate.query(sql, new Object[] { "2017-08-31 18:30:41.482", "2017-08-31 20:28:41.482" },
				new TicketingQueueMapper());
		List<TicketingQueue> newList = new ArrayList<>();

		System.out.println("Size before filtering : " + list.size());
		for (TicketingQueue ticketingQueue : list) {
			String suppCodes = ticketingQueue.getSupplierCodes();
			System.out.println(suppCodes);
			if (StringUtils.isNotEmpty(suppCodes)) {
				List<String> supCodesList = Arrays.asList(suppCodes.split(","));

				List<String> intersection = TechUtils.intersection(supplierCodes, supCodesList);
				if (intersection == null || intersection.isEmpty()) {
					newList.add(ticketingQueue);
				}
			}
		}

		System.out.println("Size after Filtering : " + newList.size());
	}

	public List<ExcelLineItem> fetchInsuranceData(String start, String end) {
		List<ExcelLineItem> items = new ArrayList<>();
		String query = "select * from user_addon us where us.tenant_id in (17,18) and addon_title='Travel Assistance and Insurance' and us.created_on>'" + start
				+ "' and us.created_on<'" + end + "'";
		List<YatraAddon> addons = this.jdbcTemplate.query(query, new YatraAddonRowMapper());
		Map<String, Object> dynamicCols = null;
		ExcelLineItem item = null;
		StringBuilder builder = new StringBuilder("");

		for (YatraAddon yatraAddon : addons) {
			builder.append("'");
			builder.append(yatraAddon.getTtId());
			builder.append("',");
		}
		String ttids = builder.toString();
		ttids = ttids.substring(0, ttids.lastIndexOf(","));
		query = "select * from pax_detail where ttid in (" + ttids + ")";

		List<PaxDetail> details = this.jdbcTemplate.query(query, new PaxDetailRowMapper());
		Map<String, PaxDetail> map = new HashMap<>();
		for (PaxDetail paxDetail : details) {
			if (map.get(paxDetail.getTtid()) == null) {
				if (StringUtils.isEmpty(paxDetail.getPaxInsured()) || "0".equals(paxDetail.getPaxInsured())) {
					map.put(paxDetail.getTtid(), paxDetail);
				}
			}
		}

		for (Map.Entry<String, PaxDetail> entry : map.entrySet()) {
			item = new ExcelLineItem();
			dynamicCols = new HashMap<>();
			dynamicCols.put("Super PNR", entry.getValue().getSuperPnr());
			dynamicCols.put("ttid", entry.getValue().getTtid());
			dynamicCols.put("Date", start);
			item.setDynamicCols(dynamicCols);
			items.add(item);
		}

		return items;
	}

	public void prepareInsuranceXmls() {
		File file = new File("data by Supr PNR.xlsx");
		List<ExcelLineItem> items = this.excelConverterService.readExcelData(ExcelLineItem.class, file, "Sheet1");
		Map<String, ExcelLineItem> superPnrMap = new HashMap<>();
		Map<String, String> pnrTtidMap = new HashMap<>();
		StringBuilder builder = new StringBuilder();
		StringBuilder superPNRBuilder = new StringBuilder();

		String query = "select ttid from payment_detail where super_pnr=? order by created_on desc limit 1";

		for (ExcelLineItem excelLineItem : items) {
			String superPnr = (String) excelLineItem.getDynamicCols().get("Supr PNR");
			superPnrMap.put(superPnr, excelLineItem);

			String ttid = this.jdbcTemplate.queryForObject(query, String.class, new Object[] { superPnr });
			pnrTtidMap.put(superPnr, ttid);
			builder.append("'");
			builder.append(ttid);
			builder.append("',");

			superPNRBuilder.append("'");
			superPNRBuilder.append(superPnr);
			superPNRBuilder.append("',");
		}

		String ttids = builder.toString();
		ttids = ttids.substring(0, ttids.lastIndexOf(","));

		String superPnrs = superPNRBuilder.toString();
		superPnrs = superPnrs.substring(0, superPnrs.lastIndexOf(","));

		query = "select * from pax_detail where ttid in (" + ttids + ")";

		List<PaxDetail> details = this.jdbcTemplate.query(query, new PaxDetailRowMapper());

		Map<String, List<PaxDetail>> paxMap = new HashMap<>();

		for (PaxDetail paxDetail : details) {
			String superPnr = paxDetail.getSuperPnr();
			List<PaxDetail> properties = paxMap.get(superPnr);
			if (properties == null) {
				properties = new ArrayList<>();
			}
			properties.add(paxDetail);
			paxMap.put(paxDetail.getSuperPnr(), properties);
		}

		query = "select * from insurance_properties where super_pnr in (" + superPnrs + ")";
		List<InsuranceProperties> insuranceProperties = jdbcTemplate.query(query, new InsurancePropertiesRowMapper());

		Map<String, List<InsuranceProperties>> insuranceMap = new HashMap<>();

		for (InsuranceProperties insurance : insuranceProperties) {
			String superPnr = insurance.getSuperPnr();
			List<InsuranceProperties> properties = insuranceMap.get(superPnr);
			if (properties == null) {
				properties = new ArrayList<>();
			}
			properties.add(insurance);
			insuranceMap.put(insurance.getSuperPnr(), properties);
		}

		query = "select mo_xml from mo_transfer_queue_06mar2018 where super_pnr in (" + superPnrs + ")";
		List<MoTransferQueue> bookingXmls = jdbcTemplate.query(query, new YatraBookingXmlRowMapper());

		Map<String, MoTransferQueue> moMap = new HashMap<>();
		for (MoTransferQueue mo : bookingXmls) {
			moMap.put(mo.getSuperPnr(), mo);
		}

		for (Map.Entry<String, String> entry : pnrTtidMap.entrySet()) {
			try {
				String superPnr = entry.getKey();
				String ttid = entry.getValue();

				List<PaxDetail> pax = paxMap.get(superPnr);
				List<InsuranceProperties> properties = insuranceMap.get(superPnr);
				List<OtherProducts> products = new ArrayList<>();

				for (PaxDetail paxDetail : pax) {
					OtherProducts otherProduct = new OtherProducts();
					products.add(otherProduct);
				}

				MoTransferQueue mo = moMap.get(superPnr);
				String bookingXml = mo.getMoXml();

				XPathReader xPathReader = new XPathReader(new ByteArrayInputStream(bookingXml.getBytes()));
				String expression = "BookingXML/Master";
				String masterDetails = xPathReader.getNodeListAsString(expression);

				expression = "BookingXML/DispatchDetails";
				String dispatchDetails = xPathReader.getNodeListAsString(expression);

				expression = "BookingXML/ClientDetails";
				String clientDetails = xPathReader.getNodeListAsString(expression);

				expression = "BookingXML/BillingDetails";
				String billingDetails = xPathReader.getNodeListAsString(expression);

				expression = "BookingXML/DeliveryDetails";
				String deliveryDetails = xPathReader.getNodeListAsString(expression);

				expression = "BookingXML/BookingDetails";
				String bookingDetails = xPathReader.getNodeListAsString(expression);

				expression = "BookingXML/PassengerInfo";
				String passengerDetails = xPathReader.getNodeListAsString(expression);
				
				Map<String, Object> context = new HashMap<>();
				context.put("masterDetails", masterDetails);
				context.put("dispatchDetails", dispatchDetails);
				context.put("clientDetails", clientDetails);
				context.put("billingDetails", billingDetails);
				context.put("deliveryDetails", deliveryDetails);
				context.put("bookingDetails", bookingDetails);
				context.put("PassengerInfo", passengerDetails);
				String html = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, "template/base-trip.vm", "UTF-8", context);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*public List<ExcelLineItem> fetchInsuranceData2(String start, String end) {
		List<ExcelLineItem> items = new ArrayList<>();
		String query = "select * from user_addon us where us.tenant_id in (17,18) and addon_title='Travel Assistance and Insurance' and us.created_on>'" + start +"' and us.created_on<'" + end +"'";
		List<YatraAddon> addons = this.jdbcTemplate.query(query, new YatraAddonRowMapper());
		Map<String, Object> dynamicCols = null;
		ExcelLineItem item = null;
		StringBuilder builder = new StringBuilder("");
		
		for (YatraAddon yatraAddon : addons) {
			builder.append("'");
			builder.append(yatraAddon.getTtId());
			builder.append("',");
		}
		String ttids = builder.toString();
		ttids = ttids.substring(0, ttids.lastIndexOf(","));
		query = "select * from pax_detail where ttid in (" + ttids + ")";
			
		List<PaxDetail> details = this.jdbcTemplate.query(query, new PaxDetailRowMapper());
		Map<String, PaxDetail> map = new HashMap<>();
		for (PaxDetail paxDetail : details) {
			if (map.get(paxDetail.getTtid()) ==  null) {
				if (StringUtils.isEmpty(paxDetail.getPaxInsured()) || "0".equals(paxDetail.getPaxInsured())) {
					map.put(paxDetail.getTtid(), paxDetail);
				}
			}
		}
		
		
		for (Map.Entry<String, PaxDetail> entry : map.entrySet()) {
		
			String superPnr =  entry.getValue().getSuperPnr();
			String insurancePriceJson="";
			String insuranceNumberJson="";
			Timestamp productDate=null;
			int paxCount=0;
			
			query="select * from pax_detail where super_pnr='" + superPnr +"'";
			List<PaxDetail> paxDetails = jdbcTemplate.query(query,new PaxDetailRowMapper());
			paxCount=paxDetails.size();
			
			query = "select * from insurance_properties where super_pnr='" + superPnr +"'";
		    List<InsuranceProperties> insuranceProperties = jdbcTemplate.query(query,new InsurancePropertiesRowMapper());
		    for (InsuranceProperties insuranceProperty : insuranceProperties) {
		    	if(insuranceProperty.getPropertyName().equalsIgnoreCase("getOfferResults")) {
		    		insurancePriceJson= insuranceProperty.getPropertyValue();
		    	} else if( insuranceProperty.getPropertyName().equalsIgnoreCase("closeSessionResponse")) {
		    		insuranceNumberJson= insuranceProperty.getPropertyValue();
		    	}
		    	 productDate=insuranceProperty.getCreatedOn();
		    }
		    
		    
		    JsonObject insurancePriceJsonObj = new Gson().fromJson(insurancePriceJson, JsonObject.class);
		    JsonElement insurancePrice=insurancePriceJsonObj.get("price");
		    int insureGrossPrice = insurancePrice.getAsInt();
		    int insureNetPrice = insureGrossPrice/paxCount;
		    
		    
		    
		    
		    JsonObject insuranceIdJson = new Gson().fromJson(insuranceNumberJson, JsonObject.class);
		    JsonElement insuranceNumber=insuranceIdJson.get("policyNumber");
		    String insureId = insuranceNumber.getAsString(); // traverse the json list of insurance ids and make a list
		    
		
			String bookingXml="";
			 query = "select mo_xml from mo_transfer_queue_06mar2018 where super_pnr='" + superPnr +"'";
		    List<MoTransferQueue> bookingXmls = jdbcTemplate.query(query,new YatraBookingXmlRowMapper());
		    if(bookingXmls.size()>0){
		    bookingXml=bookingXmls.get(0).getMoXml();
		    try {
				xPathReader xPathReader = new XPathReader(new ByteArrayInputStream(bookingXml.getBytes()));
				String expression = "BookingXML/Master";
				String masterDetails = xPathReader.getNodeListAsString(expression);	
				
				 expression = "BookingXML/DispatchDetails";
				String dispatchDetails = xPathReader.getNodeListAsString(expression);	
				
				expression = "BookingXML/ClientDetails";
				String clientDetails = xPathReader.getNodeListAsString(expression);	
				
				 expression = "BookingXML/BillingDetails";
				String billingDetails = xPathReader.getNodeListAsString(expression);	
				
				 expression = "BookingXML/DeliveryDetails";
				String deliveryDetails = xPathReader.getNodeListAsString(expression);	
				
				 expression = "BookingXML/BookingDetails";
				String bookingDetails = xPathReader.getNodeListAsString(expression);	
				
	             expression = "BookingXML/PassengerInfo";
				 String passengerDetails = xPathReader.getNodeListAsString(expression);	
				
				 String paxName = entry.getValue().getPaxFirstName()+ entry.getValue().getPaxMiddleName()+ entry.getValue().getPaxLastName();
				 
				 Template velocityTemplate = velocityEngine.getTemplate("/velocity/mo-transfer.vm");
					VelocityContext context = new VelocityContext();
					
					context.put("masterDetails", masterDetails);
					context.put("dispatchDetails", dispatchDetails);
					context.put("clientDetails", clientDetails);
					context.put("billingDetails", billingDetails);
					context.put("deliveryDetails", deliveryDetails);
					context.put("bookingDetails", bookingDetails);
					context.put("PassengerInfo", passengerDetails);
					context.put("productName", "Insurance");
					context.put("productSupplier", "Traveltag Insurance");
					context.put("productContactNumber", "");
					context.put("productContactName", paxName);
					context.put("productEmail", "");
					context.put("insuranceGrossPrice", insureGrossPrice);
					context.put("insuranceId", insureId);
					context.put("productDate", productDate);
					context.put("insuranceNetPrice",insureNetPrice);
					
					
					
				    StringWriter writer = new StringWriter();
			        velocityTemplate.merge(context, writer);
			        String finalBookingXml= writer.toString();
		    
		    
			
			
			for (Map.Entry<String, PaxDetail> entry : map.entrySet()) {
			item = new ExcelLineItem();
			dynamicCols = new HashMap<>();
			dynamicCols.put("Super PNR", entry.getValue().getSuperPnr());
			dynamicCols.put("ttid", entry.getValue().getTtid());
			dynamicCols.put("Date", start);
			item.setDynamicCols(dynamicCols);
			items.add(item);
		}
		
		return items;
	}*/

	@Override
	public void getTenantSearchResult() throws Exception {
		try {
			/*String[] domSuppliers= {"6EAPI","SGAPI","G8API","GALDOM","AASAPIDOM","1AWS4"};
			int[] domTenants= {17,30,32,200,201};*/
			ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(500);
			String[] intSuppliers = { "6EAPI", "SGAPI", "1AWSINT4", "AASAPIINT", "AIEXINT", "FLYDUBAI", "1G", "GALUAPI", "W5API", "MALINDO" };
			int[] intTenants = { 18, 31, 34, 203, 204, 205 };
			String startingDate = "2018-02-16";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, List<Future<Integer>>> futures = new LinkedHashMap<>();
			for (int k = 0; k < intTenants.length; k++) {
				for (int i = 0; i < 10; i++) {
					String requiredDate = getRequiredDate(dateFormat, startingDate, i);
					String oneDayAhead = getRequiredDate(dateFormat, startingDate, i + 1);
					StringBuilder result = new StringBuilder();
					result.append(requiredDate + "|" + intTenants[k] + "|");
					List<Future<Integer>> futureList = new ArrayList<>();
					for (int j = 0; j < intSuppliers.length; j++) {
						String sql = "select count(*) from supplier_response_data s where s.created_on>'" + requiredDate + "' and s.created_on<'" + oneDayAhead
								+ "' and s.supplier_code='" + intSuppliers[j] + "' and s.tenant_id=" + intTenants[k] + "";
						final String query1 = sql;

						Callable<Integer> task = () -> {
							return this.jdbcTemplate.queryForObject(query1, Integer.class);
						};

						Future<Integer> future = threadPool.submit(task);
						futureList.add(future);

						sql = "select count(*) from supplier_response_data s where s.created_on>'" + requiredDate + "' and s.created_on<'" + oneDayAhead
								+ "' and s.supplier_code='" + intSuppliers[j] + "' and s.tenant_id=" + intTenants[k] + " and s.is_error=1";
						final String query2 = sql;
						Callable<Integer> task1 = () -> {
							return this.jdbcTemplate.queryForObject(query2, Integer.class);
						};

						Future<Integer> future1 = threadPool.submit(task1);
						futureList.add(future1);
						result.append(intSuppliers[j]);

					}
					futures.put(result.toString(), futureList);
				}
			}

			for (Map.Entry<String, List<Future<Integer>>> entry : futures.entrySet()) {
				List<Future<Integer>> value = entry.getValue();
				String key = entry.getKey();
				int totalValue = value.get(0).get();
				int errorValue = value.get(0).get();
				System.out.println(key + "|" + totalValue + "|" + errorValue + "|");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Throwable cause = e.getCause();
			System.out.format("Invocation of %s failed because of: %s%n", cause.getMessage());
		}

	}

	public String getRequiredDate(SimpleDateFormat dateFormat, String startdate, int daysToIncriment) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(startdate));
		calendar.add(Calendar.DATE, daysToIncriment);
		return dateFormat.format(calendar.getTime());
	}
}
