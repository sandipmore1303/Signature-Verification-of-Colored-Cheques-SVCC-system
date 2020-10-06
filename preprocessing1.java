
//package ij.gui;
import java.awt.*;
import java.awt.color.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import java.util.*;
import javax.media.jai.*;
import javax.media.jai.operator.*;
import javax.media.jai.widget.*;
import java.awt.Frame;
import java.awt.image.renderable.ParameterBlock;
import java.io.IOException;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import com.sun.media.jai.codec.FileSeekableStream;
import javax.media.jai.widget.ScrollingImagePanel;
import ij.*;
import ij.process.ImageConverter;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import ij.process.*;
import ij.gui.*;
import java.awt.Rectangle;
import ij.*;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import java.awt.*;
import java.awt.color.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import java.util.*;
import javax.media.jai.*;
import javax.media.jai.operator.*;
import javax.media.jai.widget.*;
import java.awt.Frame;
import java.awt.image.renderable.ParameterBlock;
import java.io.IOException;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import com.sun.media.jai.codec.FileSeekableStream;
import javax.media.jai.widget.ScrollingImagePanel;
import ij.*;
import ij.process.ImageConverter;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import ij.process.*;

public class preprocessing1{
	
public void	ResizeImage(String inputFile,String outputFile ){
		//into 120 x 60
		
		

             /*
              * Create an input stream from the specified file name
              * to be used with the file decoding operator.
              */
             FileSeekableStream stream = null;
             try {
                 stream = new FileSeekableStream(inputFile);
             } catch (IOException e) {
                 e.printStackTrace();
                 System.exit(0);
             }

             /* Create an operator to decode the image file. */
             RenderedOp image1 = JAI.create("stream", stream);
             /*
              * Create a standard bilinear interpolation object to be
              * used with the "scale" operator.
              */
             Interpolation interp = Interpolation.getInstance(
                                        Interpolation.INTERP_BILINEAR);

             /**
              * Stores the required input source and parameters in a
              * ParameterBlock to be sent to the operation registry,
              * and eventually to the "scale" operator.
              */
             ParameterBlock params = new ParameterBlock();
             params.addSource(image1);
             float  x_scale_factor=120.0F/image1.getWidth(),y_scale_factor=60.0F/image1.getHeight();
             
             params.add(x_scale_factor);         // x scale factor i.e. width
             params.add(y_scale_factor);         // y scale factor i.e height
             params.add(0.0F);         // x translate
             params.add(0.0F);         // y translate
             params.add(interp);       // interpolation method

             /* Create an operator to scale image1. */
             RenderedOp image2 = JAI.create("scale", params);

             /* Get the width and height of image2. */
             int width = image2.getWidth();
             int height = image2.getHeight();
                  // Define the source and destination file names.
             
   
         
       

           // Encode the file as a BMP image.
           try{
    	    FileOutputStream stream1 = new FileOutputStream(outputFile);
            JAI.create("encode", image2, stream1, "BMP", null);
           }catch(IOException e){}

             // Store the image in the BMP format.
           JAI.create("filestore", image2, outputFile, "BMP", null);

             
		
		
	}
	 
void  Background_Elimination_binarization(String inputFile,String outputFile){
	
	
	
	//code  for removing noise from resized image
	
	/* RenderedOp src =  JAI.create("fileload", inputFile);
     //JAI.create("filestore", src, outputFile, null);
     // Create the medianfilter operation.
     //RenderedOp dst = JAI.create("medianfilter", src,);//default 3 x 3 median filter
     RenderedOp dst = JAI.create("medianfilter", src);
     JAI.create("filestore", dst, outputFile+".bmp", null);
	
	*/
	
	
	 	//eliminate the background assuming single colored background by settimg it to 0 & all others to 1
	 	
	 	ImagePlus impsrc = IJ.openImage(inputFile);  
        IJ.saveAs(impsrc,"bmp",outputFile);
	    ImagePlus impdst = IJ.openImage(outputFile+".bmp");  
	    //ImagePlus impdst = IJ.openImage(outputFile);  
        ImageConverter ic=new ij.process.ImageConverter(impdst);
	    ic.convertToGray8();
	    IJ.saveAs(impdst,"8-bit bmp",outputFile);
	    
        // Load the file.
        PlanarImage src1 = JAI.create("fileload",outputFile+".bmp");       

 
        // Generate a histogram.
        Histogram histogram =
            (Histogram)JAI.create("histogram", src1).getProperty("histogram");

        // Get a threshold equal to the median.
        //double[] threshold = histogram.getPTileThreshold(0.5);
        double[] threshold = histogram.getIterativeThreshold();
        
        
        
        // Binarize the image.
        PlanarImage dst1 =
            JAI.create("binarize", src1, new Double(threshold[0]));
         
            
        //to store images in monochrome format            
        JAI.create("filestore", dst1, outputFile+".bmp", null);
        
        
          
        
        //to store image as 8 bit grayscale
        
       
        impdst = IJ.openImage(outputFile+".bmp"); 
        IJ.saveAs(impdst,"8-bit bmp",outputFile);
       
         
        
	 }
	 
	
	 static PlanarImage rotate(PlanarImage inImg,float x,float y,double a) 
  	 	{
        //Interpolation interp = Interpolation.getInstance(Interpolation.INTERP_NEAREST);
        //Interpolation interp = Interpolation.getInstance(Interpolation.INTERP_BICUBIC);
        double backgroundValues[]={255.0,255.0,255.0};
        Interpolation interp = Interpolation.getInstance(Interpolation. INTERP_BILINEAR);
        ParameterBlockJAI pb = new ParameterBlockJAI("rotate");
        
        pb.setSource("source0", inImg);
        
   //System.out.println(pb.getIntParameter("xOrigin"));
    pb.setParameter("xOrigin", (float)x);
    pb.setParameter("yOrigin", (float)y);
    pb.setParameter("angle", (float)Math.toRadians((double)(a)));
    pb.setParameter("interpolation", interp);
    pb.setParameter("backgroundValues", backgroundValues);

    PlanarImage outImg= JAI.create("Rotate", pb, null);
        
        /*params.setSource("D:\SIGNATUREDATBASE\\gen6.bmp",inImg);
        params.setParameter(,(float)x); 
        params.setParameter(,(float)x);            // x org
        params.add((float)y);         // y org
        params.add((float)Math.toRadians((double)(a)));        // angle
        params.add(interp);       // interpolation method
        params.setParameter("backgroundValues", backgroundValues);
        PlanarImage outImg = JAI.create("rotate", params);*/
        return outImg;
      }
	 
void Size_Normalization(String inputFile,String outputFile){
	 	/*signature positional
information by calculating an angle 0 of corrective
rotation about the centroid of the (x,y) such that
rotating the signature by 0 brings it back to a uniform
baseline. We calculate 0 by maximizing the deviation
of the data, one direction, e.g. the x direction. The
image size is adjusted so that the width reaches a
default value while the height-to-width ratio remains
unchanged. The size normalization in offline
signature verification is important because it
establishes a common ground for image comparison.
A low spatial resolution makes all signatures look
	 	*/
	 	 	
	 	
	 	
	

	
	//find angle Beta of orientation of image of input image
/*	ImagePlus iplus=IJ.openImage(inputFile);
	ImageProcessor ip=iplus.getProcessor();
   
    	
	
	int  i,j,z=0,count=0,m=0,n=0,o=0,block_no=0;
	double x_=0.0,y_=0.0,M_xwt=0.0,M_ywt=0.0,M_t=0.0;
	double d=0.0;
	
	//find centroid of upper half of image
/*	Roi roi=new Roi(0,0,iplus.getWidth(),iplus.getHeight()/2,iplus);
	Rectangle re=roi.getBounds();
 	
	System.out.println("Max_x="+re.getMaxX()+"Max_y="+re.getMaxY()); 
	System.out.println("Min_x="+re.getMinX()+"Min_y="+re.getMinY()); 
	for(m=(int)re.getMinX();m<re.getMaxX();m++)
	  for(n=(int)re.getMinY();n<re.getMaxY();n++)
			{
			count++;				
	   	    //d=roi.getAngle((int)re.getMinX(),(int)re.getMaxY()-1,m,n);
	   	    if(ip.get(m,n)==0)
	   	    {
	   	    z++;
	   	    //System.out.println("Sign Pixel");
	   	    M_t=M_t+1.0;
	   	    M_xwt=M_xwt+m;
	   	    M_ywt=M_ywt+n;
		    //System.out.println("x1="+((int)re.getMinX())+"     y1="+((int)re.getMaxY()-1)+"    m="+m+"    n="+n+"   d="+d);		
			}
			else
			{ o++;
			//System.out.println("Non Sign Pixel");
		    //System.out.println("x1="+((int)re.getMinX())+"     y1="+((int)re.getMaxY()-1)+"    m="+m+"    n="+n+"   d="+d);		
			
			}		
		
		    }	
     	
	
	  /* System.out.println("BLACK="+z);  
	   System.out.println("WHITE="+o);  
	   System.out.println("COUNT="+count); 
	   System.out.println("M_xwt="+M_xwt);  
	   System.out.println("M_ywt="+M_ywt);  
	   System.out.println("M_t="+M_t); 
	   	
	   	*/

/*	   		 M_xwt= M_xwt/M_t;
	   		 M_ywt= M_ywt/M_t;
	   		 	
	  /* System.out.println("X_="+((int) M_xwt));  	
	   System.out.println("Y_="+((int) M_ywt));  	
	   System.out.println("X_="+( M_xwt));  	
	   System.out.println("Y_="+( M_ywt));  
	   			
	*/
	
	
	//find centroid of lower half of image	
	//roi.setLocation(0,(int)re.getMaxY());
/*	roi=new Roi(0,iplus.getHeight()/2,iplus.getWidth(),iplus.getHeight()/2,iplus);
	re=roi.getBounds();
    i=0;j=0;	d=0.0;
    int z1=0;
    int count1=0;
    m=0;n=0;
    int o1=0;
    block_no=0;
	double x_1=0.0;
	double y_1=0.0;
	double M_xwt1=0.0; 
	double M_ywt1=0.0;
	double M_t1=0.0;

*/
/*		System.out.println("Max_x="+re.getMaxX()+"Max_y="+re.getMaxY()); 
		System.out.println("Min_x="+re.getMinX()+"Min_y="+re.getMinY()); 
		for(m=(int)re.getMinX();m<re.getMaxX();m++)
			for(n=(int)re.getMinY();n<re.getMaxY();n++)
			{
			count1++;				
	   	    //d=roi.getAngle((int)re.getMinX(),(int)re.getMaxY()-1,m,n);
	   	    if(ip.get(m,n)==0)
	   	    {
	   	    z1++;
	   	    //System.out.println("Sign Pixel");
	   	    M_t1=M_t1+1.0;
	   	    M_xwt1=M_xwt1+m;
	   	    M_ywt1=M_ywt1+n;
		    //System.out.println("x1="+((int)re.getMinX())+"     y1="+((int)re.getMaxY()-1)+"    m="+m+"    n="+n+"   d="+d);		
			}
			else
			{ o1++;
			//System.out.println("Non Sign Pixel");
		    //System.out.println("x1="+((int)re.getMinX())+"     y1="+((int)re.getMaxY()-1)+"    m="+m+"    n="+n+"   d="+d);		
			
			}		
		
		    }	
     	
	
	   /*System.out.println("BLACK="+z1);  
	   System.out.println("WHITE="+o1);  
	   System.out.println("COUNT="+count1); 
       System.out.println("TOTAL BLACK="+(z+z1));  
	   System.out.println("TOTAL WHITE="+(o+o1));  
	   System.out.println("TOTAL COUNT="+(count+count1)); 	   		
	   System.out.println("M_xwt="+M_xwt1);  
	   System.out.println("M_ywt="+M_ywt1);  
	   System.out.println("M_t="+M_t1); */
	   	
/*	   		 M_xwt1= M_xwt1/M_t1;
	   		 M_ywt1= M_ywt1/M_t1;
*/	   		 	
	   	/*	System.out.println("X_="+((int) M_xwt1));  	
	   		System.out.println("Y_="+((int) M_ywt1));  	
	   		System.out.println("X_="+( M_xwt1));  	
	   		System.out.println("Y_="+( M_ywt1)); 
	   			
	   	*/	
	   			
	   			
	   			
	
	
	//find centroid of image
	
	
	
//	roi=new Roi(0,0,iplus.getWidth(),iplus.getHeight(),iplus);
	//find angle Beta of orientation of image of input image

/*	double beta_orient= roi.getAngle((int) M_xwt1,(int) M_ywt1,(int) M_xwt,(int) M_ywt);
	
	 re=roi.getBounds();
	 x_1=0.0;
     y_1=0.0;
     M_xwt1=0.0; 
     M_ywt1=0.0;
     M_t1=0.0;
	
		//System.out.println("angle_orient="+beta_orient);
	
	
	
	for(m=(int)re.getMinX();m<re.getMaxX();m++)
			for(n=(int)re.getMinY();n<re.getMaxY();n++)
			{
			count1++;				
	   	    //d=roi.getAngle((int)re.getMinX(),(int)re.getMaxY()-1,m,n);
	   	    if(ip.get(m,n)==0)
	   	    {
	   	    z1++;
	   	    //System.out.println("Sign Pixel");
	   	    M_t1=M_t1+1.0;
	   	    M_xwt1=M_xwt1+m;
	   	    M_ywt1=M_ywt1+n;
		    //System.out.println("x1="+((int)re.getMinX())+"     y1="+((int)re.getMaxY()-1)+"    m="+m+"    n="+n+"   d="+d);		
			}
			else
			{ o1++;
			//System.out.println("Non Sign Pixel");
		    //System.out.println("x1="+((int)re.getMinX())+"     y1="+((int)re.getMaxY()-1)+"    m="+m+"    n="+n+"   d="+d);		
			
			}		
		
		    }	
     	
	
	   /*
	    System.out.println("\n\nBLACK="+z1);  
	   System.out.println("WHITE="+o1);  
	   System.out.println("COUNT="+count1); 
       System.out.println("TOTAL BLACK="+(z+z1));  
	   System.out.println("TOTAL WHITE="+(o+o1));  
	   System.out.println("TOTAL COUNT="+(count+count1)); 	   		
	   System.out.println("M_xwt="+M_xwt1);  
	   System.out.println("M_ywt="+M_ywt1);  
	   System.out.println("M_t="+M_t1); 
	   	*/
	   	/*	 M_xwt1= M_xwt1/M_t1;
	   		 M_ywt1= M_ywt1/M_t1;
	   		 	
	   	/*	System.out.println("X_="+((int) M_xwt1));  	
	   		System.out.println("Y_="+((int) M_ywt1));  	
	   		System.out.println("X_="+( M_xwt1));  	
	   		System.out.println("Y_="+( M_ywt1));  
	   			
	 	*/
	 	
	 	
	 	
	 		
	 	
	 	/*
	 	
	 	
	 	
	 	
     PlanarImage inImg=JAI.create("fileload",inputFile); 
     //PlanarImage outImg=rotate(inImg,37.5f,20.90f,value);
     PlanarImage outImg=rotate(inImg,(float)M_xwt1,(float)M_ywt1,beta_orient);
     JAI.create("filestore", outImg, outputFile, null);
     ImagePlus impdst = IJ.openImage(outputFile); 
     ImageConverter ic=new ij.process.ImageConverter(impdst);
	 ic.convertToGray8();	 
     IJ.saveAs(impdst,"8-bit bmp",outputFile);
     
     
     // Load the file.
        PlanarImage src1 = JAI.create("fileload",outputFile);       

 
        // Generate a histogram.
        Histogram histogram =
            (Histogram)JAI.create("histogram", src1).getProperty("histogram");

        // Get a threshold equal to the median.
        //double[] threshold = histogram.getPTileThreshold(0.5);
        double[] threshold = histogram.getIterativeThreshold();
        
        
        
        // Binarize the image.
        PlanarImage dst1 =
            JAI.create("binarize", src1, new Double(threshold[0]));
         
            
        //to store images in monochrome format            
        JAI.create("filestore", dst1,outputFile, null);
        
        
        
        
        
        
        */
        
	 	
	 	
	
	
	//III_Modify_ROI_Image vio=new III_Modify_ROI_Image();
	
	//find angle Beta of orientation of image of input image
	ImagePlus iplus=IJ.openImage(inputFile);
	ImageProcessor ip=iplus.getProcessor();
   
   

//int max_x=0,min_x=iplus.getWidth()-1,,max_y=0,min_y=iplus.getHeight()-1,min_x1=(int)((iplus.getWidth()-1)*Math.tan(beta_orient)),max_x1=0;
int max_x=0,min_x=iplus.getWidth()-1,max_y=0,min_y=iplus.getHeight()-1,X=0,Y=0,Xt=0,Yt=0,m=0,n=0;

for( m=0;m<iplus.getWidth();m++)
			for( n=0;n<iplus.getHeight();n++)
			{if(ip.get(m,n)==0)
			    {
			    	  	
			    	
			    if(min_x>=m)
			    	min_x=m;
		         if(min_y>=n)
				    min_y=n;	
                 if(max_x<=m)
				    max_x=m;
				 if(max_y<=n)
				    max_y=n;
				    
				 					
				
			     }
			    				
			}
			
			//System.out.println("min_x="+min_x);  
	        //System.out.println("max_x="+max_x);  
	        //System.out.println("min_y="+min_y);  
	        //System.out.println("max_y="+max_y);
	        //System.out.println("min_x1="+min_x1);  
	        //System.out.println("max_x1="+max_x1);  
  



//create image of   width=max_x-Min_x
//                  height=max_y-Min_y
//ImagePlus iplus = IJ.openImage("D:\SIGNATUREDATBASE\\gen1.bmp"); 


ImagePlus impdst = IJ.createImage(outputFile,
                                    "8-bit",
                                    max_x-min_x+1,
                                    max_y-min_y+1,
                                   1);
 //IJ.save(impdst,"D:\SIGNATUREDATBASE");                                  
IJ.saveAs(impdst,"8-bit bmp",outputFile);

impdst=IJ.openImage(outputFile);
ImageProcessor op=impdst.getProcessor();



for( m=min_x;m<=max_x;m++)
			for( n=min_y;n<=max_y;n++)
			{if(ip.get(m,n)==0)
			    {
			    	op.putPixel(m-min_x,  n-min_y,  0x00000000); 
			    	
			    }				
			}



IJ.saveAs(impdst,"8-bit bmp",outputFile);	 	
 	
	 }
	 
	 
	 
	 
void Noise_Reduction(String inputFile,String outputFile){
	 	//use median fiter
	 	
	   // Load the image.
     RenderedOp src =  JAI.create("fileload", inputFile);
     JAI.create("filestore", src, outputFile, null);
     // Create the medianfilter operation.
     //RenderedOp dst = JAI.create("medianfilter", src,);//default 3 x 3 median filter
     RenderedOp dst = JAI.create("medianfilter", src);
     JAI.create("filestore", dst, outputFile, null);

	 }
	 
	 
	 

	 
void Thinning_Skeletonization(String ip,String op) throws IOException{
	 	/*The simplified algorithm
used here consists of the following three steps:

Step 1: mark all the points of the signature that are
candidates for removing (black pixels that have at
least one white 8-neighbor and at least two black 8-
neighbors pixels).

Step 2: Examine one by one all of them, following
the contour lines of the signature image, and remove
these as their removal will not cause a break in the
resulting pattern.

Step 3: If at least one point was deleted go again to
Step 1 and repeat the process once more.

Skeletonization makes the extracted features
invariant to image characteristics like the qualities of
the pen, the paper, the signer used, the digitizing
method and quality.
	 	 
	 	 */
        //ImagePlus impsrc = IJ.openImage(ip);  
        //IJ.saveAs(ip,op);
        File f2=new File(ip);
        File f1=new File(op);	 	 
	 	BufferedImage image2=null;
	 		try{
        	image2 = ImageIO.read(f2);
        	} catch(IOException e){System.out.println("Can't read File:"+ip);}
       ByteProcessor bp=new ByteProcessor(image2);
       bp.skeletonize();
       ImageIO.write( image2, "bmp" /* "png" "jpeg" ... format desired */,
               f1 /* target */ ); 
               	
               	
               	
        // Load the file.
        PlanarImage src1 = JAI.create("fileload",op);

       

 
        // Generate a histogram.
        Histogram histogram =
            (Histogram)JAI.create("histogram", src1).getProperty("histogram");

        // Get a threshold equal to the median.
        //double[] threshold = histogram.getPTileThreshold(0.5);
        double[] threshold = histogram.getIterativeThreshold();       
        
        
        // Binarize the image.
        PlanarImage dst1 =
            JAI.create("binarize", src1, new Double(threshold[0]));
            
            
        //to store images in monochrome format            
        JAI.create("filestore", dst1, op, null);      	
                      	         	
           	

}
	 
	 
	   
   
   
