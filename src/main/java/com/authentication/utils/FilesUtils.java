package com.authentication.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.authentication.dto.demo.FilesDto;

import org.springframework.web.multipart.MultipartFile;

import liquibase.util.file.FilenameUtils;

/**
 * @author TungBoom
 *
 */
public class FilesUtils {

    public static String saveUploadedFile(MultipartFile file, String uploadFolder) throws IOException {
        String currentTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String originalName = file.getOriginalFilename();
        String fileName = FilenameUtils.getBaseName(originalName) + "_" + currentTime + "."
                + FilenameUtils.getExtension(originalName);
        byte[] bytes = file.getBytes();
        File folderUpload = new File(uploadFolder);
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        Path path = Paths.get(folderUpload.getPath() + File.separator + fileName);
        Files.write(path, bytes);
        return path.toString();
    }

    public static List<FilesDto> saveMultipleUploadedFile(List<MultipartFile> files, String uploadFolder) throws IOException {
        List<FilesDto> listFilesDto = new ArrayList<>();
        if (files != null && files.size() > 0) {
            for (MultipartFile file : files) {
                String originalName = file.getOriginalFilename();
                String fileName = FilenameUtils.getBaseName(originalName) + "."
                        + FilenameUtils.getExtension(originalName);
                String pathByDate = createPathByDate();
                byte[] bytes = file.getBytes();
                File folderUpload = new File(uploadFolder + File.separator + pathByDate);
                if (!folderUpload.exists()) {
                    folderUpload.mkdirs();
                }
                Path path = Paths.get(folderUpload.getPath() + File.separator + fileName);
                Files.write(path, bytes);
                FilesDto fileDto = new FilesDto();
                fileDto.setFileName(fileName);
                fileDto.setFilePath(pathByDate + File.separator + fileName);
                fileDto.setFileSize(file.getSize());
                listFilesDto.add(fileDto);
            }
        }
        return listFilesDto;
    }

    public static String createPathByDate() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String currentTime = new SimpleDateFormat("HHmmssSSS").format(new Date());
        String pathDate = String.valueOf(year) + File.separator + String.valueOf(month + 1) + File.separator + String.valueOf(day) + File.separator + currentTime;
        return pathDate;
    }

    public static File readFileByPath(String path, String uploadFolder) {
        File file = new File(uploadFolder + File.separator + path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
    
    public static void deleteFileByPath(String pathFile, String uploadFolder) {
        File file = new File(uploadFolder + File.separator + pathFile);
        if (file.exists()) {
        	if(file.delete()){
        		System.out.println(uploadFolder + File.separator + pathFile + " has delete");
        	} else {
        		System.out.println(uploadFolder + File.separator + pathFile + " hasn't delete");
        	}
        }
    }

    public static void deleteFileByListFile(List<FilesDto> listFile, String uploadFolder) {
        // Get list file
        for (int i = 0; i < listFile.size(); i++) {
            File file = new File(uploadFolder + File.separator + listFile.get(i).getFilePath());
            if (file.exists()) {
            	if(file.delete()){
            		System.out.println(uploadFolder + File.separator + listFile.get(i).getFilePath() + " has delete");
            	} else {
            		System.out.println(uploadFolder + File.separator + listFile.get(i).getFilePath() + " hasn't delete");
            	}
            }
        }
    }

    public static File readFileByListFile(List<FilesDto> listFile, String uploadFolder) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String fullPath = URLDecoder.decode(classLoader.getResource("").getPath(), "UTF-8");
        String pathArr[] = fullPath.split("/target/classes");
        String rootPath = pathArr[0].substring(0, pathArr[0].lastIndexOf("/"));
        rootPath += File.separator + "file_zip_out";
        String currentTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        File zipfile = new File(rootPath);
        if (!zipfile.exists()) {
            zipfile.mkdirs();
        }
        // Create a buffer for reading the files
        byte[] buf = new byte[1024];
        ZipOutputStream out = null;
        try {
            // Get list file
            List<File> files = new ArrayList<>();
            for (int i = 0; i < listFile.size(); i++) {
                File file = new File(uploadFolder + File.separator + listFile.get(i).getFilePath());
                files.add(file);
            }
            // create the ZIP file
            out = new ZipOutputStream(new FileOutputStream(rootPath + File.separator + "file_" + currentTime + ".zip"));
            // compress the files
            for (int i = 0; i < files.size(); i++) {
                FileInputStream in = new FileInputStream(files.get(i).getCanonicalFile());
                try {
                    // add ZIP entry to output stream
                    out.putNextEntry(new ZipEntry(files.get(i).getName()));
                    // transfer bytes from the file to the ZIP file
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    // complete the entry
                    out.closeEntry();
                } catch (Exception e) {
                    throw e;
                } finally {
                    in.close();
                }
            }
            // complete the ZIP file
            out.close();
            return new File(rootPath + File.separator + "file_" + currentTime + ".zip");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return null;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static File zipFileByListFile(List<File> files) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String fullPath = URLDecoder.decode(classLoader.getResource("").getPath(), "UTF-8");
        String pathArr[] = fullPath.split("/target/classes");
        String rootPath = pathArr[0].substring(0, pathArr[0].lastIndexOf("/"));
        rootPath += File.separator + "file_zip_out";
        String currentTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        File zipfile = new File(rootPath);
        if (!zipfile.exists()) {
            zipfile.mkdirs();
        }
        // Create a buffer for reading the files
        byte[] buf = new byte[1024];
        ZipOutputStream out = null;
        try {
            // create the ZIP file
            out = new ZipOutputStream(new FileOutputStream(rootPath + File.separator + "file_" + currentTime + ".zip"));
            // compress the files
            for (int i = 0; i < files.size(); i++) {
                FileInputStream in = new FileInputStream(files.get(i).getCanonicalFile());
                try {
                    // add ZIP entry to output stream
                    out.putNextEntry(new ZipEntry(files.get(i).getName()));
                    // transfer bytes from the file to the ZIP file
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    // complete the entry
                    out.closeEntry();
                } catch (Exception e) {
                    throw e;
                } finally {
                    in.close();
                }
            }
            // complete the ZIP file
            out.close();
            return new File(rootPath + File.separator + "file_" + currentTime + ".zip");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return null;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
