
package switchboard.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import switchboard.models.Administrator;


public class AdministratorDB extends Database {
    
    
    public static Administrator findByPhoneNumber(String phoneNumber) {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT * FROM %s WHERE phone_number = ?", Administrator.TABLE)
            )) {
            
            pstmt.setString(1, phoneNumber);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return form(result);
            } else {
                return new Administrator();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdministratorDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static Administrator form(ResultSet result) throws SQLException {
        Administrator user = new Administrator();
        user.setId(result.getLong("id"));
        user.setFirstName(result.getString("first_name"));
        user.setLastName(result.getString("last_name"));
        user.setPhoneNumber(result.getString("phone_number"));
        user.setPassword(result.getString("password"));
        user.setCreatedAt(((Timestamp)result.getObject("created_at")).toLocalDateTime());
        return user;
    }
    
    
    
    
}

