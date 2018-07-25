package com.example.demo;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {
	private final FileStroe fileStroe;

	@Autowired
    public FileUploadController(FileStroe fileStroe) {
        this.fileStroe = fileStroe;
    }

	 @GetMapping("/")
	 public String uploadfiles(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) throws IOException {
	     model.addAttribute("name", name);
	     
	     return "uploadForm";
	   
	 } 
	 @GetMapping("/test")
	 public String test(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) throws IOException {
		     model.addAttribute("name", name);
		
		     return "test";
		   
	} 
	 @PostMapping("/")
	 public String upload(@RequestParam("file") MultipartFile file,
	            RedirectAttributes redirectAttributes) throws IOException {
		    this.fileStroe.store(file);
		     redirectAttributes.addFlashAttribute("message",
		                "You successfully uploaded " + file.getOriginalFilename() + "!");

		  return "redirect:/";
		    
		   
	} 
	 @GetMapping("/files2/{filename:.+}")
	    public String redirectServeFile(@PathVariable String filename, Model model) {
		 model.addAttribute("filename", filename);
		 return "download";
	    }
	 @GetMapping("/files/{filename:.+}")
	    @ResponseBody
	    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

	        Resource file = (Resource) fileStroe.loadAsResource(filename);
	        HttpHeaders responseHeaders = new HttpHeaders();
	        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
	                "attachment; filename=\"" + ((org.springframework.core.io.Resource) file).getFilename() + "\"").body(file);
	    }
}
