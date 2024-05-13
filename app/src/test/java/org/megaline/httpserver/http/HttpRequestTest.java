package org.megaline.httpserver.http;

// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// public class HttpRequestTest {

//     @Test
//     public void testSetMethod() {
//         HttpRequest request = new HttpRequest();
//         assertThrows(HttpParsingException.class, () -> request.setMethod("INVALID_METHOD"));
//     }

//     @Test
//     public void testSetRequestTarget() {
//         HttpRequest request = new HttpRequest();
//         assertThrows(HttpParsingException.class, () -> request.setRequestTarget(null));
//         assertThrows(HttpParsingException.class, () -> request.setRequestTarget(""));
//     }

//     @Test
//     public void testSetHttpVersion() {
//         HttpRequest request = new HttpRequest();
//         assertThrows(BadHttpVersionException.class, () -> request.setHttpVersion("HTTP/1.0"));
//     }

//     @Test
//     public void testSetBody() {
//         HttpRequest request = new HttpRequest();
//         assertThrows(HttpParsingException.class, () -> request.setBody(null));
//         String largeBody = "a".repeat(10 * 1024 * 1024 + 1);
//         assertThrows(HttpParsingException.class, () -> request.setBody(largeBody));
//     }

//     @Test
//     public void testAddHeaders() {
//         HttpRequest request = new HttpRequest();
//         assertThrows(HttpParsingException.class, () -> request.addHeaders(null, "value"));
//         assertThrows(HttpParsingException.class, () -> request.addHeaders("name", null));
//         String longName = "a".repeat(1025);
//         assertThrows(HttpParsingException.class, () -> request.addHeaders(longName, "value"));
//         String longValue = "a".repeat(4097);
//         assertThrows(HttpParsingException.class, () -> request.addHeaders("name", longValue));
//     }
// }
