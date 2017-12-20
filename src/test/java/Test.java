import com.alibaba.fastjson.JSONObject;
import com.zero.wxpay.WXPaySender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * test
 *
 * @author Zero Lee
 * @date 2017-12-20
 * @time 15:57
 */
public class Test {

    public static void main(String[] args) throws Exception {
        WXPaySender hdwx = new WXPaySender();

        // 测试统一下单
        Map result1 = hdwx.doUnifiedOrder("测试产品", "HD000000000002", "1", "110.96.88.216");
        System.out.println(result1);
        // 返回结果示例：{result_code=SUCCESS, sign=C450A524C59BD8CCEC481944930C1FF3, mch_id=1241847902, prepay_id=wx20171220134814aeda237e800916533799, return_msg=OK, appid=wx79e850ab659bb39a, code_url=weixin://wxpay/bizpayurl?pr=loOd8S0, nonce_str=29wXKCJ6zoJFAoNI, return_code=SUCCESS, trade_type=NATIVE}

        // 测试关闭订单
        Map result2 = hdwx.doOrderClose("HD000000000002");
        System.out.println(result2);
        // 返回结果示例：{result_code=SUCCESS, sign=F70E37F239789FB14D0C45BB35DE629B, mch_id=1241847902, sub_mch_id=, return_msg=OK, appid=wx79e850ab659bb39a, nonce_str=aQi4eJRxoRY2yWsD, return_code=SUCCESS}

    }

}
