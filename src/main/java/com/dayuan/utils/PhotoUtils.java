package com.dayuan.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.dayuan.entity.Photo;
import com.dayuan.repository.PhotoRepository;

public class PhotoUtils {
    public static Photo savePhoto(MultipartFile file, PhotoRepository pr, String targetName, Long targetId){
    	Photo photo = null;
    	try{
    		FileInputStream in = (FileInputStream)file.getInputStream();
    		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    		String photoName = System.currentTimeMillis() + suffix;
    		int b = 0;
            FileOutputStream out = new FileOutputStream(new File(Consts.PHOTO_DIR + photoName));
            while ((b=in.read()) != -1){
                 out.write(b);
            }
            out.flush();
            out.close();
            in.close();
            
            Photo p = new Photo();
            p.setTargetName(targetName);
            p.setTargetId(targetId);
            p.setPurl(Consts.PHOTO_URL + photoName);
            photo = pr.save(p);
            
    	}catch (Exception e){
    		return null;
    	}
    	return photo;
    }
}
