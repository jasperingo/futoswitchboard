
package switchboard.database;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import switchboard.models.Department;
import switchboard.models.Extension;
import switchboard.models.Staff;
import switchboard.models.StaffExtension;


public class StaffDB extends Database {
    
    
    public static long findIdByPhoneNumber(String phone) {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT id FROM %s WHERE phone_number = ?", Staff.TABLE)
            )) {
            
            pstmt.setString(1, phone);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getLong("id");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(StaffDB.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
         
    }
    
    
    public static boolean insert(Staff staff, Extension ext) {
        
        String sql = String.format("INSERT INTO %s (first_name, last_name, phone_number, gender) "
                + "VALUES (?, ?, ?, ?)", Staff.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            getConnection().setAutoCommit(false);
            
            pstmt.setString(1, staff.getFirstName());
            pstmt.setString(2, staff.getLastName());
            pstmt.setString(3, staff.getPhoneNumber());
            pstmt.setString(4, staff.getGender());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Department not inserted");
            }
            
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) staff.setId(keys.getLong(1));
            
            if (!ExtensionDB.insert(ext)) {
                throw new SQLException("Extension not inserted");
            }
            
            StaffExtension se = new StaffExtension();
            se.setStaff(staff);
            se.setExtension(ext);
            
            if (!insertStaffExtension(se)) {
                throw new SQLException("Staff Extension not inserted");
            }
            
            getConnection().commit();
            
            return true;
            
        } catch (SQLException ex) {
            
            try {
                getConnection().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(StaffDB.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public static boolean insert(Staff staff, long extId) {
        
        String sql = String.format("INSERT INTO %s (first_name, last_name, phone_number, gender) "
                + "VALUES (?, ?, ?, ?)", Staff.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            getConnection().setAutoCommit(false);
            
            pstmt.setString(1, staff.getFirstName());
            pstmt.setString(2, staff.getLastName());
            pstmt.setString(3, staff.getPhoneNumber());
            pstmt.setString(4, staff.getGender());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Department not inserted");
            }
            
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) staff.setId(keys.getLong(1));
            
            Extension ext = new Extension();
            ext.setId(extId);
            
            StaffExtension se = new StaffExtension();
            se.setStaff(staff);
            se.setExtension(ext);
            
            if (!insertStaffExtension(se)) {
                throw new SQLException("Staff Extension not inserted");
            }
            
            getConnection().commit();
            
            return true;
            
        } catch (SQLException ex) {
            
            try {
                getConnection().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(StaffDB.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    
    public static boolean insertStaffExtension(StaffExtension se) {
        
        String sql = String.format("INSERT INTO %s (staff_id, extension_id) "
                + "VALUES (?, ?)", StaffExtension.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, se.getStaff().getId());
            pstmt.setLong(2, se.getExtension().getId());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Staff Extension not inserted");
            }
            
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) se.setId(keys.getLong(1));
            
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    
    public static int countAllByDepartment(long id) {
        
        String sql = String.format("SELECT COUNT(staff.id) AS count "
                + "FROM %s INNER JOIN %s "
                + "ON department.id = extension.department_id "
                + "INNER JOIN %s "
                + "ON extension.id = staff_extension.extension_id "
                + "INNER JOIN %s "
                + "ON staff_extension.staff_id = staff.id "
                + "WHERE department.id = ?", 
                
                Department.TABLE, Extension.TABLE, StaffExtension.TABLE, Staff.TABLE
        );
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getInt("count");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    
    public static List<Staff> findAllByDepartment(long id, int limit, int page) {
        
        String sql = String.format("SELECT staff.first_name, staff.last_name, staff.id "
                + "FROM %s INNER JOIN %s "
                + "ON department.id = extension.department_id "
                + "INNER JOIN %s "
                + "ON extension.id = staff_extension.extension_id "
                + "INNER JOIN %s "
                + "ON staff_extension.staff_id = staff.id "
                + "WHERE department.id = ? ORDER BY staff.created_at DESC LIMIT ?, ?", 
                
                Department.TABLE, Extension.TABLE, StaffExtension.TABLE, Staff.TABLE
        );
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            pstmt.setInt(2, page);
            pstmt.setInt(3, limit);
            
            ResultSet result = pstmt.executeQuery();
            
            List<Staff> list = new ArrayList<>();
            
            while (result.next()) {
                list.add(formList(result));
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public static Staff formList(ResultSet result) throws SQLException {
        Staff s = new Staff();
        s.setId(result.getLong("id"));
        s.setFirstName(result.getString("first_name"));
        s.setLastName(result.getString("last_name"));
        return s;
    }
    
    
    public static Staff form(ResultSet result) throws SQLException {
        Staff s = new Staff();
        s.setId(result.getLong("id"));
        s.setFirstName(result.getString("first_name"));
        s.setLastName(result.getString("last_name"));
        s.setPhoneNumber(result.getString("phone_number"));
        s.setGender(result.getString("gender"));
        
        Department department = new Department();
        department.setId(result.getLong("did"));
        department.setName(result.getString("name"));
        
        Extension extension = new Extension();
        extension.setCode(result.getString("code"));
        extension.setSecured(result.getBoolean("secured"));
        extension.setDepartment(department);
        
        StaffExtension se = new StaffExtension();
        se.setExtension(extension);
        
        s.setStaffExtension(se);
        
        return s;
    }
    
    
    public static Staff find(long id) {
        
        String sql = String.format(
                "SELECT staff.first_name, staff.last_name, staff.id, "
                + "staff.phone_number, staff.gender, "
                + "department.name, department.id AS did, extension.code, extension.secured "
                + "FROM %s INNER JOIN %s "
                + "ON staff_extension.staff_id = staff.id "
                + "INNER JOIN %s "
                + "ON extension.id = staff_extension.extension_id "
                + "INNER JOIN %s "
                + "ON department.id = extension.department_id "
                + "WHERE staff.id = ?", 
                
                Staff.TABLE, StaffExtension.TABLE, Extension.TABLE, Department.TABLE
        );
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return form(result);
            }
            
            return new Staff();
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
}


