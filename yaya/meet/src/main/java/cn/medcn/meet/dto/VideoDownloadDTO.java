package cn.medcn.meet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LiuLP on 2017/12/18/018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDownloadDTO {

    private String courseId;

    private String userId;

    private String downloadUrl;

    private String fileName;
}
