public class DMStd extends NormalStd{
	
    public DMStd(String studentName ,int studentID, String major,int maxCredits,int majorNow,int mustCredit){
        super(studentName, studentID, major,maxCredits,majorNow,mustCredit);
    }
    
    public int getMaxCredits(){
    	return super.setMaxCredits(31);
    }


    public void getInfo() {
		int limitation = 0;
		if(super.getMajor() == "BA") {
			//BA 51; MIS 36
			limitation = 87;
		}else {
			//SOC 42; MIS 57
			limitation = 99;
		}
		
		if(super.getMustCredits() < limitation) {
			int x = limitation -super.getMustCredits();
			System.out.println("Sorry, "+super.getName()+", you still leave "+x+
					" credits before graduation, since you are a double major student.");
		}else {
			System.out.println("Congratulations, "+super.getName()+", your must credit is "+super.getMustCredits()+
					" you are able to graduate with "+super.getMajor()+" and MIS degree!");
		}
		
		


	}

   
}

