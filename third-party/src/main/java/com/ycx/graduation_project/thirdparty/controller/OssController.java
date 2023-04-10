package com.ycx.graduation_project.thirdparty.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author YangChenxi
 */
@RestController
public class OssController {
    @Autowired
    OSS ossClient;

    @RequestMapping("/oss/policy")
    public Map<String, String> policy() {
        // 填写Bucket名称，例如examplebucket。
        String bucket = "ycx-graduation-project";
        // 填写Host地址，格式为https://bucketname.endpoint。
        String host = "https://ycx-graduation-project.oss-cn-beijing.aliyuncs.com";
        // 设置上传回调URL，即回调服务器地址，用于处理应用服务器与OSS之间的通信。OSS会在文件上传完成后，把文件上传信息通过此回调URL发送给应用服务器。
        String callbackUrl = "https://192.168.0.0:8888";
        // 设置上传到OSS文件的前缀，可置空此项。置空后，文件将上传至Bucket的根目录下。
        String simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = simpleDateFormat + "/";
        Map<String, String> respMap = new LinkedHashMap<String, String>();
        // 创建ossClient实例。
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);


            respMap.put("accessId", "LTAI5tLW7Kj49WdJzaYU9by1");
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));

        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return respMap;
    }


//    /**
//     * 获取public key
//     *
//     * @param url
//     * @return
//     */
//    @SuppressWarnings({ "finally" })
//    public String executeGet(String url) {
//        BufferedReader in = null;
//
//        String content = null;
//        try {
//            // 定义HttpClient。
//            @SuppressWarnings("resource")
//            DefaultHttpClient client = new DefaultHttpClient();
//            // 实例化HTTP方法。
//            HttpGet request = new HttpGet();
//            request.setURI(new URI(url));
//            HttpResponse response = client.execute(request);
//
//            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//            StringBuffer sb = new StringBuffer("");
//            String line = "";
//            String NL = System.getProperty("line.separator");
//            while ((line = in.readLine()) != null) {
//                sb.append(line + NL);
//            }
//            in.close();
//            content = sb.toString();
//        } catch (Exception e) {
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();// 关闭BufferedReader。
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            return content;
//        }
//    }
//
//    /**
//     * 获取Post消息体
//     *
//     * @param is
//     * @param contentLen
//     * @return
//     */
//    public String GetPostBody(InputStream is, int contentLen) {
//        if (contentLen > 0) {
//            int readLen = 0;
//            int readLengthThisTime = 0;
//            byte[] message = new byte[contentLen];
//            try {
//                while (readLen != contentLen) {
//                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
//                    if (readLengthThisTime == -1) {// Should not happen.
//                        break;
//                    }
//                    readLen += readLengthThisTime;
//                }
//                return new String(message);
//            } catch (IOException e) {
//            }
//        }
//        return "";
//    }
//
//    /**
//     * 验证上传回调的Request
//     *
//     * @param request
//     * @param ossCallbackBody
//     * @return
//     * @throws NumberFormatException
//     * @throws IOException
//     */
//    protected boolean VerifyOSSCallbackRequest(HttpServletRequest request, String ossCallbackBody)
//            throws NumberFormatException, IOException {
//        boolean ret = false;
//        String autorizationInput = new String(request.getHeader("Authorization"));
//        String pubKeyInput = request.getHeader("x-oss-pub-key-url");
//        byte[] authorization = BinaryUtil.fromBase64String(autorizationInput);
//        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
//        String pubKeyAddr = new String(pubKey);
//        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
//                && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
//            System.out.println("pub key addr must be oss addrss");
//            return false;
//        }
//        String retString = executeGet(pubKeyAddr);
//        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
//        retString = retString.replace("-----END PUBLIC KEY-----", "");
//        String queryString = request.getQueryString();
//        String uri = request.getRequestURI();
//        String decodeUri = java.net.URLDecoder.decode(uri, "UTF-8");
//        String authStr = decodeUri;
//        if (queryString != null && !queryString.equals("")) {
//            authStr += "?" + queryString;
//        }
//        authStr += "\n" + ossCallbackBody;
//        ret = doCheck(authStr, authorization, retString);
//        return ret;
//    }
//
//    /**
//     * Post请求
//     */
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String ossCallbackBody = GetPostBody(request.getInputStream(),
//                Integer.parseInt(request.getHeader("content-length")));
//        boolean ret = VerifyOSSCallbackRequest(request, ossCallbackBody);
//        System.out.println("verify result : " + ret);
//        // System.out.println("OSS Callback Body:" + ossCallbackBody);
//        if (ret) {
//            response(request, response, "{\"Status\":\"OK\"}", HttpServletResponse.SC_OK);
//        } else {
//            response(request, response, "{\"Status\":\"verdify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 验证RSA
//     *
//     * @param content
//     * @param sign
//     * @param publicKey
//     * @return
//     */
//    public static boolean doCheck(String content, byte[] sign, String publicKey) {
//        try {
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
//            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
//            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
//            signature.initVerify(pubKey);
//            signature.update(content.getBytes());
//            boolean bverify = signature.verify(sign);
//            return bverify;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    /**
//     * 服务器响应结果
//     *
//     * @param request
//     * @param response
//     * @param results
//     * @param status
//     * @throws IOException
//     */
//    private void response(HttpServletRequest request, HttpServletResponse response, String results, int status)
//            throws IOException {
//        String callbackFunName = request.getParameter("callback");
//        response.addHeader("Content-Length", String.valueOf(results.length()));
//        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
//            response.getWriter().println(results);
//        else
//            response.getWriter().println(callbackFunName + "( " + results + " )");
//        response.setStatus(status);
//        response.flushBuffer();
//    }
//
//    /**
//     * 服务器响应结果
//     */
//    private void response(HttpServletRequest request, HttpServletResponse response, String results) throws IOException {
//        String callbackFunName = request.getParameter("callback");
//        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
//            response.getWriter().println(results);
//        else
//            response.getWriter().println(callbackFunName + "( " + results + " )");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.flushBuffer();
//
}
