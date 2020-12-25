package com.gateway.restapi.model;

public class User {

//    personal
    private String no_ktp;
    private String email;
    private String nama;
    private String education;
    private String marital;
    private String address;
    private String province;
    private String city;
    private String district;
    private String sub_district;
    private String rt;
    private String rw;
    private String living_status;

//    relative
    private String mother_name;
    private String relevan_name;
    private String relationship;
    private String no_hp_relevan;
    private String relevan_address;

//    work
    private String npwp;
    private int income;
    private String income_src;
    private String work_type;
    private String work_office;
    private String work_status;



    public User() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getRelevan_name() {
        return relevan_name;
    }

    public void setRelevan_name(String relevan_name) {
        this.relevan_name = relevan_name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getNo_hp_relevan() {
        return no_hp_relevan;
    }

    public void setNo_hp_relevan(String no_hp_relevan) {
        this.no_hp_relevan = no_hp_relevan;
    }

    public String getRelevan_address() {
        return relevan_address;
    }

    public void setRelevan_address(String relevan_address) {
        this.relevan_address = relevan_address;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getIncome_src() {
        return income_src;
    }

    public void setIncome_src(String income_src) {
        this.income_src = income_src;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getWork_office() {
        return work_office;
    }

    public void setWork_office(String work_office) {
        this.work_office = work_office;
    }

    public String getWork_status() {
        return work_status;
    }

    public void setWork_status(String work_status) {
        this.work_status = work_status;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSub_district() {
        return sub_district;
    }

    public void setSub_district(String sub_district) {
        this.sub_district = sub_district;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getLiving_status() {
        return living_status;
    }

    public void setLiving_status(String living_status) {
        this.living_status = living_status;
    }
}
