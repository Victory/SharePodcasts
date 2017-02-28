package org.dfhu.sharepodcasts.viewmodels;

import org.dfhu.sharepodcasts.morphs.ShowLettersMorph;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class BrowseHomeViewModelTest {
    @Test
    public void testGetActiveLetters() {
        ShowQuery showQuery = mock(ShowQuery.class);

        List<ShowLettersMorph> showLetters = Arrays.asList("A", "C").stream().map(c -> {
            ShowLettersMorph showLettersMorph = new ShowLettersMorph();
            showLettersMorph.titleLetter = c;
            return showLettersMorph;
        }).collect(Collectors.toList());
        when(showQuery.getShowLetters()).thenReturn(showLetters);


        BrowseHomeViewModel browseHomeViewModel =
                new BrowseHomeViewModel(null, null, showLetters);

        List<ShowLettersMorph> activeLetters = browseHomeViewModel.getShowLetters();
        assertEquals("A", activeLetters.get(0).titleLetter);
        assertEquals("C", activeLetters.get(1).titleLetter);
    }
}
