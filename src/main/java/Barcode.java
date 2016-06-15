import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.video.xuggle.XuggleVideo;

import java.io.File;
import java.io.IOException;


public class Barcode {
	
	public static final int TAILLEBANDE = 5;
	

    public static void main(String args[]) throws IOException {
    	
    	XuggleVideo video = new XuggleVideo("src/main/resources/resume2.mp4");
    	
    	File sortie;
    	int cptX = 0;
    	int cpt = 0;
    	
    	MBFImage frame = null;
    	MBFImage frameResume = null;
    	MBFImage imgSortie = new MBFImage((int)(TAILLEBANDE * video.countFrames()) / 10,video.getCurrentFrame().getHeight() );
    	System.out.println(video.getCurrentFrame().getHeight());
    	
    	System.out.println(video.countFrames());    	
    	while (video.hasNextFrame()){
    		
    		if (cpt %13 == 0){

    			frame = video.getCurrentFrame();
    			frameResume = new MBFImage(TAILLEBANDE,frame.getHeight());

    			frameResume.drawImage(frame, 0,0);
    			
    			
    			imgSortie.drawImage(frameResume,cptX,0);
    			cptX += TAILLEBANDE;
    			System.out.println(cpt);
    		
    		}
    		
    		frame = video.getNextFrame();
    		cpt++;
    	    		
    	}
    	sortie = new File("frame.png");
		ImageUtilities.write(imgSortie,"png", sortie);
    	video.close();
    	
    	
    	
    	

    	
    }
}
    	
    	
