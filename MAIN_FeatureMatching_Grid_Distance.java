//package ij.gui;
import java.io.File;
import java.util.Arrays;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import java.io.File;
import java.util.Date;
import jxl.*;
import jxl.write.*;
import jxl.write.WritableWorkbook;
import java.io.File;
import java.util.Date;
import jxl.*;
import ij.io.*;
//import jama.*;

//code will work for 92x112  images ..for testing

//NOTE::::::::::::run as  java -Xms32m -Xmx128m MAIN
public class MAIN_FeatureMatching_Grid_Distance
{ public  static int nr,nc;
  public static BufferedImage image2,image3,image4;
  public  static int NoofClasses=0,NoofClasses_to_Test=0,NoofSamples[],totalnooffiles=0,totalnooffiles_to_Test=0,NoofSamples_to_Test[];
  //m--->IMAGE_WIDTH,n--->IMAGE_HEIGHT
  //c--->NoofClasses,ni---->NoofSamples[i]  i.e. no of samples in class i
  //N--->totalnooffiles
  public static  File f3,f,f1,f2,f4;

  public static    int nl=0;

  public static int no_of_features;
  public static double criteria=75.00;
  public static int constant_k=3;
  public static double CONSTANT_THRESHOLD_COEFFICIENT=1.0;


  static  WritableWorkbook workbook;
 static WritableSheet _1DMMC_alpha,_1DMMC_red,_1DMMC_green,_1DMMC_blue,_1DLDA_alpha,_1DLDA_red,_1DLDA_green,_1DLDA_blue;


 static  Workbook workbook_read;
 static Sheet _1DMMC_alpha_read,_1DMMC_red_read,_1DMMC_green_read,_1DMMC_blue_read,_1DLDA_alpha_read,_1DLDA_red_read,_1DLDA_green_read,_1DLDA_blue_read,Wmmc_C0,Wmmc_C1,Wmmc_C2,Wmmc_C3,Wmmc_R0,Wmmc_R1,Wmmc_R2,Wmmc_R3;
 static ij.io.DirectoryChooser oi;

 //public  static void main(String[] args)  throws Exception
 public  void mainf(int k_value)  throws Exception
  { constant_k=k_value;
   oi=new ij.io.DirectoryChooser("Select test database folder");
   System.out.println("original image dir: "+oi.getDirectory());

    initialize();
    preprocessing();
    FeatureExtraction();
    Matching();
    MatchingAccuracy();




  }
  public  static void initialize() throws IOException
  {System.out.println("In step INITIALIZE");

  f = new File(oi.getDirectory());
  String[] DirinDir = f.list();
  Arrays.sort(DirinDir);

  for(int class_no=0;class_no<DirinDir.length;class_no++)
  {
   f1 = new File(oi.getDirectory()+DirinDir[class_no]);
   if(f1.isDirectory())
   {NoofClasses_to_Test++;
   }

  }
 System.out.println("NoofClasses_to_Test="+NoofClasses_to_Test);

  NoofSamples_to_Test=new int[NoofClasses_to_Test];
  //for(int i=0;i<NoofClasses;i++)
  //System.out.println(NoofSamples[i]);


  for(int class_no=0,current_class=0;class_no<DirinDir.length;class_no++)
  {
   f1 = new File(oi.getDirectory()+DirinDir[class_no]);
   if(f1.isDirectory())
   {
      String children[] = f1.list();
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File(oi.getDirectory()+DirinDir[class_no]+"\\"+children[NoofFiles]);


        if(!children[NoofFiles].equals("Thumbs.db"))
         { totalnooffiles_to_Test++;

          NoofSamples_to_Test[current_class]++;
         }
      }
    current_class ++;

   }

  }

  //for(int i=0;i<NoofClasses;i++)
  //System.out.println(NoofSamples[i]);


   System.out.println("totalnooffiles to test :"+totalnooffiles_to_Test);






