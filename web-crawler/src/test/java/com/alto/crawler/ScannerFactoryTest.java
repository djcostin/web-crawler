package com.alto.crawler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({URL.class,ScannerFactory.class})
public class ScannerFactoryTest  {

    private ScannerFactory scannerFactory = new ScannerFactory();

    @Test
    public void testCreateScannerFromDodgyUrlString() {

        Scanner scanner = scannerFactory.createScanner("htpp://dodgeurl");
        assertTrue(!scanner.hasNext());
    }

    @Test
    public void testCreateScannerFromDodgyUrl() throws Exception{

        URL url = PowerMockito.mock(URL.class);

        when(url.openStream()).thenThrow(new IOException());

        Scanner scanner = scannerFactory.createScanner(url);

        assertTrue(!scanner.hasNext());
    }

    @Test
    public void testCreateScannerFromGoodUrl() throws Exception{

        URL url = PowerMockito.mock(URL.class);

        InputStream inputStream = new ByteArrayInputStream("dddd".getBytes());

        when(url.openStream()).thenReturn(inputStream);

        Scanner scanner = scannerFactory.createScanner(url);

        assertTrue(scanner.hasNext());
        assertEquals("dddd",scanner.nextLine());
    }
}