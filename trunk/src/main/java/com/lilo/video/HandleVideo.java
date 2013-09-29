package com.lilo.video;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lilo.video.core.MediaConcatenator;
import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;

public class HandleVideo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Start...");

		String[] video = new String[] {
				"/Users/linhhong/Movies/bv3/01/01-bv3_1.flv",
				"/Users/linhhong/Movies/bv3/01/01-bv3_2.flv",
				"/Users/linhhong/Movies/bv3/01/01-bv3_3.flv",
				"/Users/linhhong/Movies/bv3/01/01-bv3_4.flv",
				"/Users/linhhong/Movies/bv3/01/01-bv3_5.flv",
				"/Users/linhhong/Movies/bv3/01/01-bv3_6.flv",
				"/Users/linhhong/Movies/bv3/01/01-bv3_7.flv"};
		
		
		String out = "/Users/linhhong/Movies/bv3/01/01-bv3.flv";
		
		String folder = "/Users/linhhong/Movies/bv3/02";
		
		
		joinVideos1(getListOfFile(folder), folder + "/02-bv3.flv");
			
	}
	
	
	public static List<String> getListOfFile(String path) {
		List<String> files = new ArrayList<String>();
		
		File folder = new File(path);
		if (folder.isDirectory()) {
			File[] arrFiles = folder.listFiles();
			for (int i=0; i<arrFiles.length; i++) {
				files.add(arrFiles[i].getPath());
				System.out.println("-" + i + ":" + arrFiles[i].getPath());
			}
		}
		
		return files;
	}
	

	public static void joinVideos(String[] srcVideos, String destVideo) {
		IContainer destContainer = IContainer.make();  
		if (destContainer.open(destVideo, IContainer.Type.WRITE, null) <0)
			throw new RuntimeException("failed to open");
		//if (destContainer.writeHeader() < 0) throw new RuntimeException();
		
		IContainer srcContainer = IContainer.make();
		if (srcContainer.open(srcVideos[0], IContainer.Type.READ, null) <0)
			throw new RuntimeException("failed to open");
		
		
		
		IPacket packet = IPacket.make();
		while (srcContainer.readNextPacket(packet) >= 0) {  
			destContainer.writePacket(packet);
		}  
		
		srcContainer.close();  
		destContainer.close();
		
	}
	
	
	public static void joinVideos3(String[] srcVideos, String destVideo) {
		try {
			
			PrintStream out = new PrintStream(destVideo);
			byte[] b = new byte[1024];
			
			for (int i=0; i<srcVideos.length; i++) {
			
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(srcVideos[i]));
			
				int num = 0;
			
				try {
					while ((num = in.read(b, 0, b.length)) != -1) {
						out.write(b, 0, num);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				in.close();
			}
			out.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public static void joinVideos2(String[] srcVideos, String destVideo) {
		
		IContainer destContainer = IContainer.make();  
		if (destContainer.open(destVideo, IContainer.Type.WRITE, null) <0)
			throw new RuntimeException("failed to open");
		if (destContainer.writeHeader() < 0) throw new RuntimeException();
		
		IContainer srcContainer = IContainer.make();
		if (srcContainer.open(srcVideos[0], IContainer.Type.READ, null) <0)
			throw new RuntimeException("failed to open");
		
		//srcContainer.getNumStreams()
		
		
		
		
		IPacket packet = IPacket.make();
		while (srcContainer.readNextPacket(packet) >= 0) {  
			destContainer.writePacket(packet);
		}  
		srcContainer.close();  
		destContainer.close();
		 
		
	}
	
	
	
	public static void joinVideos1(List<String> srcVideos, String destVideo) {
		
		IMediaReader sampleReader = ToolFactory.makeReader(srcVideos.get(0));
		MediaConcatenator sampleCon = new MediaConcatenator(1, 0);
		sampleReader.addListener(sampleCon);
		//sampleReader.addListener(ToolFactory.makeViewer());
		
		//sampleCon.addListener(ToolFactory.makeViewer());
		while (sampleReader.readPacket() == null) {
			if (sampleCon.WIDTH != -1) {
				break;
			}
		}
		
		//System.out.println("*" + sampleCon.WIDTH + " , " + sampleCon.HEIGHT);
		sampleReader.close();
		
	    // video parameters

	    final int videoStreamIndex = 0;
	    final int videoStreamId = 0;
	    final int width = sampleCon.WIDTH;
	    final int height = sampleCon.HEIGHT;//MediaConcatenator.HEIGHT;

	    // audio parameters

	    final int audioStreamIndex = 1;
	    final int audioStreamId = 0;
	    final int channelCount = 2;
	    final int sampleRate = 44100; // Hz
		
		
		List<IMediaReader> readers = new ArrayList<IMediaReader>();
		MediaConcatenator listenAdapter = new MediaConcatenator(audioStreamIndex, videoStreamIndex);
		
		for (String srcVideo : srcVideos) {
		
		//for(int i=0; i<srcVideos.length; i++) {
			IMediaReader reader = ToolFactory.makeReader(srcVideo);
			reader.addListener(listenAdapter);
			readers.add(reader);			
		}
		IMediaWriter writer = ToolFactory.makeWriter(destVideo);
		listenAdapter.addListener(writer);
		writer.addAudioStream(audioStreamIndex, audioStreamId, channelCount, sampleRate);
		writer.addVideoStream(videoStreamIndex, videoStreamId, width, height);
		
		System.out.println("Start ... " + new Date());
		for (IMediaReader reader: readers) {
			//IContainer ic = reader.getContainer();
			
			
			//IContainerParameters icps = reader.getContainer().getParameters();
			
			//writer.addListener(reader);
		
			while (reader.readPacket() == null);
			System.out.println("Finish reading - " + reader.getUrl());
			reader.close();
		}
		
		writer.close();
		
		System.out.println("Done merging..." + new Date());
		
	}

}
