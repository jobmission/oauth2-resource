package com.revengemission.sso.oauth2.resource.coupon.controller;

import com.revengemission.sso.oauth2.resource.coupon.service.StorageService;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@Controller
public class FileStorageController {

    @Value("${storage.location.public}")
    private String publicStorageLocation;

    @Value("${storage.location.protected}")
    private String protectedStorageLocation;

    @Value("#{'${storage.type.whitelist}'.split(',')}")
    private Set<String> whitelist;

    @Autowired
    StorageService storageService;
//
//    /**
//     * 附件形式
//     *
//     * @param filename
//     * @return
//     */
//    @GetMapping("/files/{filename}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws FileNotFoundException {
//
//        Resource file = storageService.loadAsResource(Paths.get(protectedStorageLocation, filename));
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//            "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }
//
//    /**
//     * 附件形式
//     *
//     * @param filename
//     * @throws IOException
//     */
//    @CrossOrigin
//    @GetMapping("/public/{filename}")
//    @ResponseBody
//    public ResponseEntity<Resource> servePublicFile(@PathVariable String filename) throws FileNotFoundException {
//
//        Resource file = storageService.loadAsResource(Paths.get(publicStorageLocation, filename));
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//            "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }

    @RequestMapping(value = "/public/{filename}", method = RequestMethod.GET)
    public void downloadStreamPublic(HttpServletResponse response, @PathVariable String filename) throws IOException {
        // Get your file stream from wherever.
        InputStream myStream = Files.newInputStream(Paths.get(publicStorageLocation, filename));
        // Copy the stream to the response's output stream.
        IOUtils.copy(myStream, response.getOutputStream());
        response.flushBuffer();
    }

    @RequestMapping(value = "/protected/{filename}", method = RequestMethod.GET)
    public void downloadStream(HttpServletResponse response, @PathVariable String filename) throws IOException {

        // Get your file stream from wherever.
        InputStream myStream = Files.newInputStream(Paths.get(protectedStorageLocation, filename));
        // Copy the stream to the response's output stream.
        IOUtils.copy(myStream, response.getOutputStream());
        response.flushBuffer();
    }

    /**
     * 当前 springfox-swagger2 上传多个问件时有问题
     *
     * @param files
     * @return
     */
    @PostMapping("/upload/protected")
    @ResponseBody
    public Map<String, Object> handleFileUploadProtected(@ApiParam(value = "选择文件", allowMultiple = true) @RequestPart(value = "files", required = false) MultipartFile files) {
        return saveToDisk(files, protectedStorageLocation);
    }

    @PostMapping(value = "/upload/public", consumes = "multipart/form-data")
    @ResponseBody
    public Map<String, Object> handleFileUpload(@ApiParam(value = "选择文件", allowMultiple = true) @RequestPart(value = "files", required = false) MultipartFile files) {
        return saveToDisk(files, publicStorageLocation);
    }

    private Map<String, Object> saveToDisk(List<MultipartFile> files, String publicStorageLocation) {
        Map<String, Object> result = new HashMap<>(16);
        List<String> fileNames = new LinkedList<>();
        if (files != null && files.size() > 0) {
            files.forEach(multipartFile -> {
                String fileType = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
                if (whitelist.contains(StringUtils.trimAllWhitespace(fileType).toLowerCase())) {
                    try {
                        String newFileName = storageService.save(Paths.get(publicStorageLocation), multipartFile);
                        fileNames.add(newFileName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (fileNames.size() > 0) {
            result.put("status", 1);
        } else {
            result.put("status", 0);
        }
        result.put("files", fileNames);
        return result;
    }

    private Map<String, Object> saveToDisk(MultipartFile multipartFile, String publicStorageLocation) {
        Map<String, Object> result = new HashMap<>(16);
        List<String> fileNames = new LinkedList<>();
        String fileType = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        if (whitelist.contains(StringUtils.trimAllWhitespace(fileType).toLowerCase())) {
            try {
                String newFileName = storageService.save(Paths.get(publicStorageLocation), multipartFile);
                fileNames.add(newFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (fileNames.size() > 0) {
            result.put("status", 1);
        } else {
            result.put("status", 0);
        }
        result.put("files", fileNames);
        return result;
    }

}
