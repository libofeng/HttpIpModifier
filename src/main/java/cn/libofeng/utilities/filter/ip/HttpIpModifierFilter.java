package cn.libofeng.utilities.filter.ip;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The filter to init a request wrapper and put it in the request chain.
 */
public class HttpIpModifierFilter implements Filter {
    private static final String CONFIG_IP_HEADER_NAME_KEY = "ip-header-name";

    private String ipHeaderName = "remote_addr";

    /**
     * Called by the web container to indicate to a filter that it is being placed into
     * service. The servlet container calls the init method exactly once after instantiating the
     * filter. The init method must complete successfully before the filter is asked to do any
     * filtering work. <br><br>
     * The web container cannot place the filter into service if the init method either<br>
     * 1.Throws a ServletException <br>
     * 2.Does not return within a time period defined by the web container
     *
     * @param filterConfig FilterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String initIpHeaderName = filterConfig.getInitParameter(CONFIG_IP_HEADER_NAME_KEY);
        if (initIpHeaderName != null && initIpHeaderName.length() > 0) {
            ipHeaderName = initIpHeaderName;
        }
    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due
     * to a client request for a resource at the end of the chain. The FilterChain passed in to this
     * method allows the Filter to pass on the request and response to the next entity in the
     * chain.<p>
     * A typical implementation of this method would follow the following pattern:- <br>
     * 1. Examine the request<br>
     * 2. Optionally wrap the request object with a custom implementation to
     * filter content or headers for input filtering <br>
     * 3. Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering <br>
     * 4. a) <strong>Either</strong> invoke the next entity in the chain using the FilterChain object (<code>chain.doFilter()</code>), <br>
     * 4. b) <strong>or</strong> not pass on the request/response pair to the next entity in the filter chain to block the request processing<br>
     * 5. Directly set headers on the response after invocation of the next entity in ther filter chain.
     *
     * @param request  ServletRequest
     * @param response ServletResponse
     * @param chain    FilterChain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpIpModifierRequestWrapper requestWrapper = new HttpIpModifierRequestWrapper(httpServletRequest, ipHeaderName);
        chain.doFilter(requestWrapper, response);
    }

    /**
     * Called by the web container to indicate to a filter that it is being taken out of service. This
     * method is only called once all threads within the filter's doFilter method have exited or after
     * a timeout period has passed. After the web container calls this method, it will not call the
     * doFilter method again on this instance of the filter. <br><br>
     * <p/>
     * This method gives the filter an opportunity to clean up any resources that are being held (for
     * example, memory, file handles, threads) and make sure that any persistent state is synchronized
     * with the filter's current state in memory.
     */
    @Override
    public void destroy() {

    }
}
