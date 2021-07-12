package com.fisco.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateCodeDTO {
    private String certificateCode;

    public void getCertCode(byte[] CertCode) throws UnsupportedEncodingException {
        this.certificateCode = Base64.encodeBase64String(CertCode);
                //new String(CertCode,"ISO-8859-1");
    }
}
