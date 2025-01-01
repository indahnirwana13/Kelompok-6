/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.login;

import config.Connect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import service.EmaiilService;

/**
 *
 * @author ahmad
 */
@WebService(serviceName = "Login")
public class Login {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "login")
    public String login(@WebParam(name = "NIM") String NIM, @WebParam(name = "password") String password) {
        //TODO write your implementation code here:
        String sql = "SELECT email FROM user WHERE NIM = ?";
        try(
                PreparedStatement st = Connect.getConnect().prepareStatement(sql);
                ){
            st.setString(1, NIM);
            String email = null;
            try(
                    ResultSet rs = st.executeQuery()
                    ){
                while(rs.next()){
                    email = rs.getString("email");
                }
            }
            
            int kodVerifikasi = (int)  (Math.random() * 100_001);
            
            String subject = "Authentication Login"; 
            String body = "Kode Referal Anda adalah : " + kodVerifikasi; 
            
            EmaiilService.sendEmail(email, subject, body);
            
            return "Berhasil Login";
            
        }catch (SQLException exception){
            exception.printStackTrace();
            return "Gagal Login";
        }
    }
}