   public static void mainp() throws Exception {
	    	
int	NoofClasses=0,NoofSamples[],totalnooffiles=0;
File f1,f2;
File f = new File("D:\\SIGNATUREDATBASE\\SIGNATUREDB_match");
String[] DirinDir = f.list();
Arrays.sort(DirinDir);
System.out.println("In step INITIALIZE :finding no of persons,no of smples in each class,total no of samples");   
for(int class_no=0;class_no<DirinDir.length;class_no++)
 { 	 
  f1 = new File("D:\\SIGNATUREDATBASE\\SIGNATUREDB_match\\"+DirinDir[class_no]); 
  if(f1.isDirectory()) 
  {NoofClasses++;
  }
 }

System.out.println("Noofclasses="+NoofClasses);

NoofSamples=new int[NoofClasses];


//find total no of classes & no of samples in each class
  
  
for(int class_no=0,current_class=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("D:\\SIGNATUREDATBASE\\SIGNATUREDB_match\\"+DirinDir[class_no]); 
   if(f1.isDirectory()) 
   {  
      String children[] = f1.list();  
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File("D:\\SIGNATUREDATBASE\\SIGNATUREDB_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
         	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         { totalnooffiles++;
           
          NoofSamples[current_class]++;
         }
      }
    current_class ++;
       
   }
  
  }
  
for(int i=0;i<NoofClasses;i++)
 System.out.println(NoofSamples[i]);
 
  
       
System.out.println("totalnooffiles:"+totalnooffiles);








//create output directories for each class 

f = new File("D:\\SIGNATUREDATBASE\\SIGNATUREDB_match");
DirinDir = f.list();
String strManyDirectories;
Arrays.sort(DirinDir);
boolean success;

for(int class_no=0;class_no<DirinDir.length;class_no++)
 { 	 
  f1 = new File("D:\\SIGNATUREDATBASE\\SIGNATUREDB_match\\"+DirinDir[class_no]); 
  if(f1.isDirectory()) 
  {
  	try{
		
	    strManyDirectories="D:\\SIGNATUREDATBASE\\Resized_match\\"+DirinDir[class_no];

		// Create multiple directories
	      	success= (new File(strManyDirectories)).mkdirs();
		
		
		if (success) {
			System.out.println("Directories: " + strManyDirectories + " created");
		}
		else 
			System.out.println("Directories: " + strManyDirectories + "not created");
			
			
			
			
			strManyDirectories="D:\\SIGNATUREDATBASE\\BackgroundEliminated_Binarized_match\\"+DirinDir[class_no];

		// Create multiple directories
		 success = (new File(strManyDirectories)).mkdirs();
		
		
		if (success) {
			System.out.println("Directories: " + strManyDirectories + " created");
		}
		else 
			System.out.println("Directories: " + strManyDirectories + "not created");
			
			
			
			
			strManyDirectories="D:\\SIGNATUREDATBASE\\Size_Normalized_match\\"+DirinDir[class_no];

		// Create multiple directories
		success = (new File(strManyDirectories)).mkdirs();
		
		
		if (success) {
			System.out.println("Directories: " + strManyDirectories + " created");
		}
		else 
			System.out.println("Directories: " + strManyDirectories + "not created");
			
			
			
			
				strManyDirectories="D:\\SIGNATUREDATBASE\\Noise_Reduced_match\\"+DirinDir[class_no];

		// Create multiple directories
		 success = (new File(strManyDirectories)).mkdirs();
		
		
		if (success) {
			System.out.println("Directories: " + strManyDirectories + " created");
		}
		else 
			System.out.println("Directories: " + strManyDirectories + "not created");
			
			
			
			
			
				strManyDirectories="D:\\SIGNATUREDATBASE\\Thinned_Skeletenized_match\\"+DirinDir[class_no];

		// Create multiple directories
		 success = (new File(strManyDirectories)).mkdirs();
		
		
		if (success) {
			System.out.println("Directories: " + strManyDirectories + " created");
		}
		else 
			System.out.println("Directories: " + strManyDirectories + "not created");
			
			
			
			
			

		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());}
 }
 }

preprocessing1 preprocessing_obj=new preprocessing1();
	    


