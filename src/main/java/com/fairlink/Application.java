/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fairlink;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@Slf4j
@Controller
@SpringBootApplication
public class Application {


    private final static String APP_ID = "2016080300155645";
    private final static String APP_PRIVATE_KEY =
            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC4WmUFv1m8FX79\n" +
                    "Hwdfblt3F8iJ39FvlkCfEWdwUA4vq8SgZrn3BnaDJxsZE5JK25NWgJmMoWQo0/Dy\n" +
                    "Y2PXyssan1Vex5OhA23VJ2oNQO2NaE+2/KGcsVnMgY5JRbMsvLdqNqcjlqoL16GW\n" +
                    "55FEs/e+gXWlr4BriMAyTHLteKCCzqtQ3IzwkW+dl5hvZtqS0ryJm90U5G0OY1d1\n" +
                    "B/o67ohZjmsfBPVsszf2S4uISQV+jOHW9QLX1MSGHOtYY7NMM8v14r2JQzk1bFKM\n" +
                    "saeNgoI3PFRLR5GGH67RGPd6tZeWlF3PQ8x+PGMAz8xMI2v8V4go7UaEcpDxInAQ\n" +
                    "iBgaHghpAgMBAAECggEAOkpKuwB/7RqW5o4TFLeeMA/TFtUwMao+kI0+OaL2eVhE\n" +
                    "ebtBfbwLaPjKdqlbjlPTSHytEGxPcSFihCGtBiRXB/ntUmSkZzrgVY961un6ssWS\n" +
                    "vvcuWo8xJ5qnVL+3IaQiEnFpc7VF2yy5KS/bHPASm6fvfQ36+wVoT/8gL7n+dp08\n" +
                    "433WBrukeNqOWgd4ph+MfnoBQVrU1A75qc29eF9Zu3PbmpWGuJ1Ss2scUoWKzGoz\n" +
                    "XFyDxVfT4ttBA/fcTMsJcP9i7jMEqrPakxSzY0qWmxfRrnU7WoEY2YOCuzbwsYjF\n" +
                    "m2lnA5vak0PfIKZediMgK3oT80nF3rWk+nuOK7zLAQKBgQDc6Q18QgIWddbZ3jr5\n" +
                    "Eu5NPmPegP7Wkrkv1Ln1HtWEJWhYqRx5cZn+Xe9Y7s+M7bEO2PWYvv9XyDrtesWO\n" +
                    "XckGFJ9pQRT9uT1Xjh2bvvByCTKtrhPY5OUhJDftDUqAyDqiCYUhPD5fgBPruatZ\n" +
                    "SPkNn5Vs3IRLoj+AmHxsXPYEkQKBgQDVos19Y+V3MbKSYcJFM5dpgwcsty3ITuVo\n" +
                    "eaTgsE+lBLbfdS16TIki2scWfH5fHfqJ/2hZDNrxtkI003OxSiQ6hrKjzCvsfzdX\n" +
                    "N8+i2OYYtUJx7uAi4KxkLtbCWFiux4NT5cdWE6EVn+9D3ghOCwnM59kt3mpXF4vk\n" +
                    "kjRuWSBSWQKBgQCG+q+OuRZleuMohC0se3C4KeMD1XXgzg3xN93X9FsqP2Eeq1YG\n" +
                    "rm8ViXfxsxmGVsXU+KQ4DJwgmuvGpzmG368w0/EDcwzMax8cHG3i+CwawZwBm1ft\n" +
                    "Mw79zQ+O/IwFXFLXJ1H9GL5TxAJoCi60g24J8PRwa906CY0bmhCMddCswQKBgHR2\n" +
                    "Rw2XOs2qww13y59MC7vSWUPwLD4pfHhi8BYpJ5HvKv5Qc9VbCdt9ZtEEVyPlROKb\n" +
                    "4LfDAUyJrjf8GfLJV0ysh5AxyrQnefTLER9WnMFVdv6DWZ7J2lDXE+5omRfW3eoL\n" +
                    "topfMw6F3LqjvOVGsTYKImQV/uSximq1pW2OmscpAoGAe4+Rhxw6wHrsUNbhp8PU\n" +
                    "8ScLODtEO17LPGPDRPfmmATck3763JyWTU6C5zZeJiP9UQy3lFU7FP8GCbGUma4C\n" +
                    "eVfEQXndD/TI/2nkjqdHEykrbFn2s48w1m/2R2n2NqHUKcnQ3x9e3MQ+xsexOatl\n" +
                    "gsRfsOGAhnk3iolbAYQax2E=";

    private final static String ALIPAY_PUBLIC_KEY =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuFplBb9ZvBV+/R8HX25b\n" +
                    "dxfIid/Rb5ZAnxFncFAOL6vEoGa59wZ2gycbGROSStuTVoCZjKFkKNPw8mNj18rL\n" +
                    "Gp9VXseToQNt1SdqDUDtjWhPtvyhnLFZzIGOSUWzLLy3ajanI5aqC9ehlueRRLP3\n" +
                    "voF1pa+Aa4jAMkxy7Xiggs6rUNyM8JFvnZeYb2baktK8iZvdFORtDmNXdQf6Ou6I\n" +
                    "WY5rHwT1bLM39kuLiEkFfozh1vUC19TEhhzrWGOzTDPL9eK9iUM5NWxSjLGnjYKC\n" +
                    "NzxUS0eRhh+u0Rj3erWXlpRdz0PMfjxjAM/MTCNr/FeIKO1GhHKQ8SJwEIgYGh4I\n" +
                    "aQIDAQAB\n";


    @RequestMapping("/")
    @ResponseBody
    public String index() {

        return "home";//就是orderString 可以直接给客户端请求，无需再做处理。
    }

    /**
     * 要求外部订单号必须唯一。
     * @return
     */
    private static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    @RequestMapping("/order_info")
    @ResponseBody
    public String pay() throws AlipayApiException {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY,
                "json", "utf-8", ALIPAY_PUBLIC_KEY, "RSA");
//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        model.setOutTradeNo(getOutTradeNo());
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("商户外网可以访问的异步地址");
        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        return response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
    }


    public static void main(String[] args) {
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);

        SpringApplication.run(Application.class, args);
    }

}
