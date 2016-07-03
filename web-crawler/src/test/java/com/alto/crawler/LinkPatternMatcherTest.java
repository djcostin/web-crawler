package com.alto.crawler;

import org.junit.Test;

import java.util.Optional;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class LinkPatternMatcherTest {

    private LinkPatternMatcher patternMatcher = new LinkPatternMatcher();

    @Test
    public void testExtractAddressHttpLink() throws Exception {

        String retVal = patternMatcher.extractAddress("fdsajfdlsakj fl;das <a href=\"http://www.domain.com\" > fdsafdsa fdsa ").get();

        assertEquals("http://www.domain.com",retVal);
    }

    @Test
    public void testExtractAddressHttpsLink() throws Exception {

        String retVal = patternMatcher.extractAddress("fdsajfdlsakj fl;das <a href=\"https://www.domain.com\"> fdsafdsa fdsa ").get();

        assertEquals("https://www.domain.com",retVal);
    }

    @Test
    public void testExtractAddressHttpNoLink() throws Exception {

        Optional<String> retVal = patternMatcher.extractAddress("fdsajfdlsakj fl;das \"http://www.domain.com\" fdsafdsa fdsa ");

        assertEquals(Optional.empty(),retVal);
    }

    @Test
    public void testJpegIsImage() throws Exception {

        assertTrue(
                patternMatcher.isImage("http://www.domain.com/ddd/test.jpg")
        );

        assertTrue(
                patternMatcher.isImage("http://www.domain.com/ddd/test.jpeg")
        );
    }

    @Test
    public void testPngIsImage() throws Exception {

        assertTrue(
                patternMatcher.isImage("http://www.domain.com/ddd/test.png")
        );
    }

    @Test
    public void determinePageCorrectType() throws Exception {

        Artifact.ArtifactType retVal = patternMatcher.determineLinkType("http://www.domain.com/ddd/test/ddd");

        assertEquals(Artifact.ArtifactType.PAGE,retVal);
    }
}