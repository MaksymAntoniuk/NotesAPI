package io.maksym.web.components.practice;

import com.microsoft.playwright.Locator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FlashAlert {
    private final Locator root;

    public FlashAlert(Locator root) {
        this.root = root;
    }

    public void shouldBeVisible(){
        assertThat(root).isVisible();
    }

    public void shouldContain(String text){
        assertThat(root).containsText(text);
    }

    public String text(){
        return root.innerText();
    }
}
