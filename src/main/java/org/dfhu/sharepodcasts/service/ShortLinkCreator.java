package org.dfhu.sharepodcasts.service;

import org.dfhu.sharepodcasts.morphs.query.ShareQuery;
import org.slf4j.Logger;

import java.math.BigInteger;
import java.util.Random;

public class ShortLinkCreator {
    private final ShareQuery shareQuery;
    private final Logger logger;

    private final Random random = new Random();

    public ShortLinkCreator(ShareQuery shareQuery, Logger logger) {
        this.shareQuery = shareQuery;
        this.logger = logger;
    }

    /**
     * Create a short link
     *
     * @return 8 bit string alpha numeric that has been tested not to be in Share collection
     *
     */
    public String create() {
        for (int ii = 0; ii < 1000; ii++) {
            String shortLink = nextShareLink();
            if (!shareQuery.shortLinkExists(shortLink)) {
                return shortLink;
            }
        }

        logger.error("Could not create short link");
        throw new RuntimeException("Could not create short link");
    }

    private String nextShareLink() {
        String s = new BigInteger(64, random).toString(32);
        return s.substring(0, 8);
    }
}
