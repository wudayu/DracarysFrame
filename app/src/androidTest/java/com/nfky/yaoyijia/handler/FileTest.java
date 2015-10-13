package com.nfky.yaoyijia.handler;

import android.test.AndroidTestCase;

import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.handler.interfaces.IFileHandler;

import java.io.IOException;

/**
 * Created by David on 10/13/15.
 */
public class FileTest extends AndroidTestCase {
    /**
     * 测试: createFile() isFileExists() deleteFile()
     */
    public void testCreateFileAndIsFileExistsAndDeleteFile() {
        IFileHandler fileHandler = FileHandler.getInstance(getContext());
        String targetFile = fileHandler.getCacheDir() + "/TEST_FILE_23df2a32ce49";

        try {
            fileHandler.createFile(targetFile);
        } catch (Exception ex) {
            Utils.debug(ex.toString());
        }

        assertTrue(fileHandler.isFileExists(targetFile));
        fileHandler.deleteFile(targetFile);
        assertFalse(fileHandler.isFileExists(targetFile));
    }

    /**
     * 测试: createFolder() deleteFolder()
     */
    public void testCreateAndDeleteFolder() {
        IFileHandler fileHandler = FileHandler.getInstance(getContext());
        String targetFileFolder = fileHandler.getCacheDir() + "/TEST_FLODRER_54a9a4bdca85/";

        fileHandler.createFolder(targetFileFolder);
        assertTrue(fileHandler.isFileExists(targetFileFolder));
        fileHandler.deleteFolder(targetFileFolder, false);
        assertFalse(fileHandler.isFileExists(targetFileFolder));
    }

    /**
     * 测试: deleteFilesInFolder()
     */
    public void testDeleteFilesInFolder() {
        IFileHandler fileHandler = FileHandler.getInstance(getContext());
        String targetFolder = fileHandler.getCacheDir() + "/TEST_FLODRER_54d9a4baca85/";
        String targetFile = targetFolder + "/TEST_FILE_23de2b32ce49";

        fileHandler.createFolder(targetFolder);
        try {
            fileHandler.createFile(targetFile);
        } catch (IOException ex) {
            Utils.debug(ex.toString());
        }
        assertTrue(fileHandler.isFileExists(targetFile));
        fileHandler.deleteFilesInFloder(targetFolder, false);
        assertFalse(fileHandler.isFileExists(targetFile));
        fileHandler.deleteFolder(targetFolder, false);
        assertFalse((fileHandler.isFileExists(targetFolder)));
    }

    /**
     * 测试: getFileBaseName() getFileExtension()
     */
    public void testGetFileBaseNameAndFileExtension() {
        IFileHandler fileHandler = FileHandler.getInstance(getContext());
        final String BASE_NAME = "http://www.google.com/wudayu/README";
        final String EXTENSION = "md";
        String fileName = BASE_NAME + "." + EXTENSION;

        String baseName = fileHandler.getFileBaseName(fileName);
        String extension = fileHandler.getFileExtension(fileName);

        assertEquals(baseName, BASE_NAME);
        assertEquals(extension, EXTENSION);
    }

    /**
     * 测试: copyFile() readTextFile()
     */
    public void testCopyFile() {
        IFileHandler fileHandler = FileHandler.getInstance(getContext());
        String sourceFile = fileHandler.getCacheDir() + "/TEST_FILE_23df2a32df49";
        String targetFile = fileHandler.getCacheDir() + "/TEST_FILE_DST_3df2a32a";
        final String TEST_CONTEXT = "test string\n  is testing";

        try {
            fileHandler.createFile(sourceFile);
        } catch (IOException ex) {
            Utils.debug(ex.toString());
        }
        fileHandler.writeTextFile(sourceFile, TEST_CONTEXT);
        fileHandler.copyFile(sourceFile, targetFile);
        String targetContent = fileHandler.readTextFile(targetFile);
        fileHandler.deleteFile(sourceFile);
        fileHandler.deleteFile(targetFile);
        assertEquals(TEST_CONTEXT, targetContent);
    }

    /**
     * 测试: getDirPath() getFileName() getFileNameWithoutSuffix()
     */
    public void testGetDirPathAndFileNameAndFileNameWithoutSuffix() {
        IFileHandler fileHandler = FileHandler.getInstance(getContext());
        final String DIR_PATH = "http://www.google.com/m/test";
        final String FILE_NAME_WITHOUT_SUFFIX = "wudayu";
        final String FILE_NAME = FILE_NAME_WITHOUT_SUFFIX + ".md";
        String testPath = DIR_PATH + "/" + FILE_NAME;

        String dirPath = fileHandler.getDirPath(testPath);
        String fileName = fileHandler.getFileName(testPath);
        String fileNameWithoutSuffix = fileHandler.getFileNameWithoutSuffix(testPath);

        assertEquals(DIR_PATH, dirPath);
        assertEquals(FILE_NAME, fileName);
        assertEquals(FILE_NAME_WITHOUT_SUFFIX, fileNameWithoutSuffix);
    }
}
