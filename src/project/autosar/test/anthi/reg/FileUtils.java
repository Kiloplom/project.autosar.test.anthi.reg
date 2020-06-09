/********************************************************************************
*Licensed Materials - Property of IBM
*(c) Copyright IBM Corporation 2014, 2017. All Rights Reserved.
*
*Note to U.S. Government Users Restricted Rights:  
*Use, duplication or disclosure restricted by GSA ADP Schedule 
*Contract with IBM Corp.
********************************************************************************/
package project.autosar.test.anthi.reg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

public class FileUtils {

    public static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

    public static BufferedReader createBufferedReader(File file) throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), DEFAULT_CHARSET));
    }

    public static BufferedWriter createBufferedWriter(File file) throws IOException {
        OutputStreamWriter sout = new OutputStreamWriter(new FileOutputStream(file, false), DEFAULT_CHARSET);
        return new BufferedWriter(sout);
    }

    public static void closeQuietly(InputStream input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    public static void closeQuietly(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    public static void closeQuietly(OutputStream output) {
        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    public static void closeQuietly(Writer writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    public static boolean saveBuffer(File file, StringBuffer buffer) {
        BufferedWriter outobj = null;
        boolean result = false;
        try {
            outobj = createBufferedWriter(file);
            outobj.write(buffer.toString());
            result = true;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            FileUtils.closeQuietly(outobj);
        }
        return result;
    }
}
