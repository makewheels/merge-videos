package com.example.mergevideos;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.io.File;
import java.io.IOException;

public class RunMerge {
    public static void main(String[] args) throws IOException {
        File originalFolder = new File("D:\\BaiduNetdiskDownload\\行车记录仪\\2021.03.06");
        FFprobe ffprobe = new FFprobe("C:\\mysofts\\ffprobe.exe");
        FFmpegProbeResult probeResult = ffprobe.probe(
                "D:\\BaiduNetdiskDownload\\FILE201214-100956-000493F.MOV");
        FFmpegFormat format = probeResult.getFormat();
        double duration = format.duration;
        System.out.println(duration);
    }
}
