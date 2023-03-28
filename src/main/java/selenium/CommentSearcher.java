package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommentSearcher {
    static final String NEEDLE = "beautiful".toLowerCase();
    static final int TOTAL_PAGES = 88;

    static final By EMPTY_PAGE = By.cssSelector(".s-empty-state");
    static final By QUESTION_LINK_OR_ANSWER_LINK_OR_COMMENT_TEXT =
            By.cssSelector(".question-hyperlink, .answer-hyperlink, .mt4");

    public static void main(String... args) throws InterruptedException {
        WebDriver driver = SeleniumWebDriver.newBraveDriver();
        System.out.println("\n>>> Searching... \"" + NEEDLE + "\"");
        long matchCount = 0;
        int pageIndex = 1;
        while (pageIndex <= TOTAL_PAGES) {
            String url = "https://stackoverflow.com/users/1371329/jaco0646?tab=activity&sort=comments&page=" + pageIndex;
            driver.get(url);
            if (isPageEmpty(driver)) break;
            System.out.print(" page " + pageIndex);
            long count = driver.findElements(QUESTION_LINK_OR_ANSWER_LINK_OR_COMMENT_TEXT)
                    .stream()
                    .map(CommentSearcher::getTextWithoutSubElements)
                    .distinct()
                    .filter(CommentSearcher::printResult)
                    .count();
            System.out.print(count > 0 ? '\n' : '\r');
            matchCount += count;
            pageIndex++;
            Thread.sleep(500);
        }
        driver.quit();
        System.out.println("<<< " + matchCount + " results");
    }

    static String getTextWithoutSubElements(WebElement we) {
        String includingSubElements = we.getText();
        return includingSubElements.split("\n")[0];
    }

    static boolean printResult(String haystack) {
        if (haystack.toLowerCase().contains(NEEDLE)) {
            System.out.println();
            System.out.print("  * ");
            System.out.print(haystack);
            return true;
        }
        return false;
    }

    static boolean isPageEmpty(WebDriver driver) {
        return driver.findElements(EMPTY_PAGE).stream().findAny().isPresent();
    }

}
