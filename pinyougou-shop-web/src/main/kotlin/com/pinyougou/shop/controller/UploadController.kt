package com.pinyougou.shop.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import priv.zhong.bean.Result
import utils.FastDFSClient


/**
 * @author 钟未鸣
 * @date 2018/4/9
 */
@RestController
class UploadController {
    @Value("\${FILE_SERVER_URL}")
    private lateinit var fileServerUrl: String

    @RequestMapping("/upload")
    fun upload(file: MultipartFile): Result {
        val originalFilename = file.originalFilename//获取文件名
        val extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1)//得到扩展名

        return try {
            val client = FastDFSClient("classpath:config/fdfs_client.conf")
            val fileId = client.uploadFile(file.bytes, extName)
            val url = fileServerUrl + fileId//图片完整地址

            Result(true, url)

        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "上传失败")
        }

    }

}