/**
 * @(#)FeatureExtraction.java
 *
 *
 * @author 
 * @version 1.00 2010/2/26
 */
//package AutomaticSignatureVerification;
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
//import AutomaticSignatureVerification.preprocessing.*;
import ij.*;
import ij.process.ImageConverter.*;
import java.io.*;
import java.io.File;
import java.util.Arrays;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

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
import java.sql.*;
import javax.sql.*; 
import javax.naming.*;
import java.io.File;
import java.util.Date;
import jxl.*;
import jxl.write.*;
import jxl.write.WritableWorkbook;


public class FeatureExtraction {

	ImagePlus iplus;
    ImageProcessor ip;
    WritableWorkbook workbook;
    public void Initialize() throws Exception
    {  
    	
       
    }
   
    
    
  public void   GlobalFeatures (WritableSheet global,String inputFile,String classname,int rowno)throws IOException,jxl.write.WriteException 
  	{
    
    	/*
Signature height. 
The height of the signature image,
after width normalization, can be considered as a way
of representing the height-to-width ratio.

Image area.
 The number of black pixels in the
image. In skeletonized signature images, it represents
a measure of the density of the signature traces.

Pure width.
 The width of the image with horizontal
blank spaces removed.

Pure height.
 The height of the signature image after
vertical blank spaces removed.

Baseline shift.
 The deference, between the vertical
centers of gravity of the left and the right part of the
image. It was taken as a measure for the orientation
of the signature.

Number of edge points.
 An edge point is defined as
a signature point that has only one 8-neighbor.


*/

    




	
	ImagePlus iplus=IJ.openImage(inputFile);
	ImageProcessor ip=iplus.getProcessor();
   
   

//int max_x=0,min_x=iplus.getWidth()-1,,max_y=0,min_y=iplus.getHeight()-1,min_x1=(int)((iplus.getWidth()-1)*Math.tan(beta_orient)),max_x1=0;
int max_x=0,min_x=iplus.getWidth()-1,max_y=0,min_y=iplus.getHeight()-1,X=0,Y=0,Xt=0,Yt=0,m=0,n=0,	blackpixels=0;

for( m=0;m<iplus.getWidth();m++)
			for( n=0;n<iplus.getHeight();n++)
			{if(ip.get(m,n)==0)
			    {
			    	blackpixels++;  	
			    	
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
			
			
			
int area=	blackpixels;
int pure_width=max_x-min_x+1;
int pure_height=max_y-min_y+1;







///anngle of orientation


	
	
	int  i,j,z=0,count=0;
	m=0;n=0;
	int o=0;
	int block_no=0;
	double x_=0.0,y_=0.0,M_xwt=0.0,M_ywt=0.0,M_t=0.0;
	double d=0.0;
	
	//find centroid of upper half of image
	Roi roi=new Roi(0,0,iplus.getWidth(),iplus.getHeight()/2,iplus);
	Rectangle re=roi.getBounds();
 	
	//System.out.println("Max_x="+re.getMaxX()+"Max_y="+re.getMaxY()); 
	//System.out.println("Min_x="+re.getMinX()+"Min_y="+re.getMinY()); 
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
     	
	
	   /*
	    *System.out.println("BLACK="+z);  
	   System.out.println("WHITE="+o);  
	   System.out.println("COUNT="+count); 
	   System.out.println("M_xwt="+M_xwt);  
	   System.out.println("M_ywt="+M_ywt);  
	   System.out.println("M_t="+M_t); 
	   	*/
	   		 M_xwt= M_xwt/M_t;
	   		 M_ywt= M_ywt/M_t;
	   		 	
	   /*
	   System.out.println("X_="+((int) M_xwt));  	
	   System.out.println("Y_="+((int) M_ywt));  	
	   System.out.println("X_="+( M_xwt));  	
	   System.out.println("Y_="+( M_ywt));  
	   			
	
	
	*/
	
	//find centroid of lower half of image	
	//roi.setLocation(0,(int)re.getMaxY());
	roi=new Roi(0,iplus.getHeight()/2,iplus.getWidth(),iplus.getHeight()/2,iplus);
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

		//System.out.println("Max_x="+re.getMaxX()+"Max_y="+re.getMaxY()); 
		//System.out.println("Min_x="+re.getMinX()+"Min_y="+re.getMinY()); 
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
	    System.out.println("BLACK="+z1);  
	   System.out.println("WHITE="+o1);  
	   System.out.println("COUNT="+count1); 
       System.out.println("TOTAL BLACK="+(z+z1));  
	   System.out.println("TOTAL WHITE="+(o+o1));  
	   System.out.println("TOTAL COUNT="+(count+count1)); 	   		
	   System.out.println("M_xwt="+M_xwt1);  
	   System.out.println("M_ywt="+M_ywt1);  
	   System.out.println("M_t="+M_t1); 
	   	*/
	   	
	   		 M_xwt1= M_xwt1/M_t1;
	   		 M_ywt1= M_ywt1/M_t1;
	   		 	
	   	/*	System.out.println("X_="+((int) M_xwt1));  	
	   		System.out.println("Y_="+((int) M_ywt1));  	
	   		System.out.println("X_="+( M_xwt1));  	
	   		System.out.println("Y_="+( M_ywt1));  
	   	*/		
	   			
	   			
	//find angle Beta of orientation of image of input image
	
	roi=new Roi(0,0,iplus.getWidth(),iplus.getHeight(),iplus);
	re=roi.getBounds();
	
	 //in degrees  	
	double beta_orient= roi.getAngle((int) M_xwt1,(int) M_ywt1,(int) M_xwt,(int) M_ywt);
	
	
	//in radians
	//beta_orient=beta_orient*Math.PI/180.0;
	
	//double beta_orient= roi.getAngle((int) M_xwt,(int) M_ywt,(int) M_xwt1,(int) M_ywt1);  	

	System.out.println("angle_orient="+beta_orient);
	/*System.out.println("Max_x="+re.getMaxX()+"Max_y="+re.getMaxY()); 
	System.out.println("Min_x="+re.getMinX()+"Min_y="+re.getMinY()); 
	//ip.set()	
*/

      //workbook = Workbook.createWorkbook(new File("D:\SIGNATUREDATBASE\\FeatureExtracted.xls"));
      //WritableSheet global = workbook.createSheet("Global Features",0);
    	
       jxl.write.Number number0 = new jxl.write.Number(0,rowno,area);
       global.addCell(number0);
       jxl.write.Number number1 = new jxl.write.Number(1,rowno,pure_width);
       global.addCell(number1);
       jxl.write.Number number2 = new jxl.write.Number(2,rowno,pure_height);
       global.addCell(number2);
       jxl.write.Number number3 = new jxl.write.Number(3,rowno,beta_orient);
       global.addCell(number3);
       global.addCell(new jxl.write.Label(4,rowno,classname));
       System.out.println("rno"+rowno);
       	   

   }
    
    
    
    
    
 public void  TextureFeatures(WritableSheet texture,String inputFile,String classname,int rowno) throws IOException,jxl.write.WriteException 
  	{
      	
  	
  	
	ImagePlus iplus=IJ.openImage(inputFile);
	ImageProcessor ip=iplus.getProcessor();
   
   

//int max_x=0,min_x=iplus.getWidth()-1,,max_y=0,min_y=iplus.getHeight()-1,min_x1=(int)((iplus.getWidth()-1)*Math.tan(beta_orient)),max_x1=0;
int 
m=0,n=0;
int P_d_1[][]=new int[2][2];

for( m=0;m<iplus.getWidth();m++)
			for( n=0;n<iplus.getHeight();n++)
			{if(m!=(iplus.getWidth()-1))
			{
			if(ip.get(m,n)==0 && ip.get(m+1,n)==0)
				P_d_1[0][0]++;
					
				
			}
			}
		
			
for( m=0;m<iplus.getWidth();m++)
			for( n=0;n<iplus.getHeight();n++)
			{if(n!=(iplus.getHeight()-1))
			{
			if(ip.get(m,n)==0 && ip.get(m,n+1)==0)
				P_d_1[0][0]++;
					
				
			}
			}
			
			System.out.println("P_d_1[0][0]="+	P_d_1[0][0]);
			
			
			
			
			
for( m=0;m<iplus.getWidth();m++)
			for( n=0;n<iplus.getHeight();n++)
			{if(m!=(iplus.getWidth()-1))
			{
			if(ip.get(m,n)==0 && ip.get(m+1,n)==255)
				P_d_1[0][1]++;
					
				
			}
			}
		
			
for( m=0;m<iplus.getWidth();m++)
			for( n=0;n<iplus.getHeight();n++)
			{if(n!=(iplus.getHeight()-1))
			{
			if(ip.get(m,n)==0 && ip.get(m,n+1)==255)
				P_d_1[0][1]++;
					
				
			}
			}
			
			System.out.println("P_d_1[0][1]="+	P_d_1[0][1]);
			





for( m=0;m<iplus.getWidth();m++)
			for( n=0;n<iplus.getHeight();n++)
			{if(m!=(iplus.getWidth()-1))
			{
			if(ip.get(m,n)==255 && ip.get(m+1,n)==0)
				P_d_1[1][0]++;
					
				
			}
			}
		
			
for( m=0;m<iplus.getWidth();m++)
			for( n=0;n<iplus.getHeight();n++)
			{if(n!=(iplus.getHeight()-1))
			{
			if(ip.get(m,n)==255 && ip.get(m,n+1)==0)
				P_d_1[1][0]++;
					
				
			}
			}
			
			System.out.println("P_d_1[1][0]="+	P_d_1[1][0]);			
			
		
		
		
for( m=0;m<iplus.getWidth();m++)
			for( n=0;n<iplus.getHeight();n++)
			{if(m!=(iplus.getWidth()-1))
			{
			if(ip.get(m,n)==255 && ip.get(m+1,n)==255)
				P_d_1[1][1]++;
					
				
			}
			}
		
			
for( m=0;m<iplus.getWidth();m++)
			for( n=0;n<iplus.getHeight();n++)
			{if(n!=(iplus.getHeight()-1))
			{
			if(ip.get(m,n)==255 && ip.get(m,n+1)==255)
				P_d_1[1][1]++;
					
				
			}
			}
			
			System.out.println("P_d_1[1][1]="+	P_d_1[1][1]);
			
	   jxl.write.Number number0 = new jxl.write.Number(0,rowno,P_d_1[0][0]);
       texture.addCell(number0);
       jxl.write.Number number1 = new jxl.write.Number(1,rowno,P_d_1[0][1]);
       texture.addCell(number1);
       jxl.write.Number number2 = new jxl.write.Number(2,rowno,P_d_1[1][0]);
       texture.addCell(number2);
       jxl.write.Number number3 = new jxl.write.Number(3,rowno,P_d_1[1][1]);
       texture.addCell(number3);
       texture.addCell(new jxl.write.Label(4,rowno,classname));
       System.out.println("rno"+rowno);
			
			
					
    	
/*
 *
 **/    	
    }
    
    
    
    
  public void  GridFeatures(WritableSheet grid_distance,WritableSheet grid_angle,int m1,int n1,String inputFile,String classname,int rowno)throws IOException,jxl.write.WriteException 
  	{
    	
      	 
  ImagePlus	iplus=IJ.openImage(inputFile);
  ImageProcessor	ip=iplus.getProcessor();
		
 
   	Roi roi=new Roi(0,0,m1,n1,iplus);
	
	Rectangle re=null;  	
	int  i,j,z=0,count=0,m=0,n=0,o=0,block_no=0;
	
  	double distance =0.0,normalizeddistance=0.0;	
	double angle=0.0,normalizedangle=0.0;
	
    String blockasstringno=null;
	for(i=0;i<iplus.getWidth();i=i+m1)
	{for( j=0;j<iplus.getHeight();j=j+n1)
		{
		roi.setLocation(i,j);
		re=roi.getBounds(); 
		normalizeddistance=0.0;
		normalizedangle=0.0;
		z=0;
		o=0;
		blockasstringno=""+block_no;
		//grid.addCell(new jxl.write.Label(0,block_no+1,blockasstringno));
		System.out.println("Block_no= "+(block_no+1)+" ");
		//System.out.println("Max_x="+re.getMaxX()+"Max_y="+re.getMaxY()); 
		for(m=(int)re.getMinX();m<re.getMaxX();m++)
			for(n=(int)re.getMinY();n<re.getMaxY();n++)
			{
			count++;		
	   	    
				    
	   	    if(ip.get(m,n)==0)
	   	    {
	   	    z++;
	   	    distance=Math.sqrt((re.getMinX()- m)*(re.getMinX()- m) + (re.getMinY()- n)*(re.getMinY()- n));
	   	    angle=roi.getAngle((int)re.getMinX(),(int)re.getMaxY()-1,m,n);
	   	    normalizeddistance +=distance;
		    normalizedangle +=angle;
	   	    System.out.println("Sign Pixel");
	   	    System.out.println("x1="+((int)re.getMinX())+"     y1="+((int)re.getMaxY()-1)+"    m="+m+"    n="+n+"   angle="+angle+"dist="+distance);		
			}
			else
			{ o++;
			//System.out.println("Non Sign Pixel");
		    //System.out.println("x1="+((int)re.getMinX())+"     y1="+((int)re.getMaxY()-1)+"    m="+m+"    n="+n+"   d="+d);		
			}		
		
		    }
		    if(z==0)
		    {
		    normalizeddistance=0;
		    normalizedangle =0;
		    }
   		    else 
   		    {
		    normalizeddistance /=z;
		    normalizedangle /=z;
		    }
            jxl.write.Number number0 = new jxl.write.Number(block_no,rowno,normalizeddistance);
            grid_distance.addCell(number0);
            
            
            jxl.write.Number number1 = new jxl.write.Number(block_no,rowno,normalizeddistance);
            grid_angle.addCell(number1);
            
			System.out.println("normalizeddistance="+normalizeddistance+"normalizedangle ="+normalizedangle );		
				    
		    block_no++; 	
		    	
     	}
	}
	grid_distance.addCell(new jxl.write.Label(block_no,rowno,classname));
	grid_angle.addCell(new jxl.write.Label(block_no,rowno,classname));
	
	      	
	   	
	   	 
    	 
    
    
}



//public static void main(String[] args) throws Exception
  public  void mainf() throws Exception{
	    	
int	NoofClasses=0,NoofSamples[],totalnooffiles=0;
File f1,f2;
File f = new File("D:\\SIGNATUREDATBASE\\Thinned_Skeletenized");
String[] DirinDir = f.list();
Arrays.sort(DirinDir);
System.out.println("In step INITIALIZE :finding no of persons,no of smples in each class,total no of samples");   
for(int class_no=0;class_no<DirinDir.length;class_no++)
 { 	 
  f1 = new File("D:\\SIGNATUREDATBASE\\Thinned_Skeletenized\\"+DirinDir[class_no]); 
  if(f1.isDirectory()) 
  {NoofClasses++;
  }
 }

System.out.println("Noofclasses="+NoofClasses);

NoofSamples=new int[NoofClasses];




	    


//find total no of classes & no of samples in each class
  
  
for(int class_no=0,current_class=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("D:\\SIGNATUREDATBASE\\Thinned_Skeletenized\\"+DirinDir[class_no]); 
   if(f1.isDirectory()) 
   {  
      String children[] = f1.list();  
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File("D:\\SIGNATUREDATBASE\\Thinned_Skeletenized\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
         	 	
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



WorkbookSettings ws = new WorkbookSettings();
    ws.setLocale(new Locale("en", "EN"));
 WritableWorkbook workbook = Workbook.createWorkbook(new File("D:\\SIGNATUREDATBASE\\FeatureExtracted.xls"), ws);

       
    	WritableSheet global = workbook.createSheet("Global Features",0);
    	WritableSheet texture = workbook.createSheet("Texture Features",1);
    	WritableSheet grid_angle = workbook.createSheet("Grid_angle Features",2); 	
    	WritableSheet grid_distance = workbook.createSheet("Grid_distance Features",3); 	
    		   		   	
    		   	
        
       

FeatureExtraction FeatureExtraction_obj=new FeatureExtraction();

//Find global features for each class 
        global.addCell(new jxl.write.Label(0,0,"Image area"));
        global.addCell(new jxl.write.Label(1,0,"Pure width"));
        global.addCell(new jxl.write.Label(2,0,"Pure height"));
        global.addCell(new jxl.write.Label(3,0,"Baseline shift"));
        global.addCell(new jxl.write.Label(4,0,"person class"));



//FeatureExtraction_obj.Initialize();
 
int current_class=0,rowno=1;
for(int class_no=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("D:\\SIGNATUREDATBASE\\Thinned_Skeletenized\\"+DirinDir[class_no]); 
   if(f1.isDirectory()) 
   {  
      String children[] = f1.list();  
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File("D:\\SIGNATUREDATBASE\\Thinned_Skeletenized\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
         	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         { 
          FeatureExtraction_obj.GlobalFeatures( global,"D:\\SIGNATUREDATBASE\\Thinned_Skeletenized\\"+DirinDir[class_no]+"\\"+children[NoofFiles],DirinDir[class_no],rowno);	    
	      rowno++;
         }
      }
    current_class ++;
       
       
   }
  
  }
  
  
  
  
  
  
  //Find texture features for each class 
        texture.addCell(new jxl.write.Label(0,0,"P_d_1[0][0]"));
        texture.addCell(new jxl.write.Label(1,0,"P_d_1[0][1]"));
        texture.addCell(new jxl.write.Label(2,0,"P_d_1[1][0]"));
        texture.addCell(new jxl.write.Label(3,0,"P_d_1[1][1]"));
        texture.addCell(new jxl.write.Label(4,0,"person class"));



//FeatureExtraction_obj.Initialize();
 
 current_class=0;rowno=1;
for(int class_no=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("D:\\SIGNATUREDATBASE\\Thinned_Skeletenized\\"+DirinDir[class_no]); 
   if(f1.isDirectory()) 
   {  
      String children[] = f1.list();  
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File("D:\\SIGNATUREDATBASE\\Thinned_Skeletenized\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
         	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         { 
          FeatureExtraction_obj.TextureFeatures( texture,"D:\\SIGNATUREDATBASE\\Thinned_Skeletenized\\"+DirinDir[class_no]+"\\"+children[NoofFiles],DirinDir[class_no],rowno);	    
	      rowno++;
         }
      }
    current_class ++;
       
       
   }
  
  }
  
  
  
  
  
  
  
   
  //Find grid features for each class 
  int m1=12,n1=6;//grid box size
  int blkn=((120*60)/(m1*n1));
        
   String blkstrng=null;
   int i=0;
   for( i=0;i<blkn;i++)     
   	
   	{blkstrng="block "+i;
   	 grid_distance.addCell(new jxl.write.Label(i,0,blkstrng));
        
   	}
   	
   	
   	grid_distance.addCell(new jxl.write.Label(i,0,"person class"));
    
 blkstrng=null;
 i=0;
   for( i=0;i<blkn;i++)     
   	
   	{blkstrng="block "+i;
   	 grid_angle.addCell(new jxl.write.Label(i,0,blkstrng));
        
   	}
   	
   	
   	grid_angle.addCell(new jxl.write.Label(i,0,"person class"));
    

//FeatureExtraction_obj.Initialize();
 
 current_class=0;rowno=1;
for(int class_no=0;class_no<DirinDir.length;class_no++)
  { 	 
   f1 = new File("D:\\SIGNATUREDATBASE\\Thinned_Skeletenized\\"+DirinDir[class_no]); 
   if(f1.isDirectory()) 
   {  
      String children[] = f1.list();  
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File("D:\\SIGNATUREDATBASE\\Thinned_Skeletenized\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);
                	    
         	 	
        if(!children[NoofFiles].equals("Thumbs.db"))
         { 
          FeatureExtraction_obj.GridFeatures(grid_distance,grid_angle,m1,n1,"D:\\SIGNATUREDATBASE\\Thinned_Skeletenized\\"+DirinDir[class_no]+"\\"+children[NoofFiles],DirinDir[class_no],rowno);	    
	      rowno++;
         }
      }
    current_class ++;
       
       
   }
  
  }
    
  
  
  
  
  
  
  
  
  
  
    
  workbook.write();
  workbook.close();

  
  } 
}
















