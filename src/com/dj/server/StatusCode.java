package com.dj.server;

/**
 * Refer {@link http://hc.apache.org/httpclient-3.x/apidocs/org/apache/commons/httpclient/HttpStatus.html} 
 */
public class StatusCode {

	public static final int SC_ACCEPTED             = 202;
    public static final int SC_BAD_GATEWAY          = 502;
    public static final int SC_BAD_REQUEST          = 400;
    public static final int SC_CREATED              = 201;
    public static final int SC_FORBIDDEN            = 403;
    public static final int SC_INTERNAL_ERROR       = 500;
    public static final int SC_MOVED                = 301;
    public static final int SC_NO_RESPONSE          = 204;
    public static final int SC_NOT_FOUND            = 404;
    public static final int SC_NOT_IMPLEMENTED      = 501;
    public static final int SC_OK                   = 200;
    public static final int SC_PARTIAL_INFORMATION  = 203;
    public static final int SC_PAYMENT_REQUIRED     = 402;
    public static final int SC_SERVICE_OVERLOADED   = 503;
    public static final int SC_UNAUTHORIZED         = 401;
    
    /**
     * Currently the most frequent and basic 200,404 and 501 is implemented.
     */
    public static String getStatusMessage(int sc)
    {
       switch (sc) {
          case SC_ACCEPTED:              return "Accepted";
          case SC_BAD_GATEWAY:           return "Bad Gateway";
          case SC_BAD_REQUEST:           return "Bad Request";
          case SC_CREATED:               return "Created";
          case SC_FORBIDDEN:             return "Forbidden";
          case SC_INTERNAL_ERROR:        return "Internal Error";
          case SC_MOVED:                 return "Moved";
          case SC_NO_RESPONSE:           return "No Response";
          case SC_NOT_FOUND:             return "Not Found";
          case SC_NOT_IMPLEMENTED:       return "Not Implemented";
          case SC_OK:                    return "OK";
          case SC_PARTIAL_INFORMATION:   return "Partial Information";
          case SC_PAYMENT_REQUIRED:      return "Payment Required";
          case SC_SERVICE_OVERLOADED:    return "Service Overloaded";
          case SC_UNAUTHORIZED:          return "Unauthorized";
          default:                       return "Unknown Status Code " + sc;
       }
    }
}
