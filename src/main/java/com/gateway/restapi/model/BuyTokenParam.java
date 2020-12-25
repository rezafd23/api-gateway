package com.gateway.restapi.model;

public class BuyTokenParam {
    private int nominal;
    private int id_nasabah_card;
    private String nama_transaksi;
    private String no_pelanggan;
    private int id_voucer;

    public BuyTokenParam() {
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public int getId_nasabah_card() {
        return id_nasabah_card;
    }

    public void setId_nasabah_card(int id_nasabah_card) {
        this.id_nasabah_card = id_nasabah_card;
    }

    public String getNama_transaksi() {
        return nama_transaksi;
    }

    public void setNama_transaksi(String nama_transaksi) {
        this.nama_transaksi = nama_transaksi;
    }

    public String getNo_pelanggan() {
        return no_pelanggan;
    }

    public void setNo_pelanggan(String no_pelanggan) {
        this.no_pelanggan = no_pelanggan;
    }

    public int getId_voucer() {
        return id_voucer;
    }

    public void setId_voucer(int id_voucer) {
        this.id_voucer = id_voucer;
    }
}
