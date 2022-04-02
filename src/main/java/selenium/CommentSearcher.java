package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;
import java.util.Optional;

public class CommentSearcher {
    static final By COMMENT_WRAPPER = By.cssSelector(".fl-grow1.mt4");
    static final By QUESTION_CLASS = By.className("question-hyperlink");
    static final By ANSWER_CLASS = By.className("answer-hyperlink");
    static final By COMMENT_CLASS = By.className("mt4");
    static final By EMPTY = By.className("s-empty-state");

    public static void main(String... args) {
        WebDriver driver = SeleniumWebDriver.newBraveDriver();
        System.out.println("\n>>> Searching...");
        long total = 0;
        int index = 1;
        while (index < 100) {
            final int i = index;
            String url = "https://stackoverflow.com/users/1371329/jaco0646?tab=activity&sort=comments&page=" + index;
            driver.get(url);
            if (isPageEmpty(driver)) break;
            long count = driver.findElements(COMMENT_WRAPPER)
                    .stream()
                    .map(CommentSearcher::match)
                    .filter(Objects::nonNull)
                    .map(it -> i + ": " + it)
                    .distinct()
                    .peek(System.out::println)
                    .count();
            total += count;
            index++;
        }
        driver.quit();
        System.out.println("<<< " + total + " Results");
    }

    static boolean isPageEmpty(WebDriver driver) {
        return driver.findElements(EMPTY).stream().findAny()
                .map(WebElement::getText)
                .filter("You have no comments"::equals)
                .isPresent();
    }

    static String match(WebElement commentWrapper) {
        Optional<WebElement> question = commentWrapper.findElements(QUESTION_CLASS).stream().findFirst();
        Optional<WebElement> answer = commentWrapper.findElements(ANSWER_CLASS).stream().findFirst();
        String comment = commentWrapper.findElement(COMMENT_CLASS).getText().toLowerCase();
        return question
                .map(WebElement::getText)
                .map(String::toLowerCase)
                .filter(q -> q.contains("interface"))
                .map(q -> question.get().getText())
                .orElse(null);
    }

}
