package com.revengemission.sso.oauth2.resource.coupon.controller;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revengemission.sso.oauth2.resource.coupon.service.StorageService;
import com.revengemission.sso.oauth2.resource.coupon.utils.ClientIpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Api(value = "文件上传、下载接口", tags = {"文件上传、下载接口"})
@Controller
public class FileStorageController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${storage.location.public}")
    private String publicStorageLocation;

    @Value("${storage.location.protected}")
    private String protectedStorageLocation;

    @Value("#{'${storage.type.allowlist}'.split(',')}")
    private Set<String> allowlist;

    @Autowired
    StorageService storageService;

//
//    /**
//     * 附件形式
//     *
//     * @param filename
//     * @return
//     */
//    @GetMapping("/files/filename")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@RequestParam(value = "filename") String filename) throws FileNotFoundException {
//
//        // 防止跨文件夹等任意文件下载
//        Path targetPath = Paths.get(publicStorageLocation, filename).normalize();
//        Path allowPath = Paths.get(publicStorageLocation).normalize();
//        if (targetPath.startsWith(allowPath)) {
//            Resource file = storageService.loadAsResource(targetPath);
//            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//
//    }

    /**
     * 下载流
     *
     * @param request
     * @param response
     * @param filename
     * @throws IOException
     */
    @RequestMapping(value = "/download/public", method = RequestMethod.GET)
    public void downloadStreamPublic(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "filename") String filename) throws IOException {
        // 防止跨文件夹等任意文件下载
        Path targetPath = Paths.get(publicStorageLocation, filename).normalize();
        Path allowPath = Paths.get(publicStorageLocation).normalize();
        if (targetPath.startsWith(allowPath)) {
            InputStream myStream = Files.newInputStream(targetPath);
            // Copy the stream to the response's output stream.
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } else {
            String toUrl = ClientIpUtils.getFullRequestUrl(request);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            Map<String, Object> responseMessage = new HashMap<>(16);
            responseMessage.put("status", HttpStatus.FORBIDDEN);
            responseMessage.put("message", "非法请求！");
            responseMessage.put("path", toUrl);
            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(HttpStatus.FORBIDDEN.value());
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(response.getOutputStream(),
                JsonEncoding.UTF8);
            objectMapper.writeValue(jsonGenerator, responseMessage);
        }
    }

    /**
     * 下载文件流
     *
     * @param request
     * @param response
     * @param filename
     * @throws IOException
     */
    @RequestMapping(value = "/download/protected", method = RequestMethod.GET)
    public void downloadStream(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "filename") String filename) throws IOException {

        // 防止跨文件夹等任意文件下载
        Path targetPath = Paths.get(protectedStorageLocation, filename).normalize();
        Path allowPath = Paths.get(protectedStorageLocation).normalize();
        if (targetPath.startsWith(allowPath)) {
            InputStream myStream = Files.newInputStream(targetPath);
            // Copy the stream to the response's output stream.
            IOUtils.copy(myStream, response.getOutputStream());
            response.flushBuffer();
        } else {
            String toUrl = ClientIpUtils.getFullRequestUrl(request);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            Map<String, Object> responseMessage = new HashMap<>(16);
            responseMessage.put("status", HttpStatus.FORBIDDEN);
            responseMessage.put("message", "非法请求！");
            responseMessage.put("path", toUrl);
            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(HttpStatus.FORBIDDEN.value());
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(response.getOutputStream(),
                JsonEncoding.UTF8);
            objectMapper.writeValue(jsonGenerator, responseMessage);
        }

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
                if (fileType == null) {
                    String multipartFileContentType = multipartFile.getContentType();
                    if (StringUtils.endsWithIgnoreCase("image/png", multipartFileContentType)) {
                        fileType = "png";
                    } else if (StringUtils.endsWithIgnoreCase("image/jpg", multipartFileContentType)) {
                        fileType = "jpg";
                    } else if (StringUtils.endsWithIgnoreCase("image/jpeg", multipartFileContentType)) {
                        fileType = "jpg";
                    }
                }
                if (allowlist.contains(StringUtils.trimAllWhitespace(fileType).toLowerCase())) {
                    try {
                        String newFileName = storageService.save(Paths.get(publicStorageLocation), multipartFile, fileType);
                        fileNames.add(newFileName);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("上传文件异常", e);
                        }
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
        if (fileType == null || "".equals(fileType)) {
            String multipartFileContentType = multipartFile.getContentType();
            if (StringUtils.endsWithIgnoreCase("image/png", multipartFileContentType)) {
                fileType = "png";
            } else if (StringUtils.endsWithIgnoreCase("image/jpg", multipartFileContentType)) {
                fileType = "jpg";
            } else if (StringUtils.endsWithIgnoreCase("image/jpeg", multipartFileContentType)) {
                fileType = "jpg";
            } else if (StringUtils.endsWithIgnoreCase("application/pdf", multipartFileContentType)) {
                fileType = "pdf";
            } else if (StringUtils.endsWithIgnoreCase("application/msword", multipartFileContentType)) {
                fileType = "doc";
            }
        }
        if (allowlist.contains(StringUtils.trimAllWhitespace(fileType).toLowerCase())) {
            try {
                String newFileName = storageService.save(Paths.get(publicStorageLocation), multipartFile, fileType);
                fileNames.add(newFileName);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("上传文件异常", e);
                }
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
