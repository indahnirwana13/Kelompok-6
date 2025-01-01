/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.tugas.akhir;

import config.Connect;
import service.EmaiilService;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 *
 * @author ahmad
 */
@WebService(serviceName = "TugasAkhir")
public class TugasAkhir {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addTugasAkhir")
    public String addTugasAkhir(@WebParam(name = "nama") String nama, @WebParam(name = "persyaratan") String persyaratan) {
        //TODO write your implementation code here:
        String sql = "INSERT INTO tugas_akhir (nama, persyaratan, tenggat_akhir)" +
                " VALUES (?, ?, ?)";

        String sql2 = "SELECT email FROM user";
        try(
                PreparedStatement st = Connect.getConnect().prepareStatement(sql);
                PreparedStatement st2 = Connect.getConnect().prepareStatement(sql2)
                ){

            LocalDate localDate = LocalDate.now().plusMonths(4);
            Date date = Date.valueOf(localDate);
            st.setString(1, nama);
            st.setString(2, persyaratan);
            st.setDate(3, date);
            st.executeUpdate();

            List<String> listEmail = new ArrayList<>();

            try(
                    ResultSet set = st2.executeQuery()
                    ){
                while(set.next()){
                    listEmail.add(set.getString("email"));
                }
            }

            String subject = "Pemberitahun Tugas Akhir";
            String body = "Pendaftaran Tugas Akhir Tahun " + nama +
                    " Telah Dibuka Sampai Dengan " + date.toString();

            listEmail.forEach(val -> {
                EmaiilService.sendEmail(val, subject, body);
            });
            return "Berhasil Menambahkan";
        }catch (SQLException exception){
            exception.printStackTrace();
            return "Gagal Menambahkan";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "editTugasAkhir")
    public String editTugasAkhir(@WebParam(name = "persyaratan") String persyaratan,
            @WebParam(name = "idTugasAkhir") int idTugasAkhir,
            @WebParam(name = "tenggatWaktu") String tenggatWaktu) {
        //TODO write your implementation code here:
        String sql = "UPDATE tugas_akhir " +
                "SET persyaratan = ?, tenggat_akhir = ? " +
                "WHERE id_tugas_akhir = ?";

        String sql2 = "SELECT email FROM user";

        String sql3 = "SELECT nama FROM tugas_akhir";
        try(
                PreparedStatement st = Connect.getConnect().prepareStatement(sql);
                PreparedStatement st2 = Connect.getConnect().prepareStatement(sql2);
                PreparedStatement st3 = Connect.getConnect().prepareStatement(sql3)
                ){
            // Contoh Penginputan yang Benar untuk parameter tenggat_waktu = "2024-12-31"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(tenggatWaktu, formatter);
            Date date = Date.valueOf(localDate);
            st.setString(1, persyaratan);
            st.setDate(2, date);
            st.setInt(3, idTugasAkhir);

            List<String> listEmail = new ArrayList<>();
            String namaTugasAkhir = null;
            try(
                    ResultSet set = st2.executeQuery();
                    ResultSet set1 = st3.executeQuery()
                    ){
                while(set.next()){
                    listEmail.add(set.getString("email"));
                }

                while (set1.next()){
                    namaTugasAkhir = set1.getString("nama");
                }
            }

            st.executeUpdate();

            String subject = "Pemberitahun Tugas Akhir";
            String body = "Pendaftaran Tugas Akhir Tahun " + namaTugasAkhir +
                    " Telah Dibuka Sampai Dengan " + date.toString();

            listEmail.forEach(val -> {
                EmaiilService.sendEmail(val, subject, body);
            });

            return "Berhasil Mengedit";
        }catch (SQLException exception){
            exception.printStackTrace();
            return "Gagal Mengedit Tugas Akhir";
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "deleteTugasAkhir")
    public String deleteTugasAkhir(@WebParam(name = "idTugasAkhir") String idTugasAkhir) {
        //TODO write your implementation code here:
        String sql = "DELETE FROM tugas_akhir WHERE id_tugas_akhir = ?";

        String sql2 = "SELECT email FROM user";

        String sql3 = "SELECT nama FROM tugas_akhir";

        try(
                PreparedStatement st = Connect.getConnect().prepareStatement(sql);
                PreparedStatement st2 = Connect.getConnect().prepareStatement(sql2);
                PreparedStatement st3 = Connect.getConnect().prepareStatement(sql3)
                ){
            st.setString(1, idTugasAkhir);

            List<String> listEmail = new ArrayList<>();
            String namaTugasAkhir = null;
            try(
                    ResultSet set = st2.executeQuery();
                    ResultSet set1 = st3.executeQuery()
            ){
                while(set.next()){
                    listEmail.add(set.getString("email"));
                }

                while (set1.next()){
                    namaTugasAkhir = set1.getString("nama");
                }
            }

            st.executeUpdate();

            String subject = "Pemberitahun Tugas Akhir";
            String body = "Pendaftaran Tugas Akhir Tahun " + namaTugasAkhir +
                    " Telah Dibuka Dihapus";

            listEmail.forEach(val -> {
                EmaiilService.sendEmail(val, subject, body);
            });

            st.executeUpdate();

            return "Berhasil Menghapus";
        }catch (SQLException exception){
            return "Gagal Menghapus";
        }
    }
}