  f = new File("D:\\SIGNATUREDATBASE\\SIGNATUREDB");
  DirinDir = f.list();
  Arrays.sort(DirinDir);

  for(int class_no=0;class_no<DirinDir.length;class_no++)
  {
   f1 = new File("D:\\SIGNATUREDATBASE\\SIGNATUREDB\\"+DirinDir[class_no]);
   if(f1.isDirectory())
   {NoofClasses++;
   }

  }
 System.out.println("Noofclasses="+NoofClasses);

  NoofSamples=new int[NoofClasses];
  //for(int i=0;i<NoofClasses;i++)
  //System.out.println(NoofSamples[i]);


  for(int class_no=0,current_class=0;class_no<DirinDir.length;class_no++)
  {
   f1 = new File("D:\\SIGNATUREDATBASE\\SIGNATUREDB\\"+DirinDir[class_no]);
   if(f1.isDirectory())
   {
      String children[] = f1.list();
      Arrays.sort(children);
      for(int NoofFiles=0;NoofFiles<children.length;NoofFiles++)
      {
      	f2=new File("D:\\SIGNATUREDATBASE\\SIGNATUREDB\\"+DirinDir[class_no]+"\\"+children[NoofFiles]);


        if(!children[NoofFiles].equals("Thumbs.db"))
         { totalnooffiles++;

          NoofSamples[current_class]++;
         }
      }
    current_class ++;

   }

  }

  //for(int i=0;i<NoofClasses;i++)
  //System.out.println(NoofSamples[i]);


