package mfc;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@SpringBootTest
public class MultiFidelityCardApplicationTests {
    @Test
    void contextLoads() {
    }
}
