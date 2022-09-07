package com.shimmita.devopssociety.modal;

public class RetrieveFirebaseCredentialsFromFirestore {
    private String Username,
            University,
            Role,
            Password,
            Passion,
            Occupation,
            Knowledge,
            Gender,
            Email,
            County,
            AdminChatSend,
            AdminChatReceive,
            PhoneNumber,imagePath;


    public RetrieveFirebaseCredentialsFromFirestore() {
    }

    public RetrieveFirebaseCredentialsFromFirestore(String username, String university, String role, String password, String passion, String occupation, String knowledge, String gender, String email, String county, String adminChatSend, String adminChatReceive, String phoneNumber,String imagePath) {
        this.Username = username;
        this.University = university;
        this.Role = role;
        this.Password = password;
        this.Passion = passion;
        this.Occupation = occupation;
        this.Knowledge = knowledge;
        this.Gender = gender;
        this.Email = email;
        this.County = county;
        this.AdminChatSend = adminChatSend;
        this.AdminChatReceive = adminChatReceive;
        this.PhoneNumber = phoneNumber;
        this.imagePath=imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUniversity() {
        return University;
    }

    public void setUniversity(String university) {
        University = university;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPassion() {
        return Passion;
    }

    public void setPassion(String passion) {
        Passion = passion;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getKnowledge() {
        return Knowledge;
    }

    public void setKnowledge(String knowledge) {
        Knowledge = knowledge;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getAdminChatSend() {
        return AdminChatSend;
    }

    public void setAdminChatSend(String adminChatSend) {
        AdminChatSend = adminChatSend;
    }

    public String getAdminChatReceive() {
        return AdminChatReceive;
    }

    public void setAdminChatReceive(String adminChatReceive) {
        AdminChatReceive = adminChatReceive;
    }
}
