package com.espressif.esptouch.android;

public class Device {
    public String nombre;
    public String estado;
    public String enLinea;
    public String CHIP_ID;
    public String IP;
    public String fechaLastIp;


    public Device(String nombre, String estado, String enLinea, String chipId, String ip, String fechaLastIp) {
        this.nombre = nombre;
        this.estado = estado;
        this.enLinea = enLinea;
        this.CHIP_ID = chipId;
        this.IP = ip;
        this.fechaLastIp = fechaLastIp;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEnLinea() {
        return enLinea;
    }

    public void setEnLinea(String enLinea) {
        this.enLinea = enLinea;
    }

    public String getCHIP_ID() {
        return CHIP_ID;
    }

    public void setCHIP_ID(String chipId) {
        this.CHIP_ID = chipId;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String ip) {
        this.IP = ip;
    }

    public String getFechaLastIp() {
        return fechaLastIp;
    }

    public void setFechaLastIp(String fechaLastIp) {
        this.fechaLastIp = fechaLastIp;
    }
}
