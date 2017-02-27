package org.dfhu.sharepodcasts.routeing;

import com.fizzed.rocker.RockerModel;
import org.dfhu.sharepodcasts.approutes.BrowseLetterRoute;
import org.dfhu.sharepodcasts.morphs.ShowMorph;
import org.dfhu.sharepodcasts.morphs.query.ShowQuery;
import org.dfhu.sharepodcasts.views.browse.BrowseLetter;
import org.junit.Test;
import spark.Request;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class BrowseLetterRouteTest {

    @Test
    public void hasAllShows() {
        ShowQuery showQuery = mock(ShowQuery.class);

        Request req = mock(Request.class);
        String letter = "B";
        when(req.params("letter")).thenReturn(letter);

        List<ShowMorph> shows = IntStream.range(0, 3)
                .mapToObj(ii -> {
                    ShowMorph showMorph = new ShowMorph();
                    showMorph.title = "B show #" + ii;
                    return showMorph;
                })
                .collect(Collectors.toList());

        when(showQuery.getShowsByLetter(letter)).thenReturn(shows);

        BrowseLetterRoute browseLetterRoute =
                new BrowseLetterRoute(showQuery);
        BrowseLetter browseLetterRouteRockerModel =
                (BrowseLetter) browseLetterRoute.getRockerModel(req, null);

        verify(req, times(1)).params("letter");
        List<ShowMorph> actual = browseLetterRouteRockerModel.vm().getShows();
        assertEquals(3, actual.size());
        assertEquals("B show #0", actual.get(0).title);

    }
}
