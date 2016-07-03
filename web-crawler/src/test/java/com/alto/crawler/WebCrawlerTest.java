package com.alto.crawler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class WebCrawlerTest {

    @Mock
    private LinkPatternMatcher linkPatternMatcher;
    @Mock
    private ScannerFactory scannerFactory;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testDoCrawlFindSinglePage() throws Exception {

        Scanner scanner = new Scanner("test home page with 0 links");

        String startingUrl = "http://wiprodigital.com";

        WebCrawler crawler = new WebCrawler(startingUrl,3);

        Artifact page = crawler.doCrawl(scanner);

        Artifact expected = new Artifact(startingUrl, Artifact.ArtifactType.PAGE);

        assertEquals(expected,page);
    }


    @Test
    public void testDoCrawlFind1ChildPage() throws Exception {

        String line ="test page with 1 link <a href =\"http://wiprodigital.com/page1\" >";
        String line2 ="child page with no linls";
        String pageAddress = "http://wiprodigital.com/page1";

        Scanner scanner = new Scanner(line);
        Scanner childScanner = new Scanner(line2);

        String startingUrl = "http://wiprodigital.com";

        WebCrawler crawler = new WebCrawler(startingUrl,
                linkPatternMatcher,
                scannerFactory);

        when(linkPatternMatcher.determineLinkType(startingUrl)).thenReturn(Artifact.ArtifactType.PAGE);
        when(linkPatternMatcher.extractAddress(line)).thenReturn(Optional.of(pageAddress));
        when(linkPatternMatcher.determineLinkType(pageAddress)).thenReturn(Artifact.ArtifactType.PAGE);

        when(scannerFactory.createScanner(pageAddress)).thenReturn(childScanner);

        when(linkPatternMatcher.extractAddress(line2)).thenReturn(Optional.empty());

        Artifact page = crawler.doCrawl(scanner);

        Artifact child = new Artifact(pageAddress,
                Artifact.ArtifactType.PAGE);

        Artifact expected = new Artifact(startingUrl,
                Artifact.ArtifactType.PAGE,
                new HashSet<Artifact>(
                        Arrays.asList(child)
                )
                );

        assertEquals(expected,page);
    }


    @Test
    public void testDoCrawlFindAnExternalLink() throws Exception {

        Scanner scanner = new Scanner("test page with 1 link <a href =\"http://www.good.com\" >");

        String startingUrl = "http://wiprodigital.com";

        WebCrawler crawler = new WebCrawler(startingUrl,3);


        Artifact page = crawler.doCrawl(scanner);

        Artifact child = new Artifact("http://www.good.com", Artifact.ArtifactType.EXTERNAL_LINK);

        Artifact expected = new Artifact(startingUrl,
                Artifact.ArtifactType.PAGE,
                new HashSet<Artifact>(
                        Arrays.asList(child)
                )
        );

        assertEquals(expected,page);

    }
}