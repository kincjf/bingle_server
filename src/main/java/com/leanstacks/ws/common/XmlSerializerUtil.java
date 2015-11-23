package com.leanstacks.ws.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

public class XmlSerializerUtil {
	
	private Marshaller marshaller;
    private Unmarshaller unmarshaller;
	
	public void setMarshaller(Jaxb2Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public void setUnmarshaller(Jaxb2Marshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
	
	/**
	 * Converts Object to XML file
	 * @param fileName
	 * @param target
	 * @throws IOException
	 */
	public void serialize(String fileName, Object target) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            marshaller.marshal(target, new StreamResult(fos));
        } finally {
        	fos.close();
        }
    }
	
	/**
	 * Converts XML to Java Object
	 * @param fileName
	 * @return Object
	 * @throws IOException
	 */
    public Object deserialize(String fileName) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
            return unmarshaller.unmarshal(new StreamSource(fis));
        } finally {
        	fis.close();
        }
    }
}
