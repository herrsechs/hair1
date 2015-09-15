package sse.tongji.hair.httpclient;

import android.content.Context;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.AuthenticationType;
import com.alibaba.sdk.android.oss.model.ClientConfiguration;

/**
 * Created by LLLLLyj on 2015/9/6.
 */
public class OSSClient {
    protected static OSSService ossService;
    protected static ClientConfiguration conf;
    public OSSClient(Context context){
        ossService = OSSServiceProvider.getService();
        ossService.setApplicationContext(context);
        ossService.setGlobalDefaultHostId("codeplay.oss-cn-beijing.aliyuncs.com");
        ossService.setAuthenticationType(AuthenticationType.ORIGIN_AKSK);

    }
}
