package com.yatra.tech.dao;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.pdfcrowd.Pdfcrowd;
import com.yatra.tech.client.RestClient;
import com.yatra.tech.config.BaseTestConfig;
import com.yatra.tech.dto.ExcelLineItem;
import com.yatra.tech.dto.RestRequest;
import com.yatra.tech.dto.RestResponse;
import com.yatra.tech.entities.ABFUserAuthorization;
import com.yatra.tech.entities.ABFUserLog;
import com.yatra.tech.entities.TicketingPdf;
import com.yatra.tech.entities.YatraAddon;
import com.yatra.tech.service.ABFUserAuthorizationService;
import com.yatra.tech.service.ABFUserLogService;
import com.yatra.tech.service.ExcelConverterService;
import com.yatra.tech.service.TechDaoService;
import com.yatra.tech.service.TransactionService;
import com.yatra.tech.utils.ZipUtils;

import net.sf.json.JSONObject;

public class GeneralAppTest extends BaseTestConfig {

	private static Logger logger = Logger.getLogger(GeneralAppTest.class);

	@Autowired
	private ABFUserAuthorizationService abfUserAuthorizationService;
	@Autowired
	private ABFUserLogService abfUserLogService;
	@Autowired
	private ExcelConverterService excelConverterService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private SpringTemplateDAO springTemplateDao;
	@Autowired
	private RestClient restClient;
	@Autowired 
	private VelocityEngine velocityEngine;
	@Autowired
	private TechDaoService techDaoService;

	@Test
	public void testABFDao() {
		YatraAddon list = this.abfUserAuthorizationService.getAddon("11280287123903", 8l);
		System.out.println(list);
		Assert.assertNotNull(list);
	}

	@Test
	public void testABFLogDao() {
		List<ABFUserLog> list = this.abfUserLogService.findAll();
		System.out.println(list);
		Assert.assertEquals(list.isEmpty(), true);
	}

	@Test
	public void testSpringTemplateDao() {
		List<ABFUserAuthorization> list = this.springTemplateDao.findAllUserAuthorization();
		System.out.println(list);
		Assert.assertNotNull(list);
	}

	@Test
	public void testExcelParsing() {
		File file = new File(ClassLoader.getSystemResource("test-data/airfailed-timestamp-analysis.xlsx").getFile());
		List<ExcelLineItem> lineItems = this.excelConverterService.readExcelData(ExcelLineItem.class, file, "booking-data");
		System.out.println(lineItems);
		Assert.assertNotNull(lineItems);
	}

	@Test
	public void testTransaction() throws Exception {
		Map<String, Object> map = new HashMap<>();
		ABFUserAuthorization authorization = new ABFUserAuthorization();
		String userId = "rahul";
		authorization.setUserId(userId);
		authorization.setAuthorization("REVIEW");
		map.put("authorization", authorization);

		ABFUserLog userLog = new ABFUserLog();
		String superPnr = "938475";
		userLog.setIdAddress("192.168.61.21");
		userLog.setPricingId("92834798347");
		userLog.setSearchId("93845798345");
		userLog.setSsoToken("9845798374585");
		userLog.setStage("REVIEW");
		userLog.setSuperPnr(superPnr);
		userLog.setTenant("dom2");
		userLog.setTtid("0498598437");
		userLog.setUserId("rahul");
		map.put("userLog", userLog);
		try {
			this.transactionService.saveTransactionData(map);
		} catch (Exception e) {
			logger.error("Exception occured while saving data");
		}

		authorization = this.abfUserAuthorizationService.findByUserId(userId);
		Assert.assertNull(authorization);

		userLog = this.abfUserLogService.findBySuperPnr(superPnr);
		Assert.assertNull(userLog);
	}

	@Test
	public void testRestClient() {
		RestRequest<String, String> request = new RestRequest<>();
		RestResponse<String> response = this.restClient.sendRestRequest(request);
		Assert.assertEquals(response.getStatus(), "FAILED");
	}

	@Test
	public void testHibernateVersioning() {
		ABFUserLog log = this.abfUserLogService.findById(63l);
		System.out.println(log);
		log.setPricingId("85798435");
		this.abfUserLogService.saveOrUpdate(log);
	}

	@Test
	public void fetchInsuranceData() {
		String[] dates = { "2018-01-25", "2018-01-26", "2018-01-27", "2018-01-28", "2018-01-29", "2018-01-30", "2018-01-31", "2018-02-01", "2018-02-02" };
		List<ExcelLineItem> items = new ArrayList<>();
		for (int i = 0; i < dates.length; i++) {
			if (i < dates.length - 1) {
				String start = dates[i];
				String end = dates[i+1];
				List<ExcelLineItem> itemsList = this.springTemplateDao.fetchInsuranceData(start, end);
				items.addAll(itemsList);
				System.out.println("Date from " + start + " to " + end + " size is : " + itemsList.size());
			}
		}
		System.out.println(items.size());
		this.excelConverterService.writeExcelData(ExcelLineItem.class, "insurance-data.xlsx", "Sheet1", items);
	}
	
