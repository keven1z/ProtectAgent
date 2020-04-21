package cn.com.x1001;

import cn.com.x1001.http.AbstractRequest;


/**
 * 流量接口
 */
public interface FlowInterface {
    /*在请求结束*/
    public void onRequestEnter(AbstractRequest request);
    /*在返回开始*/
    public void onResponseStart();
}
