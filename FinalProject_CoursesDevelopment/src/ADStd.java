public class ADStd extends NormalStd{
    public ADStd(String studentName ,int studentID, String major,int maxCredits,int majorNow,int mustCredit){
        super(studentName, studentID, major,maxCredits,majorNow,mustCredit);
    }
    
    public int getMaxCredits(){
    	return super.setMaxCredits(31);
    }
    
    public void getInfo() {
		int limitation = 0;
		
		if(super.getMajor() == "BA") {
			//BA
			limitation = 69;
		}else {
			//SOC
			limitation = 72;
		}
		
		if(super.getMustCredits() < limitation) {
			int x= limitation - super.getMustCredits();
			System.out.println("Sorry, "+super.getName()+", you still leave "+x+
					" credits before graduation, since you are an auxiliary department student.");
		}else {
			System.out.println("Congratulations, "+super.getName()+", your must credit is "+super.getMustCredits()+
					", you are able to graduate with "+super.getMajor()+" degree, and MIS as auxiliary degree!");
		}
		
		


	}

}

