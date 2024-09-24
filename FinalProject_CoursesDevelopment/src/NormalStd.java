import java.util.ArrayList;

public class NormalStd{
    private int studentID;
    private String major;
    private String studentName;
    private ArrayList<Course>enrolledCourses=new ArrayList<Course>();

    protected int currentCredits;
    private int maxCredits;
    protected int mustCredit;
    private int majorNow;

    
    public NormalStd(String studentName ,int studentID, String major,int maxCredits,int majorNow,int mustCredit){
        this.studentID=studentID;
        this.studentName=studentName;
        this.major=major;
        this.maxCredits=maxCredits;  /*自行輸入 */ /*雙輔 1 BA 2 SOC 3*/
        this.majorNow=majorNow;
        this.mustCredit=mustCredit;
        currentCredits=0;
    }


    public int getCurrentCredit(){
        return currentCredits;
    }

    
    public int getID(){
        return studentID;
    }
    public String getName(){
        return studentName;
    }
    public int getCurrentCredits(){
        return currentCredits;
    }
    public int getMaxcredits(){
        return maxCredits;
    }
    public int getMajorNow(){
        return majorNow;
    }
    public String getMajor(){
        return major;
    }
    public int  getMustCredits(){
        return mustCredit;
    }
     
    public Course getCourse(String courseName){
        int num=0;
        Course course= null;
        for(int i=0;i<enrolledCourses.size();i++){
            if(courseName.equals(enrolledCourses.get(i).getCourseName())){
            	course= enrolledCourses.get(i);
                num=num+1;
            }
        } 
        if(num!=0){
            return course;
        }
        else{
            return null;
        }
    }
    
    public int setMaxCredits(int maxCredits) {
    	this.maxCredits = maxCredits;
    	return this.maxCredits;
    }

    public void setEnrolledCourses(Course c) {
    	enrolledCourses.add(c);
    }
    

    public void drop(String courseName){
        NormalStd stu1 = new NormalStd(studentName , studentID, major , maxCredits, majorNow,mustCredit);
        ArrayList <Course>a=new ArrayList<Course>();

        for(int i=0;i<enrolledCourses.size();i++){
            a.add(enrolledCourses.get(i));
        }

        stu1.enrolledCourses=a;

        if(stu1.getCourse(courseName)!=null){
            enrolledCourses.remove(enrolledCourses.indexOf(stu1.getCourse(courseName))); 
            currentCredits=currentCredits-stu1.getCourse(courseName).getCredits();
            drop(stu1.getCourse(courseName));
    }

        if(stu1.getCourse(courseName)==null){
            System.out.println("Course "+courseName+" not found in student "+studentID);
        }

      
    
}
    public void drop(Course course){
       drop(course.getCourseName());
    }
    
    
    

    public void getInfo() {
		int limitation = 0;
		if(major == "BA") {
			//BA 51
			limitation = 51;
		}else {
			//SOC 42
			limitation = 42;
		}
		
		if(mustCredit<limitation) {
            int x=limitation-mustCredit;
			System.out.println("Sorry, "+studentName+", you still leave "+x+" credits before graduation");
            
		}else {
			System.out.println("Congratulations, "+studentName+", your must credit is "+mustCredit+", you are able to graduate with " +major+ " degree!");
		}
		
	}
    
    public void getCourseInfo() {
	 	System.out.printf("%s 已經選上的課: ",studentName);
	 	System.out.println();
	 	
	 	if(enrolledCourses.size()== 0) {
	 		System.out.println("很抱歉，你沒有選上課");
	 	}else {
	 		for(Course course:enrolledCourses){
        	System.out.println(course.getCourseName());
	 		}

        }
	 	System.out.println();
}


}

