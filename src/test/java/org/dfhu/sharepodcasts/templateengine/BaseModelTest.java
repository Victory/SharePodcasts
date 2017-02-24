package org.dfhu.sharepodcasts.templateengine;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BaseModelTest {
    @Test
    public void testAttr() {
        String input = "A \"cool\" attr";
        String expected = "A &quot;cool&quot; attr";
        String actual = BaseModel.attr(input);
        assertEquals(expected, actual);
    }
}
