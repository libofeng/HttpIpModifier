# HttpIpModifier #
A J2EE HTTP filter for reading request ip from the right HTTP header.

## What it can do ##
When you deploy your web app behind the Nginx or other front-end servers, ip value in http header will be the ip of the front-end server but not the client's, this is obviously not what we want . Here is the small utility to handle this situation by override the getRemoteAddr() method of HttpServletRequest.

## Configuration ##
What you need to do is to add this filter to your project and set the correct http header name of ip value, here is the example configuration for web.xml:


>     <filter>
>         <filter-name>HttpIpModifierFilter</filter-name>
>         <filter-class>cn.libofeng.utilities.filter.ip.HttpIpModifierFilter</filter-class>
>         <init-param>
>             <param-name>ip-header-name</param-name>
>             <param-value>HTTP_X_FORWARDED_FOR</param-value>
>         </init-param>
>     </filter>
>     <filter-mapping>
>         <filter-name>HttpIpModifierFilter</filter-name>
>         <url-pattern>/*</url-pattern>
>     </filter-mapping>