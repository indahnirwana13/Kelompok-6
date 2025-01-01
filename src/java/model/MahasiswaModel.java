/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ahmad
 */
public class MahasiswaModel {
    
    private String NIM;
    
    private String nama;
    
    private String email;

    public MahasiswaModel() {
    }

    public MahasiswaModel(String NIM, String nama, String email) {
        this.NIM = NIM;
        this.nama = nama;
        this.email = email;
    }

    public String getNIM() {
        return NIM;
    }

    public void setNIM(String NIM) {
        this.NIM = NIM;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
