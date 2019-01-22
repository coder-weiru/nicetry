package li.nicetry.app.service;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestServiceClientErrorHandler implements ResponseErrorHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(RestServiceClientErrorHandler.class);

	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {
		logErrorResponse(httpResponse);
	}

	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
		return (CLIENT_ERROR == httpResponse.getStatusCode().series()
				|| SERVER_ERROR == httpResponse.getStatusCode().series());
	}

	private void logErrorResponse(ClientHttpResponse httpResponse) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("Error occurred while invoking REST service: \n");
		sb.append("\t: Status Code: " + httpResponse.getStatusCode().name());
		sb.append("\t: Status Text: " + httpResponse.getStatusText().toString());
		sb.append("\t: Response Body: " + httpResponse.getBody().toString());

		LOGGER.error(sb.toString());
	}
}
