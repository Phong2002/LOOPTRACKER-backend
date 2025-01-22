package com.looptracker.looptracker.service.storage;

import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public interface IMinioService {
    public String uploadFile(String path, String fileName, InputStream fileStream, String contentType) throws Exception;

    InputStream download(String folderSource, String fileName);

    InputStream download(String path);

    public void deleteFile(String path, String fileName) throws Exception ;
    public void deleteFile(String path) throws Exception ;
}
