package com.derahh.onlinecompiler;

public class PostData {

    private String clientId;
    private String clientSecret;
    private String script;
    private String language;
    private String versionIndex;

    public PostData(String script) {
        this.script = script;
        this.clientId = APIClient.API_ID;
        this.clientSecret = APIClient.API_SECRET;
        this.language = APIClient.LANGUAGE;
        this.versionIndex = APIClient.VERSION_INDEX;
    }
}
