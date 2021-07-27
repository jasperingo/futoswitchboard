
package switchboard.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static switchboard.database.Database.getConnection;
import switchboard.models.CallReport;
import switchboard.models.Department;
import switchboard.models.Extension;
import switchboard.models.Staff;
import switchboard.models.StaffExtension;


public class CallReportDB extends Database {
    
    
    public static boolean insert(CallReport report) {
        
        String sql = String.format("INSERT INTO %s ("
                + "caller_number, "
                + "session_id, "
                + "staff_extension_id, "
                + "status)"
                //+ "started_at) "
                + "VALUES (?, ?, ?, ?)", CallReport.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, report.getCallerNumber());
            pstmt.setString(2, report.getSessionId());
            pstmt.setLong(3, report.getStaffExtension().getId());
            pstmt.setString(4, report.getStatus());
            //pstmt.setObject(5, report.getStartedAt());
                                    
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                throw new SQLException("Call report not inserted");
            }
            
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(CallReportDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public static CallReport findBySessionId(String sessionId) {
       
        String query = String.format(
                "SELECT call_report.id AS call_id, staff.phone_number, staff_extension.id, "
                + "extension.code, extension.pin "
                + "FROM %s INNER JOIN %s "
                + "ON call_report.staff_extension_id = staff_extension.id "
                + "INNER JOIN %s "
                + "ON staff.id = staff_extension.staff_id "
                + "INNER JOIN %s "
                + "ON extension.id = staff_extension.extension_id "
                + "WHERE call_report.session_id = ?", 
                
                CallReport.TABLE, StaffExtension.TABLE, Staff.TABLE, Extension.TABLE);
        
        try(PreparedStatement pstmt = getConnection().prepareStatement(query)){
            
            pstmt.setString(1, sessionId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()){
                
                Extension ext = new Extension();
                ext.setCode(rs.getString("code"));
                ext.setPin(rs.getString("pin"));
                
                Staff s = new Staff();
                s.setPhoneNumber(rs.getString("phone_number"));

                StaffExtension se = new StaffExtension();
                se.setId(rs.getLong("id"));
                se.setStaff(s);
                se.setExtension(ext);
                
                CallReport report = new CallReport();
                report.setId(rs.getLong("call_id"));
                report.setStaffExtension(se);
                
                return report;
            }
            
            return new CallReport();
            
        } catch (SQLException ex) {
            Logger.getLogger(CallReportDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    
    public static int updateStatus(String status, long id) {
        
        String sql = String.format("UPDATE %s SET status = ? WHERE id = ?", CallReport.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setLong(2, id);
                                    
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                return 0;
            }
            
            return 1;
            
        } catch (SQLException ex) {
            Logger.getLogger(CallReportDB.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        
    }
    
    
    public static int updateStatusWhenCallEnds(String recordUrl, int duration, double cost, String sessionId) {
        
        String sql = String.format("UPDATE %s SET status = ?, record_url = ?, duration = ?, cost = ?"
                + " WHERE session_id = ?", CallReport.TABLE);
        
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, CallReport.STATUS_ENDED);
            pstmt.setString(2, recordUrl);
            pstmt.setInt(3, duration);
            pstmt.setDouble(4, cost);
            pstmt.setString(5, sessionId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows < 1) {
                return 0;
            }
            
            return 1;
            
        } catch (SQLException ex) {
            Logger.getLogger(CallReportDB.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        
    }
    
    
    public static List<CallReport> findAll(int limit, int start) {
        
        String query = String.format(
                "SELECT call_report.id, call_report.caller_number, call_report.status, call_report.session_id, "
                + "call_report.started_at, call_report.duration, call_report.cost, call_report.record_url, "
                + "staff.first_name, staff.last_name, staff.phone_number, department.name, "
                + "extension.code "
                + "FROM %s INNER JOIN %s "
                + "ON call_report.staff_extension_id = staff_extension.id "
                + "INNER JOIN %s "
                + "ON staff.id = staff_extension.staff_id "
                + "INNER JOIN %s "
                + "ON extension.id = staff_extension.extension_id "
                + "INNER JOIN %s "
                + "ON extension.department_id = department.id "
                + "ORDER BY call_report.created_at DESC LIMIT ?, ?", 
                
                CallReport.TABLE, StaffExtension.TABLE, Staff.TABLE, Extension.TABLE, Department.TABLE);
        
        try(PreparedStatement pstmt = getConnection().prepareStatement(query)){
            
            pstmt.setInt(1, start);
            pstmt.setInt(2, limit);
            
            ResultSet rs = pstmt.executeQuery();
            
            List<CallReport> list= new ArrayList<>();
            
            while (rs.next()) {
                
                Department dept = new Department();
                dept.setName(rs.getString("name"));
                
                Extension ext = new Extension();
                ext.setCode(rs.getString("code"));
                ext.setDepartment(dept);
                
                Staff s = new Staff();
                s.setLastName(rs.getString("last_name"));
                s.setFirstName(rs.getString("first_name"));
                s.setPhoneNumber(rs.getString("phone_number"));
                
                StaffExtension se = new StaffExtension();
                se.setStaff(s);
                se.setExtension(ext);
                
                CallReport report = new CallReport();
                report.setId(rs.getLong("id"));
                report.setCost(rs.getDouble("cost"));
                report.setStatus(rs.getString("status"));
                report.setDuration(rs.getInt("duration"));
                report.setRecordUrl(rs.getString("record_url"));
                //report.setStartedAt(rs.getObject("started_at"));
                report.setCallerNumber(rs.getString("caller_number"));
                report.setSessionId(rs.getString("session_id"));
                report.setStaffExtension(se);
                
                list.add(report);
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(CallReportDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static int countAll() {
        
        String query = String.format("SELECT COUNT(id) AS count FROM %s ", CallReport.TABLE);
        
        try(PreparedStatement pstmt = getConnection().prepareStatement(query)){
            
            ResultSet rs = pstmt.executeQuery();
            
           
            if (rs.next()) {
               return rs.getInt("count");
            }
            
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(CallReportDB.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    
}


