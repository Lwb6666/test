/* ======================================================================
 * Example WSDbfetch (REST) client using HttpClient and HTTP GET
 * 
 * ====================================================================== */
package com.dxjr.portal.tzjinterface.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class HttpclientGet {	
	/** Get a web page using HTTP GET.
	 * 
	 * @param urlStr The URL of the page to be retrieved.
	 * @return A string containing the page.
	 */
	public static String getHttpUrl(String urlStr) {
		// Data obtained from service, to be returned
		String retVal = null;
		// Create a client
		HttpClient client = new HttpClient();
		// Create a HTTP GET request
		GetMethod method = new GetMethod(urlStr);
		// Allow redirects to be followed
		method.setFollowRedirects(true);
		try {
			// Execute the request using the client
			int statusCode = client.executeMethod(method);
			// Check the response status code
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
		    }
			// Get the page data, allowing for character encoding
			BufferedReader bis = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), method.getResponseCharSet()));
			int bufLen = 8 * 1024;
			char[] charBuf = new char[ bufLen ];
			StringBuffer strBuf = new StringBuffer();
			int count;
			while( (count = bis.read(charBuf) ) != -1) {
				strBuf.append(charBuf, 0, count);
			}
			bis.close();
			retVal = strBuf.toString();
		}
		catch(HttpException ex) {
			System.out.println(ex.getMessage());			
		}
		catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		finally {
			// Clean-up the connection
			method.releaseConnection();
		}
		// Return the response data
		return retVal;
	}

	/** Execution entry point
	 * 
	 * @param args Command-line arguments
	 * @return Exit status
	 */
	public static void main(String[] args) {
		// Parameters for the dbfetch call
		String dbName = "uniprotkb"; // Database name (e.g. UniProtKB)
		String id = "WAP_RAT"; // Entry identifier, name or accession
		String format = "uniprot"; // Data format
		
		// Construct the dbfetch URL
		// Document style base URL 
		String dbfetchBaseUrl = "http://www.ebi.ac.uk/Tools/dbfetch/dbfetch/";
		// Add the database name, identifiers and format to the URL
		String dbfetchUrl = dbfetchBaseUrl + dbName + "/" + id + "/" + format;

		// Get the page and print it.
		System.out.print(getHttpUrl("http://127.0.0.1:8080/dxjr_portal/api/tzj/login.html?parame1=M0YwN0Q5OERGRTYzMDY5Nw==&parame2=RkQ1NUJENDNCNzk5MDBCRA==&parame3=RDEzRTA5NEMwNkM1RkNCMQ==&parame4=NjBEMzVCNkIzRDEwNUEzMw==&parame5=M0I0OTc4NjREMTE1MTE4MQ==&sign=b6dd7262d853d243c6436b3c577164d7"));
	}
}