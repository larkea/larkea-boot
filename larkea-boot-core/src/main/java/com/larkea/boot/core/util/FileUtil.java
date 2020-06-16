package com.larkea.boot.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingInputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

    private FileUtil() {
    }

    public static String getFileType(String fileName) {
        String ext = com.google.common.io.Files.getFileExtension(fileName);
        return Strings.isNullOrEmpty(ext) ? "" : "." + ext;
    }

    public static String getHash(InputStream in, Path tmpDir) {
        return getHash(in, tmpDir, 102400);
    }

    public static String getHash(InputStream in, Path tmpDir, int bufferSize) {
        File tmpFile = null;
        try (HashingInputStream hin = new HashingInputStream(Hashing.sha256(), in)) {
            if (!tmpDir.toFile().exists()) {
                tmpDir.toFile().mkdirs();
            }
            tmpFile = File.createTempFile("files.", ".tmp", tmpDir.toFile());
            OutputStream out = new FileOutputStream(tmpFile);
            byte[] buffer = new byte[bufferSize];
            int count;
            while ((count = hin.read(buffer, 0, buffer.length)) >= 0) {
                out.write(buffer, 0, count);
            }
            out.flush();
            out.close();
            return hin.hash().toString().toLowerCase();
        } catch (IOException e) {
            LOGGER.debug("File Hash Error", e);
            throw new IllegalArgumentException(e);
        } finally {
            if (tmpFile != null && tmpFile.exists()) {
                tmpFile.delete();
            }
        }
    }
}
