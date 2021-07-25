
package switchboard.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static switchboard.database.Database.getConnection;
import switchboard.models.Extension;


public class ExtensionDB extends Database {
    
    
    public static String lastExtensionOfDepartment(long deptId) {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT code FROM %s WHERE department_id = ? ORDER BY code DESC", Extension.TABLE)
            )) {
            
            pstmt.setLong(1, deptId);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                System.out.println(result.getString("code")+" =====");
                return result.getString("code");
            }
            
            return "";
            
        } catch (SQLException ex) {
            Logger.getLogger(ExtensionDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
    
    public static long findIdByDepartment(long deptId) {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT id FROM %s WHERE department_id = ?", Extension.TABLE)
            )) {
            
            pstmt.setLong(1, deptId);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getLong("id");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(ExtensionDB.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        
        
    }
    
    
    public static boolean insert(Extension ext) {
        
        String sql = String.format("INSERT INTO %s (department_id, code, secured, pin) "
                + "VALUES (?, ?, ?, ?)", Extension.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, ext.getDepartment().getId());
            pstmt.setString(2, ext.getCode());
            pstmt.setBoolean(3, ext.isSecured());
            pstmt.setString(4, ext.getPin());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Extension not inserted");
            }
            
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) ext.setId(keys.getLong(1));
            
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    
    
}
