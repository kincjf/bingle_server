package com.leanstacks.ws;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.Marshaller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.leanstacks.ws.model.APS.APSConfig;
import com.leanstacks.ws.util.XmlSerializerUtil;

@Configuration
public class AppConfig {
	
	/**
	 * spring bean으로 생성됨
	 * @return
	 */
	@Bean
	public XmlSerializerUtil getHandler(){
	  XmlSerializerUtil handler = new XmlSerializerUtil();
	  handler.setMarshaller(getMarshaller());
	  handler.setUnmarshaller(getMarshaller());
	  return handler;
	}
	
	@Bean
	public Jaxb2Marshaller getMarshaller() {
	  Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
	  jaxb2Marshaller.setClassesToBeBound(APSConfig.class);  
	  jaxb2Marshaller.setMarshallerProperties(getMarshallerProperties());
      return jaxb2Marshaller;
	}
	
    @Bean
    public Map<String, ?> getMarshallerProperties() {
    	Map<String, Object> map = new HashMap<>();
        map.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		map.put(Marshaller.JAXB_ENCODING, "UTF-8");
        return map;    	
    }
} 
