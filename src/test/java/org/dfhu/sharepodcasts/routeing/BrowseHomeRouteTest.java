package org.dfhu.sharepodcasts.routeing;

import org.dfhu.sharepodcasts.approutes.BrowseHomeRoute;
import org.dfhu.sharepodcasts.morphs.ShowLettersMorph;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.dfhu.sharepodcasts.viewmodels.BrowseHomeViewModel;
import org.dfhu.sharepodcasts.views.browse.Browse;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class BrowseHomeRouteTest {
    @Test
    public void testGet() {
        ShowQuery showQuery = mock(ShowQuery.class);

        List<ShowLettersMorph> showLetters = Arrays.asList("A", "C").stream().map(c -> {
            ShowLettersMorph showLettersMorph = new ShowLettersMorph();
            showLettersMorph.titleLetter = c;
            return showLettersMorph;
        }).collect(Collectors.toList());
        when(showQuery.getShowLetters()).thenReturn(showLetters);

        BrowseHomeRoute browseHomeRoute
                = new BrowseHomeRoute(showQuery);
        Browse rockerModel =
                (Browse) browseHomeRoute.getRockerModel(null, null);
        BrowseHomeViewModel vm = rockerModel.vm();
        List<ShowLettersMorph> activeLetters = vm.getShowLetters();
        assertEquals(2, activeLetters.size());
        assertEquals("C", activeLetters.get(1).titleLetter);
    }
}
