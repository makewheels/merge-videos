package com.example.mergevideos;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunMerge {
    public static void main(String[] args) {
        File originalFolder = new File("D:\\BaiduNetdiskDownload\\adr\\2021.07.01");
        List<File> fileList = Arrays.asList(originalFolder.listFiles());

        List<File> mergeFiles = new ArrayList<>();
        for (File file : fileList) {
            if (file.isDirectory()) {
                continue;
            }
            mergeFiles.add(file);
            boolean isEquals5mins = VideoUtil.isTimeLengthEquals(file, 5 * 60);
            if (!isEquals5mins) {
                File target = new File(mergeFiles.get(0).getParent()
                        + "/merge/" + mergeFiles.get(0).getName());
                VideoUtil.mergeVideos(mergeFiles, target);
                mergeFiles.clear();
            }
        }
    }
}
