package com.Ged.demoSpringFramework.service;

import com.Ged.demoSpringFramework.model.DocVersions;
import com.Ged.demoSpringFramework.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DocStorageService {
    public Document saveFile(MultipartFile file);
    public Optional<Document> getFile(Integer fileId);
    public List<Document> getFiles();
    public void deleteDocument(Integer id);
    public String  convertToBase64(byte[] fileBytes);
    public List<Document> findByKeyword(String keyword);
    public List<DocVersions> addDocVer(Integer id , Document newDocument);

}
