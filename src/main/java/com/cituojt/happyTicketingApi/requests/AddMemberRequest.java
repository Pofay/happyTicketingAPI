package com.cituojt.happyTicketingApi.requests;

public class AddMemberRequest {

    private String memberEmail;

    public AddMemberRequest() {

    }

    public AddMemberRequest(String memberEmail) {
        this.setMemberEmail(memberEmail);
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }
}
