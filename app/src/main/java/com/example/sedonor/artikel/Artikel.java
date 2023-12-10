package com.example.sedonor.artikel;

import android.widget.ImageView;

public class Artikel {
    String judul, konten, imageUrl;
    public Artikel() {
        // Diperlukan untuk deserialisasi Firestore
    }
    public Artikel(String judul, String konten, String imageUrl) {
        this.judul = judul;
        this.konten = konten;
        this.imageUrl = imageUrl;
    }

    public String getJudul() {
        return judul;
    }

    public String getKonten() {
        return konten;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @Override
    public String toString() {
        return "Artikel{" +
                "judul='" + judul + '\'' +
                ", konten='" + konten + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
