import java.sql.*;

public class Company {

    private static int currentCompanyID = 1;
    public int CompanyID;
    public String Name;
    public String Email;
    public String Password;
    public String Phone;
    public String Description;
    public String Location;

    public static Company RetrieveData(int companyID){
        Company company = null;

        try {
            Connection conn = DriverManager.getConnection(DatabaseConnectionManager.url, DatabaseConnectionManager.usernameToDatabase, DatabaseConnectionManager.passwordToDatabase);
            Statement stmt = conn.createStatement();
            String sql = "select * from Companies where CompanyID=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, companyID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                company = new Company();
                company.CompanyID = resultSet.getInt("CompanyID");
                company.Name = resultSet.getString("Name");
                company.Email = resultSet.getString("Email");
                company.Password = resultSet.getString("Password");
                company.Phone = resultSet.getString("Phone");
                company.Description = resultSet.getString("Description");
                company.Location = resultSet.getString("Location");
            }
            resultSet.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return company;
    }

    //getter for currentCompanyID
    public static int getCurrentCompanyID() {
        return currentCompanyID;
    }

    //setter for currentCompanyID
    public static int setCurrentCompanyID(int companyID) {
        return Company.currentCompanyID = companyID;
    }
}
