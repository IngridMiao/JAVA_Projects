import java.util.ArrayList;
import java.util.Random;

public class Course {
    private String courseName;
    private int credits;
    private int capacity;
    private int enrolled;
    private ArrayList <NormalStd> chooseStu = new ArrayList<NormalStd>(); /*用來判斷排序 */
    private ArrayList <NormalStd> chooseStu1 = new ArrayList<NormalStd>();
    private ArrayList <NormalStd> chooseStu2= new ArrayList<NormalStd>();
    private ArrayList <NormalStd> chooseStu3= new ArrayList<NormalStd>();
    
    private ArrayList <NormalStd> enrolls = new ArrayList<NormalStd>();

    
    public Course(String courseName,int credits,String department){
        this.courseName = courseName;
        this.credits=credits;
        capacity=3;
        enrolled=0;
    }

     // Creating ArrayList for SOC department
     





    public int getCredits(){
        return credits;
    }
    
    public int getCapacity(){
        return capacity;
    }

    public boolean isFull(){
        if(enrolled==capacity){
            return true;
        }
        else{
            return false;
        }
    }

    public void enroll(NormalStd stu1){
        if(stu1.getCurrentCredit()+credits>stu1.getMaxcredits()){
            System.out.println("You can't choose more credit");
        }else {
        	chooseStu.add(stu1);
        }
    }
    
    public void compare(Course c){
        /*身分別判斷 */
        /*先將學生加入課程人數表中 再排序 */
        Random random = new Random();

        for(NormalStd stu : chooseStu){
            if(stu.getMajorNow()==1){
                chooseStu1.add(stu);
            }
            if(stu.getMajorNow()==2){
                chooseStu2.add(stu);
                
            }
            if(stu.getMajorNow()==3){
                chooseStu3.add(stu);            
            }
       }
       if(chooseStu1.size()>capacity){
        for (int i = 0; i < capacity; i++) {
        	//確定選上課程的人員名單
            int index = random.nextInt(chooseStu1.size());
            enrolls.add(chooseStu1.get(index));
            chooseStu1.remove(index);
            enrolled++;
        }
        
        for(int j=0;j<enrolls.size();j++){
        	//為課程學員更新資料
            enrolls.get(j).currentCredits+=credits;
            enrolls.get(j).mustCredit+=credits;
            enrolls.get(j).setEnrolledCourses(c);

        }
       
        
       }
       
       else if(chooseStu1.size()+chooseStu2.size()+chooseStu3.size()<=3) {
    
    	   
    	   enrolls=chooseStu;
    	   for(int j=0;j<enrolls.size();j++) {
    		   enrolls.get(j).currentCredits+=credits;
    		   enrolls.get(j).mustCredit+=credits;
    		   enrolls.get(j).setEnrolledCourses(c);
    		   enrolled++;
    	   }
       }
       
       else if(chooseStu1.size()==capacity){
            enrolls=chooseStu1;
            enrolled = capacity;
            for(int j=0;j<enrolls.size();j++){
                enrolls.get(j).currentCredits+=credits;
                enrolls.get(j).mustCredit+=credits;
                enrolls.get(j).setEnrolledCourses(c);
            }
       }
       else if(capacity>chooseStu1.size()){
        for(NormalStd stu:chooseStu1){
            enrolls.add(stu);
            enrolled++;
        }
        
            if(chooseStu2.size()>=capacity-chooseStu1.size()){
                for (int i = 0; i < capacity-chooseStu1.size(); i++) {
                    int index1 = random.nextInt(chooseStu2.size());
                    enrolls.add(chooseStu2.get(index1));
                    chooseStu2.remove(index1);
                    enrolled++;
                }

                for(int j=0;j<enrolls.size();j++){
                    enrolls.get(j).currentCredits+=credits;
                    enrolls.get(j).mustCredit+=credits;
                    enrolls.get(j).setEnrolledCourses(c);

                }
             
            }

            else if(chooseStu3.size()>capacity-chooseStu1.size()-chooseStu2.size()){
                for (int i = 0; i < capacity-chooseStu1.size()-chooseStu2.size(); i++) {
                    int index2 = random.nextInt(chooseStu3.size());
                    enrolls.add(chooseStu3.get(index2));
                    chooseStu3.remove(index2);
                    enrolled++;
                }
                
                for(int j=0;j<enrolls.size();j++){
                    enrolls.get(j).currentCredits+=credits;
                    enrolls.get(j).mustCredit+=credits;
                    enrolls.get(j).setEnrolledCourses(c);

                }
            }

       }


    }
    public void drop(NormalStd stu1){
        enrolls.remove(stu1);
        enrolled=enrolled-1;
        /* */
    }
    public String getCourseName(){
        return courseName;
    }
    public int getEnrolled(){
        return enrolled;
    }
    public String getInfo(){
        return (courseName+" "+credits+" "+enrolled+"/"+capacity);
    }
    
    
}

