package top.tianqi.plankton.system.plugin;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.tianqi.plankton.system.vo.FileInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("filePlugin")
public class FilePlugin implements StoragePlugin {

	@Value("${attachUploadBase}")
	private String uploadBase;
	
	@Override
	public void upload(String path, File file, String contentType) {
		File destFile = new File(path);
		try {
			FileUtils.moveFile(file, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] download(String fileUrl) {
		File downFile = new File(uploadBase + fileUrl);
		try {
			byte[] bs = FileUtils.readFileToByteArray(downFile);
			return bs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getUrl(String path) {
		return path;
	}

	@Override
	public List<FileInfo> browser(String path) {
		List<FileInfo> fileInfos = new ArrayList<>();
		File directory = new File(uploadBase + path);
		if (directory.exists() && directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.setName(file.getName());
				fileInfo.setUrl(path + file.getName());// 修改成虚拟目录
				fileInfo.setIsDirectory(file.isDirectory());
				fileInfo.setSize(file.length());
				fileInfo.setLastModified(new Date(file.lastModified()));
				fileInfos.add(fileInfo);
			}
		}
		return fileInfos;
	}

}
