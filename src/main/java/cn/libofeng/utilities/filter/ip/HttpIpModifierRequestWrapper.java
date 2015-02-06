package cn.libofeng.utilities.filter.ip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * A HttpRequest wrapper to override the "request.getRemoteAddr()" method
 */
class HttpIpModifierRequestWrapper extends HttpServletRequestWrapper {
    public static final String MARK_COMMA = ",";

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
        // no valid ip header name specified, return default
        if (ipHeaderName == null || ipHeaderName.length() == 0) {
            return super.getRemoteAddr();
        }

        String ip = getHeader(ipHeaderName);
        if (ip != null) {
            int commaIndex = ip.indexOf(MARK_COMMA);
            // multiple ip values set by proxies, get the 1st one.
            // this is based on the format of "client1, proxy1, proxy2,..."
            if (commaIndex > 0) {
                ip = ip.substring(0, commaIndex);
            }
        }

        return ip;
    }
}