   System.out.println("totalnooffiles in original db :"+totalnooffiles);














  }


    @SuppressWarnings("static-access")
 public static void preprocessing() throws Exception
{preprocessing1 pp=new preprocessing1();

pp.mainp();

}

    @SuppressWarnings("static-access")
  public static void  FeatureExtraction() throws IOException,Exception
   {
   	FeatureExtraction1 pp=new FeatureExtraction1();
    pp.mainf();
   }







  public static void Matching() throws Exception
  {

/*
 *WritableSheet global = workbook.createSheet("Global Features",0);
  WritableSheet texture = workbook.createSheet("Texture Features",1);
  WritableSheet grid_angle = workbook.createSheet("Grid_angle Features",2);
  WritableSheet grid_distance = workbook.createSheet("Grid_distance Features",3);

 */

	  Workbook workbook_read0 = Workbook.getWorkbook(new File("D:\\SIGNATUREDATBASE\\FeatureExtracted_match.xls"));
      //Sheet _1DMMC_alpha0,_1DMMC_red0,_1DMMC_green0,_1DMMC_blue0,_1DLDA_alpha0,_1DLDA_red0,_1DLDA_green0,_1DLDA_blue0;
       //Sheet global0 = workbook_read0.getSheet(0);
      //Sheet texture0 = workbook_read0.getSheet(1);
      Sheet grid_angle0 = workbook_read0.getSheet(2);
      /*Sheet grid_distance0 = workbook_read0.getSheet(3);
      */


      Sheet sheet0=grid_angle0; //for global features



	  Workbook workbook_read1 = Workbook.getWorkbook(new File("D:\\SIGNATUREDATBASE\\FeatureExtracted.xls"));
      //Sheet global1 = workbook_read1.getSheet(0);
      //Sheet texture1 = workbook_read1.getSheet(1);
      Sheet grid_angle1 = workbook_read1.getSheet(2);
      /*Sheet grid_distance1 = workbook_read1.getSheet(3);
      **/


      Sheet sheet1=grid_angle1; //for global features




Integer in=new Integer(constant_k);
	  WritableWorkbook workbook2= Workbook.createWorkbook(new File("D:\\SIGNATUREDATBASE\\KNNmatch_Grid_Distance_k_"+in.toString()+".xls"));//for global features
	  WritableSheet knn;

	 knn = workbook2.createSheet("knn_Grid_Distance",0);//for global
	 no_of_features=100;//for global



    int class_no=0;
  	Cell a1;
    LabelCell lc;
    NumberCell ncell ;
	jxl.write.Label label;
	jxl.write.Number number0,number1,number2;
    double red0,green0,blue0,alpha0,rgb0;
    double red1,green1,blue1,alpha1,rgb1;
    double red2,green2,blue2,alpha2,rgb2;
    String str,str1;


	 label = new jxl.write.Label(0,0,"File No");
	knn .addCell(label);

     label = new jxl.write.Label(1,0,"target class");
	knn .addCell(label);

	label = new jxl.write.Label(2,0,"Matched class");
	knn .addCell(label);

	label = new jxl.write.Label(3,0,"Tie");
	knn .addCell(label);


	label = new jxl.write.Label(4,0,"MinimumEucledianDistanceToMatchedclass");
	knn .addCell(label);

	label = new jxl.write.Label(5,0,"Matching Score");
	knn .addCell(label);

	label = new jxl.write.Label(6,0,"genuine %");
	knn .addCell(label);

	label = new jxl.write.Label(7,0,"fake %");
	knn .addCell(label);

	label = new jxl.write.Label(8,0,"False Acceptance");
	knn .addCell(label);

	label = new jxl.write.Label(9,0,"False Rejection");
	knn .addCell(label);









	/*label = new jxl.write.Label(4,0,"MaximumEucledianDistanceToMatchedclass");
	knn .addCell(label);
*/



     int i=0,j=0;
		for(i=1;i<=totalnooffiles_to_Test;i++)
	    {number2 = new jxl.write.Number(0,i,i);
		knn.addCell(number2);



    	a1 = sheet0.getCell(no_of_features,i);
        lc = (LabelCell) a1;
        str = lc.getString();
        label = new jxl.write.Label(1,i,str);
	    knn.addCell(label);


		 }




double EucledianDistance=0.0, MinEucledianDistance=0.0;

 double Eucledian_Distances[]=new double[totalnooffiles];
 String CLASS_NAME[]=new String[totalnooffiles];
 int INDEX[]=new int[totalnooffiles];

 double Eucledian_Distances_after[]=new double[totalnooffiles];
 String CLASS_NAME_after[]=new String[totalnooffiles];
 int INDEX_after[]=new int[totalnooffiles];


String class_name=null;
double Mglobal_average[]=new double[no_of_features];
double Mglobal_standard_deviation[]=new double[no_of_features];
double Mglobal_TestPattern[]=new double[no_of_features];
double Mglobal_Pattern[]=new double[no_of_features];


double Saverage[][]=new double[NoofClasses][no_of_features];
double Sstandard_deviation[][]=new double[NoofClasses][no_of_features];
double Threshold[][]=new double[NoofClasses][no_of_features];



//..find  threshold for each class

int rowno=1,classno=0;
//while(classno<NoofClasses && rowno<=totalnooffiles)


for(classno=0;classno<NoofClasses;classno++)
{for(i=0;i<NoofSamples[classno];i++)
  {
   Mglobal_Pattern=new double[no_of_features];

   //get  next  temp into Mglobal_Pattern
   for(j=0;j<no_of_features;j++)
    {
    //System.out.println("i="+j+"rowno="+rowno);
    a1 = sheet1.getCell(j,rowno);
    ncell = (NumberCell) a1;
    Mglobal_Pattern[j]= ncell.getValue();

    }
    for(j=0;j<no_of_features;j++)
    {Saverage[classno][j] += Mglobal_Pattern[j];
    }

   rowno++;
  }
  for(j=0;j<no_of_features;j++)
    {Saverage[classno][j] /= NoofSamples[classno];
    //System.out.println("i="+i+"classno"+classno+" Saverage[][i]= "+Saverage[classno][i]);
    }


}


rowno=1;classno=0;
for(classno=0;classno<NoofClasses;classno++)
{for(i=0;i<NoofSamples[classno];i++)
  {
   Mglobal_Pattern=new double[no_of_features];

   //get  next  temp into Mglobal_Pattern
   for(j=0;j<no_of_features;j++)
    {
    a1 = sheet1.getCell(j,rowno);
    ncell = (NumberCell) a1;
    Mglobal_Pattern[j]= ncell.getValue();

    }
    for(j=0;j<no_of_features;j++)
    {Sstandard_deviation[classno][j] += (Mglobal_Pattern[j]-Saverage[classno][j])*(Mglobal_Pattern[j]-Saverage[classno][j]);
    }

   rowno++;
  }
  for(j=0;j<no_of_features;j++)
    {Sstandard_deviation[classno][j] /= NoofSamples[classno];
     Sstandard_deviation[classno][j]  =Math.sqrt(Sstandard_deviation[classno][j]);
     Threshold[classno][j]            =Saverage[classno][j]-CONSTANT_THRESHOLD_COEFFICIENT*Sstandard_deviation[classno][j];
     System.out.println("\n\nj="+j+"classno"+classno+" Saverage= "+Saverage[classno][j]);
     System.out.println("j="+j+"classno"+classno+" Sstandard_deviation= "+Sstandard_deviation[classno][j]);
     System.out.println("j="+j+"classno"+classno+" Threshold= "+Threshold[classno][j]);
    }


}







rowno=1;
int current_template=1;

	for(current_template=1;current_template<=totalnooffiles_to_Test;current_template++)
	    {//compare current template with all other templates

	     Mglobal_TestPattern=new double[no_of_features];
         //M0=new Matrix(nc*nr,1);

	       //get cuurent template into  Mglobal_testPattern[4]
	       for(i=0;i<no_of_features;i++)
	       {
	    	a1 = sheet0.getCell(i,current_template);
            ncell = (NumberCell) a1;
            Mglobal_TestPattern[i] = ncell.getValue();


	    	 }


		   MinEucledianDistance=0.0;
		   //SQUARED_EuclDistances=new double[totalnooffiles];

	       for(rowno=1;rowno<=totalnooffiles;rowno++)
	       {

		   Mglobal_Pattern=new double[no_of_features];
		   //M1=new Matrix(nc*nr,1);
	       //get  template to be compared with current temp into Mglobal_Pattern
	       for(i=0;i<no_of_features;i++)
	       {
	       	//System.out.println("i="+i+"  rowno="+rowno);
	       //System.out.println("Tf="+totalnooffiles);
	       //System.out.println("v="+Mglobal_Pattern[i]);
	        a1 = sheet1.getCell(i,rowno);
            ncell = (NumberCell) a1;
            Mglobal_Pattern[i]= ncell.getValue();

	    	 }

			 //find squared eucledian distance between Mglobal_TestPattern & Mglobal_Pattern  vectors

			   EucledianDistance=0;
			   for(i=0;i<no_of_features;i++)
			   {EucledianDistance +=(Mglobal_Pattern[i]-Mglobal_TestPattern[i])*(Mglobal_Pattern[i]-Mglobal_TestPattern[i]);
			   }


			   //
			   Eucledian_Distances[rowno-1]=EucledianDistance;
			   a1 = sheet1.getCell(no_of_features,rowno);
               lc = (LabelCell) a1;
               CLASS_NAME[rowno-1]=lc.getString();

               /*
			  if(rowno==1)  //first time
			   { MinEucledianDistance= EucledianDistance;
			    a1 = global1.getCell(no_of_features,rowno);
                lc = (LabelCell) a1;
                class_name= lc.getString();
				System.out.println("curr temp="+current_template+"rowno ="+rowno);
				System.out.println("MinEucledianDistance="+MinEucledianDistance+"EucledianDistance ="+EucledianDistance);

			   }
			  else//MAX0 =(MAX0>0)?(MAX0):(0);
			   {
				//MinEucledianDistance= (MinEucledianDistance > EucledianDistance) ? (EucledianDistance):(MinEucledianDistance);
                if(MinEucledianDistance > EucledianDistance)
				   {MinEucledianDistance= EucledianDistance;
				    a1 = global1.getCell(no_of_features,rowno);
                    lc = (LabelCell) a1;
                    class_name= lc.getString();
				   }


				System.out.println("curr temp="+current_template+"rowno ="+rowno+"Class name="+class_name);
				System.out.println("MinEucledianDistance="+MinEucledianDistance+"EucledianDistance ="+EucledianDistance);
				}
				*/

	       }


	       //sort Eucledian_Distances ascending

    	double  temp ;
    	String tstring=null;
    	INDEX=new int[totalnooffiles];

    	//x[] =i;
    	System.out.println("\nBefore Sort\n");
        for ( i = 0; i < totalnooffiles; i++)
        {  INDEX[i]=i;
         //System.out.println("  "+Eucledian_Distances[i]+"  "+CLASS_NAME[i]+"   "+INDEX[i]);
        }
       for ( i=0; i<Eucledian_Distances.length-1; i++) {
        int maxIndex = i;      // Index of largest remaining value.
        for (j=i+1; j<Eucledian_Distances.length; j++) {
            if (Eucledian_Distances[maxIndex] > Eucledian_Distances[j]) {
                maxIndex = j;  // Remember index of new minimum
            }
        }
       if (maxIndex != i)
        {
            //...  Exchange current element with smallest remaining.
            temp = Eucledian_Distances[i];
            Eucledian_Distances[i] = Eucledian_Distances[maxIndex];
            Eucledian_Distances[maxIndex] = temp;

            tstring=CLASS_NAME[i];
            CLASS_NAME[i] = CLASS_NAME[maxIndex];
            CLASS_NAME[maxIndex] = tstring;


            INDEX[i]=maxIndex;
            INDEX[maxIndex]=i;

        }
      }




     System.out.println("\nAfter Sort\n");
        for ( i = 0; i < totalnooffiles; i++)
        {

        //System.out.println("  "+Eucledian_Distances[i]+"  "+CLASS_NAME[i]+"   "+INDEX[i]);
        }



	    //take first k no of Eucledian distances & find max score for a ip pattern

	    //find no of distinct classes in  CLASS_NAME & its count

	    ArrayList al = new ArrayList(1);
	    al.add(0,CLASS_NAME[0]);
	    boolean flag=false;
	    int noofdistclasses=1;

	     for ( i = 1; i < constant_k; i++)
	    {//CHECK IF CLASS NAME  is distinct
	       if(!al.contains(CLASS_NAME[i]))
	       {noofdistclasses++;
	       al.add(CLASS_NAME[i]);
	       }


	    }

	    String TEMP_CLASSNAME [] = (String []) al.toArray (new String [al.size ()]);

        System.out.println("Contents of str: ");
        for( i=0;i<al.size ();i++)
          {System.out.print("\t"+TEMP_CLASSNAME[i]);
          //System.out.print("\t"+al.size ());
          }
          System.out.print("\nnoofdistclasses="+noofdistclasses+"\n");


	    //find score
	    int SCORE[]=new int[al.size ()];

	    for ( j=0; j <al.size () ; j++)
	    {

	     for ( i = 0; i < constant_k; i++)
	     { if(TEMP_CLASSNAME [j].equals(CLASS_NAME[i]))
	        {SCORE[j]+=1;
	        }


	     }
	   }



	  int maxscoreindex=0;
	      for ( j=1; j <al.size () ; j++)
	      {if(SCORE[maxscoreindex]<SCORE[j])
	       {maxscoreindex=j;
	       }
	        //System.out.println(" "+TEMP_CLASSNAME [j]+"  "+SCORE[j]);
	       }

	  boolean MATCH_SCORE_CLASS_INDEX[]=new boolean[al.size ()];
	  double matc_mined[]=new double[al.size ()];
	  boolean tie=false;
	  for ( j=0; j <al.size () ; j++)

	     {
	     	if(j !=maxscoreindex && SCORE[j]==SCORE[maxscoreindex])
	     	{tie=true;
	     	MATCH_SCORE_CLASS_INDEX[j]=true;
	        }
	        else
	        MATCH_SCORE_CLASS_INDEX[j]=false;

	     }

	     if(tie==true)
	     {//tie find sum all squared eucl distances
	     for ( j=0; j <al.size () ; j++)
	        {if(MATCH_SCORE_CLASS_INDEX[j]==true)
	           {//find templates cooresponding to class j
	           for ( i = 0; i < constant_k; i++)
	             {if(TEMP_CLASSNAME [j]==CLASS_NAME[i])
	                {matc_mined[j]+=Eucledian_Distances[i];
	                }
	             }
	           }
	         }

	       for ( j=0; j <al.size () ; j++)
	        {System.out.println("matcnnnn="+matc_mined[j]);}


	         //find min of matc_mined[]
	         int minindex=0;
	         for ( j=0; j <al.size() ; j++)
	           {
	           	if(matc_mined[minindex]>matc_mined[j])
	            {minindex=j;
	             }
	             System.out.println("j="+j+"tie mined="+matc_mined[j]);
	             System.out.println("tie "+" "+TEMP_CLASSNAME [j]+"  "+SCORE[j]);

	           }

	          System.out.println("Final class "+" "+TEMP_CLASSNAME [minindex]+"  "+SCORE[minindex]);
	          System.out.println("minimuned="+matc_mined[minindex]);

	    	//store class_name , 	MinEucledianDistance to  EucledianDist_1DMMC.xls for current_template
	       label = new jxl.write.Label(2, current_template,TEMP_CLASSNAME [minindex]);
	       knn.addCell(label);


	       label = new jxl.write.Label(3, current_template,"Yes");
	       knn.addCell(label);

	       number2 = new jxl.write.Number(4,current_template,Eucledian_Distances[0]);
		   knn.addCell(number2);

		   number2 = new jxl.write.Number(5,current_template,SCORE[maxscoreindex]);
		   knn.addCell(number2);

		   //TEMP_CLASSNAME [minindex]
		   int kk=0,indextoclass=0;
		    for ( i=1; i <=totalnooffiles ; i++)
	           {  a1 = sheet1.getCell(no_of_features,i);
                  lc = (LabelCell) a1;
                  class_name= lc.getString();
	           	if(TEMP_CLASSNAME [minindex].equals(class_name))
	           	{kk=i-1;
	           	break;
	           	}
	           }

	           indextoclass=0;
	           int  findextoclass=0;
	         for(j=0;j<totalnooffiles;)
               {
               	if(kk>=j && kk<j+NoofSamples[indextoclass])
               	{findextoclass=indextoclass;
               	break;
               	}
               	else
               	{j=j+NoofSamples[indextoclass];
               	 indextoclass++;
               	}
               }


               System.out.println("findextoclass="+findextoclass+"   classname="+TEMP_CLASSNAME [minindex]);

               //find % of geniunness
               //approach 1 :
               // % of geniunness=(no of featrues of test pattern that are above or equal to corresponding feature's threshold of the matched class ) /(total no of features)


            int gencount=0;
             for(j=0;j<no_of_features;j++)
              {
              if(Mglobal_TestPattern[j]>=Threshold[findextoclass][j])
              gencount++;

              }

           System.out.println("% of genuineness= "+(gencount*100.0/no_of_features));

           number2 = new jxl.write.Number(6,current_template,(gencount*100.0/no_of_features));
		   knn.addCell(number2);

		   number2 = new jxl.write.Number(7,current_template,100.00-(gencount*100.0/no_of_features));
		   knn.addCell(number2);

		   //false acceptance
		   a1 = knn.getCell(1,current_template);
           lc = (LabelCell) a1;
           str = lc.getString();

           a1 = knn.getCell(2,current_template);
           lc = (LabelCell) a1;
           str1 = lc.getString();


		   if(criteria>(gencount*100.0/no_of_features) &&str.equals(str1) )
		   {label = new jxl.write.Label(8,current_template,"Yes");
	        knn.addCell(label);
		   }
		   else
		   {label = new jxl.write.Label(8,current_template,"No");
	        knn.addCell(label);
		   }


		    //false rejection


		   if(criteria<=(gencount*100.0/no_of_features) &&!str.equals(str1) )
		   {label = new jxl.write.Label(9,current_template,"Yes");
	        knn.addCell(label);
		   }
		   else
		   {label = new jxl.write.Label(9,current_template,"No");
	        knn.addCell(label);
		   }






	     }



	     else
	     {//no tie
	      maxscoreindex=0;
	      System.out.println("no tie "+TEMP_CLASSNAME [0]+"  "+SCORE[0]);
	      for ( j=1; j <al.size () ; j++)
	      {if(SCORE[maxscoreindex]<SCORE[j])
	       {maxscoreindex=j;
	       }
	        System.out.println("no tie "+TEMP_CLASSNAME [j]+"  "+SCORE[j]);
	       }

	       System.out.println("no tie "+TEMP_CLASSNAME [maxscoreindex]+"  "+SCORE[maxscoreindex]);



	    	//store class_name , 	MinEucledianDistance to  EucledianDist_1DMMC.xls for current_template
	       label = new jxl.write.Label(2, current_template,TEMP_CLASSNAME [maxscoreindex]);
	       knn.addCell(label);


	       label = new jxl.write.Label(3, current_template,"No");
	       knn.addCell(label);

	       MinEucledianDistance=0;
	       for ( j=0; j <al.size () ; j++)
	       {if(MinEucledianDistance>Eucledian_Distances[j])
	         MinEucledianDistance=Eucledian_Distances[j];
	         //System.out.println("   "+Eucledian_Distances[j]);
	       }

		   number2 = new jxl.write.Number(4,current_template,Eucledian_Distances[0]);
		   knn.addCell(number2);

		   number2 = new jxl.write.Number(5,current_template,SCORE[maxscoreindex]);
		   knn.addCell(number2);


		   int kk=0,indextoclass=0;
		    for ( i=1; i <=totalnooffiles ; i++)
	           {  a1 = sheet1.getCell(no_of_features,i);
                  lc = (LabelCell) a1;
                  class_name= lc.getString();
	           	if(TEMP_CLASSNAME [maxscoreindex].equals(class_name))
	           	{kk=i-1;
	           	break;
	           	}
	           }

	           indextoclass=0;
	           int  findextoclass=0;
	         for(j=0;j<totalnooffiles;)
               {
               	if(kk>=j && kk<j+NoofSamples[indextoclass])
               	{findextoclass=indextoclass;
               	break;
               	}
               	else
               	{j=j+NoofSamples[indextoclass];
               	 indextoclass++;
               	}
               }


               System.out.println("findextoclass="+findextoclass+"   classname="+TEMP_CLASSNAME [maxscoreindex]);

               //find % of geniunness
               //approach 1 :
               // % of geniunness=(no of featrues of test pattern that are above or equal to corresponding feature's threshold of the matched class ) /(total no of features)


            int gencount=0;
             for(j=0;j<no_of_features;j++)
              {
              if(Mglobal_TestPattern[j]>=Threshold[findextoclass][j])
              gencount++;

              }

           System.out.println("% of genuineness= "+(gencount*100.0/no_of_features));

           number2 = new jxl.write.Number(6,current_template,(gencount*100.0/no_of_features));
		   knn.addCell(number2);

		   number2 = new jxl.write.Number(7,current_template,100.00-(gencount*100.0/no_of_features));
		   knn.addCell(number2);



		    //false acceptance
		   a1 = knn.getCell(1,current_template);
           lc = (LabelCell) a1;
           str = lc.getString();

           a1 = knn.getCell(2,current_template);
           lc = (LabelCell) a1;
           str1 = lc.getString();


		   if(criteria>(gencount*100.0/no_of_features) &&str.equals(str1) )
		   {label = new jxl.write.Label(8,current_template,"Yes");
	        knn.addCell(label);
		   }
		   else
		   {label = new jxl.write.Label(8,current_template,"No");
	        knn.addCell(label);
		   }


		    //false rejection


		   if(criteria<=(gencount*100.0/no_of_features) &&!str.equals(str1) )
		   {label = new jxl.write.Label(9,current_template,"Yes");
	        knn.addCell(label);
		   }
		   else
		   {label = new jxl.write.Label(9,current_template,"No");
	        knn.addCell(label);
		   }





	     }





		 }



  	workbook2.write();
	workbook2.close();
  	workbook_read0.close();
	workbook_read1.close();
}


	public  static void MatchingAccuracy() throws Exception
 {

Integer in=new Integer(constant_k);
	  Workbook workbook_read0 = Workbook.getWorkbook(new File("D:\\SIGNATUREDATBASE\\KNNmatch_Grid_Distance_k_"+in.toString()+".xls"));
      Sheet _1DMMC_alpha0,_1DMMC_red0,_1DMMC_green0,_1DMMC_blue0,_1DLDA_alpha0,_1DLDA_red0,_1DLDA_green0,_1DLDA_blue0;

	_1DMMC_alpha0 = workbook_read0.getSheet(0);
	/*_1DMMC_red0 = workbook_read0.getSheet(1);
	_1DMMC_green0 = workbook_read0.getSheet(2);
	_1DMMC_blue0 = workbook_read0.getSheet(3);
*/



    long matchcount=0,mismatchcount=0,matchcount_False_Acceptance=0,matchcount_False_Rejection=0;
    Cell a1;
    LabelCell lc;
    NumberCell ncell ;
	jxl.write.Label label;
	jxl.write.Number number0,number1,number2;
    double red0,green0,blue0,alpha0,rgb0;
    double red1,green1,blue1,alpha1,rgb1;
    double red2,green2,blue2,alpha2,rgb2;
    String str1=null,str2=null;

    for(int i=1;i<=totalnooffiles_to_Test;i++)
    {
	a1 = _1DMMC_alpha0.getCell(1,i);
    lc = (LabelCell) a1;
    str1= lc.getString();
    a1 = _1DMMC_alpha0.getCell(2,i);
    lc = (LabelCell) a1;
    str2= lc.getString();
    if(str1.equals(str2))
    {
    	matchcount++;

    }

    else
    {
    	mismatchcount++;

    }

    a1 = _1DMMC_alpha0.getCell(8,i);
    lc = (LabelCell) a1;
    str1= lc.getString();
    a1 = _1DMMC_alpha0.getCell(9,i);
    lc = (LabelCell) a1;
    str2= lc.getString();

    if(str1.equals("Yes"))
    {
    	matchcount_False_Acceptance++;

    }
    if(str2.equals("Yes"))
    {
    	matchcount_False_Rejection++;

    }


    }



	workbook_read0.close();
	System.out.println("\n\t Value of k (nearest number of neisghborhoods)   = "+constant_k);
	System.out.println("\n\t TOTAL NO OF FILES tested  = "+totalnooffiles_to_Test);
	System.out.println("\n\t MATCHING count is = "+matchcount);
	System.out.println("\n\t MISMATCHING count is  = "+mismatchcount);
	System.out.println("\n\t MATCHING ACCURAY of Grid Distance Feature based method is = "+((matchcount*100.0)/(totalnooffiles_to_Test)*1.0));
        System.out.println("\n\t False Acceptance %="+(matchcount_False_Acceptance*100.0)/(totalnooffiles_to_Test));
        System.out.println("\n\t False Rejection  %="+(matchcount_False_Rejection*100.0)/(totalnooffiles_to_Test));



 }







}












