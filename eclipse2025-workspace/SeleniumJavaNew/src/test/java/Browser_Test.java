import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Browser_Test {
	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();
		driver.get("https://seleniumhq.org/");
		
		driver.close();
		
	}

}

