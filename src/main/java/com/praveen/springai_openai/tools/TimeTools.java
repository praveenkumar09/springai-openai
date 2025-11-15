package com.praveen.springai_openai.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.function.Supplier;

@Component
public class TimeTools {

    private final Logger logger = LoggerFactory.getLogger(TimeTools.class);

    @Tool(
            name="getCurrentLocalTime",
            description = "Get the current time in the user's timezone"
    )
    public String getCurrentTime() {
        logger.info("Returning current time in users timezone");
        return LocalTime.now().toString();
    }

    @Tool(
            name="getCurrentTime",
            description = "Get the current time in the specified timezone"
    )
    public String getCurrentTime(
            @ToolParam(description = "Value representing the time zone") String timeZone
    ){
        logger.info("Returning the current time in the specified timezone: {}",timeZone);
        return LocalTime.now(ZoneId.of(timeZone)).toString();
    }
}
