package by.epam_training.final_task.controller.user_tag;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class CountSlidesTag extends SimpleTagSupport {
    private static final Logger logger = Logger.getLogger(CountSlidesTag.class.getName());

    @Override
    public void doTag() throws JspException, IOException {
        StringWriter stringWriter = new StringWriter();
        getJspBody().invoke(stringWriter);
        int countSlides;
        try {
            countSlides = (int) Math.ceil((float) Double.parseDouble(stringWriter.toString()) / 3);
        }catch (NumberFormatException e){
            logger.log(Level.ERROR,"Wrong format number",e);
            throw new JspException(e);
        }
        getJspContext().getOut().print(countSlides);
    }
}
