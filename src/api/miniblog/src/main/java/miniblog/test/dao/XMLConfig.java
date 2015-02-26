package miniblog.test.dao;

import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
({ 
    "file:src/main/webapp/WEB-INF/applicationContext.xml",
    "file:src/main/resources/hibernate.cfg.xml", 
})
public class XMLConfig {
}
