package com.zero.wxpay;

import com.github.wxpay.sdk.WXPayUtil;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 接受微信系统退款申请异步推送
 *
 * @author Zero Lee
 * @date 2017-12-20
 * @time 14:43
 */
@WebServlet("/WXPayRefundReciver")
public class WXPayRefundReciver extends HttpServlet {

    private WXPayConfigImpl config = WXPayConfigImpl.getInstance();

    private static final long serialVersionUID = 1L;

    public WXPayRefundReciver() throws Exception {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{

            // 获取推送结果
            InputStream in = request.getInputStream();
            int len =0;
            byte[] b = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while((len = in.read(b)) != -1){
                bos.write(b, 0, len);
            }
            byte[] c = bos.toByteArray();
            String xmlStr =  new String(c,"utf-8");

            // 转换报文格式为map
            Map<String, String> result = WXPayUtil.xmlToMap(xmlStr);
            System.out.println(result);

            String req_info = result.get("req_info");
            // 解密
            String decryptStr =decryptData(req_info);
            Map data = WXPayUtil.xmlToMap(decryptStr);
            System.out.println(data);

            // 响应微信系统
            HashMap<String, String> resp = new HashMap<>();
            resp.put("return_code", "SUCCESS");
            resp.put("return_msg", "OK");
            response.getOutputStream().write(WXPayUtil.mapToXml(resp).getBytes());

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public String decryptData(String base64Data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec key_ =new SecretKeySpec(DigestUtils.md5Hex(config.getKey()).toLowerCase().getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key_);
        return new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(base64Data)));
    }
}
