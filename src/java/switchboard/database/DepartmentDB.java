
package switchboard.database;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import switchboard.models.Department;
import switchboard.models.Extension;


public class DepartmentDB extends Database {
    
    
    public static int countAll() {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT COUNT(id) AS count FROM %s", Department.TABLE)
            )) {
            
            
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
    
    public static List<Department> findAll() {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT * FROM %s  ORDER BY name ASC", Department.TABLE)
            )) {
            
            ResultSet result = pstmt.executeQuery();
            
            List<Department> list = new ArrayList<>();
            
            while (result.next()) {
                list.add(form(result));
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public static List<Department> findAll(int limit, int page) {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT * FROM %s  ORDER BY name ASC LIMIT ?, ?", Department.TABLE)
            )) {
            
            pstmt.setInt(1, page);
            pstmt.setInt(2, limit);
            
            ResultSet result = pstmt.executeQuery();
            
            List<Department> list = new ArrayList<>();
            
            while (result.next()) {
                list.add(form(result));
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public static Department find(long id) {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT * FROM %s WHERE id = ?", Department.TABLE)
            )) {
            
            pstmt.setLong(1, id);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return form(result);
            }
            
            return new Department();
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public static Department form(ResultSet result) throws SQLException {
        Department dept = new Department();
        dept.setId(result.getLong("id"));
        dept.setName(result.getString("name"));
        dept.setPrefix(result.getString("prefix"));
        dept.setMinor(result.getBoolean("minor"));
        dept.setCreatedAt(((Timestamp)result.getObject("created_at")).toLocalDateTime());
        return dept;
    }
    
    
    public static long findIdExists(long id) {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT id FROM %s WHERE id = ?", Department.TABLE)
            )) {
            
             pstmt.setLong(1, id);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getLong("id");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
         
    }
    
    public static long findIdByName(String name) {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT id FROM %s WHERE name = ?", Department.TABLE)
            )) {
            
             pstmt.setString(1, name);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getLong("id");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
         
    }

    public static long findIdByPrefix(String name) {
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(
                String.format("SELECT id FROM %s WHERE prefix = ?", Department.TABLE)
            )) {
            
            pstmt.setString(1, name);
            
            ResultSet result = pstmt.executeQuery();
            
            if (result.next()) {
                return result.getLong("id");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    
    public static boolean insert(Department dept) {
        
        String sql = String.format("INSERT INTO %s (name, prefix, minor) VALUES (?, ?, ?)", Department.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            getConnection().setAutoCommit(false);
            
            pstmt.setString(1, dept.getName());
            pstmt.setString(2, dept.getPrefix());
            pstmt.setBoolean(3, dept.isMinor());
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Department not inserted");
            }
            
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) dept.setId(keys.getLong(1));
            
            
            if (dept.isMinor()) {
                Extension ext = new Extension();
                ext.setDepartment(dept);
                ext.setCode(dept.getPrefix());
                if (!ExtensionDB.insert(ext)) {
                    throw new SQLException("Extension not inserted");
                }
            }
            
            getConnection().commit();
            
            return true;
            
        } catch (SQLException ex) {
            
            try {
                getConnection().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
            Logger.getLogger(DepartmentDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    
    
    
    
}



