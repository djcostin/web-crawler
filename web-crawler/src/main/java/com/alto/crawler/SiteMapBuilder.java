package com.alto.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Created by drcee on 01/07/2016.
 */
public class SiteMapBuilder {

    private Logger log = LoggerFactory.getLogger(SiteMapBuilder.class);
    private String startingUrl;
    private int maxLevels = 3;
    private WebCrawler crawler;

    public SiteMapBuilder withStartingUrl(String startingUrl) {
        this.startingUrl = startingUrl;
        return this;
    }

    public SiteMapBuilder withMaxLevels(int maxLevels) {
        this.maxLevels=maxLevels;
        return this;
    }

    public Artifact build() throws Exception{

        log.info("start building web crawler with address {} ", startingUrl);
        URL url = new URL(startingUrl);
        this.crawler = new WebCrawler(url.getHost(),maxLevels);

        return crawler.doCrawl(new ScannerFactory().createScanner(url));
    }
}
