import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "de.monochromata.cucumber.report.PrettyReports:target/test-results"},
        features = "src/test/java/features",
        strict = true
)
public class RunCucumberTest {
}
