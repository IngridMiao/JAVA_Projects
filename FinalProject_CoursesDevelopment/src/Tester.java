
public class Tester {
    public static void main(String[]args){
    	//測試人員
	   	
        StudentFactory normalStudentFactory = new NormalStudentFactory();
        StudentFactory dmStudentFactory = new DMStudentFactory();
        StudentFactory adStudentFactory = new ADStudentFactory();
        
        //BA NormalStd /Can’t Graduate
        NormalStd s1 = normalStudentFactory.createStudent("Jason", 109305001, "BA", 31, 1, 86);
          
        Course mis1 = new Course("程式設計一",2,"MIS");
        mis1.enroll(s1);

        //BA雙MIS選課        
        NormalStd s2 = dmStudentFactory.createStudent("Max", 110305003, "BA", 31, 1, 50);
	
        //BA輔MIS選課
        mis1.enroll(s2);

        //BA Auxiliary Department MIS /Can’t Graduate
        NormalStd s3 = adStudentFactory.createStudent("Mary", 112305002, "BA", 25, 3, 40);
        mis1.enroll(s3);

        Course mis2 = new Course("計算機概論",2,"MIS");
        Course mis3 = new Course("資料結構",3,"MIS");

	
		//BA普通生選課
		mis1.enroll(s3);
		mis2.enroll(s3);
	    mis3.enroll(s3);



		//SOC Double Major MIS /Can’t Graduate
        NormalStd s4 = dmStudentFactory.createStudent("Molly", 110204001, "SOC", 31, 1,40);
		//SOC雙MIS選課
		mis1.enroll(s4);
		mis2.enroll(s4);
		mis3.enroll(s4);
	


		//SOC  Auxiliary Department MIS/Graduate
		NormalStd s5 = adStudentFactory.createStudent("David", 109204002,"SOC", 31, 1,70);
		//SOC輔MIS選課
		
		mis1.enroll(s5);
		mis2.enroll(s5);	


	    //SOC NormalStd /Graduate
		NormalStd s6 = normalStudentFactory.createStudent("Peter", 109204003, "SOC", 25, 3,41);
	
	
		//SOC普通生選課
		mis3.enroll(s6);
		
		//課程排序身分別選課
		
	    mis1.compare(mis1);
	    mis2.compare(mis2);
	    mis3.compare(mis3);
	

	    s1.getInfo();
	    s2.getInfo();
	    s3.getInfo();
	    s4.getInfo();
	    s5.getInfo();
	    s6.getInfo();
	   
	    //選上的課
	    s1.getCourseInfo();
	    s2.getCourseInfo();
	    s3.getCourseInfo();
	    s4.getCourseInfo();
	    s5.getCourseInfo();
	    s6.getCourseInfo();
    }
    


}

