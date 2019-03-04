package cn.ken.egou.controller;

import cn.ken.egou.utils.AjaxResult;
import cn.ken.egou.utils.FastDfsApiOpr;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * fastDfs文件(图片)上传下载(到文件管理的集群服务器fastdfs)
 *
 * @author Administrator
 */
@RestController
@RequestMapping("fastDfs")
public class FastDfsController {
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public AjaxResult uploadFile(MultipartFile file) {
        String uploadPath = null;
        try {
            String originalFilename = file.getOriginalFilename();
            int i = originalFilename.lastIndexOf('.');
            //后缀
            String suffix = originalFilename.substring(i + 1);
            String prefix = originalFilename.substring(0, i);
            //上传到集群
            uploadPath = FastDfsApiOpr.upload(file.getBytes(), suffix);
            System.out.println(uploadPath);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("文件上传失败" + e.getMessage());
        }

        return AjaxResult.me().setSuccess(true).setMsg("上传成功").setObject(uploadPath);
    }

    @RequestMapping(value = "/deleteFile", method = RequestMethod.DELETE)
    public AjaxResult deleteFile(String filePath) {
//      group1/M00/00/01/rBAHcVx7qF6AWF85AAAYQ5WxuN0400.jpg     (String groupName,String fileName)
        int deleteResult = 0;
//        /group1/M00/00/01/rBAHYlx8BReABc5tAAAUhZCYAWs827.jpg
        if (filePath != null && !filePath.isEmpty()) {
            try {
                filePath = filePath.substring(1);
                int i = filePath.indexOf('/');
                String groupName = filePath.substring(0, i);
                String fileName = filePath.substring(i + 1);
                System.out.println(groupName);
                System.out.println(fileName);
                deleteResult = FastDfsApiOpr.delete(groupName, fileName);
                if (deleteResult == 0) {
                    return AjaxResult.me().setSuccess(true).setMsg("删除成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return AjaxResult.me().setSuccess(false).setMsg("删除失败" + deleteResult);
    }
}
