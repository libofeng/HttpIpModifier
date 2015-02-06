package cn.libofeng.utilities.filter.ip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * A HttpRequest wrapper to override the "request.getRemoteAddr()" method
 */
class HttpIpModifierRequestWrapper extends HttpServletRequestWrapper {
    // http header name of holding request ip
    private String ipHeaderName;

    public HttpIpModifierRequestWrapper(HttpServletRequest request, String ipHeaderName) {
        super(request);
        this.ipHeaderName = ipHeaderName;
    }

    /**
     * The default behavior of this method is to return getRemoteAddr()
     * on the wrapped request object.
     */
    @Override
    public String getRemoteAddr() {
        String ip;
        if (ipHeaderName == null || ipHeaderName.length() == 0) {
            ip = super.getRemoteAddr();
        } else {
            ip = getHeader(ipHeaderName);
        }

        return ip;
    }
}
