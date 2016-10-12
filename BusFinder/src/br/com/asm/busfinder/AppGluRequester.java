package br.com.asm.busfinder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppGluRequester {
	
	private static final String APPGLU_BASE_URL = "https://api.appglu.com/v1/queries/";
	
	public static JSONArray findRoutesByStopName(String key) throws IOException, JSONException {
		
		JSONArray result = null;

		URL url = new URL(APPGLU_BASE_URL + "findRoutesByStopName/run");
		String params = "{\"params\": {\"stopName\": \"%" + key + "%\"}}";
//		String strResult = "{\"rows\":[{\"id\":22,\"shortName\":\"131\",\"longName\":\"AGRONÔMICA VIA GAMA D'EÇA\",\"lastModifiedDate\":\"2009-10-26T02:00:00+0000\",\"agencyId\":9},{\"id\":32,\"shortName\":\"133\",\"longName\":\"AGRONÔMICA VIA MAURO RAMOS\",\"lastModifiedDate\":\"2012-07-23T03:00:00+0000\",\"agencyId\":9}],\"rowsAffected\":0}";
		String strResult = postRequest(url, params);
			
		JSONObject jsonObject = new JSONObject(strResult); 
		result = jsonObject.getJSONArray("rows");
		
		return result;
	}
	
	
	public static JSONArray findStopsByRouteId(int routeId) throws JSONException, IOException{
		
		JSONArray result = null;

		URL url = new URL(APPGLU_BASE_URL + "findStopsByRouteId/run");
		String params = "{\"params\": {\"routeId\":"+ routeId + "}}";
		
		//String conteudo = "{\"rows\":[{\"id\":26,\"name\":\"TICEN\",\"sequence\":1,\"route_id\":32},{\"id\":27,\"name\":\"RUA PROCURADOR ABELARDO GOMES\",\"sequence\":2,\"route_id\":32},{\"id\":28,\"name\":\"AVENIDA PAULO FONTES\",\"sequence\":3,\"route_id\":32},{\"id\":29,\"name\":\"RUA DOUTOR ÁLVARO MILLEN DA SILVEIRA\",\"sequence\":4,\"route_id\":32},{\"id\":30,\"name\":\"RUA DOUTOR JORGE DA LUZ FONTES\",\"sequence\":5,\"route_id\":32},{\"id\":31,\"name\":\"RUA SILVA JARDIM\",\"sequence\":6,\"route_id\":32},{\"id\":32,\"name\":\"AVENIDA MAURO RAMOS\",\"sequence\":7,\"route_id\":32},{\"id\":33,\"name\":\"AVENIDA JORNALISTA RUBENS DE ARRUDA RAMOS\",\"sequence\":8,\"route_id\":32},{\"id\":34,\"name\":\"RUA COMANDANTE CONSTANTINO NICOLAU SPYRIDES\",\"sequence\":9,\"route_id\":32},{\"id\":35,\"name\":\"RUA DELMINDA SILVEIRA\",\"sequence\":10,\"route_id\":32},{\"id\":36,\"name\":\"AVENIDA PROFESSOR HENRIQUE DA SILVA FONTES\",\"sequence\":11,\"route_id\":32},{\"id\":37,\"name\":\"TITRI\",\"sequence\":12,\"route_id\":32},{\"id\":38,\"name\":\"RUA PROFESSOR MILTON ROQUE RAMOS KRIEGER\",\"sequence\":13,\"route_id\":32},{\"id\":39,\"name\":\"RUA LAURO LINHARES\",\"sequence\":14,\"route_id\":32},{\"id\":40,\"name\":\"RUA DELMINDA SILVEIRA\",\"sequence\":15,\"route_id\":32},{\"id\":41,\"name\":\"RUA RUI BARBOSA\",\"sequence\":16,\"route_id\":32},{\"id\":42,\"name\":\"RUA FREI CANECA\",\"sequence\":17,\"route_id\":32},{\"id\":43,\"name\":\"RUA HEITOR LUZ\",\"sequence\":18,\"route_id\":32},{\"id\":44,\"name\":\"AVENIDA MAURO RAMOS\",\"sequence\":19,\"route_id\":32},{\"id\":45,\"name\":\"RUA SILVA JARDIM\",\"sequence\":20,\"route_id\":32},{\"id\":46,\"name\":\"AVENIDA GOVERNADOR GUSTAVO RICHARD\",\"sequence\":21,\"route_id\":32}],\"rowsAffected\":0}";
		String conteudo = postRequest(url, params);
		
		JSONObject jsonObject = new JSONObject(conteudo); 
		result = jsonObject.getJSONArray("rows");
			
		return result;
	}
	
	public static JSONArray findDeparturesByRouteId(int routeId) throws IOException, JSONException {
		
		JSONArray result = null;
		URL url = new URL(APPGLU_BASE_URL + "/findDeparturesByRouteId/run");
		String params = "{\"params\": {\"routeId\":"+ routeId + "}}";
		
		//String conteudo ="{\"rows\":[{\"id\":15,\"calendar\":\"WEEKDAY\",\"time\":\"05:50\"},{\"id\":16,\"calendar\":\"WEEKDAY\",\"time\":\"06:07\"},{\"id\":17,\"calendar\":\"WEEKDAY\",\"time\":\"06:25\"},{\"id\":18,\"calendar\":\"WEEKDAY\",\"time\":\"06:42\"},{\"id\":19,\"calendar\":\"WEEKDAY\",\"time\":\"07:00\"},{\"id\":20,\"calendar\":\"WEEKDAY\",\"time\":\"07:16\"},{\"id\":21,\"calendar\":\"WEEKDAY\",\"time\":\"07:33\"},{\"id\":22,\"calendar\":\"WEEKDAY\",\"time\":\"07:45\"},{\"id\":23,\"calendar\":\"WEEKDAY\",\"time\":\"07:49\"},{\"id\":24,\"calendar\":\"WEEKDAY\",\"time\":\"08:05\"},{\"id\":25,\"calendar\":\"WEEKDAY\",\"time\":\"08:21\"},{\"id\":26,\"calendar\":\"WEEKDAY\",\"time\":\"08:37\"},{\"id\":27,\"calendar\":\"WEEKDAY\",\"time\":\"08:53\"},{\"id\":28,\"calendar\":\"WEEKDAY\",\"time\":\"09:09\"},{\"id\":29,\"calendar\":\"WEEKDAY\",\"time\":\"09:25\"},{\"id\":30,\"calendar\":\"WEEKDAY\",\"time\":\"09:41\"},{\"id\":31,\"calendar\":\"WEEKDAY\",\"time\":\"09:57\"},{\"id\":32,\"calendar\":\"WEEKDAY\",\"time\":\"10:16\"},{\"id\":33,\"calendar\":\"WEEKDAY\",\"time\":\"10:35\"},{\"id\":34,\"calendar\":\"WEEKDAY\",\"time\":\"10:54\"},{\"id\":35,\"calendar\":\"WEEKDAY\",\"time\":\"11:13\"},{\"id\":36,\"calendar\":\"WEEKDAY\",\"time\":\"11:32\"},{\"id\":37,\"calendar\":\"WEEKDAY\",\"time\":\"11:51\"},{\"id\":38,\"calendar\":\"WEEKDAY\",\"time\":\"12:08\"},{\"id\":39,\"calendar\":\"WEEKDAY\",\"time\":\"12:29\"},{\"id\":40,\"calendar\":\"WEEKDAY\",\"time\":\"12:48\"},{\"id\":41,\"calendar\":\"WEEKDAY\",\"time\":\"13:07\"},{\"id\":42,\"calendar\":\"WEEKDAY\",\"time\":\"13:23\"},{\"id\":43,\"calendar\":\"WEEKDAY\",\"time\":\"13:45\"},{\"id\":44,\"calendar\":\"WEEKDAY\",\"time\":\"14:04\"},{\"id\":45,\"calendar\":\"WEEKDAY\",\"time\":\"14:23\"},{\"id\":46,\"calendar\":\"WEEKDAY\",\"time\":\"14:42\"},{\"id\":47,\"calendar\":\"WEEKDAY\",\"time\":\"15:01\"},{\"id\":48,\"calendar\":\"WEEKDAY\",\"time\":\"15:20\"},{\"id\":49,\"calendar\":\"WEEKDAY\",\"time\":\"15:40\"},{\"id\":50,\"calendar\":\"WEEKDAY\",\"time\":\"16:00\"},{\"id\":51,\"calendar\":\"WEEKDAY\",\"time\":\"16:16\"},{\"id\":52,\"calendar\":\"WEEKDAY\",\"time\":\"16:32\"},{\"id\":53,\"calendar\":\"WEEKDAY\",\"time\":\"16:48\"},{\"id\":54,\"calendar\":\"WEEKDAY\",\"time\":\"17:04\"},{\"id\":55,\"calendar\":\"WEEKDAY\",\"time\":\"17:20\"},{\"id\":56,\"calendar\":\"WEEKDAY\",\"time\":\"17:36\"},{\"id\":57,\"calendar\":\"WEEKDAY\",\"time\":\"17:52\"},{\"id\":58,\"calendar\":\"WEEKDAY\",\"time\":\"18:08\"},{\"id\":59,\"calendar\":\"WEEKDAY\",\"time\":\"18:24\"},{\"id\":60,\"calendar\":\"WEEKDAY\",\"time\":\"18:40\"},{\"id\":61,\"calendar\":\"WEEKDAY\",\"time\":\"18:56\"},{\"id\":62,\"calendar\":\"WEEKDAY\",\"time\":\"19:15\"},{\"id\":63,\"calendar\":\"WEEKDAY\",\"time\":\"19:34\"},{\"id\":64,\"calendar\":\"WEEKDAY\",\"time\":\"19:53\"},{\"id\":65,\"calendar\":\"WEEKDAY\",\"time\":\"20:12\"},{\"id\":66,\"calendar\":\"WEEKDAY\",\"time\":\"20:32\"},{\"id\":67,\"calendar\":\"WEEKDAY\",\"time\":\"20:50\"},{\"id\":68,\"calendar\":\"WEEKDAY\",\"time\":\"21:09\"},{\"id\":69,\"calendar\":\"WEEKDAY\",\"time\":\"21:28\"},{\"id\":70,\"calendar\":\"WEEKDAY\",\"time\":\"21:47\"},{\"id\":71,\"calendar\":\"WEEKDAY\",\"time\":\"22:07\"},{\"id\":72,\"calendar\":\"WEEKDAY\",\"time\":\"22:27\"},{\"id\":73,\"calendar\":\"WEEKDAY\",\"time\":\"22:47\"},{\"id\":74,\"calendar\":\"WEEKDAY\",\"time\":\"23:07\"},{\"id\":75,\"calendar\":\"WEEKDAY\",\"time\":\"23:37\"},{\"id\":76,\"calendar\":\"WEEKDAY\",\"time\":\"00:07\"},{\"id\":77,\"calendar\":\"SATURDAY\",\"time\":\"06:20\"},{\"id\":78,\"calendar\":\"SATURDAY\",\"time\":\"06:45\"},{\"id\":79,\"calendar\":\"SATURDAY\",\"time\":\"07:10\"},{\"id\":80,\"calendar\":\"SATURDAY\",\"time\":\"07:35\"},{\"id\":81,\"calendar\":\"SATURDAY\",\"time\":\"08:00\"},{\"id\":82,\"calendar\":\"SATURDAY\",\"time\":\"08:25\"},{\"id\":83,\"calendar\":\"SATURDAY\",\"time\":\"08:50\"},{\"id\":84,\"calendar\":\"SATURDAY\",\"time\":\"09:15\"},{\"id\":85,\"calendar\":\"SATURDAY\",\"time\":\"09:40\"},{\"id\":86,\"calendar\":\"SATURDAY\",\"time\":\"10:05\"},{\"id\":87,\"calendar\":\"SATURDAY\",\"time\":\"10:35\"},{\"id\":88,\"calendar\":\"SATURDAY\",\"time\":\"11:05\"},{\"id\":89,\"calendar\":\"SATURDAY\",\"time\":\"11:35\"},{\"id\":90,\"calendar\":\"SATURDAY\",\"time\":\"12:05\"},{\"id\":91,\"calendar\":\"SATURDAY\",\"time\":\"12:30\"},{\"id\":92,\"calendar\":\"SATURDAY\",\"time\":\"12:55\"},{\"id\":93,\"calendar\":\"SATURDAY\",\"time\":\"13:20\"},{\"id\":94,\"calendar\":\"SATURDAY\",\"time\":\"13:45\"},{\"id\":95,\"calendar\":\"SATURDAY\",\"time\":\"14:10\"},{\"id\":96,\"calendar\":\"SATURDAY\",\"time\":\"14:35\"},{\"id\":97,\"calendar\":\"SATURDAY\",\"time\":\"15:05\"},{\"id\":98,\"calendar\":\"SATURDAY\",\"time\":\"15:35\"},{\"id\":99,\"calendar\":\"SATURDAY\",\"time\":\"16:05\"},{\"id\":100,\"calendar\":\"SATURDAY\",\"time\":\"16:35\"},{\"id\":101,\"calendar\":\"SATURDAY\",\"time\":\"17:05\"},{\"id\":102,\"calendar\":\"SATURDAY\",\"time\":\"17:35\"},{\"id\":103,\"calendar\":\"SATURDAY\",\"time\":\"18:05\"},{\"id\":104,\"calendar\":\"SATURDAY\",\"time\":\"18:35\"},{\"id\":105,\"calendar\":\"SATURDAY\",\"time\":\"19:05\"},{\"id\":106,\"calendar\":\"SATURDAY\",\"time\":\"19:35\"},{\"id\":107,\"calendar\":\"SATURDAY\",\"time\":\"20:05\"},{\"id\":108,\"calendar\":\"SATURDAY\",\"time\":\"20:35\"},{\"id\":109,\"calendar\":\"SATURDAY\",\"time\":\"21:05\"},{\"id\":110,\"calendar\":\"SATURDAY\",\"time\":\"21:35\"},{\"id\":111,\"calendar\":\"SATURDAY\",\"time\":\"22:05\"},{\"id\":112,\"calendar\":\"SATURDAY\",\"time\":\"22:35\"},{\"id\":113,\"calendar\":\"SATURDAY\",\"time\":\"23:05\"},{\"id\":114,\"calendar\":\"SATURDAY\",\"time\":\"23:35\"},{\"id\":115,\"calendar\":\"SUNDAY\",\"time\":\"06:29\"},{\"id\":116,\"calendar\":\"SUNDAY\",\"time\":\"07:27\"},{\"id\":117,\"calendar\":\"SUNDAY\",\"time\":\"08:29\"},{\"id\":118,\"calendar\":\"SUNDAY\",\"time\":\"09:29\"},{\"id\":119,\"calendar\":\"SUNDAY\",\"time\":\"10:29\"},{\"id\":120,\"calendar\":\"SUNDAY\",\"time\":\"11:29\"},{\"id\":121,\"calendar\":\"SUNDAY\",\"time\":\"12:29\"},{\"id\":122,\"calendar\":\"SUNDAY\",\"time\":\"13:29\"},{\"id\":123,\"calendar\":\"SUNDAY\",\"time\":\"14:29\"},{\"id\":124,\"calendar\":\"SUNDAY\",\"time\":\"15:29\"},{\"id\":125,\"calendar\":\"SUNDAY\",\"time\":\"16:29\"},{\"id\":126,\"calendar\":\"SUNDAY\",\"time\":\"17:29\"},{\"id\":127,\"calendar\":\"SUNDAY\",\"time\":\"18:00\"},{\"id\":128,\"calendar\":\"SUNDAY\",\"time\":\"18:29\"},{\"id\":129,\"calendar\":\"SUNDAY\",\"time\":\"19:29\"},{\"id\":130,\"calendar\":\"SUNDAY\",\"time\":\"20:29\"},{\"id\":131,\"calendar\":\"SUNDAY\",\"time\":\"21:29\"},{\"id\":132,\"calendar\":\"SUNDAY\",\"time\":\"22:29\"},{\"id\":133,\"calendar\":\"SUNDAY\",\"time\":\"23:29\"}],\"rowsAffected\":0}"; 
		String conteudo = postRequest(url, params);
		
		JSONObject jsonObject = new JSONObject(conteudo); 
		result = jsonObject.getJSONArray("rows");
			
		return result;
	}

	
	private static String postRequest(URL url, String params)throws IOException{
		
		String result = null;
		
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		urlConnection.setRequestMethod("POST");
		urlConnection.setDoOutput(true);
		urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); 
		urlConnection.setRequestProperty("Accept", "application/json"); 
		urlConnection.setRequestProperty("X-AppGlu-Environment", "staging");
		
		//TODO
		urlConnection.setRequestProperty("Authorization", "Basic V0tENE43WU1BMXVpTThWOkR0ZFR0ek1MUWxBMGhrMkMxWWk1cEx5VklsQVE2OA=="); 
		
		OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
	    writer.write(params);
	    writer.close();				
    
		int status = urlConnection.getResponseCode();
		
		if(status == 200)
		{
			InputStream inputStream = urlConnection.getInputStream();
			
			Scanner scanner = new Scanner(inputStream, "UTF-8");
			result = scanner.useDelimiter("\\A").next();
			
			scanner.close();
			
			urlConnection.disconnect();
		}			
		else if (status <= 499) 
		{
			//TODO
            System.out.println(urlConnection.getResponseMessage());
            BufferedInputStream in = new BufferedInputStream(urlConnection.getErrorStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) 
            {
                sb.append(line);
            }
            
            throw new IOException(sb.toString());
        }			
		
		return result;
	}
}