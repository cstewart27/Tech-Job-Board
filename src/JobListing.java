import java.sql.*;

public class JobListing {
    private static int currentJobListingID = 1;
    public int jobListingID;
    public int companyID;
    public String title;
    public String description;
    public String location;
    public int salary;
    public String category;
    public String Urgency;
    public String [] skills_required = new String[8];

    public static JobListing RetrieveData(int jobID){
        JobListing jobListing = null;

        try{
            Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
            Statement stmt = conn.createStatement();
            String sql = "select * from Joblisting where JobListingID=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, jobID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                jobListing = new JobListing();
                jobListing.jobListingID = resultSet.getInt("JobListingID");
                jobListing.companyID = resultSet.getInt("CompanyID");
                jobListing.title = resultSet.getString("Title");
                jobListing.description = resultSet.getString("Description");
                jobListing.location = resultSet.getString("Location");
                jobListing.salary = resultSet.getInt("Salary");
                jobListing.category = resultSet.getString("Category");
                jobListing.Urgency = resultSet.getString("Urgency");
                String [] tempArray= resultSet.getString("Skills_Required").split(","); //split the skills into an array

                for(int i = 0; i < tempArray.length; i++){
                    jobListing.skills_required[i] = tempArray[i];
                }

                for(int i = 0; i< jobListing.skills_required.length; i++){
                    if(jobListing.skills_required[i] == null){
                        jobListing.skills_required[i] = "Empty";
                    }
                }
            }

            resultSet.close();
            stmt.close();
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jobListing;
    }

    //getter for currentJobListingID
    public static int getCurrentJobListingID() {
        return currentJobListingID;
    }
    //setter for currentJobListingID
    public static int setCurrentJobListingID(int jobListingID) {
        return JobListing.currentJobListingID = jobListingID;
    }
}
