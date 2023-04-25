import java.sql.*;

public class testingPassingUserData {

    //getting current user's ID from the getter in User class.
    int currentUserID = User.getCurrentUserID();
    User user = getAuthenticatedUser(currentUserID);



    public User getAuthenticatedUser(int userID){
        User user = null;

        final String url = "jdbc:mysql://localhost:3306/JobListingDatabase";
        final String usernameToDatabase = "root";
        final String passwordToDatabase = "1723";

        try{
            Connection conn = DriverManager.getConnection(url, usernameToDatabase, passwordToDatabase);

            Statement stmt = conn.createStatement();
            String sql = "select * from Candidates where CandidateID=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = new User();
                user.CandidateID = resultSet.getInt("CandidateID");
                user.FirstName = resultSet.getString("FirstName");
                user.LastName = resultSet.getString("LastName");
                user.Email = resultSet.getString("Email");
                user.Password = resultSet.getString("Password");
                user.Phone = resultSet.getString("Phone");
                user.Desired_Salary = resultSet.getInt("Desired_Salary");
                user.Location = resultSet.getString("Location");
                user.Skills = resultSet.getString("Skills").split(","); //split the skills into an array
            }
            resultSet.close();
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        testingPassingUserData testingPassingUserData = new testingPassingUserData();
        User user = testingPassingUserData.user;
        System.out.println(user.FirstName + " " + user.LastName);
        System.out.println(user.Email);
        System.out.println(user.Password);
        System.out.println(user.Phone);
        System.out.println(user.Desired_Salary);
        System.out.println(user.Location);
        for(int i = 0; i < user.Skills.length; i++){
            System.out.println("Job Skills " + (i+1) + ": " + user.Skills[i]);
        }
    }
}
