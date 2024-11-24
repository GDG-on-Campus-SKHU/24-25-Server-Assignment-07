package bomin.googlelogin.domain;

public enum Role {
    //USER는 Role_USER라는 키를 가지고, ADMIN은 ROLE_ADMIN이라는 키를 가진다.
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");
    private final String key;

    Role(String userkey){
        this.key=userkey;
    }
}
