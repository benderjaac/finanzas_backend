package com.primeng.primeng.util;

public enum Type {

    USUARIO("Usuario", "Id Usuario"),
    ;

    private String display;
    private String idname;

    Type(String display, String idname) {
        this.display = display;
        this.idname = idname;
    }

    public String idName() {
        return idname;
    }

    @Override
    public String toString() {
        return this.display;
    }

}