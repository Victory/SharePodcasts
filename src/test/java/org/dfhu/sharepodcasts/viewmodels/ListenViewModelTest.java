package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.morphs.ShareMorph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ListenViewModelTest {

    @Test
    public void testCommentIsSanitized() {
        String expected = "Hi Evil";
        String comment = "Hi <p>Evil<p> <script>bye</script>";
        testComment(comment, expected);
    }

    @Test
    public void testCommentHasBrackets() {
        String expected = "line 1<br>line 2";
        String comment = "line 1\nline 2";
        testComment(comment, expected);
    }

    private void testComment(String comment, String expected) {
        ShareMorph shareMorph = new ShareMorph();
        shareMorph.comment = comment;

        ListenViewModel listenViewModel =
                new ListenViewModel(null, null,null, null, shareMorph);
        String actual = listenViewModel.getShareComment();
        assertEquals(expected, actual);
    }
}
