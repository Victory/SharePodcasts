package org.dfhu.sharepodcasts.service;

import org.dfhu.sharepodcasts.morphs.query.ShareQuery;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class ShortLinkCreatorTest {

    @Test
    public void matchesRegex() {
        int trials = 1500;
        ShareQuery shareQuery = mock(ShareQuery.class);
        when(shareQuery.shortLinkExists(any())).thenReturn(false);
        ShortLinkCreator shortLinkCreator =
                new ShortLinkCreator(shareQuery, null);
        Pattern pattern = Pattern.compile("^[a-z0-9]{8}$");

        for (int ii = 0; ii < trials; ii++) {
            String key = shortLinkCreator.create();
            assertTrue("Trial: " + ii + " Key: " + key,
                    pattern.matcher(key).find());
        }

    }
}
