import java.sql.*;

public class RetrieveData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/JobListingDatabase";
        String user = "root";
        String password = "1723";

        try {
            DatabaseConnectionManager.connect(url, user, password);

            Connection con = DatabaseConnectionManager.getConnection();

            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM JobListing Where JobListingID = 1"; //select all from the JobListing table
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //process each row of data here
                int jobListingID = rs.getInt("jobListingID");
                int companyID = rs.getInt("companyID");
                String jobTitle = rs.getString("Title");
                String jobDescription = rs.getString("Description");
                String jobLocation = rs.getString("Location");
                String jobSalary = rs.getString("Salary");
                String jobCategory = rs.getString("Category");
                String Urgency = rs.getString("Urgency");
                String [] skills = rs.getString("Skills_Required").split(","); //split the skills into an array

                System.out.println("Job Listing ID: " + jobListingID);
                System.out.println("Company ID: " + companyID);
                System.out.println("Job Title: " + jobTitle);
                System.out.println("Job Description: " + jobDescription);
                System.out.println("Job Location: " + jobLocation);
                System.out.println("Job Salary: " + jobSalary);
                System.out.println("Job Category: " + jobCategory);
                System.out.println("Job Urgency: " + Urgency);
                for(int i = 0; i < skills.length; i++){
                    System.out.println("Job Skills " + (i+1) + ": " + skills[i]);
                }


            }

            rs.close();
            stmt.close();
            DatabaseConnectionManager.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