	@Test
	public void testRebookTemplate() throws Exception {
		String json = "{\"basic\":{\"name\":{\"sanitized\":\"france\",\"common\":\"France\",\"official\":\"French Republic\",\"native\":[{\"language\":\"fra\",\"type\":\"OFFICIAL\",\"value\":\"France\"}]},\"code\":{\"alpha2\":\"FR\",\"alpha3\":\"FRA\",\"numeric\":\"250\"},\"languages\":[{\"name\":\"French\",\"type\":\"OFFICIAL\",\"code\":\"fra\"}],\"location\":{\"region\":\"Europe\",\"subregion\":\"Western Europe\",\"geoJson\":\"https://assets.thebasetrip.com/api/v2/countries/maps/france.geo.json\"},\"capital\":{\"name\":\"Paris\",\"coordinates\":{\"latitude\":48.856614,\"longitude\":2.3522219},\"timeZone\":\"Europe/Paris\"},\"ituRegion\":1,\"timeZone\":{\"minUtcTimeOffset\":60,\"maxUtcTimeOffset\":60,\"minUtcTimeDstOffset\":120,\"maxUtcTimeDstOffset\":120},\"unions\":[\"EUROPEAN_UNION\",\"EUROPEAN_ECONOMIC_AREA\",\"SCHENGEN_AREA\"],\"flag\":{\"png\":\"https://assets.thebasetrip.com/api/v2/countries/flags/france.png\",\"svg\":\"https://assets.thebasetrip.com/api/v2/countries/flags/france.svg\",\"emoji\":\"🇫🇷\"},\"isSovereignState\":true,\"measurementSystem\":\"METRIC_SYSTEM\",\"wikipediaUrl\":\"https://en.wikipedia.org/wiki/France\",\"textual\":{\"title\":\"France\",\"sections\":[{\"id\":\"LOCATION\",\"body\":[\"France is located in Western Europe.\"]},{\"id\":\"SUMMARY\",\"body\":[\"Official language is French. France uses metric system (kilograms, centimeters, °C). Time zone is UTC+01:00 with daylight saving time.\"]}]}},\"currency\":{\"name\":{\"short\":\"euro\",\"full\":\"euro\"},\"code\":\"EUR\",\"symbol\":\"€\",\"subunit\":{\"name\":\"Cent\",\"ratio\":100},\"denominations\":[{\"type\":\"COIN\",\"value\":1,\"displayAs\":\"1\",\"isSubunit\":true},{\"type\":\"COIN\",\"value\":2,\"displayAs\":\"2\",\"isSubunit\":true},{\"type\":\"COIN\",\"value\":5,\"displayAs\":\"5\",\"isSubunit\":true},{\"type\":\"COIN\",\"value\":10,\"displayAs\":\"10\",\"isSubunit\":true},{\"type\":\"COIN\",\"value\":20,\"displayAs\":\"20\",\"isSubunit\":true},{\"type\":\"COIN\",\"value\":50,\"displayAs\":\"50\",\"isSubunit\":true},{\"type\":\"COIN\",\"value\":100,\"displayAs\":\"1\",\"isSubunit\":false},{\"type\":\"COIN\",\"value\":200,\"displayAs\":\"2\",\"isSubunit\":false},{\"type\":\"BANKNOTE\",\"value\":500,\"displayAs\":\"5\",\"isSubunit\":false},{\"type\":\"BANKNOTE\",\"value\":1000,\"displayAs\":\"10\",\"isSubunit\":false},{\"type\":\"BANKNOTE\",\"value\":2000,\"displayAs\":\"20\",\"isSubunit\":false},{\"type\":\"BANKNOTE\",\"value\":5000,\"displayAs\":\"50\",\"isSubunit\":false},{\"type\":\"BANKNOTE\",\"value\":10000,\"displayAs\":\"100\",\"isSubunit\":false},{\"type\":\"BANKNOTE\",\"value\":20000,\"displayAs\":\"200\",\"isSubunit\":false},{\"type\":\"BANKNOTE\",\"value\":50000,\"displayAs\":\"500\",\"isSubunit\":false}],\"textual\":{\"title\":\"Currency\",\"sections\":[{\"id\":\"SUMMARY\",\"body\":[\"France uses the euro (symbol: €), code EUR.\"]},{\"id\":\"DENOMINATIONS\",\"body\":[\"Banknotes come in denominations of 500, 200, 100, 50, 20, 10 and 5 euro. Euro is subdivided into 100 cents. Coins come in denominations of 2 and 1 euros and 50, 20, 10, 5, 2 and 1 cents.\"]}]}},\"electricity\":{\"frequencies\":[50],\"voltages\":[230],\"sockets\":[{\"type\":\"C\",\"image\":{\"png\":\"https://assets.thebasetrip.com/api/v2/sockets/C.png\",\"svg\":\"https://assets.thebasetrip.com/api/v2/sockets/C.svg\"}},{\"type\":\"E\",\"image\":{\"png\":\"https://assets.thebasetrip.com/api/v2/sockets/E.png\",\"svg\":\"https://assets.thebasetrip.com/api/v2/sockets/E.svg\"}}],\"compatibility\":{\"frequency\":\"FULL\",\"voltage\":\"FULL\",\"sockets\":\"FULL\",\"converter\":\"NOT_NEEDED\"},\"textual\":{\"title\":\"Sockets & plugs\",\"sections\":[{\"id\":\"SUMMARY\",\"body\":[\"France uses 230V, 50Hz with sockets and plugs Type C and Type E.\"]},{\"id\":\"COMPATIBILITY\",\"body\":[\"No adapter or transformer needed\"]}]}},\"tipping\":{\"categories\":[{\"category\":\"OVERALL\",\"isIllegal\":false,\"isOffensive\":false,\"workersRelyOnTips\":false,\"tip\":[{\"expectations\":\"EXPECTED\"}]},{\"category\":\"HOTEL_PORTER\",\"frequency\":\"PER_BAG\",\"isIllegal\":false,\"isOffensive\":false,\"workersRelyOnTips\":false,\"tip\":[{\"expectations\":\"EXPECTED\",\"from\":{\"type\":\"CURRENCY\",\"currency\":\"euro\",\"amount\":0.5,\"amountInteger\":50,\"displayAs\":\"€0.50\"},\"to\":{\"type\":\"CURRENCY\",\"currency\":\"euro\",\"amount\":1,\"amountInteger\":100,\"displayAs\":\"€1.00\"}}]},{\"category\":\"HOTEL_HOUSEKEEPER\",\"frequency\":\"PER_DAY\",\"isIllegal\":false,\"isOffensive\":false,\"workersRelyOnTips\":false,\"tip\":[{\"expectations\":\"EXPECTED\",\"amount\":{\"type\":\"CURRENCY\",\"currency\":\"euro\",\"amount\":1.5,\"amountInteger\":150,\"displayAs\":\"€1.50\"}}]},{\"category\":\"HOTEL_CONCIERGE\",\"isIllegal\":false,\"isOffensive\":false,\"workersRelyOnTips\":false,\"tip\":[{\"expectations\":\"EXPECTED\",\"from\":{\"type\":\"CURRENCY\",\"currency\":\"euro\",\"amount\":8,\"amountInteger\":800,\"displayAs\":\"€8.00\"},\"to\":{\"type\":\"CURRENCY\",\"currency\":\"euro\",\"amount\":20,\"amountInteger\":2000,\"displayAs\":\"€20.00\"}}]},{\"category\":\"RESTAURANT\",\"includedServiceCharge\":\"ALWAYS\",\"isIllegal\":false,\"isOffensive\":false,\"workersRelyOnTips\":false,\"tip\":[{\"expectations\":\"EXPECTED\",\"serviceQuality\":\"EXCELLENT\",\"amount\":{\"type\":\"PERCENTAGE\",\"amount\":10}},{\"expectations\":\"NOT_EXPECTED\",\"serviceQuality\":\"BAD\",\"amount\":{\"type\":\"NONE\"}}]},{\"category\":\"BAR_CAFFE\",\"isIllegal\":false,\"isOffensive\":false,\"workersRelyOnTips\":false,\"tip\":[{\"expectations\":\"EXPECTED\",\"from\":{\"type\":\"CURRENCY\",\"currency\":\"euro\",\"amount\":1,\"amountInteger\":100,\"displayAs\":\"€1.00\"},\"to\":{\"type\":\"CURRENCY\",\"currency\":\"euro\",\"amount\":2,\"amountInteger\":200,\"displayAs\":\"€2.00\"}}]},{\"category\":\"SPA\",\"placeToTip\":\"RECEPTION\",\"isIllegal\":false,\"isOffensive\":false,\"workersRelyOnTips\":false,\"tip\":[{\"expectations\":\"NOT_EXPECTED\",\"from\":{\"type\":\"PERCENTAGE\",\"amount\":10},\"to\":{\"type\":\"PERCENTAGE\",\"amount\":20}}]},{\"category\":\"TOUR_GUIDE\",\"isIllegal\":false,\"isOffensive\":false,\"workersRelyOnTips\":false,\"tip\":[{\"expectations\":\"EXPECTED\",\"from\":{\"type\":\"CURRENCY\",\"currency\":\"euro\",\"amount\":2,\"amountInteger\":200,\"displayAs\":\"€2.00\"},\"to\":{\"type\":\"CURRENCY\",\"currency\":\"euro\",\"amount\":5,\"amountInteger\":500,\"displayAs\":\"€5.00\"}}]},{\"category\":\"TOUR_BUS_DRIVER\",\"frequency\":\"PER_DAY\",\"isIllegal\":false,\"isOffensive\":false,\"workersRelyOnTips\":false,\"tip\":[{\"expectations\":\"EXPECTED\",\"from\":{\"type\":\"CURRENCY\",\"currency\":\"euro\",\"amount\":1,\"amountInteger\":100,\"displayAs\":\"€1.00\"},\"to\":{\"type\":\"CURRENCY\",\"currency\":\"euro\",\"amount\":2,\"amountInteger\":200,\"displayAs\":\"€2.00\"}}]},{\"category\":\"TAXI_DRIVER\",\"isIllegal\":false,\"isOffensive\":false,\"workersRelyOnTips\":false,\"tip\":[{\"expectations\":\"EXPECTED\",\"amount\":{\"type\":\"PERCENTAGE\",\"amount\":10}}]}],\"textual\":{\"title\":\"Tipping\",\"sections\":[{\"id\":\"SUMMARY\",\"body\":[\"Tipping is quite common in France and it's good etiquette to leave one. How much money you leave depends on the service and how happy you were with it. Receipts always include a service charge.\"]},{\"id\":\"SUMMARY_SHORT\",\"body\":[\"Expected to tip\"]}]}},\"atms\":{\"coverage\":\"VERY_COMMON\",\"localNames\":[\"distributeur\",\"distributeurs automatiques de billets (DAB)\"],\"textual\":{\"title\":\"ATMs\",\"sections\":[{\"id\":\"SUMMARY\",\"body\":[\"Automatic teller machines (local names: distributeur or distributeurs automatiques de billets (DAB)) are very common in France. You can find them in both large & small cities, tourist areas etc.\",\"You do not need a chip & PIN card to use an ATM — your standard magnetic card will work fine.\"]},{\"id\":\"COVERAGE\",\"body\":[\"Very common\"]}]}},\"creditDebitCard\":{\"acceptanceInternationalCards\":\"VERY_HIGH\",\"cards\":[{\"brand\":\"VISA\",\"acceptance\":\"HIGH\"},{\"brand\":\"MASTERCARD\",\"acceptance\":\"HIGH\"},{\"brand\":\"MAESTRO\",\"acceptance\":\"HIGH\"},{\"brand\":\"AMERICAN_EXPRESS\",\"acceptance\":\"MEDIUM\"},{\"brand\":\"DINERS_CLUB\",\"acceptance\":\"LOW\"},{\"brand\":\"DISCOVER\",\"acceptance\":\"NONE\"},{\"brand\":\"JCB\",\"acceptance\":\"NONE\"},{\"brand\":\"UNION_PAY\",\"acceptance\":\"NONE\"},{\"brand\":\"RU_PAY\",\"acceptance\":\"NONE\"}],\"hotlineNumbers\":[{\"brand\":\"MASTERCARD\",\"number\":\"0 800 90 1387\",\"isTollFree\":true},{\"brand\":\"VISA\",\"number\":\"0800 90 1179\",\"isTollFree\":true}],\"textual\":{\"title\":\"Credit & debit cards\",\"sections\":[{\"id\":\"SUMMARY\",\"body\":[\"Credit cards are widely accepted within France (at hotels, shops, and restaurants, travel agencies, car-rental agencies etc.). Visa, MasterCard and Maestro are the most widely accepted. American Express is less common. Diners Club is pretty rare. Discover, JCB, UnionPay and RuPay are unknown.\"]},{\"id\":\"ACCEPTANCE\",\"body\":[\"Widely accepted\"]}]}},\"driving\":{\"license\":{\"acceptance\":\"IDP\",\"textual\":{\"title\":\"Driving license\",\"sections\":[{\"id\":\"SUMMARY_SHORT\",\"body\":[\"IDP is required\"]},{\"id\":\"SUMMARY\",\"body\":[\"An International Driving Permit (IDP) is required alongside your original license. IDP can be issued by your local automobile association before you leave home.\"]}]}},\"road\":{\"petrol\":{\"stations\":[{\"name\":\"Shell\"},{\"name\":\"Avia\"},{\"name\":\"Intermarché\"},{\"name\":\"Carrefour\"},{\"name\":\"Total\"},{\"name\":\"AS24\"},{\"name\":\"Esso\"},{\"name\":\"Système U\"},{\"name\":\"Leclerc\"}],\"fuel\":[{\"type\":\"PETROL\",\"localNames\":[\"Sans plomb (SP) (95/98)\",\"essence\",\"carburant\"]},{\"type\":\"DIESEL\",\"localNames\":[\"Gazole\",\"Diesel\"]}]},\"entities\":[{\"type\":\"TRAFFIC_NEWS\",\"name\":\"Autoroute Info\",\"url\":\"http://www.autorouteinfo.fr\",\"radio\":\"107.7MHz\"},{\"type\":\"MOTORWAYS_TOLLS\",\"name\":\"ASFA\",\"url\":\"http://www.autoroutes.fr/\"},{\"type\":\"TRAFFIC_NEWS\",\"name\":\"Autoroutes.fr\",\"url\":\"http://www.autoroutes.fr/en/Realtime-traffic-information.htm\",\"radio\":\"107.7\"},{\"type\":\"ROAD_ASSISTANCE\",\"name\":\"Autoreoutes.fr\",\"url\":\"http://www.autoroutes.fr/en/breakdown-service.htm\"}],\"textual\":{\"title\":\"On the road\",\"sections\":[{\"id\":\"FUEL\",\"body\":[\"Petrol (unleaded) gas options are called Sans plomb (SP) (95/98), essence or carburant. Petrol costs about €1.25 (1.46 CHF) per liter.\",\"Diesel options are called Gazole or Diesel.\"]},{\"id\":\"PETROL_STATIONS\",\"body\":[\"Most popular petrol stations in France are Shell, Avia, Intermarché, Carrefour, Total, AS24, Esso, Système U and Leclerc.\"]},{\"id\":\"ENTITIES\",\"body\":[\"Road assistance is available at null (Autoreoutes.fr). For general news on France's traffic use Autoroute Info or Autoroutes.fr. For news on France’s motorways and tolls use ASFA.\"]}]}},\"rules\":{\"side\":\"RIGHT\",\"transmission\":\"MANUAL\",\"allowedTurnOnRed\":false,\"alcoholLimitPercentage\":0.05,\"speedLimits\":[{\"roadType\":\"WITHIN_TOWNS\",\"limit\":\"50\"},{\"roadType\":\"RESIDENTIAL_AREA\",\"limit\":\"30\"},{\"roadType\":\"UNDIVIDED_HIGHWAY\",\"limit\":\"90\"},{\"roadType\":\"MOTORWAY\",\"limit\":\"130\"},{\"roadType\":\"EXPRESSWAY\",\"limit\":\"110\"}],\"textual\":{\"title\":\"Road rules\",\"sections\":[{\"id\":\"GENERAL\",\"body\":[\"In France you drive on the right side. Most cars have manual transmission (stick).\"]},{\"id\":\"SPEED_LIMITS\",\"body\":[\"Unless otherwise posted, the speed limits for cars and motorcycles are as follows: 110 km/h (68 mph) on expressways, 130 km/h (80 mph) on motorways, 30 km/h (18 mph) in residential areas, 50 km/h (31 mph) in all built-up areas and 90 km/h (55 mph) on undivided highways.\"]},{\"id\":\"TURN_ON_RED\",\"body\":[\"Right turn on red lights is strictly forbidden unless an additional green semaphore arrow allows it.\"]},{\"id\":\"ALCOHOL\",\"body\":[\"It is illegal to drive with blood alcohol content higher than 0.05%.\"]}]}}},\"dialCode\":{\"calling\":[\"33\"],\"exit\":[\"00\"],\"trunk\":[\"0\"],\"textual\":{\"title\":\"Dial code\",\"sections\":[{\"id\":\"SUMMARY\",\"body\":[\"To call France from Switzerland, dial + or 00 (exit code for Switzerland), then 33 (the country code for France), then the area code (without the initial 0) and the local number.\"]},{\"id\":\"LOCAL_CALL_EXAMPLE\",\"body\":[\"01 1234567\"]},{\"id\":\"INTERNATIONAL_CALL_EXAMPLE\",\"body\":[\"+ 33 1 1234567\"]},{\"id\":\"LOCAL_CALLS\",\"body\":[\"For local calls within France, start with the area code (with the initial 0). In the case above area code is 1 .\"]}]}},\"internet\":{\"averageSpeedKbps\":8917,\"wifi\":{\"coverage\":\"VERY_HIGH\"},\"textual\":{\"title\":\"Internet\",\"sections\":[{\"id\":\"INTERNET_SPEED\",\"body\":[\"Internet speed in France is on average 8.9 Mbps which is 47% slower than in Switzerland (16.7 Mbps).\"]},{\"id\":\"WIFI\",\"body\":[\"WiFi coverage in France is very high. Most hotels, hostels, cafes, restaurants and bars have it.\"]}]}},\"mobilePhone\":{\"dataPackage\":{\"sizeMb\":2048,\"price\":{\"amount\":10.71,\"amountInteger\":1071,\"currency\":\"EUR\",\"displayAs\":\"€10.71\"}},\"carriers\":[{\"name\":\"Bouygues Télécom\",\"website\":\"https://www.bouyguestelecom.fr/\"},{\"name\":\"Free\",\"website\":\"http://www.free.fr/\"},{\"name\":\"Orange\",\"website\":\"http://www.orange.com/\"},{\"name\":\"SFR\",\"website\":\"http://www.sfr.fr/\"}],\"capabilities\":[{\"name\":\"GSM 1800\",\"type\":\"TYPE_2G\"},{\"name\":\"GSM 900\",\"type\":\"TYPE_2G\"},{\"name\":\"UMTS 2100\",\"type\":\"TYPE_3G\"},{\"name\":\"UMTS 900\",\"type\":\"TYPE_3G\"},{\"name\":\"LTE 1800 (3)\",\"type\":\"TYPE_4G\"},{\"name\":\"LTE 2600 (7)\",\"type\":\"TYPE_4G\"},{\"name\":\"LTE 700 (28)\",\"type\":\"TYPE_4G\"},{\"name\":\"LTE 800 (20)\",\"type\":\"TYPE_4G\"}],\"textual\":{\"title\":\"Mobile phone\",\"sections\":[{\"id\":\"SIM_CARD_TELECOMS\",\"body\":[\"You can buy a SIM card for about €10.71 (12.53 CHF), which includes 2 GB of data plan. You can choose from 4 telecoms: <a href='https://www.bouyguestelecom.fr/' target='_blank'>Bouygues Télécom</a>, <a href='http://www.free.fr/' target='_blank'>Free</a>, <a href='http://www.orange.com/' target='_blank'>Orange</a> or <a href='http://www.sfr.fr/' target='_blank'>SFR</a>.\"]},{\"id\":\"MOBILE_NETWORKS\",\"body\":[\"France uses similar GSM mobile networks (GSM 1800 and GSM 900) as Switzerland so you should be able to normally use your mobile phone.\"]},{\"id\":\"ROAMING\",\"body\":[\"If you don't want to buy SIM card in France upon arrival, make sure to check the roaming charges with your telecom provider before your departure so there are no surprises when you return home.\"]}]}},\"health\":{\"vaccination\":{\"policy\":\"NOT_REQUIRED\",\"risks\":[{\"type\":\"ROUTINE_IMMUNIZATIONS\",\"isSelective\":false},{\"type\":\"HEPATITIS_A\",\"isSelective\":true},{\"type\":\"HEPATITIS_B\",\"isSelective\":true},{\"type\":\"RABIES\",\"isSelective\":true}],\"textual\":{\"title\":\"Vaccinations\",\"sections\":[{\"id\":\"SUMMARY\",\"body\":[\"No vaccinations are required to enter the country, but it is recommended to perform routine immunizations for your protection and to prevent the spread of infectious diseases.\"]},{\"id\":\"SELECTIVE\",\"body\":[\"These apply only to select travelers or persons on work assignments.\"]}]}}},\"alcohol\":{\"data\":[{\"legal\":true,\"minimumDrinkingAgeBeerWine\":18,\"minimumDrinkingAgeSpirits\":18,\"minimumPurchaseAgeBeerWine\":18,\"minimumPurchaseAgeSpirits\":18}],\"textual\":{\"title\":\"Alcohol\",\"sections\":[{\"id\":\"LEGALITY\",\"body\":[\"Legal\"]},{\"id\":\"SUMMARY\",\"body\":[\"Minimum age to purchase and drink alcohol is 18 years old.\"]},{\"id\":\"DRIVING\",\"body\":[\"Blood-alcohol limit is 0.05% (0.5g per litre of blood) while driving.\"]}]}},\"drugs\":{\"types\":[{\"type\":\"CANNABIS\",\"purpose\":\"POSSESSION\",\"useType\":\"PERSONAL\",\"legal\":false,\"decriminalized\":false},{\"type\":\"CANNABIS\",\"purpose\":\"POSSESSION\",\"useType\":\"MEDICAL\",\"legal\":true,\"decriminalized\":true}],\"textual\":{\"title\":\"Drugs\",\"sections\":[{\"id\":\"DRUGS\",\"body\":[\"Drugs (including cannabis/marijuana) are illegal & criminalized which means you are risking jail time with other penalties (fine, community service, rehabilitation) if you are caught.\"]}]}},\"prostitution\":{\"legal\":{\"payForSex\":false,\"offerServices\":true,\"brothels\":false},\"textual\":{\"title\":\"Prostitution\",\"sections\":[{\"id\":\"LEGALITY\",\"body\":[\"Semi-legal (only offering services)\"]},{\"id\":\"SUMMARY\",\"body\":[\"Even though prostitution is strictly speaking legal, consuming it is not - meaning that if you get caught using the services of a prostitute, you will be fined as a client, not a prostitute herself. Brothels are illegal.\"]}]}},\"religion\":{\"distribution\":[{\"type\":\"CHRISTIANITY\",\"percentage\":63},{\"type\":\"ISLAM\",\"percentage\":7.5},{\"type\":\"UNAFFILIATED\",\"percentage\":28},{\"type\":\"HINDUISM\",\"percentage\":0.05},{\"type\":\"BUDDHISM\",\"percentage\":0.5},{\"type\":\"FOLK\",\"percentage\":0.3},{\"type\":\"OTHER\",\"percentage\":0.2},{\"type\":\"JUDAISM\",\"percentage\":0.5}]},\"emergency\":{\"numbers\":[{\"type\":\"UNIVERSAL\",\"numbers\":[\"112\"]}]},\"embassy\":{\"name\":\"Embassy of Switzerland in France\",\"address\":\"142 Rue de Grenelle, 75007 Paris, France\",\"phone\":\"+33 1 49 55 67 00\",\"website\":\"http://www.eda.admin.ch/paris\"}}";;
		Map<String, Object> map = new HashMap<>();
		map.put("jsonData", JSONObject.fromObject(json));
		String html = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, "template/base-trip.vm", "UTF-8", map);
		System.out.println(html);
		FileWriter fileWriter = new FileWriter("test.html");
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.print(html);
	    printWriter.close();
	}
	
	@Test
	public void testTenantData() {
		try {
			this.springTemplateDao.getTenantSearchResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTicketingData() throws Exception {
		List<TicketingPdf> tickets = this.techDaoService.findTicketsBySuperPnr("0304824450513");
		for (TicketingPdf ticketingPdf : tickets) {
			Blob eticketBlob = ticketingPdf.getTicket();
			byte[] ticketByteArray = ZipUtils.decompressByteArray(eticketBlob.getBytes(1, (int)eticketBlob.length()));
			FileOutputStream fos = new FileOutputStream(ticketingPdf.getSuperPnr() + "-" + ticketingPdf.getPaxId() + ".pdf");
			fos.write(ticketByteArray);
			fos.close();
		}
	}
	
	@Test
	public void testTicketingData1() throws Exception {
		TicketingPdf ticketingPdf = this.techDaoService.findTicketBySuperPnr("0304824450513");
		Blob eticketBlob = ticketingPdf.getTicket();
		byte[] ticketByteArray = ZipUtils.decompressByteArray(eticketBlob.getBytes(1, (int)eticketBlob.length()));
		FileOutputStream fos = new FileOutputStream(ticketingPdf.getSuperPnr() + "-" + ticketingPdf.getPaxId() + ".html");
		fos.write(ticketByteArray);
		fos.close();
	}
	
	@Test
	public void testTicketingHtml() throws Exception {
		String html = this.techDaoService.generateEticketHtml("0304824450513");
		System.out.println(html);
		TicketingPdf ticketingPdf = this.techDaoService.findTicketBySuperPnr("0304824450513");
		FileUtils.writeStringToFile(new File(ticketingPdf.getSuperPnr() + "-" + ticketingPdf.getPaxId() + ".html"), html);
	}
	
	@Test
	public void testTicketingPdf() throws Exception {
		byte[] ticketByteArray = this.techDaoService.getTicketPdfByteArray("0304824450513");
		TicketingPdf ticketingPdf = this.techDaoService.findTicketBySuperPnr("0304824450513");
		FileOutputStream fos = new FileOutputStream(ticketingPdf.getSuperPnr() + "-" + ticketingPdf.getPaxId() + ".pdf");
		fos.write(ticketByteArray);
		fos.close();
	}
	
	@Test
	public void testPDF() {
		try {
			String HTML = "<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"width: 360px;\"><head> <title>Flight E-ticket</title></head><body style=\"margin: 0; padding: 0; width: 360px;\"> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"font-size:12px; font-family: arial;min-width:320px;max-width:360px;margin:0 auto;border:18px solid #F96E6C;\"> <tbody style=\"box-shadow: 0 6px 10px 0 rgba(0, 0, 0, 0.2);\"> <tr> <td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr> <td height=\"16\" colspan=\"3\"></td></tr><tr> <td width=\"16\"></td><td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr> <td width=\"100%\"> <div style=\"padding-bottom: 14px;\"> <div style=\"display: inline-block;vertical-align:middle; width: 60px; height: 25px;border: 1px solid #E4E4E4;padding: 6px 2px;border-radius: 3px;margin-right: 10px;\"> <img src=\"http://ns.yatracdn.com/common/images/fresco/mailer/airline-logo/CZ.gif\"/> </div><span style=\"vertical-align:middle;display: inline-block;color: #666666;\"> <span style=\"display:block; font-size: 14px;padding-bottom:4px;\"> <span style=\"color: #333333;\">China Southern Airlines</span> CZ - 3796/ 3027 </span> <span style=\"display:block; font-size: 12px;\">Economy</span> </span> </div></td></tr><tr> <td width=\"100%\"> <div style=\"padding-bottom: 4px;;color: #999999;font-size: 12px;\"> <span style=\"display:inline-block;width: 49%; text-align:left\">PNR</span> <span style=\"display:inline-block;width: 49%; text-align:right\">Booking Ref No.</span> </div><div style=\"color: #333333;font-size: 12px;\"> <span style=\"display:inline-block;width: 49%; text-align:left\">KHF7RL</span> <span style=\"display:inline-block;width: 49%; text-align:right\">5234567890</span> </div></td></tr></table> </td><td width=\"16\"></td></tr><tr> <td height=\"16\" colspan=\"3\"></td></tr></table> </td></tr><tr> <td height=\"1\" colspan=\"3\" style=\"border-top: 1px solid #c3c6ca;display: block;opacity: 0.5;\"></td></tr><tr> <td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr> <td height=\"16\" colspan=\"3\"></td></tr><tr> <td width=\"16\"></td><td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr> <td style=\"padding-bottom: 5px;\"> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr style=\"font-size: 12px; color: #666666;\"> <td width=\"33.33%\" style=\"text-align: left;\">Yiwu</td><td width=\"33.33%\" style=\"text-align: center;\">2 Stops, via LosVegas, Guangzhou</td><td width=\"33.33%\" style=\"text-align: right;\">New Delhi</td></tr></table> </td></tr><tr> <td style=\"padding-bottom: 5px;\"> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr style=\"color: #333333;\"> <td width=\"33.33%\" style=\"font-weight: bold; text-align: left; font-size: 30px; text-transform: uppercase;\">YIW</td><td width=\"33.33%\" style=\"text-align: center; font-size: 30px;\"> <div> <span style=\"text-align: center ;width: 74px; height: 1px; padding-top: 3px; display: inline-block;border-top: 1px solid #e4e4e4;\"> <span style=\"width: 5px;height: 5px; border-radius: 50%; display: inline-block;background: #e4e4e4; float:left; position: relative; top: -6px\"></span> <span style=\"position: relative;top: -10px;width: 25px;height: 12px;display: inline-block;background: #fff;\"> <img width=\"13\" height=\"12\" src=\"https://ns.yatracdn.com/common/images/emailers/flights/plane.jpg\"/> </span> <span style=\"width: 5px;height: 5px; border-radius: 50%; display: inline-block;background: #e4e4e4; float: right; position: relative; top: -6px\"></span> </span> </div></td><td width=\"33.33%\" style=\"font-weight: bold; text-align: right; font-size: 30px; text-transform: uppercase;\">DEL</td></tr></table> </td></tr><tr> <td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr style=\"font-size: 12px; color: #666666;\"> <td width=\"46%\" style=\"text-align: left;\">Yiwu</td><td width=\"1%\" style=\"text-align: center;\"></td><td width=\"46%\" style=\"text-align: right;\">Indira Gandhi</td></tr></table> </td></tr><tr> <td height=\"1\" colspan=\"3\" style=\"border-top: 1px solid #c3c6ca;display: block;opacity: 0.5; margin-top: 16px; margin-bottom: 16px\"></td></tr><tr> <td style=\"padding-bottom: 5px;\"> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr style=\"font-size: 12px; color: #999999;\"> <td width=\"46%\" style=\"text-align: left;\">Departure</td><td width=\"1%\" style=\"text-align: center;\"></td><td width=\"46%\" style=\"text-align: right;\">Arrival</td></tr></table> </td></tr><tr> <td style=\"padding-bottom: 5px;\"> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr style=\"font-size: 24px; color: #333333;\"> <td width=\"46%\" style=\"text-align: left;\">07:50 AM</td><td width=\"1%\" style=\"text-align: center;\"></td><td width=\"46%\" style=\"text-align: right;\">10:50 AM</td></tr></table> </td></tr><tr> <td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr style=\"font-size: 12px; color: #666666;\"> <td width=\"46%\" style=\"text-align: left;\">Fri, 25 May</td><td width=\"1%\" style=\"text-align: center;\"></td><td width=\"46%\" style=\"text-align: right;\">Sun, 27 May</td></tr></table> </td></tr><tr> <td height=\"1\" colspan=\"3\" style=\"border-top: 1px solid #c3c6ca;display: block;opacity: 0.5; margin-top: 16px; margin-bottom: 16px\"></td></tr><tr> <td colspan=\"3\" style=\"display: block;font-size: 12px; color: #999999;padding-bottom: 5px;\">Passenger</td></tr><tr> <td colspan=\"3\" style=\"display: block; font-size: 24px; color: #333333;\">Mr. Arun Kohli</td></tr><tr> <td style=\"padding-bottom: 5px;padding-top: 14px;\"> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"5\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;table-layout: fixed;width: 100%;\"> <tr style=\"font-size: 12px; color: #666666;\"> <td style=\"text-align: center; vertical-align: top;\"> <div style=\"min-width: 62px; max-width: 86px; padding-top: 10px; padding-bottom: 10px; margin: 0 8px; border: solid 1px #e4e4e4; border-radius: 4px; background-color: #ffffff;\"> <img src=\"https://ns.yatracdn.com/common/images/emailers/flights/baggage-icon.jpg\"/> </div><div style=\"padding-top: 5px; min-width: 62px; max-width: 86px;\">1 piece</div></td></tr></table> </td></tr></table> </td><td width=\"16\"></td></tr></table> </td></tr><tr> <td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr> <td width=\"16\"> <div style=\"width: 0; height: 0; border-top: 16px solid transparent;border-bottom: 16px solid transparent; border-left: 16px solid #f96e6c; box-shadow: -10px 0px 0px #f96e6c;\"></div></td><td> <div style=\"text-align: center ;width: 100%; height: 1px; margin-top: 3px; display: inline-block;border-top: 1px dashed #a2a6a9;\"></div></td><td width=\"16\"> <div style=\"width: 0; height: 0; border-top: 16px solid transparent;border-bottom: 16px solid transparent; border-right: 16px solid #f96e6c; box-shadow: 10px 0px 0px #f96e6c;\"></div></td></tr><tr> <td colspan=\"3\" style=\"text-align: center;\"> <div> <img src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQyf2C9wJ1UGKL4-dodk7NNH0HHrvUVJf51MDcwKfd0Yav5emah8g\"/> </div></td></tr><tr> <td height=\"16\" colspan=\"3\"></td></tr></table> </td></tr></tbody> </table></body></html>";
			
            // create the API client instance
            Pdfcrowd.HtmlToImageClient client = new Pdfcrowd.HtmlToImageClient("rahulvardhan", "85f88ed8de348931d8e8d9822313cec9");

            client.setScreenshotWidth(360);
            // configure the conversion
            client.setOutputFormat("png");

            // run the conversion and write the result to a file
            byte[] bytes = client.convertString(HTML);
            
            FileUtils.writeByteArrayToFile(new File("D:/test.jpg"), bytes);
            
        }
        catch(Pdfcrowd.Error why) {
            // report the error to the standard error stream
            System.err.println("Pdfcrowd Error: " + why);
        }
        catch(Exception why) {
            // report the error to the standard error stream
            System.err.println("IO Error: " + why.getMessage());
        }

	}
	
	@Test
	public void create() {
		String src = "https://www.yatra.com";
		int width = 1000;
		int height = 1000;
		BufferedImage image = null;
		JEditorPane pane = new JEditorPane();
		EditorKit kit = new HTMLEditorKit();
		pane.setEditorKit(kit);
		pane.setEditable(false);
		pane.setMargin(new Insets(0, 0, 0, 0));
		try {
			pane.setPage(src);
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			Container c = new Container();
			SwingUtilities.paintComponent(g, pane, c, 0, 0, width, height);
			g.dispose();
			File outputfile = new File("D:/image.jpg");
			ImageIO.write(image, "jpg", outputfile);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void testISODateInVelocity() {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("name", "Rahul");
			map.put("dateTool", new DateTool());
			String html = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, "test-velocity.vm", "UTF-8", map);
			System.out.println(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHTMLHeight() throws Exception {
		String html = "<body style=\"margin: 0; padding: 0; width: 360px;\"> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"font-size:12px; font-family: arial;min-width:320px;max-width:360px;margin:0 auto;border:18px solid #F96E6C;\"> <tbody style=\"box-shadow: 0 6px 10px 0 rgba(0, 0, 0, 0.2);\"> <tr> <td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr> <td height=\"16\" colspan=\"3\"></td></tr><tr> <td width=\"16\"></td><td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr> <td width=\"100%\"> <div style=\"padding-bottom: 14px;\"> <div style=\"display: inline-block;vertical-align:middle; width: 60px; height: 25px;border: 1px solid #E4E4E4;padding: 6px 2px;border-radius: 3px;margin-right: 10px;\"> <img src=\"http://ns.yatracdn.com/common/images/fresco/mailer/airline-logo/CZ.gif\"/> </div><span style=\"vertical-align:middle;display: inline-block;color: #666666;\"> <span style=\"display:block; font-size: 14px;padding-bottom:4px;\"> <span style=\"color: #333333;\">China Southern Airlines</span> CZ - 3796/ 3027 </span> <span style=\"display:block; font-size: 12px;\">Economy</span> </span> </div></td></tr><tr> <td width=\"100%\"> <div style=\"padding-bottom: 4px;;color: #999999;font-size: 12px;\"> <span style=\"display:inline-block;width: 49%; text-align:left\">PNR</span> <span style=\"display:inline-block;width: 49%; text-align:right\">Booking Ref No.</span> </div><div style=\"color: #333333;font-size: 12px;\"> <span style=\"display:inline-block;width: 49%; text-align:left\">KHF7RL</span> <span style=\"display:inline-block;width: 49%; text-align:right\">5234567890</span> </div></td></tr></table> </td><td width=\"16\"></td></tr><tr> <td height=\"16\" colspan=\"3\"></td></tr></table> </td></tr><tr> <td height=\"1\" colspan=\"3\" style=\"border-top: 1px solid #c3c6ca;display: block;opacity: 0.5;\"></td></tr><tr> <td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr> <td height=\"16\" colspan=\"3\"></td></tr><tr> <td width=\"16\"></td><td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr> <td style=\"padding-bottom: 5px;\"> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr style=\"font-size: 12px; color: #666666;\"> <td width=\"33.33%\" style=\"text-align: left;\">Yiwu</td><td width=\"33.33%\" style=\"text-align: center;\">2 Stops, via LosVegas, Guangzhou</td><td width=\"33.33%\" style=\"text-align: right;\">New Delhi</td></tr></table> </td></tr><tr> <td style=\"padding-bottom: 5px;\"> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr style=\"color: #333333;\"> <td width=\"33.33%\" style=\"font-weight: bold; text-align: left; font-size: 30px; text-transform: uppercase;\">YIW</td><td width=\"33.33%\" style=\"text-align: center; font-size: 30px;\"> <div> <span style=\"text-align: center ;width: 74px; height: 1px; padding-top: 3px; display: inline-block;border-top: 1px solid #e4e4e4;\"> <span style=\"width: 5px;height: 5px; border-radius: 50%; display: inline-block;background: #e4e4e4; float:left; position: relative; top: -6px\"></span> <span style=\"position: relative;top: -10px;width: 25px;height: 12px;display: inline-block;background: #fff;\"> <img width=\"13\" height=\"12\" src=\"https://ns.yatracdn.com/common/images/emailers/flights/plane.jpg\"/> </span> <span style=\"width: 5px;height: 5px; border-radius: 50%; display: inline-block;background: #e4e4e4; float: right; position: relative; top: -6px\"></span> </span> </div></td><td width=\"33.33%\" style=\"font-weight: bold; text-align: right; font-size: 30px; text-transform: uppercase;\">DEL</td></tr></table> </td></tr><tr> <td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr style=\"font-size: 12px; color: #666666;\"> <td width=\"46%\" style=\"text-align: left;\">Yiwu</td><td width=\"1%\" style=\"text-align: center;\"></td><td width=\"46%\" style=\"text-align: right;\">Indira Gandhi</td></tr></table> </td></tr><tr> <td height=\"1\" colspan=\"3\" style=\"border-top: 1px solid #c3c6ca;display: block;opacity: 0.5; margin-top: 16px; margin-bottom: 16px\"></td></tr><tr> <td style=\"padding-bottom: 5px;\"> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr style=\"font-size: 12px; color: #999999;\"> <td width=\"46%\" style=\"text-align: left;\">Departure</td><td width=\"1%\" style=\"text-align: center;\"></td><td width=\"46%\" style=\"text-align: right;\">Arrival</td></tr></table> </td></tr><tr> <td style=\"padding-bottom: 5px;\"> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr style=\"font-size: 24px; color: #333333;\"> <td width=\"46%\" style=\"text-align: left;\">07:50 AM</td><td width=\"1%\" style=\"text-align: center;\"></td><td width=\"46%\" style=\"text-align: right;\">10:50 AM</td></tr></table> </td></tr><tr> <td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr style=\"font-size: 12px; color: #666666;\"> <td width=\"46%\" style=\"text-align: left;\">Fri, 25 May</td><td width=\"1%\" style=\"text-align: center;\"></td><td width=\"46%\" style=\"text-align: right;\">Sun, 27 May</td></tr></table> </td></tr><tr> <td height=\"1\" colspan=\"3\" style=\"border-top: 1px solid #c3c6ca;display: block;opacity: 0.5; margin-top: 16px; margin-bottom: 16px\"></td></tr><tr> <td colspan=\"3\" style=\"display: block;font-size: 12px; color: #999999;padding-bottom: 5px;\">Passenger</td></tr><tr> <td colspan=\"3\" style=\"display: block; font-size: 24px; color: #333333;\">Mr. Arun Kohli</td></tr><tr> <td style=\"padding-bottom: 5px;padding-top: 14px;\"> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"5\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;table-layout: fixed;width: 100%;\"> <tr style=\"font-size: 12px; color: #666666;\"> <td style=\"text-align: center; vertical-align: top;\"> <div style=\"min-width: 62px; max-width: 86px; padding-top: 10px; padding-bottom: 10px; margin: 0 8px; border: solid 1px #e4e4e4; border-radius: 4px; background-color: #ffffff;\"> <img src=\"https://ns.yatracdn.com/common/images/emailers/flights/baggage-icon.jpg\"/> </div><div style=\"padding-top: 5px; min-width: 62px; max-width: 86px;\">1 piece</div></td></tr></table> </td></tr></table> </td><td width=\"16\"></td></tr></table> </td></tr><tr> <td> <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#ffffff\" style=\"border-collapse: collapse;\"> <tr> <td width=\"16\"> <div style=\"width: 0; height: 0; border-top: 16px solid transparent;border-bottom: 16px solid transparent; border-left: 16px solid #f96e6c; box-shadow: -10px 0px 0px #f96e6c;\"></div></td><td> <div style=\"text-align: center ;width: 100%; height: 1px; margin-top: 3px; display: inline-block;border-top: 1px dashed #a2a6a9;\"></div></td><td width=\"16\"> <div style=\"width: 0; height: 0; border-top: 16px solid transparent;border-bottom: 16px solid transparent; border-right: 16px solid #f96e6c; box-shadow: 10px 0px 0px #f96e6c;\"></div></td></tr><tr> <td colspan=\"3\" style=\"text-align: center;\"> <div> <img src=\"http://local.yatra.com:8080/air-pay-book-service/dom/barcode/generate/barcode?superPnr=5234567890&amp;paxName=Kohli/Arun Mr&amp;airlineCode=CZ&amp;origin=YIW&amp;destination=DEL&amp;dayOfYear=125&amp;flightNumber=3796&amp;airlinePnr=KHF7RL&amp;seatNo=&amp;cabin=Economy&amp;format=aztec\"/> </div></td></tr><tr> <td height=\"16\" colspan=\"3\"></td></tr></table> </td></tr></tbody> </table></body>";
		String CSS = "";
		ElementList el = XMLWorkerHelper.parseToElementList(html, CSS);
        File file = new File("D:/test.pdf");
		float width = 360;
        float max = 10000;
        ColumnText ct = new ColumnText(null);
        ct.setSimpleColumn(new Rectangle(width, max));
        for (Element e : el) {
            ct.addElement(e);
        }
        ct.go(true);
        float y = ct.getYLine();
        System.out.println("Width : " + width + " Height : " + (max - y));
        Rectangle pagesize = new Rectangle(width, max - y);
        // step 1
        Document document = new Document(pagesize, 0, 0, 0, 0);
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // step 3
        document.open();
        // step 4
        ct = new ColumnText(writer.getDirectContent());
        ct.setSimpleColumn(pagesize);
        for (Element e : el) {
            ct.addElement(e);
        }
        ct.go();
        // step 5
        document.close();
	}
}
