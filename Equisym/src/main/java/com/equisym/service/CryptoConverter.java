package com.equisym.service;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.beans.factory.annotation.Value;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {
 
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    
    @Value("${aes.encryption.key}")
    private String k;
    
    private byte[] KEY;
 
    @Override
    public String convertToDatabaseColumn(String message) 
    {
      // do some encryption
    	KEY = k.getBytes();
      Key key = new SecretKeySpec(KEY, "AES");
      try {
         Cipher c = Cipher.getInstance(ALGORITHM);
         c.init(Cipher.ENCRYPT_MODE, key);
         return Base64.getEncoder().encodeToString(c.doFinal(message.getBytes()));
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
    }
 
    
    public String convertToEntityAttribute(String data) 
    {
      KEY = k.getBytes();
      Key key = new SecretKeySpec(KEY, "AES");
      try {
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        return new String(c.doFinal(Base64.getDecoder().decode(data)));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
}
