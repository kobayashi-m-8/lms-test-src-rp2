package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * キーワード検索 正常系
 * 
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		webDriver.get("http://localhost:8080/lms/");
		assertEquals("ログイン | LMS", webDriver.getTitle(), "ログイン画面が表示されること");
		getEvidence("Case05", "test01");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		loginId.clear();
		loginId.sendKeys("StudentAA01");

		WebElement password = webDriver.findElement(By.id("password"));
		password.clear();
		password.sendKeys("StudentAA011");

		webDriver.findElement(By.cssSelector("input[type='submit']")).click();

		String actualTitle = webDriver.getTitle();
		String expectedTitle = "コース詳細 | LMS";
		assertEquals(expectedTitle, actualTitle, "コース詳細画面に遷移すること");
		getEvidence("Case05", "test02");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		WebElement dropdown = webDriver.findElement(By.cssSelector("li.dropdown > a.dropdown-toggle"));
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", dropdown);

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		WebElement helpLink = wait.until(
				driver -> driver.findElement(By.cssSelector("li.dropdown ul.dropdown-menu li a[href='/lms/help']")));

		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", helpLink);

		String actualTitle = webDriver.getTitle();
		String expectedTitle = "ヘルプ | LMS";
		assertEquals(expectedTitle, actualTitle, "ヘルプ画面に遷移すること");
		getEvidence("Case05", "test03");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を開く")
	void test04() {
		WebElement dropdown = webDriver.findElement(By.cssSelector("li.dropdown > a.dropdown-toggle"));
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", dropdown);

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		WebElement helpLink = wait.until(
				driver -> driver.findElement(By.cssSelector("li.dropdown ul.dropdown-menu li a[href='/lms/help']")));
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", helpLink);

		wait.until(driver -> driver.getTitle().equals("ヘルプ | LMS"));

		WebElement faqLink = webDriver.findElement(By.cssSelector("a[href='/lms/faq']"));
		((JavascriptExecutor) webDriver).executeScript("window.open(arguments[0].href,'_blank');", faqLink);

		List<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
		webDriver.switchTo().window(tabs.get(tabs.size() - 1));

		wait.until(driver -> driver.getTitle().equals("よくある質問 | LMS"));
		assertEquals("よくある質問 | LMS", webDriver.getTitle(), "よくある質問画面に遷移すること");

		getEvidence("Case05", "test04");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
	    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

	    WebElement keywordInput = wait.until(driver ->
	        driver.findElement(By.id("form"))
	    );
	    keywordInput.clear();
	    keywordInput.sendKeys("研修");

	    WebElement searchButton = webDriver.findElement(By.cssSelector("input[type='submit'][value='検索']"));
	    searchButton.click();

	    wait.until(driver -> !driver.findElements(By.cssSelector("table.table tbody tr")).isEmpty());

	    List<WebElement> results = webDriver.findElements(By.cssSelector("table.table tbody tr td dl dt span:nth-child(2)"));

	    assertFalse(results.isEmpty(), "検索結果が表示されること");

	    boolean found = results.stream().anyMatch(e -> e.getText().contains("研修"));
	    assertTrue(found, "検索結果のいずれかにキーワードが含まれること");

	    getEvidence("Case05", "test05");
	}


	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
	    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

	    WebElement keywordInput = wait.until(driver ->
	        driver.findElement(By.id("form"))
	    );
	    keywordInput.clear();
	    keywordInput.sendKeys("研修");

	    WebElement clearButton = webDriver.findElement(By.cssSelector("input[type='button'][value='クリア']"));
	    clearButton.click();

	    wait.until(driver -> driver.findElement(By.id("form")).getAttribute("value").isEmpty());

	    assertEquals("", keywordInput.getAttribute("value"), "キーワードが消去されること");

	    getEvidence("Case05", "test06");
	}

}
