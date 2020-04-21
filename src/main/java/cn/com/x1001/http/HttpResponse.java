package cn.com.x1001.http;

public class HttpResponse {
    private Object response;
    public HttpResponse(Object response) {
        this.response = response;
    }
    public Object getResponse() {
        return response;
    }
}
