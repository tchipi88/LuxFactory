
import com.tsoft.app.App;
import com.tsoft.app.service.template.TemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TsoftApplicationTests {

    @Autowired
    TemplateService templateService;

    @Test
    public void renderTemplate() throws Exception {
        templateService.processTemplateEngine("com.tsoft.app");
    }

}
