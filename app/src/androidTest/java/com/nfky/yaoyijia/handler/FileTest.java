package com.nfky.yaoyijia.handler;

import android.test.AndroidTestCase;

import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.handler.interfaces.IFileHandler;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

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
        String targetFolder = fileHandler.getCacheDir() + "/TEST_FLODRER_54a9a4bdca85/";

        fileHandler.createFolder(targetFolder);
        assertTrue(fileHandler.isFileExists(targetFolder));
        fileHandler.deleteFolder(targetFolder, false);
        assertFalse(fileHandler.isFileExists(targetFolder));
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
     * 测试: copyFile() readTextFile() writeTextFile()
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

    /**
     * 测试: listFiles()
     */
    public void testListFiles() {
        IFileHandler fileHandler = FileHandler.getInstance(getContext());
        String outterFile01 = fileHandler.getCacheDir() + "/TEST_FILE_23df4b32ce4901";
        String outterFile02 = fileHandler.getCacheDir() + "/TEST_FILE_23df1c32ce4902";
        String outterFolder = fileHandler.getCacheDir() + "/TEST_FOLDER_f243dce4902";
        String innerFile = outterFolder + "/TEST_FILE_23df2a32cd2903";

        try {
            fileHandler.createFile(outterFile01);
            fileHandler.createFile(outterFile02);
            fileHandler.createFolder(outterFolder);
            fileHandler.createFile(innerFile);
        } catch (IOException ex) {
            Utils.debug(ex.toString());
        }

        List<String> fileList = fileHandler.listFiles(fileHandler.getCacheDir(), new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains("TEST_");
            }
        }, true);

        fileHandler.deleteFile(outterFile01);
        fileHandler.deleteFile(outterFile02);
        fileHandler.deleteFolder(outterFolder, true);

        assertTrue(fileList.contains(outterFile01));
        assertTrue(fileList.contains(outterFile02));
        assertTrue(fileList.contains(innerFile));
        assertEquals(fileList.size(), 3);
    }

    /**
     * 测试: renameFile()
     */
    public void testRenameFile() {
        IFileHandler fileHandler = FileHandler.getInstance(getContext());
        String originFile = fileHandler.getCacheDir() + "/TEST_FILE_23df4b32dd4901";
        String newFile = fileHandler.getCacheDir() + "/TEST_FILE_23df1c32dd4902";

        try {
            fileHandler.createFile(originFile);
        } catch (IOException ex) {
            Utils.debug(ex.toString());
        }

        fileHandler.renameFile(originFile, newFile);
        List<String> fileList = fileHandler.listFiles(fileHandler.getCacheDir(), new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains("TEST_");
            }
        }, true);

        fileHandler.deleteFile(originFile);
        fileHandler.deleteFile(newFile);

        assertEquals(fileList.size(), 1);
        assertEquals(fileList.get(0), newFile);
    }

    /**
     * 测试: getFileNameInUrl
     */
    public void testGetFileNameInUrl() {
        IFileHandler fileHandler = FileHandler.getInstance(getContext());
        final String FILE_NAME = "wudayu.md";
        final String FILE_PATH = "http://www.google.com/m/" + FILE_NAME;

        String fileName = fileHandler.getFileNameInUrl(FILE_PATH);

        assertEquals(fileName, FILE_NAME);
    }

}
