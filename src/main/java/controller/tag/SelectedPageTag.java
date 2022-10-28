package controller.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class SelectedPageTag extends SimpleTagSupport {

    private static final Logger logger = LogManager.getLogger(SelectedPageTag.class);

    @Override
    public void doTag() throws IOException {
        HttpServletRequest request = getRequest();
        sendViewUriToJsp(request);
    }

    /**
     * Get request from jspContext
     *
     * @return httpRequest
     */
    private HttpServletRequest getRequest() {
        PageContext pageContext = (PageContext) getJspContext();
        return (HttpServletRequest) pageContext.getRequest();
    }

    /**
     * Writes URI of current view to out stream of jspContext
     *
     */
    private void sendViewUriToJsp(HttpServletRequest request)
            throws IOException {
        JspWriter out = getJspContext().getOut();
        logger.info("currPage ==> " + request.getRequestURI());
        out.print(request.getRequestURI());
    }
}
