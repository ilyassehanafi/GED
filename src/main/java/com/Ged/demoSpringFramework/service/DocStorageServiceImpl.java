package com.Ged.demoSpringFramework.service;

import com.Ged.demoSpringFramework.model.DocVersions;
import com.Ged.demoSpringFramework.model.Document;
import com.Ged.demoSpringFramework.repository.DocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class DocStorageServiceImpl implements DocStorageService {
    @Autowired
    private DocRepository docRepository;

    @Override
    public Document saveFile(MultipartFile file){
        String docName = file.getOriginalFilename();
        try{
            Document document = new Document(docName,file.getContentType(),file.getBytes());
            return docRepository.save(document);
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
    @Override
    public Optional<Document> getFile(Integer fileId){
        return docRepository.findById((fileId));
    }
    @Override
    public List<Document> getFiles(){
        return docRepository.findAllByOrderByDocNameAsc();
    }
    @Override
    public void deleteDocument(Integer id)
    {
        docRepository.deleteById(id);
    }
    @Override
    public String  convertToBase64(byte[] fileBytes)
    {
        String value = new String( Base64.getMimeEncoder().encode(fileBytes));
        return value;
    }
    @Override
    public List<Document> findByKeyword(String keyword) {
        return docRepository.findByKeyWord(keyword);
    }
    @Override
    public List<DocVersions> addDocVer(Integer id, Document newDocument) {
        docRepository.save(newDocument);
        Optional<Document> doc  = docRepository.findById(id);
        DocVersions docVersions = new DocVersions(LocalDateTime.now(), newDocument);
        if(doc.isPresent()){
            if(doc.get().getVersions()==null){
                List<DocVersions> version = new ArrayList<>();
                version.add(docVersions);
                doc.get().setVersions(version);
            }else{
                doc.get().getVersions().add(docVersions);
            }
        }
        docRepository.save(doc.get());
        return doc.get().getVersions();
    }
}

