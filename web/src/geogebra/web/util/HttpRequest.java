package geogebra.web.util;

import geogebra.common.main.AbstractApplication;

import com.google.gwt.http.client.*;

/**
 * @author Zoltan Kovacs <zoltan@geogebra.org>
 * Implements HTTP requests and responses for web.
 */
public class HttpRequest extends geogebra.common.util.HttpRequest {
	
	/* The following code has been copied mostly from
	 * http://code.google.com/intl/hu-HU/webtoolkit/doc/latest/DevGuideServerCommunication.html#DevGuideHttpRequests
	 */
	@Override
    public void sendRequest(String url) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));

		try {
			builder.setTimeoutMillis(timeout * 1000);
			AbstractApplication.debug("Sending request " + url + " until timeout " + timeout);
			Request request = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					// Couldn't connect to server (could be timeout, SOP violation, etc.)
					responseText = exception.getMessage();
					success = false;
					processed = true;
				}
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						// Process the response in response.getText()
						responseText = response.getText();
						success = true;
						processed = true;
					} else {
						// Handle the error.  Can get the status text from response.getStatusText()
						responseText = response.getStatusText();
						success = false;
						processed = true;
					}
				}
			});
		} catch (RequestException e) {
			// Couldn't connect to server
			success = false;
			processed = true;
		}
	}

	@Override
    public String sendRequestGetResponseSync(String url) {
		AbstractApplication.warn("not available");
	    return null;
    }

	@Override
    public void sendRequestPost(String url, String post) {
	    // TODO: Implement this as a real post,
		// currently we fall back to GET:
		sendRequest(url + "?" + post);	    
    }
}