package com.alto.crawler;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArtifactTypeTest {

    @Test
    public void testGetDomain() {

        Artifact page = new Artifact("http://www.google.com", Artifact.ArtifactType.PAGE);

        assertTrue(page!=null);
    }

}