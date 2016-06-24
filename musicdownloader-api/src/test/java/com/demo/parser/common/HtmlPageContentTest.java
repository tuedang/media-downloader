package com.demo.parser.common;

import com.google.common.io.Resources;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HtmlPageContentTest {
    private static HtmlPageContent htmlPageContent;

    @BeforeClass
    public static void init() throws IOException {
        htmlPageContent = HtmlPageContent.fromURL(Resources.getResource("com/demo/parser/tuyen-tap-nhung-ca-khuc-rock-quoc-te-hay-adam-lambert.xQBpvCYhDpjq.html"), HtmlPageContent.ContentType.HTML);
    }

    @Test
    public void canLoadContent() {
        assertNotNull(htmlPageContent);
        assertNotNull(htmlPageContent.getJsoupDocument().html());
    }

    @Test
    public void cssSelector() {
        String result = htmlPageContent.cssSelector("div.name_title .name-singer");
        assertEquals("Adam Lambert", result);
    }

    @Test
    public void searchText() {
        String playlistUrl = "http://www.nhaccuatui.com/flash/xml?html5=true&key2=30c3ec16f202334185ba0a107fe2f5ad";

        String result = htmlPageContent.searchText("div.playing_absolute", "player.peConfig.xmlURL", 4, "\";");
        assertEquals(playlistUrl, result);

        String result2 = htmlPageContent.searchText("div.playing_absolute", "player.peConfig.xmlURL = \"", 0, "\";");
        assertEquals(playlistUrl, result2);
    }
}
