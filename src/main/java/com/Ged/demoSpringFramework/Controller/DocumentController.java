package com.Ged.demoSpringFramework.Controller;

import com.Ged.demoSpringFramework.model.Document;
import com.Ged.demoSpringFramework.service.DocStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class DocumentController {

    @Autowired
    private DocStorageService docStorageService;

    @GetMapping("/doc")
    public String get(Model model){
        List<Document> documents = docStorageService.getFiles();
        model.addAttribute("docs",documents);
        return "doc";
    }
    @PostMapping("/uploadFiles")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
        for( MultipartFile file : files){
            docStorageService.saveFile(file);
        }
        return "redirect:/ajouterDoc";
    }
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
        Document document = docStorageService.getFile(fileId).get();
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(document.getDocType()))
                .header("Content-disposition" , "attachment; ")
                .body(new ByteArrayResource(document.getDocumentData()));
    }
    @GetMapping("/deleteFile/{fileId}")
    public String deleteFile(@PathVariable Integer fileId){
        docStorageService.deleteDocument(fileId);
        return "redirect:/supprimerDoc";
    }
    @GetMapping("/doc-viewer/{fileId}")
    public ResponseEntity<byte[]> visualiser(@PathVariable Integer fileId, Model model){
        Document document = null;
        List<Document> documents = docStorageService.getFiles();
        for(Document doc : documents){
            if(doc.getId().equals(fileId)){
                document = doc;
                break;
            }
        }
        String value = new String(docStorageService.convertToBase64(document.getDocumentData()));
        model.addAttribute("bases",value);
        model.addAttribute("docType",document.getDocType());
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType(document.getDocType()));

        headers.add("content-disposition", "inline;filename=" + document.getDocName());


        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(document.getDocumentData(), headers, HttpStatus.OK);
        return response;
    }
    @GetMapping("/afficherDoc")
    public String afficherDoc(Model model){
        List<Document> documents = docStorageService.getFiles();
        model.addAttribute("docs",documents);
        return "afficherDoc";
    }
    @GetMapping("/ajouterDoc")
    public String ajouterDoc(Model model){
        List<Document> documents = docStorageService.getFiles();
        model.addAttribute("docs",documents);
        return "ajouterDoc";
    }
    @GetMapping("/telechargerDoc")
    public String telechargerDoc(Model model){
        List<Document> documents = docStorageService.getFiles();
        model.addAttribute("docs",documents);
        return "telechargerDoc";
    }
    @GetMapping("/supprimerDoc")
    public String supprimerDoc(Model model){
        List<Document> documents = docStorageService.getFiles();
        model.addAttribute("docs",documents);
        return "supprimerDoc";
    }
    @GetMapping("/visualiserDoc")
    public String visualiserDoc(Model model){
        List<Document> documents = docStorageService.getFiles();
        model.addAttribute("docs",documents);
        return "visualiserDoc";
    }
    @GetMapping("/login")
    public String login()
    {
        return "login";
    }
    @GetMapping("/search")
    public String recherche(Model model , String keyword){
        List<Document> documents = docStorageService.getFiles();
        model.addAttribute("docs",documents);
        if(keyword!=null){
            model.addAttribute("wantedDoc" , docStorageService.findByKeyword(keyword));
        }
        return "search";
    }
    @PostMapping("/versioning/{fileId}")
    public String visualiser(@PathVariable Integer fileId, @RequestParam("files") MultipartFile[] files, Model model){
        Document newDoc = null;
        for( MultipartFile file : files){
            String docName = file.getOriginalFilename();
            try {
                newDoc = new Document(docName,file.getContentType(),file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        docStorageService.addDocVer(fileId,newDoc);
        List<Document> documents = docStorageService.getFiles();
        model.addAttribute("docVers" , documents);
        return "redirect:/docVer/{fileId}";
    }
    @GetMapping("/versioning")
    public String versioningDoc(Model model){
        List<Document> documents = docStorageService.getFiles();
        model.addAttribute("docVers",documents);
        return "versioning";
    }
    @GetMapping("docVer/{fileId}")
    public String viewVersions(@PathVariable Integer fileId, Model model){
        Document document = docStorageService.getFile(fileId).get();
        model.addAttribute("doc",document);
        return "docVer";
    }
}
