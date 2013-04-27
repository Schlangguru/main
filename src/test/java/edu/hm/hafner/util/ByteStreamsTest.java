package edu.hm.hafner.util;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.common.io.ByteStreams;
import com.google.common.io.OutputSupplier;

import org.junit.Test;


/**
 * Testet die Klasse BysteStreams.
 *
 * @author Sebastian Seidl
 */
public class ByteStreamsTest {

    @Test
    public void byteStreamsCopyTestWithNormalInput() throws IOException{
        InputStream in = mock(InputStream.class);
        OutputStream out = mock(OutputStream.class);
        OutputSupplier<OutputStream> outSup = mock(OutputSupplier.class);

        when(in.read(any(byte[].class))).thenReturn(10, 10, 10, -1);
        when(outSup.getOutput()).thenReturn(out);

        ByteStreams.copy(in, outSup);

        verify(outSup, times(1)).getOutput();
        verify(in, times(4)).read(any(byte[].class));
        verify(out, atLeast(3)).write(any(byte[].class), anyInt(), anyInt());
        verify(out, times(1)).close();
        verify(in, never()).close();
    }

    @Test
    public void byteStreamsCopyTestWithEmptynput() throws IOException{
        InputStream in = mock(InputStream.class);
        OutputStream out = mock(OutputStream.class);
        OutputSupplier<OutputStream> outSup = mock(OutputSupplier.class);

        when(in.read(any(byte[].class))).thenReturn(-1);
        when(outSup.getOutput()).thenReturn(out);

        ByteStreams.copy(in, outSup);

        verify(outSup, times(1)).getOutput();
        verify(in, times(1)).read(any(byte[].class));
        verify(out, never()).write(any(byte[].class), anyInt(), anyInt());
        verify(out, times(1)).close();
        verify(in, never()).close();
    }

    @Test(expected = IOException.class)
    public void byteStreamsCopyTestThrowException() throws IOException{
        InputStream in = mock(InputStream.class);
        OutputStream out = mock(OutputStream.class);
        OutputSupplier<OutputStream> outSup = mock(OutputSupplier.class);

        when(outSup.getOutput()).thenReturn(out);
        doThrow(new IOException()).when(in).read(any(byte[].class));

        ByteStreams.copy(in, outSup);
    }

    @Test(expected = NullPointerException.class)
    public void byteStreamsCopyTestWithNullAsOutputStream() throws IOException{
        InputStream in = mock(InputStream.class);
        OutputSupplier<OutputStream> outSup = mock(OutputSupplier.class);

        when(in.read(any(byte[].class))).thenReturn(10, -1);
        when(outSup.getOutput()).thenReturn(null);

        ByteStreams.copy(in, outSup);
    }

    @Test(expected = IOException.class)
    public void byteStreamsCopyTestOutputStreamCloseException() throws IOException{
        InputStream in = mock(InputStream.class);
        OutputStream out = mock(OutputStream.class);
        OutputSupplier<OutputStream> outSup = mock(OutputSupplier.class);

        when(in.read(any(byte[].class))).thenReturn(-1);
        when(outSup.getOutput()).thenReturn(out);
        doThrow(IOException.class).when(out).close();

        ByteStreams.copy(in, outSup);
    }

    @Test(expected = IOException.class)
    public void byteStreamsCopyTestOutputStreamException() throws IOException{
        InputStream in = mock(InputStream.class);
        OutputStream out = mock(OutputStream.class);
        OutputSupplier<OutputStream> outSup = mock(OutputSupplier.class);

        when(in.read(any(byte[].class))).thenReturn(10, -1);
        when(outSup.getOutput()).thenReturn(out);
        doThrow(IOException.class).when(out).write(any(byte[].class), anyInt(), anyInt());
        doThrow(IOException.class).when(out).close();

        ByteStreams.copy(in, outSup);
    }
}

