/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.user;

import config.Connect;
import service.EmaiilService;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ahmad
 */
@WebService(serviceName = "User")
public class User {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addUser")
    public String addUser(@WebParam(name = "NIM") String NIM, @WebParam(name = "nama") String nama, @WebParam(name = "email") String email) {
        //TODO write your implementation code here:
        String sql = "INSERT INTO user (NIM, nama, email, status) VALUES (?, ?, ?, ?)";
        try(
                PreparedStatement st = Connect.getConnect().prepareStatement(sql);
                ){
            st.setString(1, NIM);
            st.setString(2, nama);
            st.setString(3, email);
            st.setString(4, "BELUM SELESAI");

            st.executeUpdate();

            return "Berhasil Menambahkan User";
        }catch (SQLException exception){
            exception.printStackTrace();
            return "Gagal Menambahkan User";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "editStatusMhs")
    public String editStatusMhs(@WebParam(name = "NIM") String NIM, @WebParam(name = "status") String status) {
        //TODO write your implementation code here:
        String sql = "UPDATE user " +
                "SET status = ? " +
                "WHERE NIM = ?";

        String sql2 = "SELECT email FROM user WHERE NIM = ?";

        try(
                PreparedStatement st = Connect.getConnect().prepareStatement(sql);
                PreparedStatement st2 = Connect.getConnect().prepareStatement(sql2)
                ){
            st2.setString(1, NIM);
            String email = null;
            try(
                    ResultSet set = st2.executeQuery()
                    ){
                while(set.next()){
                    email = set.getString("email");
                }
            }
            st.setString(1, status);
            st.setString(2, NIM);



            st.executeUpdate();

            String object = "Perubahan Status Tugas Akhir";
            String body = "Status Anda Menjadi " + status;

            EmaiilService.sendEmail(email, object, body);

            return "Berhasil Mengedit Status Menjadi " + status;
        }catch (SQLException exception){
            exception.printStackTrace();
            return "Gagal Mengedit User";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "deleteUser")
    public String deleteUser(@WebParam(name = "NIM") String NIM) {
        //TODO write your implementation code here:
        String sql = "DELETE FROM user WHERE NIM = ?";

        try(
                PreparedStatement st = Connect.getConnect().prepareStatement(sql)
                ){
            st.setString(1, NIM);

            st.executeUpdate();

            return "Berhasil Menghapus";
        }catch (SQLException exception){
            exception.printStackTrace();
            return "Berhasil Menghapsu";
        }
    }

    
    
}
