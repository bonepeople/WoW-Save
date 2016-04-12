import java.util.*;
// import java.util.Base64;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Data_url
{
	private static String oauth_request_method = "GET";
	private static String requst_token_url = "";
	private static String oauth_consumer_key = "xcS7PsEKuX9ixPGB";
	private static String oauth_nonce = "";
	private static String oauth_signature_method ="HMAC-SHA1";
	private static String oauth_timestamp = "";
	private static String oauth_version = "1.0";
	private static String consumer_secret = "minHrUbWn1k5AqPZ";
	private static String oauth_token = "05f9b66c14fda958c841b584";
	private static String oauth_token_secret = "50faffd392f2445bb0b474c245db6954";
	private static String oauth_signature = "";
	
	
	
	private static String set_nonce()
	{
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 8; i++)
		{
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	
	private static String set_timestamp()
	{
		//需要说明一下的是这里的时间戳为10位而不是13位，因此截取0-10位置。
		java.util.Date date = new Date();
		long time = date.getTime();
		return (time + "").substring(0, 10);
	}
	
	
	private static String hmacsha1(String data)
	{
		byte[] byteHMAC = null;
		String key = consumer_secret + "&" + oauth_token_secret;
    		try
		{
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec spec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			mac.init(spec);
			byteHMAC = mac.doFinal(data.getBytes());
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException ignore)
		{
			
		}
		String oauth = Base64.getEncoder().encodeToString(byteHMAC);
		
		return oauth;
	}
	
	
	private static String oauthEncode(String __str)
	{
		try
		{
			return URLEncoder.encode(__str, "utf-8").replace("*", "%2A").replace("%7E", "~").replace("+", "%20");
		}
		catch(UnsupportedEncodingException __e)
		{
			return null;
		}
	}
	
	public static String get_requesttoken()
	{
		//返回值
		//{"oauth_token_secret":"1d3daa322ad64c6aa53348a649f64bdd","oauth_token":"1d7adeee1c144866bd0bc632278ae7c5","expires_in":1800,"oauth_callback_confirmed":false}

		String basic_str;
		String requestToken = "";
		
		try
		{
			requst_token_url = "https://openapi.kuaipan.cn/open/requestToken";
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			
			String bss;
			bss = oauth_request_method + "&"
			+ oauthEncode(requst_token_url) + "&";
			
			String bsss = "oauth_consumer_key=" + oauth_consumer_key 
			+ "&oauth_nonce=" + oauth_nonce 
			+ "&oauth_signature_method=" + oauth_signature_method 
			+ "&oauth_timestamp=" + oauth_timestamp 
			+ "&oauth_version=" + oauth_version;
			bsss = oauthEncode(bsss);
			
			basic_str = bss + bsss;
			
			oauth_signature = hmacsha1(basic_str);
			
			
			requestToken = requst_token_url + "?" 
			+ "oauth_nonce=" + oauthEncode(oauth_nonce)
			+ "&oauth_timestamp=" + oauthEncode(oauth_timestamp)
			+ "&oauth_consumer_key=" + oauthEncode(oauth_consumer_key)
			+ "&oauth_signature_method=" + oauthEncode(oauth_signature_method)
			+ "&oauth_version=" + oauthEncode(oauth_version)
			+ "&oauth_signature=" + oauthEncode(oauth_signature);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return requestToken;
		}

	}
	
	public static String get_accesstoken(String oauth_verifier)
	{
		//返回值
		//{"oauth_token_secret":"50faffd392f2445bb0b474c245db6954","oauth_token":"05f9b66c14fda958c841b584","charged_dir":"430575814363316227","user_id":100251244,"expires_in":31536000}
		
		String basic_str;
		String accesstoken = "";
		
		try
		{
			requst_token_url = "https://openapi.kuaipan.cn/open/accessToken";
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			
			String bss;
			bss = oauth_request_method + "&"
			+ oauthEncode(requst_token_url) + "&";
			
			String bsss = "oauth_consumer_key=" + oauth_consumer_key 
			+ "&oauth_nonce=" + oauth_nonce 
			+ "&oauth_signature_method=" + oauth_signature_method 
			+ "&oauth_timestamp=" + oauth_timestamp 
			+ "&oauth_token=" + oauth_token
			+ "&oauth_verifier=" + oauth_verifier
			+ "&oauth_version=" + oauth_version;
			bsss = oauthEncode(bsss);
			
			basic_str = bss + bsss;
			
			oauth_signature = hmacsha1(basic_str);
			
			
			accesstoken = requst_token_url + "?" 
			+ "oauth_nonce=" + oauthEncode(oauth_nonce)
			+ "&oauth_timestamp=" + oauthEncode(oauth_timestamp)
			+ "&oauth_consumer_key=" + oauthEncode(oauth_consumer_key)
			+ "&oauth_signature_method=" + oauthEncode(oauth_signature_method)
			+ "&oauth_version=" + oauthEncode(oauth_version)
			+ "&oauth_token=" + oauthEncode(oauth_token)
			+ "&oauth_verifier=" + oauthEncode(oauth_verifier)
			+ "&oauth_signature=" + oauthEncode(oauth_signature);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return accesstoken;
		}
		
	}
	
	public static String get_account_info()
	{
		//返回值
		//{"max_file_size":4294967296,"catalogs":[],"user_id":100251244,"fields":[],"mobile":"","quota_total":5893017600,"quota_used":89921411,"user_name":"1397040303@qq.com"}
		
		String basic_str;
		String account_info = "";
		
		try
		{
			requst_token_url = "http://openapi.kuaipan.cn/1/account_info";
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			
			String bss;
			bss = oauth_request_method + "&"
			+ oauthEncode(requst_token_url) + "&";
			
			String bsss = "oauth_consumer_key=" + oauth_consumer_key 
			+ "&oauth_nonce=" + oauth_nonce 
			+ "&oauth_signature_method=" + oauth_signature_method 
			+ "&oauth_timestamp=" + oauth_timestamp 
			+ "&oauth_token=" + oauth_token
			+ "&oauth_version=" + oauth_version;
			bsss = oauthEncode(bsss);
			
			basic_str = bss + bsss;
			
			oauth_signature = hmacsha1(basic_str);
			
			
			account_info = requst_token_url + "?" 
			+ "oauth_nonce=" + oauthEncode(oauth_nonce)
			+ "&oauth_timestamp=" + oauthEncode(oauth_timestamp)
			+ "&oauth_consumer_key=" + oauthEncode(oauth_consumer_key)
			+ "&oauth_signature_method=" + oauthEncode(oauth_signature_method)
			+ "&oauth_version=" + oauthEncode(oauth_version)
			+ "&oauth_token=" + oauthEncode(oauth_token)
			+ "&oauth_signature=" + oauthEncode(oauth_signature);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return account_info;
		}
		
	}
	
	
	public static String get_metadata(String path)//("abc/")
	{
		//url:http://openapi.kuaipan.cn/1/metadata/<root>/<path>(abc/)
		//返回值
		//{"msg":"file not exist"}
		//{"files":[{"sha1":"","is_deleted":false,"name":"123","rev":"1","create_time":"2015-08-26 19:38:44","modify_time":"2015-08-26 19:38:44","share_id":"0","size":0,"type":"folder","file_id":"430575814363316229"},{"sha1":"","is_deleted":false,"name":"BONEPEOPLE","rev":"1","create_time":"2015-08-26 19:40:04","modify_time":"2015-08-26 19:40:04","share_id":"0","size":0,"type":"folder","file_id":"430575814363316231"},{"sha1":"","is_deleted":false,"name":"abc","rev":"1","create_time":"2015-08-26 19:38:29","modify_time":"2015-08-26 19:38:29","share_id":"0","size":0,"type":"folder","file_id":"430575814363316228"}],"sha1":"xcS7PsEKuX9ixPGB","is_deleted":false,"name":"WoW save","root":"app_folder","rev":"1","file_id":"430575814363316227","share_id":"0","create_time":"2015-08-21 19:11:50","modify_time":"2015-08-21 19:11:50","path":"\/","hash":"705f9b66c","size":0,"type":"folder","files_total":3}
		
		String basic_str;
		String metadata = "";
		
		try
		{
			requst_token_url = "http://openapi.kuaipan.cn/1/metadata/app_folder/" + URLEncoder.encode(path, "utf-8").replace("%2F", "/");
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			
			String bss;
			bss = oauth_request_method + "&"
			+ oauthEncode(requst_token_url) + "&";
			
			String bsss = "oauth_consumer_key=" + oauth_consumer_key 
			+ "&oauth_nonce=" + oauth_nonce 
			+ "&oauth_signature_method=" + oauth_signature_method 
			+ "&oauth_timestamp=" + oauth_timestamp 
			+ "&oauth_token=" + oauth_token
			+ "&oauth_version=" + oauth_version;
			bsss = oauthEncode(bsss);
			
			basic_str = bss + bsss;
			
			oauth_signature = hmacsha1(basic_str);
			
			
			metadata = requst_token_url + "?" 
			+ "oauth_nonce=" + oauthEncode(oauth_nonce)
			+ "&oauth_timestamp=" + oauthEncode(oauth_timestamp)
			+ "&oauth_consumer_key=" + oauthEncode(oauth_consumer_key)
			+ "&oauth_signature_method=" + oauthEncode(oauth_signature_method)
			+ "&oauth_version=" + oauthEncode(oauth_version)
			+ "&oauth_token=" + oauthEncode(oauth_token)
			+ "&oauth_signature=" + oauthEncode(oauth_signature);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return metadata;
		}
		
	}
	
	
	public static String get_create_folder(String path)//("abc/")
	{
		//返回值
		//{"msg":"ok","path":"\/987654321","root":"app_folder","file_id":"430575814363317148"}
		
		String basic_str;
		String metadata = "";
		try
		{
			requst_token_url = "http://openapi.kuaipan.cn/1/fileops/create_folder";
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			
			String bss;
			bss = oauth_request_method + "&"
			+ oauthEncode(requst_token_url) + "&";
			
			String bsss = "oauth_consumer_key=" + oauth_consumer_key 
			+ "&oauth_nonce=" + oauth_nonce 
			+ "&oauth_signature_method=" + oauth_signature_method 
			+ "&oauth_timestamp=" + oauth_timestamp 
			+ "&oauth_token=" + oauth_token
			+ "&oauth_version=" + oauth_version
			+ "&path=" + oauthEncode(path)
			+ "&root=app_folder";
			bsss = oauthEncode(bsss);
			
			basic_str = bss + bsss;
			
			oauth_signature = hmacsha1(basic_str);
			
			
			metadata = requst_token_url + "?" 
			+ "oauth_nonce=" + oauthEncode(oauth_nonce)
			+ "&oauth_timestamp=" + oauthEncode(oauth_timestamp)
			+ "&oauth_consumer_key=" + oauthEncode(oauth_consumer_key)
			+ "&oauth_signature_method=" + oauthEncode(oauth_signature_method)
			+ "&oauth_version=" + oauthEncode(oauth_version)
			+ "&oauth_token=" + oauthEncode(oauth_token)
			+ "&oauth_signature=" + oauthEncode(oauth_signature)
			+ "&path=" + oauthEncode(path)
			+ "&root=" + oauthEncode("app_folder");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return metadata;
		}
		
	}
	
	
	public static String get_upload_locate()
	{
		//返回值
		//{"url": "http://p5.dfs.kuaipan.cn:8080/cdlnode/", "stat": "OK"}
		
		String basic_str;
		String upload_locate = "";
		
		try
		{
			requst_token_url = "http://api-content.dfs.kuaipan.cn/1/fileops/upload_locate";
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			
			String bss;
			bss = oauth_request_method + "&"
			+ oauthEncode(requst_token_url) + "&";
			
			String bsss = "oauth_consumer_key=" + oauth_consumer_key 
			+ "&oauth_nonce=" + oauth_nonce 
			+ "&oauth_signature_method=" + oauth_signature_method 
			+ "&oauth_timestamp=" + oauth_timestamp 
			+ "&oauth_token=" + oauth_token
			+ "&oauth_version=" + oauth_version;
			bsss = oauthEncode(bsss);
			
			basic_str = bss + bsss;
			
			oauth_signature = hmacsha1(basic_str);
			
			
			upload_locate = requst_token_url + "?" 
			+ "oauth_nonce=" + oauthEncode(oauth_nonce)
			+ "&oauth_timestamp=" + oauthEncode(oauth_timestamp)
			+ "&oauth_consumer_key=" + oauthEncode(oauth_consumer_key)
			+ "&oauth_signature_method=" + oauthEncode(oauth_signature_method)
			+ "&oauth_version=" + oauthEncode(oauth_version)
			+ "&oauth_token=" + oauthEncode(oauth_token)
			+ "&oauth_signature=" + oauthEncode(oauth_signature);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return upload_locate;
		}
		
	}
	
	public static String get_upload_file(String upload_url, String path)//("http://p5.dfs.kuaipan.cn:8080/cdlnode","/TradeLog.txt")
	{
		//返回值
		//{"type": "file", "rev": "1", "file_id": "430575814363317111", "size": "169"}
		
		String basic_str;
		String upload_file = "";
		
		try
		{
			requst_token_url = upload_url + "/1/fileops/upload_file";
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			oauth_request_method = "POST";
			
			String bss;
			bss = oauth_request_method + "&"
			+ oauthEncode(requst_token_url) + "&";
			
			String bsss = "oauth_consumer_key=" + oauth_consumer_key 
			+ "&oauth_nonce=" + oauth_nonce 
			+ "&oauth_signature_method=" + oauth_signature_method 
			+ "&oauth_timestamp=" + oauth_timestamp 
			+ "&oauth_token=" + oauth_token
			+ "&oauth_version=" + oauth_version
			+ "&overwrite=true"
			+ "&path=" + oauthEncode(path)
			+ "&root=app_folder";
			bsss = oauthEncode(bsss);
			
			basic_str = bss + bsss;
			
			oauth_signature = hmacsha1(basic_str);
			
			
			upload_file = requst_token_url + "?" 
			+ "oauth_consumer_key=" + oauthEncode(oauth_consumer_key)
			+ "&oauth_nonce=" + oauthEncode(oauth_nonce)
			+ "&oauth_signature=" + oauthEncode(oauth_signature)
			+ "&oauth_signature_method=" + oauthEncode(oauth_signature_method)
			+ "&oauth_timestamp=" + oauthEncode(oauth_timestamp)
			+ "&oauth_token=" + oauthEncode(oauth_token)
			+ "&oauth_version=" + oauthEncode(oauth_version)
			+ "&overwrite=" + oauthEncode("true")
			+ "&path=" + oauthEncode(path)
			+ "&root=" + oauthEncode("app_folder");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			oauth_request_method = "GET";
			return upload_file;
		}
		
	}
	
	
	public static String get_download_file(String path)//("/TradeLog.txt")
	{
		//返回值
		//{"url": "http://p5.dfs.kuaipan.cn:8080/cdlnode/", "stat": "OK"}
		
		String basic_str;
		String download_file = "";
		
		try
		{
			requst_token_url = "http://api-content.dfs.kuaipan.cn/1/fileops/download_file";
			oauth_nonce = set_nonce();
			oauth_timestamp = set_timestamp();
			
			String bss;
			bss = oauth_request_method + "&"
			+ oauthEncode(requst_token_url) + "&";
			
			String bsss = "oauth_consumer_key=" + oauth_consumer_key 
			+ "&oauth_nonce=" + oauth_nonce 
			+ "&oauth_signature_method=" + oauth_signature_method 
			+ "&oauth_timestamp=" + oauth_timestamp 
			+ "&oauth_token=" + oauth_token
			+ "&oauth_version=" + oauth_version
			+ "&path=" + oauthEncode(path)
			+ "&root=" + "app_folder";
			bsss = oauthEncode(bsss);
			
			basic_str = bss + bsss;
			
			oauth_signature = hmacsha1(basic_str);
			
			download_file = requst_token_url + "?" 
			+ "oauth_nonce=" + oauthEncode(oauth_nonce)
			+ "&oauth_timestamp=" + oauthEncode(oauth_timestamp)
			+ "&oauth_consumer_key=" + oauthEncode(oauth_consumer_key)
			+ "&oauth_signature_method=" + oauthEncode(oauth_signature_method)
			+ "&oauth_version=" + oauthEncode(oauth_version)
			+ "&oauth_token=" + oauthEncode(oauth_token)
			+ "&oauth_signature=" + oauthEncode(oauth_signature)
			+ "&path=" + oauthEncode(path)
			+ "&root=" + oauthEncode("app_folder");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			return download_file;
		}
		
	}
}
