  //code for noise resizing image to 120 x 60
int current_class=0;
for(int class_no=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("D:\\SIGNATUREDATBASE\\SIGNATUREDB_match\\"+DirinDir[class_no]); 
   if(f1.isDirectory()) 
   {  
      String children[] = f1.list();  
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File("D:\\SIGNATUREDATBASE\\SIGNATUREDB_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
         	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         { preprocessing_obj.ResizeImage("D:\\SIGNATUREDATBASE\\SIGNATUREDB_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles],"D:\\SIGNATUREDATBASE\\Resized_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);	    
	      
         }
      }
    current_class ++;
       
   }
  
  }
  
  
  
  
    //code for background elimination & binarization
  
  //int startindex=0;
  //String test=null;
  String onlynameoffile=null;
  current_class=0;
  for(int class_no=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("D:\\SIGNATUREDATBASE\\Resized_match\\"+DirinDir[class_no]); 
   if(f1.isDirectory()) 
   {  
      String children[] = f1.list();  
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File("D:\\SIGNATUREDATBASE\\Resized_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
         	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         { 
          
           onlynameoffile=children[NoofFiles].substring(0,children[NoofFiles].indexOf('.',0));
           //System.out.println(children[NoofFiles]);
           //System.out.println(onlynameoffile);    
	       preprocessing_obj.Background_Elimination_binarization("D:\\SIGNATUREDATBASE\\Resized_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles],"D:\\SIGNATUREDATBASE\\BackgroundEliminated_Binarized_match\\"+DirinDir[class_no]+"\\"+onlynameoffile);
           
         }
      }
    current_class ++;
       
   }
  
  }
  
  //code for noise reduction 
  
 
  
  current_class=0;
  for(int class_no=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("D:\\SIGNATUREDATBASE\\BackgroundEliminated_Binarized_match\\"+DirinDir[class_no]); 
   if(f1.isDirectory()) 
   {  
      String children[] = f1.list();  
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File("D:\\SIGNATUREDATBASE\\BackgroundEliminated_Binarized_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
         	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         { 
                    
	       preprocessing_obj.Noise_Reduction("D:\\SIGNATUREDATBASE\\BackgroundEliminated_Binarized_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles],"D:\\SIGNATUREDATBASE\\Noise_Reduced_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
           
         }
      }
    current_class ++;
       
   }
  
  }
  
    
  
  
  
  
  
  
    //code for skeletaniztion
  current_class=0;
  for(int class_no=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("D:\\SIGNATUREDATBASE\\BackgroundEliminated_Binarized_match\\"+DirinDir[class_no]); 
   if(f1.isDirectory()) 
   {  
      String children[] = f1.list();  
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File("D:\\SIGNATUREDATBASE\\BackgroundEliminated_Binarized_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
         	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         { 
                    
	       preprocessing_obj.Thinning_Skeletonization("D:\\SIGNATUREDATBASE\\BackgroundEliminated_Binarized_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles],"D:\\SIGNATUREDATBASE\\Thinned_Skeletenized_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
           
         }
      }
    current_class ++;
       
   }
  
  }
  
    
   /* 
    //code for size normalization
  current_class=0;
  for(int class_no=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("D:\SIGNATUREDATBASE\\SIGNATUREDB_Thinned_Skeletenized_match\\"+DirinDir[class_no]); 
   if(f1.isDirectory()) 
   {  
      String children[] = f1.list();  
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File("D:\SIGNATUREDATBASE\\SIGNATUREDB_Thinned_Skeletenized_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
         	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         { 
                    
	       preprocessing_obj.Size_Normalization("D:\SIGNATUREDATBASE\\SIGNATUREDB_Thinned_Skeletenized_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles],"D:\SIGNATUREDATBASE\\SIGNATUREDB_Size_Normalized_match\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
           
         }
      }
    current_class ++;
       
   }
  
  }
  
    
  */
  
  
  
  
  
  
  
  
  
  
  } 
   

	 
	 

}
