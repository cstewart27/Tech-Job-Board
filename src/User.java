import java.sql.*;

public class User {
    private static int currentUserID = 1;
    public int CandidateID;
    public String FirstName;
    public String LastName;
    public String Email;
    public String Password;
    public String Phone;
    public int Desired_Salary;
    public String Location;
    public String [] Skills = new String[8];


    public static User RetrieveData(int userID){
        User user = null;

        try{
            Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
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
                String [] tempArray = resultSet.getString("Skills").split(","); //split the skills into an array

                for(int i = 0; i < tempArray.length; i++){
                    user.Skills[i] = tempArray[i];
                }
                for(int i = 0; i< user.Skills.length; i++){
                    if(user.Skills[i] == null){
                        user.Skills[i] = "Empty";
                    }
                }

            }
            resultSet.close();
            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    //getter for currentUserID
    public static int getCurrentUserID() {
        return currentUserID;
    }
    //setter for currentUserID
    public static int setCurrentUserID(int userID) {
        return User.currentUserID = userID;
    }

}
