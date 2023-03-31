package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommentSearcher {
    public static void main(String... args) {
        new CommentQuery("Beautiful", 10)
                .searchComments();
    }

    static class CommentQuery {
        private static final By EMPTY_PAGE = By.cssSelector(".s-empty-state");
        private static final By QUESTION_LINK_OR_ANSWER_LINK_OR_COMMENT_TEXT =
                By.cssSelector(".question-hyperlink, .answer-hyperlink, .mt4");

        private final WebDriver driver = SeleniumWebDriver.newBraveDriver();
        private final Needle needle;
        private final int pageLimit;

        private long totalMatches = 0;
        private int pageIndex = 1;

        CommentQuery(String searchTerm, int pageLimit) {
            this.needle = new Needle(searchTerm);
            this.pageLimit = pageLimit;
        }

        void searchComments() {
            System.out.println("\n>>> Searching... \"" + needle + "\"");
            while (pageIndex <= pageLimit && hasNextPage()) {
                searchPage();
                sleep();
            }
            driver.quit();
            System.out.println("<<< " + totalMatches + " results");
        }

        void searchPage() {
            System.out.print(" page " + pageIndex);
            long pageMatches = driver.findElements(QUESTION_LINK_OR_ANSWER_LINK_OR_COMMENT_TEXT)
                    .stream()
                    .map(Haystack::new)
                    .distinct()
                    .filter(needle::in)
                    .peek(Haystack::print)
                    .count();
            System.out.print(pageMatches > 0 ? '\n' : '\r');
            totalMatches += pageMatches;
            pageIndex++;
        }

        boolean hasNextPage() {
            driver.get("https://stackoverflow.com/users/1371329/jaco0646?tab=activity&sort=comments&page=" + pageIndex);
            return driver.findElements(EMPTY_PAGE).stream().findAny().isEmpty();
        }

        static void sleep() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Needle {
        private final String searchTerm;
        private final String needle;

        Needle(String searchTerm) {
            this.searchTerm = searchTerm;
            this.needle = searchTerm.toLowerCase();
        }

        boolean in(Haystack haystack) {
            return haystack.haystack.contains(needle);
        }

        @Override
        public String toString() {
            return searchTerm;
        }
    }

    static class Haystack {
        private final String comment;
        private final String haystack;

        Haystack(WebElement we) {
            String includingSubElements = we.getText();
            String excludingSubElements = includingSubElements.split("\n")[0];
            this.comment = excludingSubElements;
            this.haystack = excludingSubElements.toLowerCase();
        }

        void print() {
            System.out.println();
            System.out.print("  * ");
            System.out.print(comment);
        }
    }
}
