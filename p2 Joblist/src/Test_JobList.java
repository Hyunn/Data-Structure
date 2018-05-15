// This does not need to be submitted


public class Test_JobList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JobList newjoblist = new JobList();
		Job newjob = new Job("New", 100, 60);
		Job otherjob = new Job ("Other", 200, 500);
		Job secondjob = new Job ("2", 20, 50);
		newjoblist.add(newjob);
		newjoblist.add(otherjob);
		System.out.println(newjoblist.get(0));
		System.out.println(newjoblist.get(1));
		System.out.println(newjoblist.contains(newjob));
		System.out.println(newjoblist.contains(otherjob));		
		newjoblist.add(0, secondjob);

		System.out.println(newjoblist.get(0));
		System.out.println(newjoblist.get(1));
		
		System.out.println(newjoblist.isEmpty());
		
		
	}

}
