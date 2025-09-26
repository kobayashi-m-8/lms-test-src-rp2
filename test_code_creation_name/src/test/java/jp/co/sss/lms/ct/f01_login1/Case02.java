package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
		// TODO ここに追加
		 // トップページにアクセス
		webDriver.get("http://localhost:8080/lms");

        String actualTitle = webDriver.getTitle();
        String expectedTitle = "ログイン | LMS";
        assertEquals(expectedTitle, actualTitle, "ログイン画面が表示されること");

        getEvidence("Case02", "test01");
    }

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		// TODO ここに追加
		WebElement loginId = webDriver.findElement(By.id("loginId"));
        loginId.clear();
        loginId.sendKeys("UnknownUser01");

        WebElement password = webDriver.findElement(By.id("password"));
        password.clear();
        password.sendKeys("test1234");
        
		webDriver.findElement(By.cssSelector("input[type='submit']")).click();

		WebElement errorMsg = webDriver.findElement(By.cssSelector("span.help-inline.error"));

		assertTrue(errorMsg.isDisplayed());
		assertTrue(errorMsg.getText().contains("ログインに失敗しました。"));
		
		getEvidence("Case02", "test02");
	}

}
