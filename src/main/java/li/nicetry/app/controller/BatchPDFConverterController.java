package li.nicetry.app.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import li.nicetry.app.exception.ServiceException;
import li.nicetry.app.service.UrlConverterService;

@RestController
@Api(value = "", produces = "application/hal+json")
@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BatchPDFConverterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchPDFConverterController.class);

	private UrlConverterService urlConverterService;
	
	@Autowired
	public BatchPDFConverterController(UrlConverterService urlConverterService) {
		Assert.notNull(urlConverterService, "'urlConverterService' must not be null");
		// this.pdfConverterService = pdfConverterService;
	}

	@ApiOperation(value = "Returns the .", 
				notes = ".")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = byte[].class),
			@ApiResponse(code = 400, message = "Input Validation Error"),
			@ApiResponse(code = 401, message = "Unauthorized"), 
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), 
			@ApiResponse(code = 500, message = "Failure") })
	public ResponseEntity<?> process()
	{
		

		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleIOException(IOException exe) {
		LOGGER.error("Error:", exe);

		Map<String, Object> responseMap = new LinkedHashMap<String, Object>();
		responseMap.put("message", exe.getMessage());
		responseMap.put("code", HttpStatus.BAD_REQUEST);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<Map<String, Object>>(responseMap, httpHeaders, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<Map<String, Object>> handleApiServiceException(ServiceException exe) {
		LOGGER.error("Error:", exe);

		Map<String, Object> responseMap = new LinkedHashMap<String, Object>();
		responseMap.put("message", exe.getMessage());
		responseMap.put("code", HttpStatus.INTERNAL_SERVER_ERROR);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<Map<String, Object>>(responseMap, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}