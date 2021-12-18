package com.example.mergevideos;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RuntimeUtil;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class VideoUtil {
    private static FFprobe ffprobe;

    static {
        try {
            ffprobe = new FFprobe("C:\\mysofts\\ffprobe.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isTimeLengthEquals(File videoFile, double timeLength) {
        FFmpegProbeResult probeResult = null;
        try {
            probeResult = ffprobe.probe(videoFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        FFmpegFormat format = probeResult.getFormat();
        double duration = format.duration;
        //允许一定误差
        return Math.abs(duration - timeLength) < 2.0;
    }

    /**
     * filelist.txt
     * <p>
     * file 'input1.mkv'
     * file 'input2.mkv'
     * file 'input3.mkv'
     * <p>
     * ffmpeg -f concat -i filelist.txt -c copy output.mkv
     */
    public static void mergeVideos(List<File> originalFiles, File target) {
        File targetFolder = target.getParentFile();
        if (!target.exists()) {
            targetFolder.mkdirs();
        }

        //创建 filelist.txt文件
        File filelist = new File(targetFolder, originalFiles.get(0).getName() + ".filelist.txt");
        StringBuilder stringBuilder = new StringBuilder();
        for (File file : originalFiles) {
            stringBuilder.append("file '" + file.getAbsolutePath() + "'\n");
        }
        FileUtil.writeString(stringBuilder.toString(), filelist, StandardCharsets.UTF_8);
        String command = "ffmpeg -f concat -safe 0 -i \"" + filelist.getAbsolutePath()
                + "\" -c:v copy -c:a aac \""
                + target.getAbsolutePath().replace(".MOV", ".mp4")
                + "\"";
        System.out.println(command);
        String exec = RuntimeUtil.execForStr(command);
        System.out.println(exec);
        filelist.delete();
    }
}
