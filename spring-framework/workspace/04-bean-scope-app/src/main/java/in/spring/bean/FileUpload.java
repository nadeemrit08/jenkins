package in.spring.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FileUpload {
	
	public FileUpload() {
		System.out.println("FileUpload.FileUpload()");
	}

	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void display() {
		System.out.println(fileName);
	}
}
