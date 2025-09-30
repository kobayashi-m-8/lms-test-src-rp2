package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
		getEvidence("Case07", "test01");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		 WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		loginId.clear();
		loginId.sendKeys("StudentAA01");

		WebElement password = webDriver.findElement(By.id("password"));
		password.clear();
		password.sendKeys("StudentAA011");

		webDriver.findElement(By.cssSelector("input[type='submit']")).click();
		wait.until(ExpectedConditions.titleIs("コース詳細 | LMS"));
		String actualTitle = webDriver.getTitle();
		String expectedTitle = "コース詳細 | LMS";
		assertEquals(expectedTitle, actualTitle, "コース詳細画面に遷移すること");
		getEvidence("Case07", "test02");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
	    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

	    WebElement detailButton = wait.until(
	        ExpectedConditions.elementToBeClickable(
	            By.xpath("//tr[td/span[text()='未提出']]//input[@type='submit' and @value='詳細']"))
	    );

	    detailButton.click();

	    wait.until(ExpectedConditions.titleIs("セクション詳細 | LMS"));

	    assertEquals("セクション詳細 | LMS", webDriver.getTitle());
	    getEvidence("Case07", "test03");
	}


	@Test
	@Order(4)
	@DisplayName("テスト04 日報提出ボタン押下でレポート登録画面に遷移")
	void test04() {
	    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
	    WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//input[@type='submit' and contains(@value,'日報')]")
	    ));
	    submitButton.click();

	    String actualTitle = webDriver.getTitle();
	    String expectedTitle = "レポート登録 | LMS"; 
	    assertEquals(expectedTitle, actualTitle);
	    getEvidence("Case07", "test04");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
	    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

	    try {
	        WebElement textarea = webDriver.findElement(By.tagName("textarea"));
	        textarea.clear();
	        textarea.sendKeys("本日の研修内容を報告します。");
	    } catch (NoSuchElementException e) {
	        // textareaが無い場合無視
	    }

	    WebElement submitButton = wait.until(
	        ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='提出する']"))
	    );
	    submitButton.click();

	    WebElement updatedButton = wait.until(
	        ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//input[@type='submit' and contains(@value,'提出済み')]")
	        )
	    );

	    assertTrue(updatedButton.isDisplayed());

	    getEvidence("Case07", "test05");
	}


}
