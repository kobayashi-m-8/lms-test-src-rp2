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

/**
 * 結合テスト ログイン機能①
 * ケース01：ログイン画面への遷移
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース01 ログイン画面への遷移")
public class Case01 {

    /** 前処理：ChromeDriver作成 */
    @BeforeAll
    static void before() {
        createDriver();
    }

    /** 後処理：ChromeDriver終了 */
    @AfterAll
    static void after() {
        closeDriver();
    }

    @Test
    @Order(1)
    @DisplayName("テスト01 トップページURLでアクセス")
    void test01() {
        webDriver.get("http://localhost:8080/lms");

        String actualTitle = webDriver.getTitle();
        String expectedTitle = "ログイン | LMS";
        assertEquals(expectedTitle, actualTitle, "ログイン画面が表示されること");

        getEvidence("Case01", "test01");
    }
}
