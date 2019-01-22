package li.nicetry.app;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import li.nicetry.app.service.RestServiceClientErrorHandler;

@Configuration
@AutoConfigureBefore({ WebMvcAutoConfiguration.class, ErrorMvcAutoConfiguration.class })
@ComponentScan(basePackageClasses = AppConfig.class)
public class AppConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

	@Bean
	@ConditionalOnMissingBean(MessageSource.class)
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	@ConditionalOnMissingBean(LocalValidatorFactoryBean.class)
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}

	@Bean
	@ConditionalOnMissingBean(Jackson2ObjectMapperBuilder.class)
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
		builder.modulesToInstall(new JavaTimeModule());
		builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return builder;
	}

	@Bean
	@ConditionalOnMissingBean(RestTemplate.class)
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

	@Bean
	@ConditionalOnMissingBean(RestTemplateBuilder.class)
	public RestTemplateBuilder restTemplateBuilder(RestTemplateCustomizer restTemplateCustomizer) {
		return new RestTemplateBuilder(restTemplateCustomizer);
	}

	@Bean
	@ConditionalOnMissingBean(RestServiceClientErrorHandler.class)
	public RestServiceClientErrorHandler restServiceClientErrorHandler() {
		return new RestServiceClientErrorHandler();
	}

	@Bean
	@ConditionalOnMissingBean(RestTemplateCustomizer.class)
	public RestTemplateCustomizer restTemplateCustomizer() {
		return restTemplate -> {
			List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<HttpMessageConverter<?>>();
			httpMessageConverters.add(mappingJackson2HttpMessageConverter());
			httpMessageConverters.add(new ByteArrayHttpMessageConverter());
			restTemplate.setMessageConverters(httpMessageConverters);
			restTemplate.setErrorHandler(restServiceClientErrorHandler());
		};
	}

	@Bean
	@ConditionalOnMissingBean(MappingJackson2HttpMessageConverter.class)
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(objectMapperBuilder().build());
		return converter;
	}
}
