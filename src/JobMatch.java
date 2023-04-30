import java.sql.*;

public class JobMatch {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public JobMatch() {
        // Initialize database connection
        try {
            conn = DriverManager.getConnection("jdbc:mysql80://localhost:3306/JobListingDatabase", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void matchAndStore() {
        try {
            // Get all candidates
            String candidateQuery = "SELECT * FROM Candidate";
            ps = conn.prepareStatement(candidateQuery);
            rs = ps.executeQuery();

            while (rs.next()) {
                int candidateId = rs.getInt("ID");
                String candidateName = rs.getString("name");
                String[] candidateSkills = rs.getString("skills").split(",");

                // Get all jobs
                String jobQuery = "SELECT * FROM JobListing";
                ps = conn.prepareStatement(jobQuery);
                ResultSet jobResultSet = ps.executeQuery();

                while (jobResultSet.next()) {
                    int jobId = jobResultSet.getInt("JobID");
                    String[] jobSkills = jobResultSet.getString("JobSkills").split(",");

                    // Calculate match score
                    int matchScore = 0;
                    for (String candidateSkill : candidateSkills) {
                        for (String jobSkill : jobSkills) {
                            if (candidateSkill.equalsIgnoreCase(jobSkill)) {
                                matchScore++;
                            }
                        }
                    }

                    // Store match data
                    String insertQuery = "INSERT INTO Match (CandidateID, JobID, MatchScore) VALUES (?, ?, ?)";
                    ps = conn.prepareStatement(insertQuery);
                    ps.setInt(1, candidateId);
                    ps.setInt(2, jobId);
                    ps.setInt(3, matchScore);
                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showJobs(int candidateId) {
        try {
            // Get matched jobs for a candidate
            String query = "SELECT * FROM Match WHERE CandidateID = ? ORDER BY MatchScore DESC";
            ps = conn.prepareStatement(query);
            ps.setInt(1, candidateId);
            rs = ps.executeQuery();

            // Display matched jobs in UI
            while (rs.next()) {
                int jobId = rs.getInt("JobID");
                int matchScore = rs.getInt("MatchScore");

                String jobQuery = "SELECT * FROM JobListing WHERE JobID = ?";
                PreparedStatement jobPs = conn.prepareStatement(jobQuery);
                jobPs.setInt(1, jobId);
                ResultSet jobRs = jobPs.executeQuery();

                if (jobRs.next()) {
                    String jobTitle = jobRs.getString("JobTitle");
                    // Display job in UI
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}